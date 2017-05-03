package eu.daxiongmao.prv.wordpress.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpServiceTest.class);

    // @Ignore("You must adjust the test configuration in src/test/resources/app_settings/gallery-files.properties")
    @Test
    public void testListWordpressUploadDirectory() throws IOException, URISyntaxException {
        // Load properties file
        final Path configFile = Paths.get(FileServiceTest.class.getClassLoader().getResource("app_settings/gallery-files.properties").toURI());
        final AppPropertiesService appPropService = new AppPropertiesService();
        final Properties properties = appPropService.loadApplicationProperties(configFile);

        // Process properties
        final String ftpHost = properties.getProperty("ftp.url");
        final int ftpPort = Integer.parseInt(properties.getProperty("ftp.port"));
        final String ftpUsername = properties.getProperty("ftp.username");
        final String ftpPassword = properties.getProperty("ftp.password");
        final int ftpMaxLevel = Integer.parseInt(properties.getProperty("ftp.maxLevel"));
        final String wordpressRelativePath = properties.getProperty("wordpress.relativePath");

        // Business test case
        final FtpService ftpService = new FtpService();
        ftpService.connect(ftpHost, ftpPort, ftpUsername, ftpPassword);
        final List<String> files = ftpService.listDirectoryContent(wordpressRelativePath + "/wp-content/uploads/", null, 1, ftpMaxLevel);
        ftpService.disconnect();

        // Assertions
        Assert.assertNotNull(files);
        Assert.assertFalse(files.isEmpty());
    }

    // @Ignore("You must adjust the test configuration in src/test/resources/app_settings/gallery-files.properties")
    @Test
    public void testDownloadWordpressGalleryFirstDirectory() throws IOException, URISyntaxException {
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
            final List<String> galleries = ftpService.listGalleryDirectories(wordpressRelativePath);
            ftpService.downloadGalleryContent(wordpressRelativePath, galleries.get(0), backupDirectory);
            ftpService.waitForAllDownloadsThenDisconnect();

            // Assertions
            Assert.assertTrue(Files.exists(backupDirectory));
            Assert.assertNotNull(galleries);
            Assert.assertFalse(galleries.isEmpty());
            Assert.assertTrue(Files.exists(Paths.get(backupDirectory.toString(), "gallery", galleries.get(0))));
        } catch (final Exception e) {
            LOGGER.error("Failed to download gallery", e);
        } finally {
            // Cleanup test
            final FileService fileService = new FileService();
            fileService.recursiveDelete(backupDirectory);
            // Files.deleteIfExists(backupDirectory);
        }

    }

}
