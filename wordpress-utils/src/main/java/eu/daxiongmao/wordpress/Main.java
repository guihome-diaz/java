package eu.daxiongmao.wordpress;

import java.util.Locale;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import eu.daxiongmao.wordpress.ui.view.RootPaneView;
import eu.daxiongmao.wordpress.ui.view.component.DaxiongmaoSplashScreen;

/**
 * To start the application.<br>
 * One of the things the @SpringBootApplication does is a component scan. But, it only scans on sub-packages.
 * i.e. if you put that class in com.mypackage, then it will scan for all classes in sub-packages i.e. <code>com.mypackage.*.</code>
 *
 */
@SpringBootApplication
@ComponentScan({"eu.daxiongmao.wordpress"})
@EntityScan("eu.daxiongmao.wordpress.server.model")
@EnableJpaRepositories("eu.daxiongmao.wordpress.server.dao")
public class Main extends AbstractJavaFxApplicationSupport {

    public static void main(final String[] args) {
        final DaxiongmaoSplashScreen splash = new DaxiongmaoSplashScreen();
        launchApp(Main.class, RootPaneView.class, splash, new Locale("en", "GB"), args);
    }

}