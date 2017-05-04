package eu.daxiongmao.prv.wordpress.controller;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import eu.daxiongmao.prv.wordpress.model.ui.Property;
import eu.daxiongmao.prv.wordpress.service.AppPropertiesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;

public class SettingsController implements Initializable {

    private ResourceBundle bundle;

    @FXML
    Button saveButton;

    @FXML
    Button cancelButton;

    @FXML
    TableView<Property> properties;
    @FXML
    TableColumn<Property, String> propKeyColumn;
    @FXML
    TableColumn<Property, String> propValueColumn;

    final ObservableList<Property> propertiesContent = FXCollections.observableArrayList();

    private final AppPropertiesService appPropertiesService = new AppPropertiesService();

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // JavaFX bindings

        // Initialize the column bindings.
        // See: http://code.makery.ch/library/javafx-8-tutorial/part2/
        propKeyColumn.setCellValueFactory(cellData -> cellData.getValue().keyProperty());
        propKeyColumn.setEditable(false);
        propValueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        propValueColumn.setEditable(true);

        // Allow table to be edited
        properties.setEditable(true);
        // allows the individual cells to be selected
        properties.getSelectionModel().cellSelectionEnabledProperty().set(true);
        // when character or numbers pressed it will start edit in editable fields
        // + this will allow arrow keys and TAB to go to the new cell (even new raw)
        properties.setOnKeyPressed(event -> {
            if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                editFocusedCell();
            } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.TAB) {
                properties.getSelectionModel().selectNext();
                event.consume();
            } else if (event.getCode() == KeyCode.LEFT) {
                // work around because of a JavaFX bug
                // TableView.getSelectionModel().selectPrevious() stopped working on the first column in the last row of the table
                selectPrevious();
                event.consume();
            }
        });

        // Listen for selection changes and show the person details when changed.
        // properties.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));

        // Load values
        final Properties applicationProperties = appPropertiesService.loadApplicationProperties();
        applicationProperties.forEach((key, value) -> propertiesContent.add(new Property((String) key, (String) value)));
        // Bind values
        properties.setItems(propertiesContent);
    }

    /**
     * To edit a cell of the table-view.<br>
     * See: https://dzone.com/articles/editable-tables-in-javafx
     */
    private void editFocusedCell() {
        final TablePosition<Property, ?> focusedCell = properties.focusModelProperty().get().focusedCellProperty().get();
        properties.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    /**
     * To select the previous column or the first column of the raw.<br>
     * See: https://dzone.com/articles/editable-tables-in-javafx
     */
    private void selectPrevious() {
        if (properties.getSelectionModel().isCellSelectionEnabled()) {
            // in cell selection mode, we have to wrap around, going from right-to-left, and then wrapping to the end of the previous line
            final TablePosition<Property, ?> pos = properties.getFocusModel().getFocusedCell();

            if (pos.getColumn() - 1 >= 0) {
                // go to previous row
                properties.getSelectionModel().select(pos.getRow(), getTableColumn(pos.getTableColumn(), -1));
            } else if (pos.getRow() < properties.getItems().size()) {
                // wrap to end of previous row
                properties.getSelectionModel().select(pos.getRow() - 1, properties.getVisibleLeafColumn(properties.getVisibleLeafColumns().size() - 1));
            }
        } else {
            final int focusIndex = properties.getFocusModel().getFocusedIndex();
            if (focusIndex == -1) {
                properties.getSelectionModel().select(properties.getItems().size() - 1);
            } else if (focusIndex > 0) {
                properties.getSelectionModel().select(focusIndex - 1);
            }
        }
    }

    /**
     * To retrieve the table column related to a particular (raw,column) selection
     *
     * @param column
     *            selected column
     * @param offset
     *            selected raw
     * @return property of that particular column
     */
    private TableColumn<Property, ?> getTableColumn(final TableColumn<Property, ?> column, final int offset) {
        final int columnIndex = properties.getVisibleLeafIndex(column);
        final int newColumnIndex = columnIndex + offset;
        return properties.getVisibleLeafColumn(newColumnIndex);
    }

    public void doSave() {
        // TODO
    }

    public void doReset() {
        // TODO
    }

    public void doCancel() {
        // TODO
    }

    public void doBack() {
        // TODO
    }
}
