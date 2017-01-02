package eu.daxiongmao.prv.astrology.controller;

import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import eu.daxiongmao.prv.astrology.AstrologyApp;
import eu.daxiongmao.prv.astrology.model.chinese.ChineseYear;
import eu.daxiongmao.prv.astrology.model.western.WesternZodiac;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class AstrologyResultsController implements Initializable {

    private static AstrologyResultsController instance = null;

    private ResourceBundle bundle;

    @FXML
    VBox container;

    // Main attributes
    @FXML
    Label pageTitle;
    @FXML
    Label currentDate;
    @FXML
    ImageView homepageButton;
    @FXML
    ImageView quitButton;

    // Western zodiac
    @FXML
    VBox western;
    @FXML
    Label westernTitle;
    @FXML
    VBox westernPicture;
    @FXML
    ImageView westernSignImage;
    @FXML
    Label westernSignName;
    @FXML
    VBox westernText;
    @FXML
    Label westernSignTitle;
    @FXML
    WebView westernSignText;

    // Chinese zodiac
    @FXML
    VBox chinese;
    @FXML
    Label chineseTitle;
    @FXML
    VBox chinesePicture;
    @FXML
    ImageView chineseSignImage;
    @FXML
    Label chineseSignName;
    @FXML
    VBox chineseText;
    @FXML
    Label chineseSignTitle;
    @FXML
    WebView chineseSignText;

    public AstrologyResultsController() {
        super();
        // I suppose this screen always appears after the same set of actions ; it is not bound to any application or previous state.
        // You must call a particular method to refresh the content
        AstrologyResultsController.instance = this;
    }

    public static synchronized AstrologyResultsController getInstance() {
        if (instance == null) {
            new AstrologyResultsController();
        }
        return AstrologyResultsController.instance;
    }

    public void onHomepageClick() {
        AstrologyApp.getInstance().loadPage(AstrologyApp.ASTROLOGY_INPUT);
    }

    public void reset() throws URISyntaxException {
        currentDate.setText("No date");
        // WESTERN
        setWesternSign(null);

        // CHINESE
        setChineseSign(null);
    }

    public void setZodiac(final LocalDate birthDate, final ChineseYear chinese, final WesternZodiac western) throws URISyntaxException {
        final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("E dd MMM yyyy", AstrologyApp.getInstance().getLang());
        currentDate.setText(birthDate.format(dateTimeFormat));

        // WESTERN
        setWesternSign(western);

        // CHINESE
        setChineseSign(chinese);
    }

    private void setWesternSign(final WesternZodiac sign) {
        // Default values
        String imageUrl = "/img/unknown.png";
        westernSignName.setText("-?-");
        westernSignTitle.setText("-");

        final WebEngine webEngine = westernSignText.getEngine();
        webEngine.setUserStyleSheetLocation(getClass().getResource("/css/astrology.css").toString());
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(westernSignText);

        if (sign == null) {
            webEngine.loadContent("You must select a date on the homepage first");
        } else {
            // Text
            westernSignTitle.setText(bundle.getString("western.zodiac." + sign.name().toLowerCase() + ".name"));
            webEngine.loadContent(bundle.getString("western.zodiac." + sign.name().toLowerCase() + ".description"));

            // Image
            switch (sign) {
            case AQUARIUS:
                imageUrl = "/img/western_zodiac/zodiac_01_aquarius.png";
                westernSignName.setText("♒");
                break;
            case PISCES:
                imageUrl = "/img/western_zodiac/zodiac_02_pisces.png";
                westernSignName.setText("♓");
                break;
            case ARIES:
                imageUrl = "/img/western_zodiac/zodiac_03_aries.png";
                westernSignName.setText("♈");
                break;
            case TAURUS:
                imageUrl = "/img/western_zodiac/zodiac_04_taurus.png";
                westernSignName.setText("♉");
                break;
            case GEMINI:
                imageUrl = "/img/western_zodiac/zodiac_05_gemini.png";
                westernSignName.setText("♊");
                break;
            case CANCER:
                imageUrl = "/img/western_zodiac/zodiac_06_cancer.png";
                westernSignName.setText("♋");
                break;
            case LEO:
                imageUrl = "/img/western_zodiac/zodiac_07_leo.png";
                westernSignName.setText("♌");
                break;
            case VIRGO:
                imageUrl = "/img/western_zodiac/zodiac_08_virgo.png";
                westernSignName.setText("♍");
                break;
            case LIBRA:
                imageUrl = "/img/western_zodiac/zodiac_09_libra.png";
                westernSignName.setText("♎");
                break;
            case SCORPIO:
                imageUrl = "/img/western_zodiac/zodiac_10_scorpio.png";
                westernSignName.setText("♏");
                break;
            case SAGITTARUS:
                imageUrl = "/img/western_zodiac/zodiac_11_sagittarius.png";
                westernSignName.setText("♐");
                break;
            case CAPRICORN:
                imageUrl = "/img/western_zodiac/zodiac_12_capricorn.png";
                westernSignName.setText("♑");
                break;
            default:
                break;
            }
        }

        final URL url = getClass().getResource(imageUrl);
        final Image picture = new Image(url.toExternalForm());
        westernSignImage.setImage(picture);
        // Max width = 150px
        westernSignImage.setFitWidth(150);
        westernSignImage.setPreserveRatio(true);
        westernSignImage.setSmooth(true);
        westernSignImage.setCache(true);
    }

    private void setChineseSign(final ChineseYear year) throws URISyntaxException {
        // Default values
        String imageUrl = "/img/unknown.png";
        chineseSignName.setText("-?-");
        chineseSignTitle.setText("-");

        final WebEngine webEngine = chineseSignText.getEngine();
        webEngine.setUserStyleSheetLocation(getClass().getResource("/css/astrology.css").toString());
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(chineseSignText);

        if (year == null) {
            webEngine.loadContent("You must select a date on the homepage first");
        } else {
            // Text
            final String signNameI18N = bundle.getString("chinese.zodiac." + year.getZodiac().name().toLowerCase() + ".name");
            final String elementI18N = bundle.getString("chinese.zodiac.element") + bundle.getString("chinese.zodiac." + year.getElement().name().toLowerCase());
            final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_LOCAL_DATE;
            final String timeLog = String.format("%s -> %s", year.getStartDate().format(dateTimeFormat), year.getEndDate().format(dateTimeFormat));
            chineseSignTitle.setText(String.format("%s - %s [%s]", signNameI18N, elementI18N, timeLog));

            webEngine.loadContent(bundle.getString("chinese.zodiac." + year.getZodiac().name().toLowerCase() + ".description"));

            // Image
            chineseSignName.setText(String.format("%s [%s]", year.getZodiac().getChineseSign(), year.getZodiac().getChineseName()));
            switch (year.getZodiac()) {
            case RAT:
                imageUrl = "/img/chinese_zodiac/chinese_zodiac_01_rat.png";
                break;
            case OX:
                imageUrl = "/img/chinese_zodiac/chinese_zodiac_02_ox.png";
                break;
            case TIGER:
                imageUrl = "/img/chinese_zodiac/chinese_zodiac_03_tiger.png";
                break;
            case RABBIT:
                imageUrl = "/img/chinese_zodiac/chinese_zodiac_04_rabbit.png";
                break;
            case DRAGON:
                imageUrl = "/img/chinese_zodiac/chinese_zodiac_05_dragon.png";
                break;
            case SNAKE:
                imageUrl = "/img/chinese_zodiac/chinese_zodiac_06_snake.png";
                break;
            case HORSE:
                imageUrl = "/img/chinese_zodiac/chinese_zodiac_07_horse.png";
                break;
            case GOAT:
                imageUrl = "/img/chinese_zodiac/chinese_zodiac_08_goat.png";
                break;
            case MONKEY:
                imageUrl = "/img/chinese_zodiac/chinese_zodiac_09_monkey.png";
                break;
            case ROOSTER:
                imageUrl = "/img/chinese_zodiac/chinese_zodiac_10_rooster.png";
                break;
            case DOG:
                imageUrl = "/img/chinese_zodiac/chinese_zodiac_11_dog.png";
                break;
            case PIG:
                imageUrl = "/img/chinese_zodiac/chinese_zodiac_12_pig.png";
                break;
            default:
                break;
            }
        }

        final URL url = getClass().getResource(imageUrl);
        final Image picture = new Image(url.toExternalForm());
        chineseSignImage.setImage(picture);
        // Max width = 150px
        chineseSignImage.setFitWidth(150);
        chineseSignImage.setPreserveRatio(true);
        chineseSignImage.setSmooth(true);
        chineseSignImage.setCache(true);
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        bundle = resources;
        homepageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                AstrologyApp.getInstance().loadPage(AstrologyApp.ASTROLOGY_INPUT);
            }
        });
        quitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }
}
