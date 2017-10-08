package eu.daxiongmao.wordpress.server.service.ftp;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To test the FTP file service.
 *
 * @author Guillaume Diaz
 * @version 1.0, oct. 2017
 * @since 1.0
 */
public class FtpServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpServiceTest.class);

    private static final String MOCK_FTP_USERNAME = "user";
    private static final String MOCK_FTP_PASSWORD = "password";
    private static int fakeFtpPort;

    private static FakeFtpServer fakeFtpServer;

    /**
     * To start fake server (based on mock FTP server, see: http://mockftpserver.sourceforge.net/fakeftpserver-getting-started.html)
     *
     * @throws URISyntaxException
     */
    @BeforeClass
    public static void onInit() throws URISyntaxException {
        fakeFtpServer = new FakeFtpServer();

        // Port to listen to
        // 0 == random free port
        // use a port number > 1000 if you want to avoid some issues
        fakeFtpServer.setServerControlPort(0);

        // Create files and directories list
        final FileSystem ftpFileSystem = new UnixFakeFileSystem();
        final File mockFtpRoot = new File(FtpServiceTest.class.getClassLoader().getResource("dataset").toURI());
        if (!mockFtpRoot.exists() && !mockFtpRoot.isDirectory()) {
            throw new IllegalArgumentException("Cannot init mock FTP: invalid root folder ... " + mockFtpRoot.toString());
        }
        addDirectoryToMockFtpServer(ftpFileSystem, mockFtpRoot);
        fakeFtpServer.setFileSystem(ftpFileSystem);

        // Create users
        final File userFtpRoot = new File(FtpServiceTest.class.getClassLoader().getResource("dataset/www").toURI());
        fakeFtpServer.addUserAccount(new UserAccount(MOCK_FTP_USERNAME, MOCK_FTP_PASSWORD, userFtpRoot.toString()));

        // Start server
        fakeFtpServer.start();

        // save port for later use
        fakeFtpPort = fakeFtpServer.getServerControlPort();
    }

    @AfterClass
    public static void onShutdown() {
        if (fakeFtpServer != null) {
            fakeFtpServer.stop();
        }
    }

    @Test
    public void testListFtpFiles() {
        Assert.assertNotNull(fakeFtpServer);
        final FtpService ftpService = new FtpService("localhost", fakeFtpPort, MOCK_FTP_USERNAME, MOCK_FTP_PASSWORD);
        try {
            final FtpFileHandler loggerHandler = (ftpFile, directory) -> {
                LOGGER.debug("FTP file: " + directory + "/" + ftpFile.getName());
            };
            final File userFtpRoot = new File(FtpServiceTest.class.getClassLoader().getResource("dataset/www").toURI());
            final FtpServiceIterator ftpIterator = new FtpServiceIterator(userFtpRoot.toString(), null, 0, 5, loggerHandler, null);
            ftpService.listDirectoryContent(ftpIterator);
        } catch (final Exception e) {
            LOGGER.error("Test failure", e);
            Assert.fail(e.getMessage());
        } finally {
            ftpService.disconnect();
        }
    }

    /**
     * Add the given directory and all it's files recursively to the Mock FTP server.
     *
     * @param ftpFileSystem
     *            Mock FTP server to populate
     * @param dir
     *            directory to add and analyze
     */
    private static void addDirectoryToMockFtpServer(final FileSystem ftpFileSystem, final File dir) {
        // Declare the new directory
        ftpFileSystem.add(new DirectoryEntry(dir.getAbsolutePath()));

        // Add files recursively
        for (final File file : dir.listFiles()) {
            // skip parent directory and directory itself
            if (file.getName().equals(".") || file.getName().equals("..")) {
                continue;
            }

            if (file.isDirectory()) {
                // recursive call
                addDirectoryToMockFtpServer(ftpFileSystem, file);
            } else {
                final FileEntry ftpFile = new FileEntry(file.toString(), "jUnit content");
                if (ftpFileSystem.getEntry(ftpFile.getPath()) != null) {
                    // for security only, it should never occur!
                    LOGGER.warn("Skipping duplicate FTP entry for: " + ftpFile);
                } else {
                    ftpFileSystem.add(ftpFile);
                }
            }
        }
    }

}
