package eu.daxiongmao.prv.fx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Component
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @FXML
    TextField email;
    @FXML
    PasswordField password;
    @FXML
    Button loginButton, registerButton;

    public void doLogin() {
        LOGGER.info("Login attempt");
        // FIXME
    }

    public void doRegister() {
        // FIXME
    }


}
