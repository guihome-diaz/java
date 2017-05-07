package eu.daxiongmao.prv.wordpress.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
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
            LOGGER.info(String.format("Connection established with %s:%s | user: %s", host, port, user));
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
        LOGGER.info("About to disconnect the connection with the FTP server, await for all download to complete");
        // Wait for completion. !! Give enough time for slow connections !!
        try {
            downloadThreadPools.awaitTermination(120, TimeUnit.SECONDS);
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
                LOGGER.info("Connection to FTP server is now close");
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
     * To process a given FTP directory and extract all items (file(s) | directory/ies) to process.
     *
     * @param dirToList
     *            root FTP path to analyze
     * @param ftpFileHandler
     *            function to check if the given item should be process or not
     * @return items to process
     *         <ul>
     *         <li>Key: file name (with relative FTP path)</li>
     *         <li>Value: FTP file settings</li>
     *         </ul>
     * @throws IOException
     *             failed to read remote FTP folder
     */
    private Map<String, FTPFile> getItemsToProcess(final String dirToList, final FtpFileHandler ftpFileHandler) throws IOException {
        final Map<String, FTPFile> itemsToProcess = new ConcurrentHashMap<>();

        // Server files
        final FTPFile[] subFiles = ftpClient.listFiles(dirToList);
        if (subFiles != null && subFiles.length > 0) {
            for (final FTPFile aFile : subFiles) {
                final String currentFileName = aFile.getName();

                // skip parent directory and directory itself
                if (currentFileName.equals(".") || currentFileName.equals("..")) {
                    continue;
                }

                // Analyze and process item
                ftpFileHandler.handleFtpFile(aFile, itemsToProcess);
            }
        }
        return itemsToProcess;
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
    public Set<String> listGalleryDirectories(final String wordpressRoot) throws IOException {
        // argument check
        if (wordpressRoot == null || wordpressRoot.isEmpty()) {
            LOGGER.error("No wordpress root provided! Cannot list galleries!");
            return new HashSet<>();
        }

        // Build remote path
        String dirToList = wordpressRoot;
        if (!dirToList.endsWith("/")) {
            dirToList += "/";
        }
        dirToList += "wp-content/gallery";

        /** ---- Processing ---- */
        final FtpFileHandler handler = ((aFtpFile, items) -> {
            // Each gallery should be its own directory
            if (aFtpFile.isDirectory()) {
                items.put(aFtpFile.getName(), aFtpFile);
            }
        });
        final Map<String, FTPFile> galleries = getItemsToProcess(dirToList, handler);

        /** ----- Logging ----- */
        final StringBuilder log = new StringBuilder("\n");
        galleries.forEach((key, value) -> log.append("   * ").append(key).append("\n"));
        LOGGER.info(String.format("Found %s galleries: %s", galleries.size(), log.toString()));
        return galleries.keySet();
    }

    /**
     * To list Wordpress's galleries content.
     *
     * @param wordpressRoot
     *            wordpress root (RELATIVE path)
     * @return list of galleries
     * @throws IOException
     *             some errors occurred
     */
    public Map<String, FTPFile> listAllGalleriesAndTheirContent(final String wordpressRoot) throws IOException {
        final Set<String> galleries = listGalleryDirectories(wordpressRoot);
        // Get files
        final Map<String, FTPFile> galleriesContent = new ConcurrentHashMap<>();
        galleries.forEach((gallery) -> {
            final String dirName = (wordpressRoot.endsWith("/") ? wordpressRoot : wordpressRoot + "/") + "wp-content/gallery/" + gallery;
            try {
                LOGGER.debug(String.format("... Analysing content of gallery: %s", gallery));
                galleriesContent.putAll(listFtpGalleryContent(dirName, gallery));
            } catch (final IOException e) {
                LOGGER.error("Failed to list FTP files for gallery: " + gallery, e);
            }
        });

        // Log and order the map
        final Map<String, FTPFile> sortedContent = new TreeMap<>();
        final StringBuilder log = new StringBuilder("\n");
        galleriesContent.forEach((key, value) -> {
            log.append("   * ").append(key).append("\n");
            sortedContent.put(key, value);
        });
        LOGGER.info(String.format("Found %s files: %s", sortedContent.size(), sortedContent.toString()));
        return sortedContent;
    }

    private Map<String, FTPFile> listFtpGalleryContent(final String dirName, final String galleryName) throws IOException {
        final FtpFileHandler handler = ((aFtpFile, items) -> {
            if (aFtpFile.isFile()) {
                String fileName = aFtpFile.getName();
                // Remove backup extension
                if (fileName.endsWith(FtpService.IMAGE_BACKUP_ENDING)) {
                    fileName = fileName.substring(0, fileName.indexOf(FtpService.IMAGE_BACKUP_ENDING));
                }
                // keep the high resolution (bigger size)
                if (!items.containsKey(fileName) || items.get(fileName).getSize() < aFtpFile.getSize()) {
                    items.put(galleryName + "/" + fileName, aFtpFile);
                }
            }
        });
        return getItemsToProcess(dirName, handler);
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
