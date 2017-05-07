package eu.daxiongmao.prv.wordpress.service;

import java.util.Map;

import org.apache.commons.net.ftp.FTPFile;

/**
 * To handle a FTP file processing.
 *
 * @author Guillaume Diaz
 * @version 1.0 - May 2017
 */
public interface FtpFileHandler {

    /**
     * To handle a FTP item processing.
     *
     * @param ftpItem
     *            FTP item (file | directory to process.
     * @param filesToKeepAndProcess
     *            current file will be added to this map if all conditions are OK
     */
    public void handleFtpFile(FTPFile ftpItem, Map<String, FTPFile> filesToKeepAndProcess);

}
