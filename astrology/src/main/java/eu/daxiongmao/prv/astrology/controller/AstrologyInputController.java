package eu.daxiongmao.prv.astrology.controller;

import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import eu.daxiongmao.prv.astrology.AstrologyApp;
import eu.daxiongmao.prv.astrology.model.chinese.ChineseYear;
import eu.daxiongmao.prv.astrology.model.western.WesternZodiac;
import eu.daxiongmao.prv.astrology.service.ChineseZodiacService;
import eu.daxiongmao.prv.astrology.service.WesternZodiacService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class AstrologyInputController implements Initializable {

    public AstrologyInputController() {
        super();
    }

    private ResourceBundle bundle;
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @FXML
    VBox container;

    @FXML
    Label pageTitle;

    @FXML
    Label birthDateText;

    @FXML
    DatePicker birthDateInput;

    @FXML
    Button computeButton;

    @FXML
    Label copyrightText;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        bundle = resources;
        birthDateInput.setShowWeekNumbers(true);

        final StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

            @Override
            public String toString(final LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(final String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        birthDateInput.setConverter(converter);
        birthDateInput.setPromptText(DATE_FORMAT.toLowerCase());
        birthDateInput.requestFocus();
    }

    public void doComputeAstrology() throws URISyntaxException {
        AstrologyApp.getInstance().loadPage(AstrologyApp.ASTROLOGY_RESULTS);
        final LocalDate birthDate = birthDateInput.getValue();
        final ChineseYear chinese = ChineseZodiacService.getChineseBirthYear(birthDate);
        final WesternZodiac western = WesternZodiacService.getWesternZodiac(birthDate);
        AstrologyResultsController.getInstance().setZodiac(birthDate, chinese, western);
    }

}
