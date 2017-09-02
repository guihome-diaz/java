package de.felixroske.jfxsupport;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * This class is derived from {@link AbstractFxmlView}
 * <p>
 * It provides the support of i18n and the ability to use the view's ResourceBundle into the corresponding controller.
 * </p>
 *
 * @author Guillaume Diaz
 */
public class AbstractFxmlController {

    @Autowired
    protected ConfigurableApplicationContext applicationContext;

    private ResourceBundle bundle;

    void setBundle(final ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

}
