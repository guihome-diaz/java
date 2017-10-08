package eu.daxiongmao.wordpress.server.service.ftp;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

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
    private static final String MOCK_FTP_SERVER_RELATIVE_ROOT = "/mockFtp";

    private static int fakeFtpPort;
    private static int numberOfFilesInFtp = 0;

    private static FakeFtpServer fakeFtpServer;

    // ---------------------------------------------- SETUP ------------------------------------------

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
        addDirectoryToMockFtpServer(ftpFileSystem, mockFtpRoot, MOCK_FTP_SERVER_RELATIVE_ROOT);
        fakeFtpServer.setFileSystem(ftpFileSystem);

        // Create users
        fakeFtpServer.addUserAccount(new UserAccount(MOCK_FTP_USERNAME, MOCK_FTP_PASSWORD, MOCK_FTP_SERVER_RELATIVE_ROOT));

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

    /**
     * Add the given directory and all it's files recursively to the Mock FTP server.
     *
     * @param ftpFileSystem
     *            Mock FTP server to populate
     * @param realDirectory
     *            real directory to add and analyze == this is the file / folder on the local system
     * @param relativePath
     *            FTP relative path to use for the file
     */
    private static void addDirectoryToMockFtpServer(final FileSystem ftpFileSystem, final File realDirectory, final String relativePath) {
        // Declare the new directory
        ftpFileSystem.add(new DirectoryEntry(relativePath));

        // Add files recursively
        for (final File file : realDirectory.listFiles()) {
            // skip parent directory and directory itself
            if (file.getName().equals(".") || file.getName().equals("..")) {
                continue;
            }

            if (file.isDirectory()) {
                // recursive call
                addDirectoryToMockFtpServer(ftpFileSystem, file, relativePath + "/" + file.getName());
            } else {
                final FileEntry ftpFile = new FileEntry(relativePath + "/" + file.getName(), "jUnit content");
                if (ftpFileSystem.getEntry(ftpFile.getPath()) != null) {
                    // for security only, it should never occur!
                    LOGGER.warn("Skipping duplicate FTP entry for: " + ftpFile.getPath());
                } else {
                    ftpFileSystem.add(ftpFile);
                    numberOfFilesInFtp++;
                    if (LOGGER.isTraceEnabled()) {
                        LOGGER.trace(" * mock FTP file: " + ftpFile.getPath());
                    }
                }
            }
        }
    }

    // ---------------------------------------------- UNIT TESTS ------------------------------------------

    /**
     * Generic test to validate the mock FTP server behavior.<br>
     * This test just log the content of the FTP.<br>
     * If this test fail you must check your configuration: some is wrong.<br>
     * <u>Other tests results can only be considered if that test is OK</u>
     */
    @Test
    public void testMockFtpConfiguration() {
        Assert.assertNotNull(fakeFtpServer);
        final FtpService ftpService = new FtpService("localhost", fakeFtpPort, MOCK_FTP_USERNAME, MOCK_FTP_PASSWORD);
        try {
            final FtpFileHandler loggerHandler = (ftpFile, directory) -> {
                LOGGER.debug("FTP file: " + directory + "/" + ftpFile.getName());
            };
            final FtpServiceIterator ftpIterator = new FtpServiceIterator(MOCK_FTP_SERVER_RELATIVE_ROOT, null, 0, 15, loggerHandler, null);
            final List<String> filesProcessed = ftpService.listDirectoryContent(ftpIterator);
            Assert.assertNotNull(filesProcessed);
            Assert.assertFalse(filesProcessed.isEmpty());
            Assert.assertEquals(numberOfFilesInFtp, filesProcessed.size());
        } catch (final Exception e) {
            LOGGER.error("Mock FTP server # configuration check test # FAILURE !! Check your environment and setup: something is wrong", e);
            Assert.fail(e.getMessage());
        } finally {
            ftpService.disconnect();
        }
    }

}
