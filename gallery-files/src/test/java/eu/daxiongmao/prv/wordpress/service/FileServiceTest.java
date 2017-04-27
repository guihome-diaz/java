package eu.daxiongmao.prv.wordpress.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.daxiongmao.prv.wordpress.model.files.DirectoryContent;

public class FileServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceTest.class);

    @Test
    public void listGalleryDirectoriesTest() throws IOException {
        final Path root = Paths.get("/mnt/data/Temp/Photos/xiongmaos/gallery/");

        final FileService serviceToTest = new FileService();
        final List<DirectoryContent> directories = serviceToTest.getDirectories(root);

        Assert.assertNotNull(directories);
        Assert.assertFalse(directories.isEmpty());
        directories.forEach(item -> LOGGER.info(item.toString()));

    }

}
