package de.felixroske.jfxsupport;

import java.util.Locale;

import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The enum {@link GUIState} stores Scene and Stage objects as singletons in
 * this VM.
 *
 * @author Felix Roske
 * @author Andreas Jay
 * @author Guillaume Diaz
 */
public enum GUIState {

    INSTANCE;
    private static Scene scene;

    private static Stage stage;

    private static String title;

    private static HostServices hostServices;

    /**
     * Container to update on the current page.<br>
     * This assume that the first page to be loaded on application start is a "root" or "landing page" that contains an {@link AnchorPane} that will be populate /
     * refresh later on.
     */
    private static AnchorPane contentPaneToUpdate;

    /**
     * First page to be loaded on application start.
     */
    private static Class<? extends AbstractFxmlView> rootView;

    /**
     * Current page the user is currently browsing | displaying.
     */
    private static Class<? extends AbstractFxmlView> view;

    /**
     * Language to use for content display.<br>
     * If NULL then pages will appear using JVM's default locale.
     */
    private static Locale locale;

    public static String getTitle() {
        return title;
    }

    static Scene getScene() {
        return scene;
    }

    static Stage getStage() {
        return stage;
    }

    static void setScene(final Scene scene) {
        GUIState.scene = scene;
    }

    static void setStage(final Stage stage) {
        GUIState.stage = stage;
    }

    static void setTitle(final String title) {
        GUIState.title = title;
    }

    public static HostServices getHostServices() {
        return hostServices;
    }

    static void setHostServices(final HostServices hostServices) {
        GUIState.hostServices = hostServices;
    }

    /**
     * @param contentPaneToUpdate
     *            container (AnchorPane) to update | refresh later on.<br>
     *            This assume that the first page to be loaded on application start is a "root" or "landing page" that contains an {@link AnchorPane} that will be populate /
     *            refresh later on.
     */
    static void setContentPaneToUpdate(final AnchorPane contentPaneToUpdate) {
        GUIState.contentPaneToUpdate = contentPaneToUpdate;
    }

    static AnchorPane getContentPaneToUpdate() {
        return contentPaneToUpdate;
    }

    /**
     * @return Language to use for content display.<br>
     *         If NULL then pages will appear using JVM's default locale.
     */
    public static Locale getLocale() {
        return locale;
    }

    /**
     * @param locale
     *            language to use for content display.
     */
    static void setLocale(final Locale locale) {
        GUIState.locale = locale;
    }

    /**
     * To set the current view.<br>
     * (i) You must call this method on each view change so you can reload the page later on when user ask for a language change
     *
     * @param view
     *            current view.
     */
    static void setView(final Class<? extends AbstractFxmlView> view) {
        GUIState.view = view;
    }

    /**
     * To know what is the current page on display.<br>
     * !! This is not the root container but the CONTENT view.
     *
     * @return Current page the user is currently browsing | displaying.
     */
    public static Class<? extends AbstractFxmlView> getView() {
        return view;
    }

    /**
     * To display the application root page | container
     *
     * @return First page to be loaded on application start. It is usually a container or a dashboard
     */
    static Class<? extends AbstractFxmlView> getRootView() {
        return rootView;
    }

    /**
     * To display the application root page | container
     *
     * @param rootView
     *            First page to be loaded on application start. It is usually a container or a dashboard
     */
    static void setRootView(final Class<? extends AbstractFxmlView> rootView) {
        GUIState.rootView = rootView;
    }

}
