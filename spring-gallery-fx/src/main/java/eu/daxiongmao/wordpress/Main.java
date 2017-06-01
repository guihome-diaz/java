package eu.daxiongmao.wordpress;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import eu.daxiongmao.wordpress.ui.view.RootPaneView;

@SpringBootApplication
public class Main extends AbstractJavaFxApplicationSupport {
    public static void main(final String[] args) {
        launchApp(Main.class, RootPaneView.class, args);
    }
}
