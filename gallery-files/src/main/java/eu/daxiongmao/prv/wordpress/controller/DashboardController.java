package eu.daxiongmao.prv.wordpress.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class DashboardController implements Initializable {

    private ResourceBundle bundle;

    @FXML
    Button configButton;

    @FXML
    Button downloadFilesButton;

    @FXML
    Button openFolderButton;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // JavaFX bindings

        // Load values
    }

    public void doDownloadFiles() {
        // TODO
    }

    public void doOpenFolder() {
        // TODO
    }
}
