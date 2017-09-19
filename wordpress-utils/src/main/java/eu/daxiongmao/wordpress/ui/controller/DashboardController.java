package eu.daxiongmao.wordpress.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.GUIState;
import javafx.fxml.FXML;

@FXMLController
public class DashboardController extends AbstractFxmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

    private static final int ACTIONS_ICON_SIZE_IN_PIXELS = 16;

    @FXML
    void initialize() {
        // Set page title
        // TODO set title using resource bundle (i18n)
        GUIState.setContainerTitle("Dashboard");
    }

    public void doDownloadPhotos() {
        // TODO
        LOGGER.info("Download photos");
    }

    public void doDownloadVideos() {
        // TODO
        LOGGER.info("Download videos");
    }
}
