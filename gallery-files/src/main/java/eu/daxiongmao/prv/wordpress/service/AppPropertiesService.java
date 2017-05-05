package eu.daxiongmao.prv.wordpress.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To read and manage application's properties.
 *
 * @author guillaume diaz
 * @version 1.0 - May 2017
 *
 */
public class AppPropertiesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppPropertiesService.class);

    /**
     * To load the application's configuration.<br>
     * This will create the default configuration if it doesn't yet exists.
     *
     * @return application configuration
     */
    public Properties loadApplicationProperties() {
        return loadApplicationProperties(null);
    }

    /**
     * To load the application's configuration.<br>
     * This will create the default configuration if it doesn't yet exists.
     *
     * @param configPath
     *            to load a particular configuration file
     * @return application configuration
     */
    Properties loadApplicationProperties(final Path configPath) {
        final Properties appProps = new Properties();

        try {
            // Get file
            Path configFile = null;
            if (configPath == null) {
                configFile = getConfigurationFile();
            } else {
                configFile = configPath;
            }
            // Load content
            try (final FileInputStream fis = new FileInputStream(configFile.toFile())) {
                appProps.load(fis);
            }
        } catch (final IOException e) {
            LOGGER.error("failed to access / create the configuration folder | file", e);
        } catch (final URISyntaxException e) {
            LOGGER.error("failed to read default configuration file", e);
        }

        return appProps;
    }

    /**
     * To get the application's configuration file.<br>
     * It will create the folder and the file with default values if it doesn't yet exist
     *
     * @return application's configuration file.
     * @throws IOException
     *             failed to access / create the configuration folder | file
     * @throws URISyntaxException
     *             failed to read default configuration file
     */
    private Path getConfigurationFile() throws IOException, URISyntaxException {
        // Init configuration folder
        final String userHomeDirectory = System.getProperty("user.home");
        final Path appConfigFolder = Paths.get(userHomeDirectory, "daxiongmao", "gallery-files");
        if (!Files.exists(appConfigFolder)) {
            Files.createDirectories(appConfigFolder);
            LOGGER.info("Previous configuration not found - Creation of the configuration folder: " + appConfigFolder);
        }

        // Init configuration file
        final Path configFile = appConfigFolder.resolve("gallery-files.properties");
        if (!Files.exists(configFile)) {
            final Path emptyConfigFile = Paths.get(AppPropertiesService.class.getClassLoader().getResource("config/gallery-files.properties").toURI());
            Files.copy(emptyConfigFile, configFile);
            LOGGER.info("Previous configuration not found - Creation of the configuration file with default values: " + configFile);
        }

        return configFile;
    }

    /**
     * To save the new properties
     *
     * @param newProperties
     *            new configuration values
     * @param configPath
     *            configuration file to update. if NULL it will update the default file.
     * @return flag. "true" if the file was updated
     * @throws IllegalStateException
     *             failed to save configuration file
     */
    public boolean saveProperties(final Properties newProperties, final Path configPath) {
        // Load current properties
        final Properties existingProps = loadApplicationProperties();

        // check if content has changed
        boolean hasChanged = false;
        final Enumeration<?> items = existingProps.propertyNames();
        while (items.hasMoreElements()) {
            final String key = (String) items.nextElement();
            final String value = newProperties.getProperty(key);

            if (value != null && !value.trim().isEmpty()) {
                if (existingProps.containsKey(key) && !existingProps.get(key).equals(value.trim())) {
                    LOGGER.debug(String.format("Configuration change - update key: %s = %s", key, value.trim()));
                    existingProps.setProperty(key, value.trim());
                    hasChanged = true;
                }
            } else if (existingProps.containsKey(key)) {
                LOGGER.debug(String.format("Configuration change - remove key: %s", key));
                existingProps.remove(key);
                hasChanged = true;
            }
        }

        // Save changes, if any
        if (hasChanged) {
            try {
                // Get file
                Path configFile = null;
                if (configPath == null) {
                    configFile = getConfigurationFile();
                } else {
                    configFile = configPath;
                }
                // Load content
                try (final FileOutputStream fos = new FileOutputStream(configFile.toFile())) {
                    existingProps.store(fos, null);
                    LOGGER.info("New configuration has been saved");
                }
            } catch (final Exception e) {
                LOGGER.error("failed to save configration", e);
                throw new IllegalStateException(e);
            }
        }

        return hasChanged;
    }

}
