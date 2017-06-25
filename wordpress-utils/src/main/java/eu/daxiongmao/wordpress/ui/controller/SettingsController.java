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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

@FXMLController
public class SettingsController extends AbstractFxmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsController.class);

    @FXML
    Button saveButton;

    @FXML
    Button cancelButton;

    @FXML
    TableView<AppPropertyFx> properties;
    @FXML
    TableColumn<AppPropertyFx, String> propKeyColumn;
    @FXML
    TableColumn<AppPropertyFx, String> propValueColumn;
    @FXML
    TableColumn<AppPropertyFx, String> propDescriptionColumn;

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

        // Setup columns (see http://docs.oracle.com/javafx/2/ui_controls/table-view.htm)
        // Non editable column
        propKeyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        propKeyColumn.setEditable(false);

        // Editable column VALUE
        propValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        propValueColumn.setEditable(true);
        propValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        propValueColumn.setOnEditCommit(new EventHandler<CellEditEvent<AppPropertyFx, String>>() {
            @Override
            public void handle(final CellEditEvent<AppPropertyFx, String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setValue(t.getNewValue());
            }
        });

        // Editable column DESCRIPTION
        propDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        propDescriptionColumn.setEditable(true);
        propDescriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        propDescriptionColumn.setOnEditCommit(new EventHandler<CellEditEvent<AppPropertyFx, String>>() {
            @Override
            public void handle(final CellEditEvent<AppPropertyFx, String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setDescription(t.getNewValue());
            }
        });
    }

    public void doSave() {
        // FIXME GDZ
    }

    public void doCancel() {
        // FIXME GDZ
    }

}
