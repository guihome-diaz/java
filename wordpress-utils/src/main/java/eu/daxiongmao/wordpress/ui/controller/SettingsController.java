package eu.daxiongmao.wordpress.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import eu.daxiongmao.wordpress.server.dao.AppPropertyRepository;
import eu.daxiongmao.wordpress.server.model.AppProperty;
import eu.daxiongmao.wordpress.ui.dto.AppPropertyFx;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@FXMLController
public class SettingsController extends AbstractFxmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsController.class);

    @FXML
    Button saveButton;

    @FXML
    Button cancelButton;

    @FXML
    Button searchButton;

    @FXML
    Button addButton;

    @FXML
    TextField searchField;

    @FXML
    TableView<AppPropertyFx> properties;
    @FXML
    TableColumn<AppPropertyFx, String> propKeyColumn;
    @FXML
    TableColumn<AppPropertyFx, String> propValueColumn;
    @FXML
    TableColumn<AppPropertyFx, String> propDescriptionColumn;
    @FXML
    TableColumn<AppPropertyFx, Button> propEditColumn;
    @FXML
    TableColumn<AppPropertyFx, Button> propDeleteColumn;

    final ObservableList<AppPropertyFx> propertiesContent = FXCollections.observableArrayList();

    @Autowired
    private AppPropertyRepository appPropsRepository;

    @FXML
    void initialize() {
        setupUi();

        // Load values
        // FIXME GDZ + don't forget to add the pagination
        final List<AppProperty> dbAppProperties = new ArrayList<>();
        dbAppProperties.forEach((dbProp) -> propertiesContent.add(new AppPropertyFx(dbProp.getKey(), dbProp.getValue(), dbProp.getDescription())));
        properties.setItems(propertiesContent);
    }

    private void setupUi() {
        // Allow table to be edited
        properties.setEditable(true);
        // Only work on 1 row at a time
        properties.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Setup columns (see http://docs.oracle.com/javafx/2/ui_controls/table-view.htm)
        // Non editable columns
        propKeyColumn.setEditable(false);
        propValueColumn.setEditable(false);
        propDescriptionColumn.setEditable(false);
    }

    public void doSave() {
        // FIXME GDZ
        LOGGER.info("Saving properties");
    }

    public void doCancel() {
        // FIXME GDZ
    }

    public void doSearch() {
        // FIXME GDZ
        LOGGER.info("Searching for property: ");
    }

    public void doAdd() {
        // FIXME GDZ
        LOGGER.info("Adding new property");
    }

}
