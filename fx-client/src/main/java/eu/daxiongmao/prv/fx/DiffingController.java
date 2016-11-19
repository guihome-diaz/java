package eu.daxiongmao.prv.fx;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;

public class DiffingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DiffingController.class);

	private static DiffingController instance = null;

	@FXML
	TextArea sourceOriginal, sourceRevised;
	@FXML
	Button applyDiffingButton;
	@FXML
	WebView diffingOriginal, diffingRevised;

	public void applyDiffing() {
		LOGGER.info("Applying diffing...");
		final String original = sourceOriginal.getText().trim();
		final String revised = sourceRevised.getText().trim();

		// FIXME apply diffing algorithm over here!

		// Render diffing results in HTML form
		final String newLine = System.getProperty("line.separator");
		final String diffOriginal = original.replace(newLine, "<br>");
		final String diffRevised = revised.replace(newLine, "<br>");
		diffingOriginal.getEngine().loadContent("<b><i>" + diffOriginal + "</i></b>");
		diffingRevised.getEngine().loadContent("<b><i>" + diffRevised + "</i></b>");
	}

	public DiffingController() {
		super();
		// I suppose this screen always appears after the same set of actions ; it is not bound to any application or previous state.
		DiffingController.instance = this;
	}

	public static synchronized DiffingController getInstance() {
		return DiffingController.instance;
	}

	public void initSourceContent(final List<String> original, final List<String> revised) {
		sourceOriginal.clear();
		for (String orig : original) {
			sourceOriginal.setText(sourceOriginal.getText() + orig);
		}

		sourceRevised.clear();
		for (String rev : revised) {
			sourceRevised.setText(sourceRevised.getText() + rev);
		}
	}

}
