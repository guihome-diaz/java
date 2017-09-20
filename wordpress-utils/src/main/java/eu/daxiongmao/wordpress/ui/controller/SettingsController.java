package eu.daxiongmao.wordpress.ui.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.GUIState;
import eu.daxiongmao.wordpress.Main;
import eu.daxiongmao.wordpress.config.ApplicationProperties;
import eu.daxiongmao.wordpress.server.dao.AppPropertyRepository;
import eu.daxiongmao.wordpress.server.model.AppProperty;
import eu.daxiongmao.wordpress.ui.convert.AppPropertyConvertor;
import eu.daxiongmao.wordpress.ui.dto.AppPropertyFx;
import eu.daxiongmao.wordpress.ui.session.AppSession;
import eu.daxiongmao.wordpress.ui.session.ScreenNames;
import eu.daxiongmao.wordpress.ui.view.SettingEditionView;
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

    private static final int ICON_SIZE = 32;

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
    @FXML
    TableColumn<AppPropertyFx, AppPropertyFx> editColumn;

    @FXML
    private SettingEditionController propertyEditController;

    @Autowired
    private AppPropertyRepository appPropsRepository;

    private int pageNumber = 0;

    @FXML
    void initialize() {
        setupUi();
        loadDbValues();
        // Set page title
        GUIState.setContainerTitle("settings.pageTitle");
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

        // Setup columns size, bind the column to the table (in percentage) so they are always resized
        properties.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        propIdColumn.prefWidthProperty().bind(properties.widthProperty().multiply(0.03));
        propIdColumn.minWidthProperty().bind(properties.widthProperty().multiply(0.03));

        propKeyColumn.prefWidthProperty().bind(properties.widthProperty().multiply(0.25));
        propKeyColumn.minWidthProperty().bind(properties.widthProperty().multiply(0.25));

        propValueColumn.prefWidthProperty().bind(properties.widthProperty().multiply(0.25));
        propValueColumn.minWidthProperty().bind(properties.widthProperty().multiply(0.25));

        propDescriptionColumn.prefWidthProperty().bind(properties.widthProperty().multiply(0.35));
        propDescriptionColumn.minWidthProperty().bind(properties.widthProperty().multiply(0.35));

        editColumn.prefWidthProperty().bind(properties.widthProperty().multiply(0.05));
        editColumn.minWidthProperty().bind(properties.widthProperty().multiply(0.05));

        deleteColumn.prefWidthProperty().bind(properties.widthProperty().multiply(0.05));
        deleteColumn.minWidthProperty().bind(properties.widthProperty().multiply(0.05));

        setupDeleteColumn();
        setupEditColumn();
    }

    private void setupEditColumn() {
        // (1) Column will return the current object on click
        editColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        // (2) Column behavior
        editColumn.setCellFactory(editCell -> {
            final TableCell<AppPropertyFx, AppPropertyFx> cell = new TableCell<AppPropertyFx, AppPropertyFx>() {
                @Override
                public void updateItem(final AppPropertyFx item, final boolean empty) {
                    // Set icon
                    super.updateItem(item, empty);
                    if (item != null && item.getKey() != null && item.getId() != null) {
                        final HBox box= new HBox();
                        final URL url = getClass().getResource("/img/icons/icon-edit.png");
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
            cell.setOnMouseClicked(e -> setupEditActionAndConfirmDialog(cell));
            return cell;
        });
    }

    private void setupEditActionAndConfirmDialog(final TableCell<AppPropertyFx, AppPropertyFx> cell) {
        if (!cell.isEmpty()) {
            // register session values
            final Map<String, Object> editProperties = new HashMap<>();
            editProperties.put("item", cell.getItem());
            AppSession.SCREENS_PROPERTIES.put(ScreenNames.EDIT_PROPERTY.name(), editProperties);
            // display screen
            final SettingEditionController controller = applicationContext.getBean(SettingEditionController.class);
            Main.showView(SettingEditionView.class);
        }
    }

    private void setupDeleteColumn() {
        // (1) Column will return the current object on click
        deleteColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        // (2) Column behavior
        deleteColumn.setCellFactory(deleteCell -> {
            final TableCell<AppPropertyFx, AppPropertyFx> cell = new TableCell<AppPropertyFx, AppPropertyFx>() {
                @Override
                public void updateItem(final AppPropertyFx item, final boolean empty) {
                    // Set icon
                    super.updateItem(item, empty);
                    if (item != null && item.getKey() != null && item.getId() != null) {
                        final HBox box = new HBox();
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
        // register session values
        final Map<String, Object> newProperties = new HashMap<>();
        newProperties.put("item", new AppPropertyFx());
        AppSession.SCREENS_PROPERTIES.put(ScreenNames.EDIT_PROPERTY.name(), newProperties);
        // display screen
        final SettingEditionController controller = applicationContext.getBean(SettingEditionController.class);
        Main.showView(SettingEditionView.class);
    }

    public void doSearch() {
        final String searchValue = searchField.getText();
        if (StringUtils.isEmpty(searchValue)) {
            final Alert popup = new Alert(AlertType.WARNING);
            popup.setTitle("Warning");
            popup.setHeaderText(getBundle().getString("settings.search.noValue"));
        } else {
            LOGGER.info("Searching for property: " + searchValue);
            // FIXME GDZ
        }
    }

    public void showPrevious() {
        Main.showPreviousPage();
    }

}
