package eu.daxiongmao.prv.wordpress.controller.dialog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ExceptionDialog {

    /**
     * To display an error dialog.
     * 
     * @param bundle
     *            i18n resource (language bundle)
     * @param errorMessage
     *            i18n error key
     * @param ex
     *            exception to process
     */
    public static void showExceptionDialog(final ResourceBundle bundle, final String errorMessage, final Exception ex) {
        final Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(bundle.getString("exception.window.title"));
        alert.setHeaderText(bundle.getString("exception.window.header"));
        alert.setContentText(bundle.getString(errorMessage));

        // Create expandable Exception.
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        final String exceptionText = sw.toString();

        final Label label = new Label(bundle.getString("exception.window.error"));

        final TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        final GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }
}
