package eu.daxiongmao.wordpress.server;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        insertIfNotExist(new AppProperty("db.version", "1.0", "Version of the database"));
        insertIfNotExist(new AppProperty("app.version", "1.0", "Version of the current application"));
        insertIfNotExist(new AppProperty("db.config.nbResultsPerPage", "15", "To set the number of raw to return per page"));
        insertIfNotExist(new AppProperty("ftp.hostname", null, "FTP server hostname (ex: ftp.daxiongmao.eu)"));
        insertIfNotExist(new AppProperty("ftp.port", "21", "FTP server control port (web standard is 21)"));
        insertIfNotExist(new AppProperty("ftp.username", null, "FTP username"));
        insertIfNotExist(new AppProperty("ftp.password", null, "FTP password"));
        insertIfNotExist(
                new AppProperty("wordpress.root.relativePath", "/www/", "Relative path on the FTP server to the Wordpress root (ex: /www or /www/myBlog)"));
        insertIfNotExist(
                new AppProperty("local.backup.folder", null, "Local folder for backup. This is where FTP files will be download (ex: D:\\Backup\\myBlog)"));
    }

    /**
     * To insert a property if does not already exists
     *
     * @param prop
     *            property to insert
     * @return property has it was insert or retrieve from database or NULL if given property is not valid
     */
    AppProperty insertIfNotExist(final AppProperty prop) {
        if (prop == null || StringUtils.isEmpty(prop)) {
            return null;
        }

        AppProperty dbProperty = appPropRepository.findByKey(prop.getKey());
        if (dbProperty == null) {
            dbProperty = appPropRepository.save(prop);
        }
        return dbProperty;
    }
}
