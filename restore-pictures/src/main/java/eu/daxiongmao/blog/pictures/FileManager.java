package eu.daxiongmao.blog.pictures;

import eu.daxiongmao.blog.pictures.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * To handle files operations.
 * @version 1.0 (2024/10)
 * @since 2024/10
 * @author Guillaume Diaz
 */
public class FileManager {

    protected static final Logger logger = LogManager.getLogger(Constants.LOGGER_NAME);

    /**
     * To ensure all given images exist.
     * It returns ONLY IMAGES THAT DO NOT exist.
     * @param images images to check
     * @return images that do not exist on hard drive.
     */
    public Set<Image> ensureImagesExists(Set<Image> images) {
        Set<Image> missingImages = new HashSet<>();
        for (Image image : images) {
            String imagePath = image.galleryPathOneDrive() + File.pathSeparator + image.imageName();
            if (Files.notExists(Paths.get(imagePath))) {
                logger.warn(() -> "Image does not exist. imagePath=" + imagePath);
                missingImages.add(image);
            }
        }
        return missingImages;
    }

}
