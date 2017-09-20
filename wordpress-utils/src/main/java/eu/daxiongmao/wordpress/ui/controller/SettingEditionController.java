package eu.daxiongmao.wordpress.ui.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.GUIState;
import eu.daxiongmao.wordpress.Main;
import eu.daxiongmao.wordpress.server.dao.AppPropertyRepository;
import eu.daxiongmao.wordpress.server.model.AppProperty;
import eu.daxiongmao.wordpress.ui.convert.AppPropertyConvertor;
import eu.daxiongmao.wordpress.ui.dto.AppPropertyFx;
import eu.daxiongmao.wordpress.ui.session.AppSession;
import eu.daxiongmao.wordpress.ui.session.ScreenNames;
import eu.daxiongmao.wordpress.ui.view.SettingsView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@FXMLController
public class SettingEditionController extends AbstractFxmlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingEditionController.class);

    @FXML
    private TextField propId;
    @FXML
    private TextField propKey;
    @FXML
    private TextField propValue;
    @FXML
    private TextArea propDescription;

    @Autowired
    private AppPropertyRepository repository;

    private AppPropertyFx propModel;

    @FXML
    void initialize() {
        final Map<String, Object> editProperties = AppSession.SCREENS_PROPERTIES.get(ScreenNames.EDIT_PROPERTY.name());
        setPropModel((AppPropertyFx) editProperties.get("item"));

        // propId.textProperty().bindBidirectional(propModel.getIdProperty());
        propKey.textProperty().bindBidirectional(propModel.getKeyProperty());
        propValue.textProperty().bindBidirectional(propModel.getValueProperty());
        propDescription.textProperty().bindBidirectional(propModel.getDescriptionProperty());

        // Set page title
        GUIState.setContainerTitle("settings.property.editionTitle");
    }

    private void setPropModel(final AppPropertyFx propModel) {
        if (propModel != null) {
            this.propModel = propModel;
        } else {
            this.propModel = new AppPropertyFx();
        }

        // Set UI values
        if (this.propModel.getId() != null && this.propModel.getId() > 0) {
            propId.setText(Integer.toString(this.propModel.getId()));
        }
        if (this.propModel.getKey() != null) {
            propKey.setText(this.propModel.getKey());
        }
        if (this.propModel.getValue() != null) {
            propValue.setText(this.propModel.getValue());
        }
        if (this.propModel.getDescription() != null) {
            propDescription.setText(this.propModel.getDescription());
        }
    }

    public void doSave() {
        // Check args
        final StringBuilder errorLog = new StringBuilder();
        if (propKey.getText().trim().isEmpty() || propValue.getText().trim().isEmpty()) {
            final Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Missing properties");
            alert.setHeaderText(getBundle().getString("settings.property.validationHeader"));
            alert.setContentText(getBundle().getString("settings.property.validationError"));
            alert.showAndWait();
            return;
        }

        // Save value
        propModel.setKey(propKey.getText().trim());
        propModel.setValue(propValue.getText().trim());
        propModel.setDescription(propDescription.getText().trim());
        AppProperty dbPropToSave = AppPropertyConvertor.fxPropToDb(propModel);
        dbPropToSave = repository.save(dbPropToSave);

        // reload previous screen - it will reload the values from DB
        Main.showView(SettingsView.class);
    }

    public void doCancel() {
        // reload previous screen - it will reload the values from DB
        Main.showView(SettingsView.class);
    }

}

