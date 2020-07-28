package eu.daxiongmao.prv.fx;

import eu.daxiongmao.prv.fx.controller.RootPaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is based on Rusan Molchanov (https://github.com/ruslanys/fish-springboot-javafx) code.<br>
 * @author xinxiongmao [custom version - 2016/11]
 * @author Ruslan Molchanov (ruslanys@gmail.com) - 2015/08
 */
@Configuration
public class ConfigurationControllers {

    @Bean(name = "rootView")
    public View getRootView() throws IOException {
        return loadView("fxml/rootPane.fxml");
    }

    /**
     * This controller is the first to be loaded ; it contains the global application layout and menus.<br>
     * This controller is bind to {@link #getRootView()}. As a result, JavaFX will create it ; then Spring will register it as a singleton.
     */
    @Bean
    public RootPaneController getRootController() throws IOException {
        return (RootPaneController) getRootView().getController();
    }

    @Bean(name = "loginView")
    public View getLoginView() throws IOException {
        return loadView("fxml/login.fxml");
    }

    /**
     * To load a FXML file.<br>
     * This will initialize both the Scene and the corresponding controller.
     * @param url FXML file to load
     * @return corresponding scene and controller
     * @throws IOException FXML file not found
     */
    protected View loadView(final String url) throws IOException {
        InputStream fxmlStream = null;
        try {
            fxmlStream = getClass().getClassLoader().getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return new View(loader.getRoot(), loader.getController());
        } finally {
            if (fxmlStream != null) {
                fxmlStream.close();
            }
        }
    }

    public class View {
        private Parent view;
        private Object controller;

        public View(final Parent view, final Object controller) {
            this.view = view;
            this.controller = controller;
        }

        /**
         * @return JavaFX scene [= display content]
         */
        public Parent getView() {
            return view;
        }

        public void setView(final Parent view) {
            this.view = view;
        }

        /**
         * @return scene's controller
         */
        public Object getController() {
            return controller;
        }

        public void setController(final Object controller) {
            this.controller = controller;
        }
    }
}
