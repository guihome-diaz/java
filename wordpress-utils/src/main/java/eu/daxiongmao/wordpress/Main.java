package eu.daxiongmao.wordpress;

import java.util.Locale;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import eu.daxiongmao.wordpress.ui.view.RootPaneView;
import eu.daxiongmao.wordpress.ui.view.component.DaxiongmaoSplashScreen;

@SpringBootApplication
public class Main extends AbstractJavaFxApplicationSupport {
    public static void main(final String[] args) {
        final DaxiongmaoSplashScreen splash = new DaxiongmaoSplashScreen();
        launchApp(Main.class, RootPaneView.class, splash, new Locale("en", "GB"), args);
    }
}
