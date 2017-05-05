package eu.daxiongmao.prv.wordpress.controller;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.daxiongmao.prv.wordpress.GalleryFilesApp;
import eu.daxiongmao.prv.wordpress.model.ui.Property;
import eu.daxiongmao.prv.wordpress.service.AppPropertiesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class SettingsController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsController.class);

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

    /** Functional service */
    private final AppPropertiesService appPropertiesService = new AppPropertiesService();

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // Java FX bindings
        bundle = resources;

        // Allow table to be edited
        properties.setEditable(true);

        // Setup columns (see http://docs.oracle.com/javafx/2/ui_controls/table-view.htm)
        // Non editable column
        propKeyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        propKeyColumn.setEditable(false);
        // Editable column
        propValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        propValueColumn.setEditable(true);
        propValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        propValueColumn.setOnEditCommit(new EventHandler<CellEditEvent<Property, String>>() {
            @Override
            public void handle(final CellEditEvent<Property, String> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setValue(t.getNewValue());
            }
        });

        // Load values
        final Properties applicationProperties = appPropertiesService.loadApplicationProperties();
        applicationProperties.forEach((key, value) -> propertiesContent.add(new Property((String) key, (String) value)));
        properties.setItems(propertiesContent);
    }

    public void doSave() {
        final Properties savedConfig = new Properties();
        propertiesContent.forEach(item -> savedConfig.put(item.getKey(), item.getValue()));
        try {
            final boolean configHasBeenUpdated = appPropertiesService.saveProperties(savedConfig, null);
            if (configHasBeenUpdated) {
                final Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("settings.save.popup.success.header"));
                alert.setContentText(bundle.getString("settings.save.popup.success.message"));
                alert.showAndWait();
            }
        } catch (final IllegalStateException e) {
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(bundle.getString("settings.save.popup.error.header"));
            alert.setContentText(bundle.getString("settings.save.popup.error.message"));
        }

        GalleryFilesApp.getInstance().loadPage(GalleryFilesApp.PAGE_DASHBOARD);
    }

    public void doCancel() {
        GalleryFilesApp.getInstance().loadPage(GalleryFilesApp.PAGE_DASHBOARD);
    }

}
