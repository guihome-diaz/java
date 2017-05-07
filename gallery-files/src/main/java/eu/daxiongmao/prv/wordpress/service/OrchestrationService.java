package eu.daxiongmao.prv.wordpress.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.daxiongmao.prv.wordpress.model.files.DirectoryContent;

public class OrchestrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrchestrationService.class);

    private FtpService ftpService = new FtpService();

    private FileService fileService = new FileService();

    private AppPropertiesService appPropertiesService = new AppPropertiesService();

    public void downloadGalleries() {
        // Get properties
        final Properties properties = appPropertiesService.loadApplicationProperties();
        final String ftpHost = properties.getProperty("ftp.url");
        final int ftpPort = Integer.parseInt(properties.getProperty("ftp.port"));
        final String ftpUsername = properties.getProperty("ftp.username");
        final String ftpPassword = properties.getProperty("ftp.password");
        final String wordpressRelativePath = properties.getProperty("wordpress.relativePath");

        // Get backup folder (we suppose previous check has been made to ensure it is correct)
        final String backupFolder = properties.getProperty("backup.local.path");
        final Path backupPath = Paths.get(backupFolder);

        try {
            // Connect to FTP and get content
            ftpService.connect(ftpHost, ftpPort, ftpUsername, ftpPassword);

            // Get local files
            final List<DirectoryContent> localDirectories = fileService.getDirectories(backupPath);

            // Get FTP files
            final Map<String, FTPFile> ftpFiles = ftpService.listAllGalleriesAndTheirContent(wordpressRelativePath);

            // TODO extract list of files to download

        } catch (final Exception e) {
            LOGGER.error("failed to backup galleries files", e);
        } finally {
            ftpService.waitForAllDownloadsThenDisconnect();
        }
    }

}
