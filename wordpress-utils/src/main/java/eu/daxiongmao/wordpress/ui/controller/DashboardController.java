package eu.daxiongmao.wordpress.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import eu.daxiongmao.wordpress.Main;
import eu.daxiongmao.wordpress.ui.view.SettingsView;
import javafx.fxml.FXML;

@FXMLController
public class DashboardController extends AbstractFxmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

    private static final int ACTIONS_ICON_SIZE_IN_PIXELS = 16;

    @FXML
    void initialize() {
        LOGGER.info("Dashboard is ready");
    }

    public void doConfiguration() {
        Main.showView(SettingsView.class);
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