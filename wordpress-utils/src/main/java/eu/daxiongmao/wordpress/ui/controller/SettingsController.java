package eu.daxiongmao.wordpress.ui.controller;

import java.net.URL;
import java.util.Optional;

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
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

@FXMLController
public class SettingsController extends AbstractFxmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsController.class);

    private static final int ACTIONS_ICON_SIZE_IN_PIXELS = 16;

    @FXML
    Button newButton;
    @FXML
    Button nextPage, previousPage;
    @FXML
    Button searchButton;
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
        // Button configuration
        recomputeNextButtonStatus();
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
        setupDeleteColumn();
    }

    private void setupDeleteColumn() {
        // (1) Delete column will return the current object on click
        deleteColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        // (2) Delete column behavior
        deleteColumn.setCellFactory(deleteCell -> {
            final TableCell<AppPropertyFx, AppPropertyFx> cell = new TableCell<AppPropertyFx, AppPropertyFx>() {
                @Override
                public void updateItem(final AppPropertyFx item, final boolean empty) {
                    // Set icon
                    super.updateItem(item, empty);
                    if (item != null && item.getKey() != null && item.getId() != null) {
                        final HBox box= new HBox();
                        final URL url = getClass().getResource("/img/icons/icon-delete.png");
                        final Image picture = new Image(url.toExternalForm());
                        final ImageView icon = new ImageView(picture);
                        icon.setFitHeight(ACTIONS_ICON_SIZE_IN_PIXELS);
                        icon.setPreserveRatio(true);
                        box.getChildren().add(icon);
                        setGraphic(box);
                    }
                }
            };
            // Set action on click
            cell.setOnMouseClicked(e -> setupDeleteActionAndConfirmDialog(cell));
            return cell;
        });
    }

    private void setupDeleteActionAndConfirmDialog(final TableCell<AppPropertyFx, AppPropertyFx> cell) {
        if (!cell.isEmpty()) {
            final AppProperty entityToDelete = AppPropertyConvertor.fxPropToDb(cell.getItem());

            final Alert confirmPopup = new Alert(AlertType.CONFIRMATION);
            confirmPopup.setTitle(getBundle().getString("settings.delete.popup.title"));
            confirmPopup.setHeaderText(getBundle().getString("settings.delete.popup.headerText"));
            confirmPopup.setContentText(entityToDelete.toHtmlString());

            final Optional<ButtonType> result = confirmPopup.showAndWait();
            if (result.get() == ButtonType.OK) {
                appPropsRepository.delete(entityToDelete);
                properties.getItems().remove(cell.getIndex());

            }
        }
    }

    /**
     * Call this method on delete and add button.
     */
    private void recomputeNextButtonStatus() {
        final Integer nbOfRowsPerRequest = appPropsRepository.findByKey(ApplicationProperties.DB_CONFIG_NB_RESULTS_PER_PAGE.key).getValueAsInteger();
        final long totalEntries = appPropsRepository.count();

        // Button configuration
        if (pageNumber == 0) {
            previousPage.setDisable(true);
            previousPage.setVisible(false);
        } else {
            previousPage.setDisable(false);
            previousPage.setVisible(true);
        }

        if ((pageNumber + 1) * nbOfRowsPerRequest > totalEntries) {
            nextPage.setDisable(true);
            nextPage.setVisible(false);
        } else {
            nextPage.setDisable(false);
            nextPage.setVisible(true);
        }

    }

    public void doShowNextPage() {
        pageNumber++;
        loadDbValues();
    }

    public void doShowPreviousPage() {
        pageNumber--;
        loadDbValues();
    }

    public void doAddNewItem() {
        // FIXME GDZ
        LOGGER.info("Saving properties");
    }

    public void doSearch() {
        // FIXME GDZ
        LOGGER.info("Searching for property: ");
    }

}
