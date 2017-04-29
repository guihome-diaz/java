package eu.daxiongmao.prv.wordpress.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.daxiongmao.prv.wordpress.model.files.DirectoryContent;

public class FileServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceTest.class);

    @Test
    public void listGalleryDirectories_TestReadAll() throws IOException, URISyntaxException {
        final Path root = Paths.get(FileServiceTest.class.getClassLoader().getResource("dataset").toURI());

        final FileService serviceToTest = new FileService();
        final List<DirectoryContent> directories = serviceToTest.getDirectories(root);

        Assert.assertNotNull(directories);
        Assert.assertFalse(directories.isEmpty());
        Assert.assertEquals(7, directories.size());
        directories.forEach(item -> LOGGER.info(item.toString()));
    }

    @Ignore("This requires real files on the disk into a particular path. It cannot run anywhere: this is not a UNIT test but a VALIDATION test")
    @Test
    public void listGalleryDirectories_RealPathOnHardDrive_Test() throws IOException {
        final Path root = Paths.get("/mnt/data/Temp/Photos/xiongmaos/gallery/");

        final FileService serviceToTest = new FileService();
        final List<DirectoryContent> directories = serviceToTest.getDirectories(root);

        Assert.assertNotNull(directories);
        Assert.assertFalse(directories.isEmpty());
        directories.forEach(item -> LOGGER.info(item.toString()));
    }

}
