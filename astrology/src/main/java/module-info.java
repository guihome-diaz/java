module astrology {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    // Because Logback 1.3.x is already Java9 ready and has the latest SLF4J embedded,
    // you can rely on that single line
    requires org.slf4j;

    opens eu.daxiongmao.prv.astrology to org.slf4j, javafx.fxml, javafx.base, javafx.controls, javafx.web, javafx.graphics;
    opens eu.daxiongmao.prv.astrology.controller to org.slf4j, javafx.fxml, javafx.base, javafx.controls, javafx.web, javafx.graphics;

    exports eu.daxiongmao.prv.astrology;
}