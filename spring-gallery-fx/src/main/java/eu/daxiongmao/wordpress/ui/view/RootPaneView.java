package eu.daxiongmao.wordpress.ui.view;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;

/**
 * To load the FXML.<br>
 * The corresponding controller (= the one declare in the FXML file) will automatically be loaded as a Spring component
 * thanks to Spring-JavaFX.<br>
 * Please note that all JavaFX annotation and UI elements must be declared and used in the controller only.
 *
 * @version 1.0
 * @since 1.0
 * @author Guillaume Diaz
 * @author current code is based on: https://github.com/roskenet/spring-javafx-examples.git
 *
 */
@FXMLView(value = "/fxml/rootPane.fxml", bundle = "langs.wordpress")
public class RootPaneView extends AbstractFxmlView {

}
