package eu.daxiongmao.wordpress.ui.controller;

import java.net.URL;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import eu.daxiongmao.wordpress.Main;
import eu.daxiongmao.wordpress.ui.view.DashboardView;
import eu.daxiongmao.wordpress.ui.view.SettingsView;
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
public class RootPaneController extends AbstractFxmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootPaneController.class);

    /** The icon will have the following height size (auto width stretch). */
    private static final int ICON_SIZE = 24;

    @FXML
    MenuItem dashboardItem;
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

    @Value("${h2.tcp.port}")
    private int h2consolePort;

    /**
     * To init the screen content once Spring Boot + JavaFX have been loaded.<br>
     * This will be execute BEFORE presenting the screen to the user with a guarantee of non-null values.
     */
    @FXML
    void initialize() {
        // Set local attributes
        setMenuItemIcon(dashboardItem, "/img/icons/icon-home.png");
        setMenuItemIcon(configurationItem, "/img/icons/icon-settings.png");
        setMenuItemIcon(quitItem, "/img/icons/icon-exit.png");
        setMenuItemIcon(englishItem, "/img/icons/icon-UK.png");
        setMenuItemIcon(frenchItem, "/img/icons/icon-FR.png");
        setMenuItemIcon(chineseItem, "/img/icons/icon-CN.png");
        setMenuItemIcon(aboutItem, "/img/icons/icon-about.png");

        Main.showDefaultView(DashboardView.class);
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
        LOGGER.info("Closing JavaFX application");

        // orderly shut down FX
        Platform.exit();

        // But: there might still be a daemon thread left over from OkHttp (some async dispatcher)
        // so assume everything is fine and call System.exit(0)
        System.exit(0);
    }

    public void aboutPopup() {
        final Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(getBundle().getString("popup.help.title"));
        alert.setHeaderText(getBundle().getString("popup.help.header"));
        alert.setContentText(getBundle().getString("popup.help.content"));
        alert.showAndWait();
    }

    public void setEnglishLanguage() {
        // set global lang
        Main.setLanguage(new Locale("en", "GB"));
        // reload page
        Main.reloadView();
    }

    public void setFrenchLanguage() {
        // set global lang
        Main.setLanguage(new Locale("fr", "FR"));
        // reload page
        Main.reloadView();
    }

    public void setChineseLanguage() {
        // set global lang
        Main.setLanguage(new Locale("zh", "CN"));
        // reload page
        Main.reloadView();
    }

    public void configureApplication() {
        Main.showView(SettingsView.class);
    }

    public void showDashboard() {
        Main.showView(DashboardView.class);
    }
}
