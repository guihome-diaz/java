package eu.daxiongmao.wordpress.server.service.ftp;

import org.apache.commons.net.ftp.FTPFile;

/**
 * Generic interface to handle interaction with FTP file
 *
 * @author Guillaume Diaz
 * @version 1.0, oct. 2017
 * @since 1.0
 */
@FunctionalInterface
public interface FtpFileHandler {

    /**
     * To perform a particular action with a FTP file.
     *
     * @param file
     *            file to handle
     * @param directory
     *            directory of the file. CAREFUL! This is the FTP path == relative directory
     */
    void handle(FTPFile file, String directory);

}
