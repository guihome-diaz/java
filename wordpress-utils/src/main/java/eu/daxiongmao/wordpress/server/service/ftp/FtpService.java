package eu.daxiongmao.wordpress.server.service.ftp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * To interact with a FTP server.
 * @author Guillaume Diaz
 * @version 1.0, oct. 2017
 */
@Component
public class FtpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpService.class);

    private FTPClient ftpClient;

    private static final String IMAGE_BACKUP_ENDING = "_backup";

    private final ExecutorService downloadThreadPools = Executors.newFixedThreadPool(5);

    /**
     * Default constructor, for Spring
     */
    public FtpService() {
    }

    /**
     * Constructor that will establish a FTP connection on startup.<br>
     * Use that for the jUnits tests.
     *
     * @param host
     *            URL to FTP host
     * @param port
     *            FTP port
     * @param user
     *            FTP username
     * @param pwd
     *            FTP password
     */
    public FtpService(final String host, final int port, final String user, final String pwd) {
        final boolean connectionOk = connect(host, port, user, pwd);
        if (!connectionOk) {
            throw new IllegalArgumentException("Cannot start: failed to connected to FTP server");
        }
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
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setAutodetectUTF8(true);

            // Increase buffer size to avoid connection hanging
            ftpClient.setBufferSize(1024 * 1024);

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
     * <strong>Movies filter</strong><br>
     * To filter FTP files and only keep the MOVIES.<br>
     * The filter is based on the file extension.
     *
     * @return FTP file filter for MOVIES
     */
    public FtpFileFilter getMoviesExtensionsFilter() {
        final FtpFileFilter fileFilter = (ftpFile) -> {
            if (!ftpFile.getName().contains(".")) {
                return false;
            }

            final String fileExtension = ftpFile.getName().substring(ftpFile.getName().lastIndexOf(".")).toLowerCase();
            final Set<String> extensions = new HashSet<>();
            extensions.add(".mov");
            extensions.add(".mp4");
            extensions.add(".m4v");
            return extensions.contains(fileExtension);
        };
        return fileFilter;
    }

    /**
     * <strong>Photos filter</strong><br>
     * To filter FTP files and only keep the PHOTOS.<br>
     * The filter is based on the file extension.
     *
     * @return FTP file filter for PHOTOS
     */
    public FtpFileFilter getPhotosExtensionsFilter() {
        final FtpFileFilter fileFilter = (ftpFile) -> {
            if (!ftpFile.getName().contains(".")) {
                return false;
            }

            final String fileExtension = ftpFile.getName().substring(ftpFile.getName().lastIndexOf(".")).toLowerCase();
            final Set<String> extensions = new HashSet<>();
            extensions.add(".png");
            extensions.add(".jpg");
            extensions.add(".jpeg");
            extensions.add(".gif");
            return extensions.contains(fileExtension);
        };
        return fileFilter;
    }

    /**
     * To list the content of a remote directory and its sub-folders.<br>
     * !! This is recursive !!
     *
     * @param ftpIterator
     *            FTP iteration parameters.
     * @return list of FILES that were handled and processed. Each entry = relative path + / + processed file name.<br>
     *         /!\ This does NOT include the DIRECTORIES<br>
     *         It only counts files that match FILTER criteria if provided
     * @throws IOException
     *             some errors occurred
     */
    public List<String> listDirectoryContent(final FtpServiceIterator ftpIterator) throws IOException {
        final List<String> files = new ArrayList<>();

        // Build remote path
        String dirToList = "";
        if (ftpIterator.parentDir != null) {
            dirToList = ftpIterator.parentDir;
            if (ftpIterator.currentDir != null && !ftpIterator.currentDir.trim().isEmpty()) {
                if (!dirToList.endsWith("/")) {
                    dirToList += "/";
                }
                dirToList += ftpIterator.currentDir;
            }
        } else {
            dirToList = ftpIterator.currentDir;
        }

        // Server files
        final FTPFile[] subFiles = ftpClient.listFiles(dirToList);
        if (subFiles != null && subFiles.length > 0) {
            for (final FTPFile aFile : subFiles) {
                final String currentFileName = aFile.getName();

                // skip parent directory and directory itself
                if (currentFileName.equals(".") || currentFileName.equals("..")) {
                    continue;
                }

                // recursive call for sub-directories (as long as the maxLevel is not reached)
                if (aFile.isDirectory() && (ftpIterator.level + 1 < ftpIterator.maxLevel)) {
                    final FtpServiceIterator nextIteration = new FtpServiceIterator(dirToList, currentFileName, ftpIterator.level + 1, ftpIterator.maxLevel,
                            ftpIterator.handler, ftpIterator.filter);
                    files.addAll(this.listDirectoryContent(nextIteration));
                }

                // File handling
                if (aFile.isFile() && (ftpIterator.filter == null || ftpIterator.filter.filter(aFile))) {
                    files.add(dirToList + "/" + currentFileName);
                    ftpIterator.handler.handle(aFile, dirToList);
                }
            }
        }
        return files;
    }

}
