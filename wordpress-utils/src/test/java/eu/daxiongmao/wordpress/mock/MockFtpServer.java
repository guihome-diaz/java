package eu.daxiongmao.wordpress.mock;

import java.io.File;
import java.net.URISyntaxException;

import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To mock the FTP server.
 *
 * @author Guillaume Diaz
 * @version 1.0, 2017-10
 */
public class MockFtpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockFtpServer.class);

    /** Private factory design pattern. */
    private MockFtpServer() {
    }

    /**
     * <p>
     * To start a fake FTP server that can be used for unit and integration tests.<br>
     * This is based on <a href="http://mockftpserver.sourceforge.net/fakeftpserver-getting-started.html">mock FTP server</a><br>
     * The FTP will start with mock content, according to the provided <code>mockRoot</code>
     * </p>
     * <h3>Port management</h3>
     * <p>
     * This will start the server on a particular port (if provided), else it will use a free port.<br>
     * To get the dynamic port, do <code>fakeFtpServer.getServerControlPort();</code> on the return object.
     * </p>
     *
     * @param mockRoot
     *            real content to use to populate the fake FTP server. This must be a directory, all its content and sub-folder will be added to the server
     * @param relativeRoot
     *            relative root for the FTP server. This is usually <code>'/'</code> or <code>'/www'</code>
     * @param ftpUserAccount
     *            user account to use with the FTP server. You must provide it!
     * @param port
     *            (optional) If provided the fake FTP server will listen to a particular port ; else it will use any free port
     * @return Fake FTP server instance
     * @throws URISyntaxException
     *             failed to populate the fake FTP server with mock data.
     */
    public static FakeFtpServer startFakeFtpServer(final File mockRoot, final String relativeRoot, final UserAccount ftpUserAccount, final Integer port)
            throws URISyntaxException {
        final FakeFtpServer fakeFtpServer = new FakeFtpServer();

        // Port to listen to
        // 0 == random free port
        // use a port number > 1000 if you want to avoid some issues
        if (port != null) {
            fakeFtpServer.setServerControlPort(port);
        } else {
            fakeFtpServer.setServerControlPort(0);
        }

        // Create files and directories list
        final FileSystem ftpFileSystem = new UnixFakeFileSystem();
        if (!mockRoot.exists() && !mockRoot.isDirectory()) {
            throw new IllegalArgumentException("Cannot init mock FTP: invalid root folder: " + mockRoot.toString());
        }
        addDirectoryToMockFtpServer(ftpFileSystem, mockRoot, relativeRoot);
        fakeFtpServer.setFileSystem(ftpFileSystem);

        // Create users
        fakeFtpServer.addUserAccount(ftpUserAccount);

        // Start server (this will actually bind the port)
        fakeFtpServer.start();

        // To get the running port, use the code line below
        // int fakeFtpPort = fakeFtpServer.getServerControlPort();

        // Return the server.
        return fakeFtpServer;
    }

    /**
     * Add the given directory and all it's files recursively to the Mock FTP server.
     *
     * @param ftpFileSystem
     *            Mock FTP server to populate
     * @param realDirectory
     *            real directory to add and analyze == this is the file / folder on the local system
     * @param relativePath
     *            FTP relative path to use for the file
     */
    private static void addDirectoryToMockFtpServer(final FileSystem ftpFileSystem, final File realDirectory, final String relativePath) {
        // Declare the new directory (skip '/' because this is the default)
        if (!"/".equals(relativePath)) {
            ftpFileSystem.add(new DirectoryEntry(relativePath));
        }

        // Add files recursively
        for (final File file : realDirectory.listFiles()) {
            // skip parent directory and directory itself
            if (file.getName().equals(".") || file.getName().equals("..")) {
                continue;
            }
            if (file.isDirectory()) {
                // recursive call
                if (!"/".equals(relativePath)) {
                    addDirectoryToMockFtpServer(ftpFileSystem, file, relativePath + "/" + file.getName());
                } else {
                    addDirectoryToMockFtpServer(ftpFileSystem, file, "/" + file.getName());
                }
            } else {
                final FileEntry ftpFile = new FileEntry(relativePath + "/" + file.getName(), "jUnit content");
                if (ftpFileSystem.getEntry(ftpFile.getPath()) != null) {
                    // for security only, it should never occur!
                    LOGGER.warn("Skipping duplicate FTP entry for: " + ftpFile.getPath());
                } else {
                    ftpFileSystem.add(ftpFile);
                    if (LOGGER.isTraceEnabled()) {
                        LOGGER.trace(" * mock FTP file: " + ftpFile.getPath());
                    }
                }
            }
        }
    }

}
