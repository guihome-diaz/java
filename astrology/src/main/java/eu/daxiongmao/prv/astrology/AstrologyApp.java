package eu.daxiongmao.prv.astrology;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.daxiongmao.prv.astrology.model.ui.SessionDTO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Astrology application start
 * @author Guillaume Diaz
 * @version 1.0 - 2016/12 (creation)
 * @version 2.0 - 2019/11 (migration to OpenJavaFX 13 + Java 11)
 *                the FXML files must be in /src/main/resources/_package-folder_/*.fxml
 *                The application must have a "module-info" to work with Java 9+ modules
 */
public class AstrologyApp extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(AstrologyApp.class);

    private static AstrologyApp instance = null;

    public static final String ASTROLOGY_INPUT = "astrologyInput.fxml";
    public static final String ASTROLOGY_RESULTS = "astrologyResults.fxml";

    private Stage primaryStage;
    private AnchorPane contentPanel;

    @Override
    public void start(final Stage primaryStage) {
        instance = this;
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Astrology");
        // this close all stages and exit application when the main stage is closed
        this.primaryStage.setOnCloseRequest(e -> Platform.exit());

        loadRootPanel();
        loadPage(ASTROLOGY_INPUT);
    }

    /**
     * Root panel = window + menu bar.
     * (i) there is no content, this is just the container!
     */
    public void loadRootPanel() {
        try {
            // Load root panel from FXML file
            final FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("langs.Astrology", SessionDTO.getInstance().getLocale()));
            loader.setLocation(getClass().getResource("rootPane.fxml"));
            final VBox rootPanel = loader.load();

            for (final Node node : rootPanel.getChildren()) {
                if (node instanceof AnchorPane) {
                    contentPanel = (AnchorPane) node;
                }
            }

            // Show the scene containing the root layout.
            final Scene scene = new Scene(rootPanel);
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (final Exception e) {
            LOGGER.error("Failed to load ROOT panel", e);
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public void loadPage(final String fxmlFile) {
        try {
            // Load the FXML file and set into the center of the main layout
            final FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            loader.setResources(ResourceBundle.getBundle("langs.Astrology", SessionDTO.getInstance().getLocale()));
            final AnchorPane page = (AnchorPane) loader.load();
            // Clear current content and apply new content
            contentPanel.getChildren().clear();
            contentPanel.getChildren().add(page);
        } catch (final Exception e) {
            // Exception gets thrown if the fxml file could not be loaded
            LOGGER.error("Failed to load page", e);
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /**
     * @return Returns the main stage. Use that to refresh the page content.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }

    public static synchronized AstrologyApp getInstance() {
        return AstrologyApp.instance;
    }

    public void reloadPage() {
        loadRootPanel();
        loadPage(ASTROLOGY_INPUT);
    }
}
