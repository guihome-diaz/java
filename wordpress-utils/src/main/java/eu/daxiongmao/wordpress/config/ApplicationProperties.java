package eu.daxiongmao.wordpress.config;

/**
 * Database application's properties.<br>
 * Each property will be tested for on boot and created with default value if it doesn't exists yet
 * 
 * @author Guillaume Diaz
 *
 */
public enum ApplicationProperties {
    DB_VERSION("db.version", "1.0", "Version of the database"),
    APP_VERSION("app.version", "1.0", "Version of the current application"),

    // Target website
    WORDPRESS_WEBSITE_URL("wordpress.site.url", "http://daxiongmao.eu", "Wordpress website URL"),

    // Navigation settings
    DB_CONFIG_NB_RESULTS_PER_PAGE("db.config.nbResultsPerPage", "15", "To set the number of raw to return per page"),

    // FTP settings
    FTP_HOSTNAME("ftp.hostname", null, "FTP server hostname (ex: ftp.daxiongmao.eu)"),
    FTP_PORT("ftp.port", "21", "FTP server control port (web standard is 21)"),
    FTP_USERNAME("ftp.username", null, "FTP username"),
    FTP_PASSWORD("ftp.password", null, "FTP password"),
    FTP_WORDPRESS_RELATIVE_PATH("wordpress.root.relativePath", "/www/", "Relative path on the FTP server to the Wordpress root (ex: /www or /www/myBlog)"),

    // Where to save the file?
    LOCAL_BACKUP_FOLDER("local.backup.folder", null, "Local folder for backup. This is where the files will be downloaded (ex: D:\\Backup\\myBlog)");

    public final String key;
    public final String defaultValue;
    public final String description;

    private ApplicationProperties(final String key, final String defaultValue, final String description) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.description = description;
    }

}
