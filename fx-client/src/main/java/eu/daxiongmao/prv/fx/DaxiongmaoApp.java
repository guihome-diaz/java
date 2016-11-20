package eu.daxiongmao.prv.fx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The following startup class is based on:
 * <ul>
 * <li>Personal template</li>
 * <li><i><a href="https://www.youtube.com/watch?v=hjeSOxi3uPg">Integrating Spring with JavaFX tutorial</a></i></li>
 * <li><i><a href="https://www.youtube.com/watch?v=RifjriAxbw8">Switching scenes in JavaFX</a></i></li>
 * </ul>
 * @author xinxiongmao
 *
 */
@Lazy
@SpringBootApplication
public class DaxiongmaoApp extends Application {

    private ConfigurableApplicationContext springContext;

    @Qualifier("rootView")
    @Autowired
    private ConfigurationControllers.View view;

    public static void main(final String[]   args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        // Init and load spring
        SpringApplicationBuilder builder = new SpringApplicationBuilder(DaxiongmaoApp.class);
        String[] args = getParameters().getRaw().stream().toArray(String[]::new);
        // You must set "headless" flag for TestFX integration testing or else you will get a java.awt.HeadlessException during tests
        builder.headless(false);
        springContext = builder.run(args);

        springContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void start(final Stage stage) {
        stage.setTitle("Daxiongmao");
        stage.setScene(new Scene(view.getView()));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        // Stop JavaFX
        super.stop();
        // Stop spring
        springContext.close();
    }

}
