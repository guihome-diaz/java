package de.felixroske.jfxsupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The Class AbstractJavaFxApplicationSupport.
 *
 * @author Felix Roske
 */
public abstract class AbstractJavaFxApplicationSupport extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJavaFxApplicationSupport.class);

    private static String[] savedArgs = new String[0];

    private static Class<? extends AbstractFxmlView> savedInitialView;

    private static ConfigurableApplicationContext applicationContext;

    /** The splash screen. */
    private static SplashScreen splashScreen;

    private static List<Image> icons = new ArrayList<>();

    private final BooleanProperty appCtxLoaded = new SimpleBooleanProperty(false);

    public static Stage getStage() {
        return GUIState.getStage();
    }

    public static Scene getScene() {
        return GUIState.getScene();
    }

    public static HostServices getAppHostServices() {
        return GUIState.getHostServices();
    }

    /*
     * (non-Javadoc)
     *
     * @see javafx.application.Application#init()
     */
    @Override
    public void init() throws Exception {
        CompletableFuture.supplyAsync(() -> {
            final ConfigurableApplicationContext ctx = SpringApplication.run(this.getClass(), savedArgs);

            final List<String> fsImages = PropertyReaderHelper.get(ctx.getEnvironment(), "javafx.appicons");

            if (!fsImages.isEmpty()) {
                fsImages.forEach((s) -> icons.add(new Image(getClass().getResource(s).toExternalForm())));
            } else { // add factory images
                icons.add(new Image(getClass().getResource("/icons/gear_16x16.png").toExternalForm()));
                icons.add(new Image(getClass().getResource("/icons/gear_24x24.png").toExternalForm()));
                icons.add(new Image(getClass().getResource("/icons/gear_36x36.png").toExternalForm()));
                icons.add(new Image(getClass().getResource("/icons/gear_42x42.png").toExternalForm()));
                icons.add(new Image(getClass().getResource("/icons/gear_64x64.png").toExternalForm()));
            }
            return ctx;
        }).thenAccept(this::launchApplicationView);
    }

    /*
     * (non-Javadoc)
     *
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(final Stage stage) throws Exception {
        GUIState.setStage(stage);
        GUIState.setHostServices(this.getHostServices());
        final Stage splashStage = new Stage(StageStyle.UNDECORATED);
        // this close stage and all scenes ; then exit application
        splashStage.setOnCloseRequest(windowEvent -> {
            LOGGER.info("Closing JavaFX application");

            // orderly shut down FX
            Platform.exit();

            // But: there might still be a daemon thread left over from OkHttp (some async dispatcher)
            // so assume everything is fine and call System.exit(0)
            System.exit(0);
        });

        if (AbstractJavaFxApplicationSupport.splashScreen.visible()) {
            final Scene splashScene = new Scene(splashScreen.getParent());
            splashStage.setScene(splashScene);
            splashStage.show();
        }

        final Runnable showMainAndCloseSplash = () -> {
            showInitialView();
            if (AbstractJavaFxApplicationSupport.splashScreen.visible()) {
                splashStage.hide();
                splashStage.setScene(null);
            }
        };

        synchronized (this) {
            if (appCtxLoaded.get() == true) {
                // Spring ContextLoader was faster
                Platform.runLater(showMainAndCloseSplash);
            } else {
                appCtxLoaded.addListener((ov, oVal, nVal) -> {
                    Platform.runLater(showMainAndCloseSplash);
                });
            }
        }

    }

    /**
     * Show initial view.
     */
    private void showInitialView() {
        final String stageStyle = applicationContext.getEnvironment().getProperty("javafx.stage.style");
        if (!StringUtils.isEmpty(stageStyle)) {
            GUIState.getStage().initStyle(StageStyle.valueOf(stageStyle.toUpperCase()));
        } else {
            GUIState.getStage().initStyle(StageStyle.DECORATED);
        }
        // stage.hide();
        GUIState.getStage().setOnCloseRequest(windowEvent -> {
            LOGGER.info("Closing JavaFX application");

            // orderly shut down FX
            Platform.exit();

            // But: there might still be a daemon thread left over from OkHttp (some async dispatcher)
            // so assume everything is fine and call System.exit(0)
            System.exit(0);
        });

        showView(savedInitialView);
    }

    /**
     * Launch application view.
     *
     * @param ctx
     *            the ctx
     */
    private void launchApplicationView(final ConfigurableApplicationContext ctx) {
        AbstractJavaFxApplicationSupport.applicationContext = ctx;
        appCtxLoaded.set(true);
    }

    /**
     * Show view.
     *
     * @param newView
     *            the new view
     */
    public static void showDefaultView(final Class<? extends AbstractFxmlView> newView) {
        final AbstractFxmlView view = applicationContext.getBean(newView);
        final Parent viewContent = view.getView();

        if (GUIState.getScene() != null && !(GUIState.getRootView().equals(newView)) && GUIState.getContentPaneToUpdate() != null
                && view.getFxmlPanel() != null) {
            // Application startup - first view
            // Clear current content and apply new content
            GUIState.getContentPaneToUpdate().getChildren().clear();
            GUIState.getContentPaneToUpdate().getChildren().add((AnchorPane) view.getFxmlPanel());

            // Save reference to the current view
            GUIState.setView(newView);
        }

        GUIState.getStage().setScene(GUIState.getScene());
        applyEnvPropsToView();
        GUIState.getStage().getIcons().addAll(icons);
        GUIState.getStage().show();
    }

    /**
     * Show view.
     *
     * @param newView
     *            the new view
     */
    public static void showView(final Class<? extends AbstractFxmlView> newView) {
        final AbstractFxmlView view = applicationContext.getBean(newView);
        final Parent viewContent = view.getView();

        if (GUIState.getScene() == null) {
            // Application startup!
            GUIState.setRootView(newView);
            setupRootView(view, viewContent);
        } else {
            if (GUIState.getRootView().equals(newView)) {
                // UI Reload
                setupRootView(view, viewContent);
            } else {
                if (GUIState.getContentPaneToUpdate() != null && view.getFxmlPanel() != null) {
                    // Clear current content and apply new content
                    GUIState.getContentPaneToUpdate().getChildren().clear();
                    GUIState.getContentPaneToUpdate().getChildren().add((AnchorPane) view.getFxmlPanel());

                    // Save reference to the current view
                    GUIState.setView(newView);
                } else {
                    // Replace all the screen
                    GUIState.getScene().setRoot(viewContent);
                    // Save reference to the current view
                    GUIState.setView(newView);
                }
            }
        }

        GUIState.getStage().setScene(GUIState.getScene());
        applyEnvPropsToView();
        GUIState.getStage().getIcons().addAll(icons);
        GUIState.getStage().show();
    }

    private static void setupRootView(final AbstractFxmlView view, final Parent viewContent) {
        GUIState.setScene(new Scene(viewContent));

        // Is their an AnchorPane container that will be update | refresh later on?
        final Pane rootPanel = (Pane) view.getFxmlPanel();
        for (final Node node : rootPanel.getChildren()) {
            if (node instanceof AnchorPane) {
                GUIState.setContentPaneToUpdate((AnchorPane) node);
            }
        }
    }

    /**
     * To reload the current view.<br>
     * This is useful if you want to load a new language and stay on the same screen.
     */
    public static void reloadView() {
        showView(GUIState.getRootView());
        // View can be NULL in the early development stage ; or if there is no default view set
        if (GUIState.getView() != null) {
            showView(GUIState.getView());
        }
    }

    /**
     * Set display language.<br>
     * You must call {@link #reloadView()} afterwards if you want to apply the new language immediately on the current view.
     *
     * @param locale
     *            Language to use for content display.<br>
     *            If NULL then pages will appear using JVM's default locale.
     */
    public static void setLanguage(final Locale locale) {
        GUIState.setLocale(locale);
    }

    /**
     * Apply env props to view.
     */
    private static void applyEnvPropsToView() {
        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), "javafx.title", String.class, GUIState.getStage()::setTitle);

        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), "javafx.stage.width", Double.class, GUIState.getStage()::setWidth);

        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), "javafx.stage.height", Double.class, GUIState.getStage()::setHeight);

        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), "javafx.stage.resizable", Boolean.class, GUIState.getStage()::setResizable);
    }

    /*
     * (non-Javadoc)
     *
     * @see javafx.application.Application#stop()
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        if (applicationContext != null) {
            applicationContext.close();
        } // else: someone did it already
        // FIXME close H2 + actuators + Java FX as well
    }

    /**
     * Sets the title. Allows to overwrite values applied during construction at
     * a later time.
     *
     * @param title
     *            the new title
     */
    protected static void setTitle(final String title) {
        GUIState.getStage().setTitle(title);
    }

    /**
     * Launch app.
     *
     * @param appClass
     *            the app class
     * @param view
     *            the view
     * @param args
     *            the args
     */
    public static void launchApp(final Class<? extends AbstractJavaFxApplicationSupport> appClass, final Class<? extends AbstractFxmlView> view, final String[] args) {
        launchApp(appClass, view, new SplashScreen(), null, args);
    }

    /**
     * Launch app.
     *
     * @param appClass
     *            the app class
     * @param view
     *            the view
     * @param displayLanguage
     *            to force the default language of the user interface
     * @param args
     *            the args
     */
    public static void launchApp(final Class<? extends AbstractJavaFxApplicationSupport> appClass, final Class<? extends AbstractFxmlView> view,
            final Locale displayLanguage, final String[] args) {
        launchApp(appClass, view, new SplashScreen(), displayLanguage, args);
    }

    /**
     * Launch app.
     *
     * @param appClass
     *            the app class
     * @param view
     *            the view
     * @param splashScreen
     *            the splash screen
     * @param args
     *            the args
     */
    public static void launchApp(final Class<? extends AbstractJavaFxApplicationSupport> appClass, final Class<? extends AbstractFxmlView> view,
            final SplashScreen splashScreen, final String[] args) {
        launchApp(appClass, view, splashScreen, null, args);
    }

    /**
     * Launch app.
     *
     * @param appClass
     *            the app class
     * @param view
     *            the root view (container |or| first page)
     * @param splashScreen
     *            the splash screen
     * @param displayLanguage
     *            to force the default language of the user interface
     * @param args
     *            the args
     */
    public static void launchApp(final Class<? extends AbstractJavaFxApplicationSupport> appClass, final Class<? extends AbstractFxmlView> view,
            final SplashScreen splashScreen, final Locale displayLanguage, final String[] args) {
        savedInitialView = view;
        savedArgs = args;

        // Force language, only if asked
        if (displayLanguage != null) {
            setLanguage(displayLanguage);
        }

        // Custom or default splash screen?
        if (splashScreen != null) {
            AbstractJavaFxApplicationSupport.splashScreen = splashScreen;
        } else {
            AbstractJavaFxApplicationSupport.splashScreen = new SplashScreen();
        }

        // JavaFX standard boot
        Application.launch(appClass, args);
    }

    /**
     * To open the default browser and display a particular URL<br>
     * (i) Unlike java.awt.Desktop the JavaFX works well everywhere
     *
     * @param url
     *            webpage link to open
     */
    public static void openBrowser(final String url) {
        getAppHostServices().showDocument(url);
    }
}
