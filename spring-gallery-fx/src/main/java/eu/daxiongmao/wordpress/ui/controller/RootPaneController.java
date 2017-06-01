package eu.daxiongmao.wordpress.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * To bind UI elements to actions and behavior.
 * 
 * @version 1.0
 * @since 1.0
 * @author Guillaume Diaz
 * @author current code is based on: https://github.com/roskenet/spring-javafx-examples.git
 *
 */
@FXMLController
public class RootPaneController {

    private ResourceBundle bundle = ResourceBundle.getBundle("langs.wordpress");

    /** The icon will have the following height size (auto width stretch). */
    private static final int ICON_SIZE = 24;

    @FXML
    MenuItem configurationItem;
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

    /**
     * To init the screen content once Spring Boot + JavaFX have been loaded.<br>
     * This will be execute BEFORE presenting the screen to the user with a guarantee of non-null values.
     */
    @FXML
    void initialize() {
        // Set local attributes
        setMenuItemIcon(configurationItem, "/img/icons/icon-settings.png");
        setMenuItemIcon(quitItem, "/img/icons/icon-exit.png");
        setMenuItemIcon(englishItem, "/img/icons/icon-UK.png");
        setMenuItemIcon(frenchItem, "/img/icons/icon-FR.png");
        setMenuItemIcon(chineseItem, "/img/icons/icon-CN.png");
        setMenuItemIcon(aboutItem, "/img/icons/icon-about.png");
    }

    private void setMenuItemIcon(final MenuItem menuItem, final String iconUrl) {
        final URL url = getClass().getResource(iconUrl);
        final Image picture = new Image(url.toExternalForm());
        final ImageView icon = new ImageView(picture);
        icon.setFitHeight(ICON_SIZE);
        icon.setPreserveRatio(true);
        menuItem.setGraphic(icon);
    }

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
        // TODO set global lang
        // TODO reload page
    }

    public void setFrenchLanguage() {
        // TODO set global lang
        // TODO reload page
    }

    public void setChineseLanguage() {
        // TODO set global lang
        // TODO reload page
    }

    public void configureApplication() {
        // TODO
    }

}
