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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.daxiongmao.wordpress.mock.MockFtpServer;

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
    private static final int NB_OF_FILES_IN_MOCK_FTP = 46;

    private static int fakeFtpPort;
    private static FakeFtpServer fakeFtpServer;

    // ---------------------------------------------- SETUP ------------------------------------------

    /**
     * To start fake server
     *
     * @throws URISyntaxException
     */
    @BeforeClass
    public static void onInit() throws URISyntaxException {
        // Start fake FTP server
        final UserAccount fakeFtpUser = new UserAccount(MOCK_FTP_USERNAME, MOCK_FTP_PASSWORD, "/");
        final File mockFtpRoot = new File(MockFtpServer.class.getClassLoader().getResource("dataset").toURI());
        fakeFtpServer = MockFtpServer.startFakeFtpServer(mockFtpRoot, "/", fakeFtpUser, null);

        // save port for later use
        fakeFtpPort = fakeFtpServer.getServerControlPort();
    }

    @AfterClass
    public static void onShutdown() {
        if (fakeFtpServer != null) {
            fakeFtpServer.stop();
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
            final FtpServiceIterator ftpIterator = new FtpServiceIterator("/", null, 0, 15, loggerHandler, null);
            final List<String> filesProcessed = ftpService.listDirectoryContent(ftpIterator);
            Assert.assertNotNull(filesProcessed);
            Assert.assertFalse(filesProcessed.isEmpty());
            Assert.assertEquals(NB_OF_FILES_IN_MOCK_FTP, filesProcessed.size());
        } catch (final Exception e) {
            LOGGER.error("Mock FTP server # configuration check test # FAILURE !! Check your environment and setup: something is wrong", e);
            Assert.fail(e.getMessage());
        } finally {
            ftpService.disconnect();
        }
    }

}
