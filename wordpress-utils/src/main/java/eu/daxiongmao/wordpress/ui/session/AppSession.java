package eu.daxiongmao.wordpress.ui.session;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains information related to the user session and data that is shared between screens. <br>
 * This is an utility class that must be used as "static" only.<br>
 * TODO: this is a workaround with JavaFX. Feel free to improve!
 *
 * @author Guillaume Diaz
 */
public class AppSession {

    /**
     * Private factory design pattern.
     */
    private AppSession() {
    }

    /**
     * Dictionary of {screen name, related properties [key, value]}.<br>
     * Use that to exchange information between screens.
     * <ul>
     * <li>main key: screen name</li>
     * <ul>
     * <li>2nd key: property name</li>
     * <li>Value: related properties [you'll have to cast them before use</li>
     * </ul>
     * </ul>
     */
    public static final Map<String, Map<String, Object>> SCREENS_PROPERTIES = new HashMap<>();


}
