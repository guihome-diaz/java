module galleryFiles {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    // Because Logback 1.3.x is already Java9 ready and has the latest SLF4J embedded,
    // you can rely on that single line
    requires org.slf4j;
    // Apache commons
    requires commons.net;

    opens eu.daxiongmao.prv.wordpress to org.slf4j, javafx.fxml, javafx.base, javafx.controls, javafx.web, javafx.graphics;
    opens eu.daxiongmao.prv.wordpress.controller to org.slf4j, javafx.fxml, javafx.base, javafx.controls, javafx.web, javafx.graphics;
    opens eu.daxiongmao.prv.wordpress.model.ui to org.slf4j, javafx.fxml, javafx.base, javafx.controls, javafx.web, javafx.graphics;
    opens eu.daxiongmao.prv.wordpress.model.files to org.slf4j, javafx.fxml, javafx.base, javafx.controls, javafx.web, javafx.graphics;

    exports eu.daxiongmao.prv.wordpress;
}