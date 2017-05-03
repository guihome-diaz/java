package eu.daxiongmao.prv.wordpress.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpService.class);

    private FTPClient ftpClient;

    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList(".png", ".jpg", ".jpeg", ".gif");
    private static final List<String> VIDEO_EXTENSIONS = Arrays.asList(".mov", ".mp4", ".m4v");
    private static final String IMAGE_BACKUP_ENDING = "_backup";

    private final ExecutorService downloadThreadPools = Executors.newFixedThreadPool(5);

    /**
     * Default constructor.<br>
     * This will use <strong>5 download</strong> threads.
     */
    public FtpService() {
    }

    /**
     * To establish connection to FTP server.
     *
     * @param host
     *            URL to FTP host
     * @param port
     *            FTP port
     * @param user
     *            FTP username
     * @param pwd
     *            FTP password
     * @return boolean - "true" if the connection was successfully established ; "false" in case of failure.
     */
    public boolean connect(final String host, final int port, final String user, final String pwd) {
        ftpClient = new FTPClient();

        try {
            // Connection to the server
            ftpClient.connect(host, port);
            final int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                LOGGER.error(String.format("Cannot connect to FTP server '%s' # error code: %s", host, reply));
                return false;
            }

            // Authentication
            if (!ftpClient.login(user, pwd)) {
                LOGGER.error("login failed");
                return false;
            }

            // Set file transfer type to binary
            // This is mandatory if you plan to deal with non-texted document or texted written in Chinese
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            // Connection OK: enter passive mode
            ftpClient.enterLocalPassiveMode();
            return true;
        } catch (final IOException e) {
            LOGGER.error("Failed to connect to FTP server", e);
            // disconnect on error
            disconnect();
            return false;
        }
    }

    public void waitForAllDownloadsThenDisconnect() {
        // No more download jobs will be accepted
        downloadThreadPools.shutdown();
        // Wait for completion. !! Give enough time for slow connections !!
        try {
            downloadThreadPools.awaitTermination(300, TimeUnit.SECONDS);
        } catch (final InterruptedException e) {
            LOGGER.error("Failed to wait for all some downloads. Operation was interrupted", e);
        }

        disconnect();
    }

    /**
     * To close the FTP connection.
     */
    public void disconnect() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (final IOException e) {
                LOGGER.error("Failed to close connection with FTP server", e);
            }
        }
    }

    /**
     * To list the content of a remote directory.
     *
     * @param parentDir
     *            parent directory - to start with this must be the RELATIVE path
     * @param currentDir
     *            directory to list (optional)
     * @param level
     *            current level
     * @param maxLevel
     *            maximum level to go into (max recursion)
     * @return list of files
     * @throws IOException
     *             some errors occurred
     */
    public List<String> listDirectoryContent(final String parentDir, final String currentDir, final int level, final int maxLevel) throws IOException {
        final List<String> files = new ArrayList<>();

        // Build remote path
        String dirToList = "";
        if (parentDir != null) {
            dirToList = parentDir;
            if (currentDir != null && !currentDir.trim().isEmpty()) {
                if (!dirToList.endsWith("/")) {
                    dirToList += "/";
                }
                dirToList += currentDir;
            }
        } else {
            dirToList = currentDir;
        }

        // Server files
        final FTPFile[] subFiles = ftpClient.listFiles(dirToList);
        files.add(dirToList);
        if (subFiles != null && subFiles.length > 0) {
            for (final FTPFile aFile : subFiles) {
                final String currentFileName = aFile.getName();

                // skip parent directory and directory itself
                if (currentFileName.equals(".") || currentFileName.equals("..")) {
                    continue;
                }

                // recursive call for sub-directories (as long as the maxLevel is not reached)
                if (aFile.isDirectory() && (level + 1 < maxLevel)) {
                    files.addAll(this.listDirectoryContent(dirToList, currentFileName, level + 1, maxLevel));
                }

                // File handling
                if (aFile.isFile()) {
                    files.add(currentFileName);
                }
            }
        }
        return files;
    }

    /**
     * To list Wordpress's gallery plugins directories.
     *
     * @param wordpressRoot
     *            wordpress root (RELATIVE path)
     * @return list of galleries
     * @throws IOException
     *             some errors occurred
     */
    public List<String> listGalleryDirectories(final String wordpressRoot) throws IOException {
        final List<String> galleries = new ArrayList<>();

        // argument check
        if (wordpressRoot == null || wordpressRoot.isEmpty()) {
            LOGGER.error("No wordpress root provided! Cannot list galleries!");
            return galleries;
        }

        // Build remote path
        String dirToList = wordpressRoot;
        if (!dirToList.endsWith("/")) {
            dirToList += "/";
        }
        dirToList += "wp-content/gallery";

        // Server files
        final FTPFile[] subFiles = ftpClient.listFiles(dirToList);
        if (subFiles != null && subFiles.length > 0) {
            for (final FTPFile aFile : subFiles) {
                final String currentFileName = aFile.getName();

                // skip parent directory and directory itself
                if (currentFileName.equals(".") || currentFileName.equals("..")) {
                    continue;
                }

                // Each gallery should be its own directory
                if (aFile.isDirectory()) {
                    galleries.add(currentFileName);
                }
            }
        }

        final StringBuilder log = new StringBuilder("\n");
        galleries.forEach(item -> log.append("   * ").append(item).append("\n"));
        LOGGER.info(String.format("Found %s galleries: %s", galleries.size(), log.toString()));
        return galleries;
    }

    public void downloadGalleryContent(final String wordpressRoot, final String ftpGalleryName, final Path localBackupFolder) throws IOException {
        final Map<String, FTPFile> galleryContent = new HashMap<>();

        // Build remote path
        final String dirName = (wordpressRoot.endsWith("/") ? wordpressRoot : wordpressRoot + "/") + "wp-content/gallery/" + ftpGalleryName;

        // Get list of files
        final FTPFile[] subFiles = ftpClient.listFiles(dirName);
        if (subFiles != null && subFiles.length > 0) {
            for (final FTPFile aFile : subFiles) {
                final String currentFileName = aFile.getName();
                // skip parent directory and directory itself
                if (currentFileName.equals(".") || currentFileName.equals("..")) {
                    continue;
                }

                // extract files
                if (aFile.isFile()) {
                    String fileName = currentFileName;
                    // Remove backup extension
                    if (currentFileName.endsWith(FtpService.IMAGE_BACKUP_ENDING)) {
                        fileName = currentFileName.substring(0, currentFileName.indexOf(FtpService.IMAGE_BACKUP_ENDING));
                    }
                    // keep the high resolution (bigger size)
                    if (!galleryContent.containsKey(fileName) || galleryContent.get(fileName).getSize() < aFile.getSize()) {
                        galleryContent.put(fileName, aFile);
                    }
                }
            }
        }

        // download files
        if (!galleryContent.isEmpty()) {
            final Path backupDir = Paths.get(localBackupFolder.toString(), "gallery", ftpGalleryName);
            Files.createDirectories(backupDir);
            LOGGER.info(String.format("Downloading gallery '%s' ... Please wait", ftpGalleryName));
            galleryContent.forEach((name, ftpFile) -> {
                // Remove backup extension
                String backupFilename = ftpFile.getName();
                if (backupFilename.endsWith(FtpService.IMAGE_BACKUP_ENDING)) {
                    backupFilename = backupFilename.substring(0, backupFilename.indexOf(FtpService.IMAGE_BACKUP_ENDING));
                }
                downloadFile(dirName + "/" + name, Paths.get(backupDir.toString(), backupFilename));
            });
            LOGGER.info(String.format("Gallery '%s' has been download! %s files saved to: %s", ftpGalleryName, galleryContent.size(),
                    Paths.get(localBackupFolder.toString(), "gallery")));
        }
    }

    /**
     * To download a file from the FTP server
     *
     * @param ftpFile
     * @param localBackupFile
     * @return
     */
    public boolean downloadFile(final String ftpFile, final Path localBackupFile) {
        boolean success = false;
        try (FileOutputStream fos = new FileOutputStream(localBackupFile.toFile())) {
            LOGGER.debug(" ... Downloading " + ftpFile);
            ftpClient.retrieveFile(ftpFile, fos);
            LOGGER.debug(String.format(" ... FTP file '%s' has been saved to: %s", ftpFile, localBackupFile.toString()));
            success = true;
        } catch (final IOException e) {
            LOGGER.error(String.format("Failed to download FTP file: %s | Target: %s", ftpFile, localBackupFile.toString()), e);
        }
        return success;
    }

}
