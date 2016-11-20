package eu.daxiongmao.prv.fx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;

@Component
public class RootPaneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootPaneController.class);

    @FXML
    MenuItem openItem, saveItem, quitItem;
    @FXML
    MenuItem aboutItem;

    public void openFile() {
        // FIXME
    }

    public void exitApplication() {
        Platform.exit();
    }

    public void aboutPopup() {
        // TODO remove that code once migration to Java 8.40+ will be effective
        // JavaFX doesn't include Popup supports until Java8.40 ... In the meantime you must use a 3rd party dialog library.
        // I'm using http://code.makery.ch/blog/javafx-2-dialogs/
        // You'll find the library usage on the website.
        // FIXME
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About...");
        alert.setHeaderText("About this application");
        alert.setContentText("JavaFX 8 template v1.0 - 2016/06 - G.Diaz");

        alert.showAndWait();
    }
}
