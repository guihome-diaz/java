package eu.daxiongmao.wordpress.server.service.ftp;

import org.apache.commons.net.ftp.FTPFile;

/**
 * Generic interface to filter FTP files.
 *
 * @author Guillaume Diaz
 * @version 1.0, oct. 2017
 * @since 1.0
 */
@FunctionalInterface
public interface FtpFileFilter {

    /**
     * To check if the given file matches some filters.
     *
     * @param file
     *            file to analyze
     * @return Flag
     *         <ul>
     *         <li>True: the file has to be kept / processed later on</li>
     *         <li>False: the given file doesn't match the filter</li>
     *         </ul>
     */
    boolean filter(FTPFile file);
}
