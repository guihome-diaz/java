package eu.daxiongmao.prv.fx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

	private static App instance = null;

	private Stage primaryStage;
	private AnchorPane contentPanel;

	@Override
	public void start(final Stage primaryStage) {
		setInstance(this);
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Daxiongmao");

		initRootPane();
		loadDiffingPanel();
	}

	/** Root panel = window + menu bar. (i) there is no content, this is just the container! */
	private void initRootPane() {
		try {
			// Load root panel from FXML file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getClassLoader().getResource("fxml/rootPane.fxml"));
			VBox rootPanel = (VBox) loader.load();

			for (Node node : rootPanel.getChildren()) {
				if (node instanceof AnchorPane) {
					this.contentPanel = (AnchorPane) node;
				}
			}

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootPanel);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadDiffingPanel() {
		try {
			// Load the FXML file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(App.class.getClassLoader().getResource("fxml/diffing.fxml"));
			AnchorPane diffingPage = (AnchorPane) loader.load();
			// Clear current content and apply new content
			contentPanel.getChildren().clear();
			contentPanel.getChildren().add(diffingPage);

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}


	/**
	 * @return Returns the main stage. Use that to refresh the page content.
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(final String[] args) {
		launch(args);
	}

	private static synchronized void setInstance(final App instance) {
		App.instance = instance;
	}

	public static synchronized App getInstance() {
		return App.instance;
	}
}
