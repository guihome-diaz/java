package eu.daxiongmao.wordpress.server.service.ftp;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
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


    // ---------------------------------------------- SETUP ------------------------------------------

    /**
     * To start fake server
     *
     * @throws URISyntaxException
     */
    public FakeFtpServer startFtpServer() throws URISyntaxException {
        final UserAccount fakeFtpUser = new UserAccount(MOCK_FTP_USERNAME, MOCK_FTP_PASSWORD, "/");
        final File mockFtpRoot = new File(MockFtpServer.class.getClassLoader().getResource("dataset").toURI());
        return MockFtpServer.startFakeFtpServer(mockFtpRoot, "/", fakeFtpUser, null);
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
        FakeFtpServer fakeFtpServer = null;
        FtpService ftpService = null;
        try {
            // ----------- GIVEN ------------
            // start server
            fakeFtpServer = startFtpServer();
            TimeUnit.SECONDS.sleep(1);
            Assert.assertNotNull(fakeFtpServer);
            final int ftpPort = fakeFtpServer.getServerControlPort();
            // FTP service
            ftpService = new FtpService("localhost", ftpPort, MOCK_FTP_USERNAME, MOCK_FTP_PASSWORD);

            // ----------- WHEN ------------
            final FtpFileHandler loggerHandler = (ftpFile, directory) -> {
                LOGGER.debug("FTP file: " + directory + "/" + ftpFile.getName());
            };
            final FtpServiceIterator ftpIterator = new FtpServiceIterator("/", null, 0, 15, loggerHandler, null);

            // ----------- DO ------------
            final List<String> filesProcessed = ftpService.listDirectoryContent(ftpIterator);
            Assert.assertNotNull(filesProcessed);
            Assert.assertFalse(filesProcessed.isEmpty());
            Assert.assertEquals(NB_OF_FILES_IN_MOCK_FTP, filesProcessed.size());
        } catch (final Exception e) {
            LOGGER.error("Mock FTP server # configuration check test # FAILURE !! Check your environment and setup: something is wrong", e);
            Assert.fail(e.getMessage());
        } finally {
            if (ftpService != null) {
                ftpService.disconnect();
            }
            if (fakeFtpServer != null) {
                fakeFtpServer.stop();
            }
        }
    }

    /**
     * Generic test to validate the mock FTP server behavior # levels.<br>
     * This test just log the content of the FTP.<br>
     * If this test fail you must check your configuration: some is wrong.<br>
     * <u>Other tests results can only be considered if that test is OK</u>
     */
    @Test
    public void testMockFtpConfigurationLevels() {

        FakeFtpServer fakeFtpServer = null;
        FtpService ftpService = null;
        try {
            // ----------- GIVEN ------------
            // start server
            fakeFtpServer = startFtpServer();
            TimeUnit.SECONDS.sleep(1);
            Assert.assertNotNull(fakeFtpServer);
            final int ftpPort = fakeFtpServer.getServerControlPort();
            // FTP service
            ftpService = new FtpService("localhost", ftpPort, MOCK_FTP_USERNAME, MOCK_FTP_PASSWORD);

            // ----------- WHEN ------------
            final FtpFileHandler loggerHandler = (ftpFile, directory) -> {
                LOGGER.debug("FTP file: " + directory + "/" + ftpFile.getName());
            };
            final FtpServiceIterator ftpIterator = new FtpServiceIterator("/", null, 0, 7, loggerHandler, null);

            // ----------- DO ------------
            final List<String> filesProcessed = ftpService.listDirectoryContent(ftpIterator);
            Assert.assertNotNull(filesProcessed);
            Assert.assertFalse(filesProcessed.isEmpty());
            Assert.assertNotEquals(NB_OF_FILES_IN_MOCK_FTP, filesProcessed.size());
        } catch (final Exception e) {
            LOGGER.error("Mock FTP server # configuration check test # FAILURE !! Check your environment and setup: something is wrong", e);
            Assert.fail(e.getMessage());
        } finally {
            if (ftpService != null) {
                ftpService.disconnect();
            }
            if (fakeFtpServer != null) {
                fakeFtpServer.stop();
            }
        }
    }

    /**
     * Generic test to validate the mock FTP server behavior # movies filter.<br>
     * This test just log the content of the FTP.<br>
     * If this test fail you must check your configuration: some is wrong.<br>
     * <u>Other tests results can only be considered if that test is OK</u>
     */
    @Test
    public void testMockFtpConfigurationMoviesFilters() {
        FakeFtpServer fakeFtpServer = null;
        FtpService ftpService = null;
        try {
            // ----------- GIVEN ------------
            // start server
            fakeFtpServer = startFtpServer();
            TimeUnit.SECONDS.sleep(1);
            Assert.assertNotNull(fakeFtpServer);
            final int ftpPort = fakeFtpServer.getServerControlPort();
            // FTP service
            ftpService = new FtpService("localhost", ftpPort, MOCK_FTP_USERNAME, MOCK_FTP_PASSWORD);

            // ----------- WHEN ------------
            final FtpFileHandler loggerHandler = (ftpFile, directory) -> {
                LOGGER.debug("FTP file: " + directory + "/" + ftpFile.getName());
            };
            final FtpServiceIterator ftpIterator = new FtpServiceIterator("/", null, 0, 15, loggerHandler, ftpService.getMoviesExtensionsFilter());

            // ----------- DO ------------
            final List<String> filesProcessed = ftpService.listDirectoryContent(ftpIterator);
            Assert.assertNotNull(filesProcessed);
            Assert.assertFalse(filesProcessed.isEmpty());
            Assert.assertNotEquals(NB_OF_FILES_IN_MOCK_FTP, filesProcessed.size());
            Assert.assertEquals(17, filesProcessed.size());
        } catch (final Exception e) {
            LOGGER.error("Mock FTP server # configuration check test # FAILURE !! Check your environment and setup: something is wrong", e);
            Assert.fail(e.getMessage());
        } finally {
            if (ftpService != null) {
                ftpService.disconnect();
            }
            if (fakeFtpServer != null) {
                fakeFtpServer.stop();
            }
        }
    }

    /**
     * Generic test to validate the mock FTP server behavior # photos filter.<br>
     * This test just log the content of the FTP.<br>
     * If this test fail you must check your configuration: some is wrong.<br>
     * <u>Other tests results can only be considered if that test is OK</u>
     */
    @Test
    public void testMockFtpConfigurationPhotosFilters() {
        FakeFtpServer fakeFtpServer = null;
        FtpService ftpService = null;
        try {
            // ----------- GIVEN ------------
            // start server
            fakeFtpServer = startFtpServer();
            TimeUnit.SECONDS.sleep(1);
            Assert.assertNotNull(fakeFtpServer);
            final int ftpPort = fakeFtpServer.getServerControlPort();
            // FTP service
            ftpService = new FtpService("localhost", ftpPort, MOCK_FTP_USERNAME, MOCK_FTP_PASSWORD);

            // ----------- WHEN ------------
            final FtpFileHandler loggerHandler = (ftpFile, directory) -> {
                LOGGER.debug("FTP file: " + directory + "/" + ftpFile.getName());
            };
            final FtpServiceIterator ftpIterator = new FtpServiceIterator("/", null, 0, 15, loggerHandler, ftpService.getPhotosExtensionsFilter());

            // ----------- DO ------------
            final List<String> filesProcessed = ftpService.listDirectoryContent(ftpIterator);
            Assert.assertNotNull(filesProcessed);
            Assert.assertFalse(filesProcessed.isEmpty());
            Assert.assertNotEquals(NB_OF_FILES_IN_MOCK_FTP, filesProcessed.size());
            Assert.assertEquals(18, filesProcessed.size());
        } catch (final Exception e) {
            LOGGER.error("Mock FTP server # configuration check test # FAILURE !! Check your environment and setup: something is wrong", e);
            Assert.fail(e.getMessage());
        } finally {
            if (ftpService != null) {
                ftpService.disconnect();
            }
            if (fakeFtpServer != null) {
                fakeFtpServer.stop();
            }
        }
    }
}
