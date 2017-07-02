package eu.daxiongmao.wordpress.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import eu.daxiongmao.wordpress.config.ApplicationProperties;
import eu.daxiongmao.wordpress.server.dao.AppPropertyRepository;
import eu.daxiongmao.wordpress.server.model.AppProperty;
import eu.daxiongmao.wordpress.ui.convert.AppPropertyConvertor;
import eu.daxiongmao.wordpress.ui.dto.AppPropertyFx;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
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
    TableColumn<AppPropertyFx, Integer> propIdColumn;
    @FXML
    TableColumn<AppPropertyFx, String> propKeyColumn;
    @FXML
    TableColumn<AppPropertyFx, String> propValueColumn;
    @FXML
    TableColumn<AppPropertyFx, String> propDescriptionColumn;
    @FXML
    TableColumn<AppPropertyFx, AppPropertyFx> deleteColumn;

    @Autowired
    private AppPropertyRepository appPropsRepository;

    private int pageNumber = 0;

    @FXML
    void initialize() {
        setupUi();
        loadDbValues();
    }

    private void loadDbValues() {
        final Integer nbOfRowsPerRequest = appPropsRepository.findByKey(ApplicationProperties.DB_CONFIG_NB_RESULTS_PER_PAGE.key).getValueAsInteger();

        // Load values from DB
        final Page<AppProperty> props = appPropsRepository.findAll(new PageRequest(pageNumber, nbOfRowsPerRequest));
        pageNumber++;

        // DB to FX list
        properties.setItems(AppPropertyConvertor.dbPropToFx(props.getContent()));
    }

    private void setupUi() {
        // Allow table to be edited
        properties.setEditable(true);
        // Only work on 1 row at a time
        properties.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Setup columns (see http://docs.oracle.com/javafx/2/ui_controls/table-view.htm)
        propIdColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());
        propKeyColumn.setCellValueFactory(cellData -> cellData.getValue().getKeyProperty());
        propValueColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        propDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());

        deleteColumn.setCellFactory(deleteCell -> {
            final TableCell<AppPropertyFx, AppPropertyFx> cell = new TableCell<AppPropertyFx, AppPropertyFx>() {
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    final AppProperty entityToDelete = AppPropertyConvertor.fxPropToDb(cell.getItem());
                    appPropsRepository.delete(entityToDelete);
                    properties.getItems().remove(cell.getIndex());
                }
            });
            return cell;
        });

        // Non editable columns
        propKeyColumn.setEditable(false);
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
