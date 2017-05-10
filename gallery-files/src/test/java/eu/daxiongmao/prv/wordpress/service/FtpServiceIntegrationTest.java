package eu.daxiongmao.prv.wordpress.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPFile;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpServiceIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpServiceIntegrationTest.class);

    @Ignore("You must adjust the test configuration in src/test/resources/app_settings/gallery-files.properties")
    @Test
    public void testListWordpressGalleriesAndTheirContent() throws IOException, URISyntaxException {
        // Load properties file
        final Path configFile = Paths.get(FileServiceTest.class.getClassLoader().getResource("app_settings/gallery-files.properties").toURI());
        final AppPropertiesService appPropService = new AppPropertiesService();
        final Properties properties = appPropService.loadApplicationProperties(configFile);

        // Process properties
        final String ftpHost = properties.getProperty("ftp.url");
        final int ftpPort = Integer.parseInt(properties.getProperty("ftp.port"));
        final String ftpUsername = properties.getProperty("ftp.username");
        final String ftpPassword = properties.getProperty("ftp.password");
        final String wordpressRelativePath = properties.getProperty("wordpress.relativePath");

        // Business test case
        final FtpService ftpService = new FtpService();
        ftpService.connect(ftpHost, ftpPort, ftpUsername, ftpPassword);
        final Map<String, FTPFile> files = ftpService.listAllGalleriesAndTheirContent(wordpressRelativePath);
        ftpService.disconnect();

        // Assertions
        Assert.assertNotNull(files);
        Assert.assertFalse(files.isEmpty());
    }

    @Ignore("You must adjust the test configuration in src/test/resources/app_settings/gallery-files.properties")
    @Test
    public void testListWordpressGalleriesAndTheirContentAndStartDownload() throws IOException, URISyntaxException {
        // Load properties file
        final Path configFile = Paths.get(FileServiceTest.class.getClassLoader().getResource("app_settings/gallery-files.properties").toURI());
        final AppPropertiesService appPropService = new AppPropertiesService();
        final Properties properties = appPropService.loadApplicationProperties(configFile);

        // Process properties
        final String ftpHost = properties.getProperty("ftp.url");
        final int ftpPort = Integer.parseInt(properties.getProperty("ftp.port"));
        final String ftpUsername = properties.getProperty("ftp.username");
        final String ftpPassword = properties.getProperty("ftp.password");
        final String wordpressRelativePath = properties.getProperty("wordpress.relativePath");

        // Create temp folder to backup files
        final Path backupDirectory = Files.createTempDirectory("gallery-files_junit");

        try {
            // Business test case
            final FtpService ftpService = new FtpService();
            ftpService.connect(ftpHost, ftpPort, ftpUsername, ftpPassword);
            final Map<String, FTPFile> files = ftpService.listAllGalleriesAndTheirContent(wordpressRelativePath);
            files.forEach((filename, ftpFile) -> {
                ftpService.downloadFile(filename, backupDirectory);
            });
            ftpService.waitForAllDownloadsThenDisconnect();

            // Assertions
            Assert.assertTrue(Files.exists(backupDirectory));
        } catch (final Exception e) {
            LOGGER.error("Failed to download gallery", e);
        } finally {
            // Cleanup test
            final FileService fileService = new FileService();
            fileService.recursiveDelete(backupDirectory);
        }
    }

}
