package eu.daxiongmao.wordpress.server.service.ftp;

/**
 * This represent the current status and the action(s) to take while iterating over FTP directories.<br>
 * This class is only designed to be used by the {@link FtpService#listDirectoryContent(String, String, int, int)} method
 *
 * @author guillaume diaz
 * @version 1.0 - oct. 2017
 * @since oct. 2017, wordpress tool v1.0.9
 */
class FtpServiceIterator {

    /** Max. level to iterate over. == max. nb of sub-directories to go into (max recursion) */
    public final int maxLevel;

    /** Current level (= iteration number) */
    public final int level;

    /**
     * Parent directory || Root directory
     * <ul>
     * <li><strong>Application bootup</strong> (iteration 1): this is the "root" directory to scan.</li>
     * <li><strong>iteration N</strong>: reference of the parent [iteration - 1] folder</li>
     * </ul>
     */
    public final String parentDir;

    /**
     * Current sub-directory (optional).<br>
     * When available that means the algo is current iterating over a sub-folder
     */
    public final String currentDir;

    /** File filter (optional) - to only keep some particular files. If not set then all files will be processed. */
    public final FtpFileFilter filter;

    /** Action to perform on FTP files. */
    public final FtpFileHandler handler;

    /**
     * Constructor
     *
     * @param parentDir
     *            Parent directory || Root directory
     *            <ul>
     *            <li><strong>Application bootup</strong> (iteration 1): this is the "root" directory to scan.</li>
     *            <li><strong>iteration N</strong>: reference of the parent [iteration - 1] folder</li>
     *            </ul>
     * @param currentDir
     *            Current sub-directory (optional).<br>
     *            When available that means the algo is current iterating over a sub-folder
     * @param level
     *            Current level (= iteration number)
     * @param maxLevel
     *            Max. level to iterate over. == max. nb of sub-directories to go into (max recursion)
     * @param handler
     *            Action to perform on FTP files
     * @param filter
     *            File filter (optional) - to only keep some particular files. If not set then all files will be processed.
     */
    FtpServiceIterator(final String parentDir, final String currentDir, final int level, final int maxLevel, final FtpFileHandler handler,
            final FtpFileFilter filter) {
        this.parentDir = parentDir;
        this.currentDir = currentDir;
        this.level = level;
        this.maxLevel = maxLevel;
        this.handler = handler;
        this.filter = filter;
    }
}
