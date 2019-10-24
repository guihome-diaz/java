module astrology {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires slf4j.api;

    opens eu.daxiongmao.prv.astrology to slf4j.api, javafx.fxml, javafx.base, javafx.controls, javafx.web, javafx.graphics;
    opens eu.daxiongmao.prv.astrology.controller to slf4j.api, javafx.fxml, javafx.base, javafx.controls, javafx.web, javafx.graphics;

    exports eu.daxiongmao.prv.astrology;
}