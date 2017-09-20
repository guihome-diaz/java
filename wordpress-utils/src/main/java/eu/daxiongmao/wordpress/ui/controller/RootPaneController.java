package eu.daxiongmao.wordpress.ui.controller;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import de.felixroske.jfxsupport.FXMLController;
import eu.daxiongmao.wordpress.Main;
import eu.daxiongmao.wordpress.ui.view.DashboardView;
import eu.daxiongmao.wordpress.ui.view.SettingsView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
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

    private static final int BUTTON_ICON_SIZE = 18;

    // Navigation buttons
    @FXML
    Button navigationLeftButton;
    @FXML
    Button navigationRightButton;
    @FXML
    Button navigationHomeButton;

    // Languages options
    @FXML
    MenuButton languageSelectionButton;
    @FXML
    MenuItem englishItem;
    @FXML
    MenuItem frenchItem;
    @FXML
    MenuItem chineseItem;

    // Title
    @FXML
    Label title;

    // To access configuration page
    @FXML
    MenuButton configurationButton;
    @FXML
    MenuItem settingsItem;
    @FXML
    MenuItem databaseItem;
    @FXML
    MenuItem logsItem;

    // About
    @FXML
    Button aboutButton;

    @Value("${h2.tcp.port}")
    private int h2consolePort;

    /**
     * To init the screen content once Spring Boot + JavaFX have been loaded.<br>
     * This will be execute BEFORE presenting the screen to the user with a guarantee of non-null values.
     */
    @FXML
    void initialize() {
        // Compute Navigation bar
        computeMenuItems();
        computeButtons();

        // APPLICATION STARTUP: Load the default view if there is nothing else on display
        if (!Main.hasHistory()) {
            final Runnable displayDefaultScreen = () -> {
                LOGGER.info("Loading default content");
                Main.showView(DashboardView.class);
            };
            // Ask JavaFX to display the view once system will be ready
            Platform.runLater(displayDefaultScreen);
        }
    }

    /**
     * To compute each menu action and icon
     */
    private void computeMenuItems() {
        // Languages
        setMenuItemIcon(englishItem, "/img/icons/icon-UK.png");
        setMenuItemIcon(frenchItem, "/img/icons/icon-FR.png");
        setMenuItemIcon(chineseItem, "/img/icons/icon-CN.png");

        // DevTools
        setMenuItemIcon(settingsItem, "/img/icons/properties-128x128.png");
        setMenuItemIcon(databaseItem, "/img/icons/db_icon.png");
        setMenuItemIcon(logsItem, "/img/icons/log_file_icon.png");
    }

    /**
     * To compute the buttons icons and menu composition
     */
    private void computeButtons() {
        setButtonIcon(navigationLeftButton, "/img/icons/Arrow-left-128x128.png");
        setButtonIcon(navigationRightButton, "/img/icons/Arrow-right-128x128.png");
        setButtonIcon(navigationHomeButton, "/img/icons/homepage-128x128.png");
        setButtonIcon(languageSelectionButton, "/img/icons/translate-128x128.png");
        setButtonIcon(configurationButton, "/img/icons/control-panel-128x128.png");
        setButtonIcon(aboutButton, "/img/icons/info-128x128.png");
    }

    private void setButtonIcon(final ButtonBase button, final String iconUrl) {
        final URL url = getClass().getResource(iconUrl);
        final Image picture = new Image(url.toExternalForm());
        final ImageView icon = new ImageView(picture);
        icon.setFitHeight(BUTTON_ICON_SIZE);
        icon.setPreserveRatio(true);
        button.setGraphic(icon);
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

    /**
     * To display information about the current application
     */
    public void aboutPopup() {
        final Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(getBundle().getString("popup.help.title"));
        alert.setHeaderText(getBundle().getString("popup.help.header"));
        alert.setContentText(getBundle().getString("popup.help.content"));
        alert.showAndWait();
    }

    /**
     * To set the current language to ENGLISH
     */
    public void setEnglishLanguage() {
        // set global lang
        Main.setLanguage(new Locale("en", "GB"));
        // reload page
        Main.reloadView();
    }

    /**
     * To set the current language to FRENCH
     */
    public void setFrenchLanguage() {
        // set global lang
        Main.setLanguage(new Locale("fr", "FR"));
        // reload page
        Main.reloadView();
    }

    /**
     * To set the current language to CHINESE
     */
    public void setChineseLanguage() {
        // set global lang
        Main.setLanguage(new Locale("zh", "CN"));
        // reload page
        Main.reloadView();
    }

    /**
     * To display the application's settings.
     */
    public void openSettingsPage() {
        Main.showView(SettingsView.class);
    }

    /**
     * To open a new webpage to consult the embedded database.
     */
    public void openDatabaseLink() {
        AbstractJavaFxApplicationSupport.openBrowser("http://localhost:8082");
    }

    /**
     * To open a new webpage to consult the application logs.
     */
    public void openLogFile() {
        final String home = System.getProperty("user.home");
        final Path logFile = Paths.get(home, "daxiongmao", "wordpress-utils", "wordpress-utils.log");
        // Old Java AWT way
        // (i) This does NOT work on Linux* and MAC OS platforms ; On Windows it is better than the JavaFX alternative
        // DesktopUtils.openFile(logFile.toFile());

        // JavaFX way
        AbstractJavaFxApplicationSupport.openBrowser(logFile.toFile().toURI().toString());
    }

    /**
     * Action for the {@link #navigationLeftButton} button click
     */
    public void viewPreviousPage() {
        // TODO
    }

    /**
     * Action for the {@link #navigationRightButton} button click
     */
    public void viewNextPage() {
        // TODO
    }

    /**
     * Action for the {@link #navigationHomeButton} button click
     */
    public void viewHomePage() {
        Main.showView(DashboardView.class);
    }

    /**
     * To set the current page title
     *
     * @param pageTitle
     *            page title - this must be the key to an I18N string that exists in the languages bundles
     */
    public void setTitle(final String pageTitle) {
        title.setText(getBundle().getString(pageTitle));
    }

    /**
     * To recompute the buttons statuses:<br>
     * this will disable the button of the current page & the history back / next depending on the cases.
     */
    public void recomputeButtons() {

    }
}
