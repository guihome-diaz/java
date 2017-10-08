package eu.daxiongmao.wordpress.server;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import eu.daxiongmao.wordpress.config.ApplicationProperties;
import eu.daxiongmao.wordpress.server.dao.AppPropertyRepository;
import eu.daxiongmao.wordpress.server.model.AppProperty;

@Component
public class WordpressService {

    static final Logger LOGGER = LoggerFactory.getLogger(WordpressService.class);

    @Autowired
    private AppPropertyRepository appPropRepository;

    @PostConstruct
    public void init() {
        LOGGER.info("Checking default properties");

        // Default properties to add
        for (final ApplicationProperties prop : ApplicationProperties.values()) {
            insertIfNotExist(prop);
        }
    }

    /**
     * To insert a property if does not already exists
     *
     * @param prop
     *            property to insert
     * @return property has it was insert or retrieve from database or NULL if given property is not valid
     */
    AppProperty insertIfNotExist(final ApplicationProperties prop) {
        if (prop == null || StringUtils.isEmpty(prop)) {
            return null;
        }

        AppProperty dbProperty = appPropRepository.findByKey(prop.key);
        if (dbProperty == null) {
            dbProperty = appPropRepository.save(new AppProperty(prop.key, prop.defaultValue, prop.description));
        }
        return dbProperty;
    }
}
