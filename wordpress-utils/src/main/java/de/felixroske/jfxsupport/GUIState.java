package de.felixroske.jfxsupport;

import java.util.Locale;

import eu.daxiongmao.wordpress.ui.controller.RootPaneController;
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

    private static HostServices hostServices;

    /**
     * Container to update on the current page.<br>
     * This assume that the first page to be loaded on application start is a "root" or "landing page" that contains an {@link AnchorPane} that will be populate /
     * refresh later on.
     */
    private static AnchorPane contentPaneToUpdate;

    /**
     * Container view (= root view)
     */
    private static Class<? extends AbstractFxmlView> containerView;

    /**
     * Current page the user is currently browsing | displaying.
     */
    private static Class<? extends AbstractFxmlView> view;

    /**
     * Language to use for content display.<br>
     * If NULL then pages will appear using JVM's default locale.
     */
    private static Locale locale;

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
     * This is CONTENT view.
     *
     * @return Current page the user is currently browsing | displaying.
     */
    public static Class<? extends AbstractFxmlView> getView() {
        return view;
    }

    /**
     * To set the current page title
     *
     * @param pageTitle
     *            page title
     */
    public static void setContainerTitle(final String pageTitle) {
        try {
            final RootPaneController rootController = AbstractJavaFxApplicationSupport.applicationContext.getBean(RootPaneController.class);
            rootController.setTitle(pageTitle);
        } catch (final Exception e) {
            System.err.println("Root controller wasn't found");
            // Do nothing: no bean detected!
        }
    }

    static void setContainerView(final Class<? extends AbstractFxmlView> containerView) {
        GUIState.containerView = containerView;
    }

    static Class<? extends AbstractFxmlView> getContainerView() {
        return containerView;
    }

}
