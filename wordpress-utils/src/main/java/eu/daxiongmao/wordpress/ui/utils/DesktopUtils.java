package eu.daxiongmao.wordpress.ui.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesktopUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DesktopUtils.class);

    /** Private factory design pattern. */
    private DesktopUtils() {
    }

    /**
     * To open the default browser and display a particular URL, providing that the current system is a Desktop.
     *
     * @param url
     *            web-page link to open
     * @deprecated Use JavaFX <code>Application.getAppHostServices().showDocument(url);</code> instead !
     */
    @Deprecated
    public static void openBrowser(final String url) {
        final Desktop desktop = getDesktop();
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI(url));
            } catch (final URISyntaxException | IOException e) {
                LOGGER.error("Failed to open URL: " + url, e);
            }
        } else {
            LOGGER.warn("Action 'BROWSE' is not supported on this system. Cannot open URL: " + url);
        }
    }

    /**
     * To open a particular file with the default application, providing that the current system has Desktop features.
     *
     * @param file
     *            file to open
     * @deprecated Use JavaFX <code>Application.getAppHostServices().showDocument(file.toUri().toString());</code> instead !
     */
    @Deprecated
    public static void openFile(final File file) {
        final Desktop desktop = getDesktop();
        if (desktop != null && desktop.isSupported(Desktop.Action.OPEN)) {
            try {
                desktop.open(file);
            } catch (final IOException e) {
                LOGGER.error("Failed to open file: " + file.toURI().toString(), e);
            }
        } else {
            LOGGER.warn("Action 'OPEN' is not supported on this system. Cannot open file: " + file.toURI().toString());
        }
    }

    private static Desktop getDesktop() {
        return Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    }

}
