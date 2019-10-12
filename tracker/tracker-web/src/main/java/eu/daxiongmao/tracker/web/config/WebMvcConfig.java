package eu.daxiongmao.tracker.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.ServletContext;
import java.util.Locale;
import java.util.Properties;

/**
 * Configuration for Spring MVC. This is required to work smoothly with _thymeleaf_ template engine.
 * @author Guillaume Diaz
 * @since v0.0.1-SNAPSHOT (2019-09)
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ServletContext servletContext;

    @Autowired
    public WebMvcConfig(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Bean
    @Description("To retrieve application version (maven version)")
    public String applicationVersion() {
        try {
            Properties props = new Properties();
            props.load(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF"));
            return props.getProperty("Implementation-Version");
        } catch (Exception e) {
            return "LOCAL";
        }
    }

    /**
     * To set which locale is currently used.
     * @return language to use for UI rendering
     */
    @Bean
    public LocaleResolver localeResolver(){
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        // use English as default
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        // if set to "-1" cookie is deleted when browser shuts down
        // for 1 day set to "24*60*60"
        localeResolver.setCookieMaxAge(24*60*60);
        return  localeResolver;
    }

    /**
     * To listen to all incoming requests and catch the parameter "lang".
     * This is used to override browser's language through request param.
     * <ul>Examples:
     * <li>http://localhost:9090/?lang=fr</li>
     * <li>http://localhost:9090/dashboard?lang=es</li>
     * </ul>
     * @return language interceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    /**
     * To add some interceptors to all requests
     * @param registry interceptors registry. Add your interceptor(s) over here, mind the order!
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }

    private boolean isSupportedLocale(Locale userLocale) {
        return (Locale.ENGLISH.getLanguage().equals(userLocale.getLanguage()))
                || (Locale.FRENCH.getLanguage().equals(userLocale.getLanguage()))
                || (Locale.GERMAN.getLanguage().equals(userLocale.getLanguage()))
                || (Locale.forLanguageTag("nl").equals(userLocale.getLanguage()))
                || (Locale.forLanguageTag("pt").equals(userLocale.getLanguage()));
    }

}
