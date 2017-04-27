package eu.daxiongmao.prv.wordpress.controller;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import eu.daxiongmao.prv.wordpress.GalleryFilesApp;
import eu.daxiongmao.prv.wordpress.model.ui.SessionDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RootPaneController implements Initializable {

    /** The icon will have the following height size (auto width stretch). */
    private static final int ICON_SIZE = 24;

    @FXML
    MenuItem quitItem;
    @FXML
    MenuItem englishItem;
    @FXML
    MenuItem frenchItem;
    @FXML
    MenuItem chineseItem;
    @FXML
    MenuItem aboutItem;

    public void exitApplication() {
        Platform.exit();
    }

    public void aboutPopup() {
        final Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About...");
        alert.setHeaderText("Wordpress's Gallery plugin files management");
        alert.setContentText("2017/04 - Daxiongmao.eu [G.Diaz] - Creation");
        alert.showAndWait();
    }

    public void setEnglishLanguage() {
        SessionDTO.getInstance().setLocale(new Locale("en", "GB"));
        GalleryFilesApp.getInstance().reloadPage();
    }

    public void setFrenchLanguage() {
        SessionDTO.getInstance().setLocale(new Locale("fr", "FR"));
        GalleryFilesApp.getInstance().reloadPage();
    }

    public void setChineseLanguage() {
        SessionDTO.getInstance().setLocale(new Locale("zh", "CN"));
        GalleryFilesApp.getInstance().reloadPage();
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setMenuItemIcon(quitItem, "/img/icons/icon-exit.png");
        setMenuItemIcon(englishItem, "/img/icons/icon-UK.png");
        setMenuItemIcon(frenchItem, "/img/icons/icon-FR.png");
        setMenuItemIcon(chineseItem, "/img/icons/icon-CN.png");
        setMenuItemIcon(aboutItem, "/img/icons/icon-about.png");
    }

    private void setMenuItemIcon(final MenuItem menuItem, final String iconUrl) {
        final URL url = RootPaneController.class.getResource(iconUrl);
        final Image picture = new Image(url.toExternalForm());
        final ImageView icon = new ImageView(picture);
        icon.setFitHeight(ICON_SIZE);
        icon.setPreserveRatio(true);
        menuItem.setGraphic(icon);
    }

}
