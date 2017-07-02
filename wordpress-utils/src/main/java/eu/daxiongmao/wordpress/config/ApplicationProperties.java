package eu.daxiongmao.wordpress.config;

/**
 * Database application's properties.
 * @author Guillaume Diaz
 *
 */
public enum ApplicationProperties {
	DB_VERSION("db.version"),
	APP_VERSION("app.version"),
	
	DB_CONFIG_NB_RESULTS_PER_PAGE("db.config.nbResultsPerPage"),
	
	FTP_HOSTNAME("ftp.hostname"),
	FPT_PORT("ftp.port"),
	FPT_USERNAME("ftp.username"),
	FTP_PASSWORD("ftp.password"),
	
	WORDPRESS_ROOT_RELATIVE_PATH("wordpress.root.relativePath");

	public final String key;
	
	private ApplicationProperties(String key) {
		this.key = key;
	}
	
}
