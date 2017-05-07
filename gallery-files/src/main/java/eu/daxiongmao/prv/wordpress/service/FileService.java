package eu.daxiongmao.prv.wordpress.service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.daxiongmao.prv.wordpress.model.files.DirectoryContent;

/**
 * To perform file operation related to WORDPRESS GALLERY files plugin.
 *
 * @author guillaume diaz
 * @version 1.0 - 2017/04
 */
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    private static final String GALLERY_PLUGIN_GENERATED_FOLDER_DYNAMIC = "dynamic";
    private static final String GALLERY_PLUGIN_GENERATED_FOLDER_THUMBS = "thumbs";

    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList(".png", ".jpg", ".jpeg", ".gif");
    private static final List<String> VIDEO_EXTENSIONS = Arrays.asList(".mov", ".mp4", ".m4v");
    private static final String IMAGE_BACKUP_ENDING = "_backup";

    /**
     * To delete a given path and all its content (recursive).
     *
     * @param directory
     *            root directory to delete
     * @throws IOException
     *             failed to perform file operation(s)
     */
    public void recursiveDelete(final Path directory) throws IOException {
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * To retrieve backup content - if it exists.
     *
     * @param ftpGalleryName
     *            Gallery name to retrieve
     * @param localBackupFolder
     *            local folder to analyse. Gallery must be in $folder/gallery/"ftpGalleryName"
     * @return folder content or empty map
     * @throws IOException
     *             failed to access local backup folder. are you sure it exists?
     */
    public Map<String, Path> getGalleryContent(final String ftpGalleryName, final Path localBackupFolder) throws IOException {
        final Map<String, Path> pictures = new ConcurrentHashMap<>();

        // Get files
        final Path galleryDir = Paths.get(localBackupFolder.toString(), "gallery", ftpGalleryName);
        if (Files.exists(galleryDir)) {
            Files.list(galleryDir).forEach((file) -> {
                if (Files.isRegularFile(file)) {
                    pictures.put(file.getFileName().toString(), file);
                }
            });
            LOGGER.info(String.format("Previous gallery '%s' found! It already has %s pictures", galleryDir, pictures.size()));
        }
        return pictures;
    }

    /**
     * <p>
     * To list directories and their respective content.<br>
     * (i) This function is based on a Wordpress blog structure. It will search for images and videos that are in:
     * </p>
     * <ul>
     * <li>Gallery plugin's folders</li>
     * <li>Upload directory</li>
     * </ul>
     * <p>
     * <strong>Important:</strong><br>
     * This will exclude <code>thumbs</code> and <code>dynamic</code> folders. == it will not scan them for content.
     * </p>
     * <p>
     * <i>Side effects:</i><br>
     * You might retrieve pictures and movies from other folders, however this was NOT designed for and tested.
     * </p>
     *
     * @param root
     *            folder to analyze - it will scan the sub-directories
     * @return list of directories that have images /and/or/ videos | backup + the description of the folders content
     * @throws IOException
     *             failed to access the given root
     */
    public List<DirectoryContent> getDirectories(final Path root) throws IOException {
        final Predicate<Path> predicate = (file -> isVideoFile(file) || isImageFile(file) || isBackupFile(file));
        final Set<Path> directoriesToAnalyse = listDirectoriesThatContains(root, predicate);

        final List<DirectoryContent> results = new ArrayList<>();
        directoriesToAnalyse.stream().forEach(path -> {
            final DirectoryContent res = analyseDirectory(path);
            if (res != null) {
                results.add(res);
            }
        });
        return results;
    }

    private Set<Path> listDirectoriesThatContains(final Path root, final Predicate<Path> predicate) throws IOException {
        final Set<Path> resultsDirectories = new HashSet<>();
        Files.walk(root)
                // only keep directories
                .filter(file -> Files.isDirectory(file)).filter(directory -> {
                    final String directoryName = directory.toString();
                    try {
                        // Exclude GALLERY plugin generated content
                        if (directoryName.endsWith(GALLERY_PLUGIN_GENERATED_FOLDER_DYNAMIC) || directoryName.endsWith(GALLERY_PLUGIN_GENERATED_FOLDER_THUMBS)) {
                            LOGGER.debug("Skipping folder: " + directoryName);
                            return false;
                        }
                        // Only keep directories that contains asked
                        if (Files.list(directory).anyMatch(predicate)) {
                            LOGGER.debug("New directory with video||image||backup detected: " + directoryName);
                            return true;
                        }
                    } catch (final IOException e) {
                        LOGGER.error("Failed to read folder: " + directoryName, e);
                    }
                    return false;
                }).forEach(directory -> resultsDirectories.add(directory));
        return resultsDirectories;
    }

    private DirectoryContent analyseDirectory(final Path file) {
        final DirectoryContent folderContent = new DirectoryContent(file);
        try {
            Files.list(file).forEach(path -> {
                if (isImageFile(path)) {
                    folderContent.addImage(path);
                } else if (isVideoFile(path)) {
                    folderContent.addVideo(path);
                } else if (isBackupFile(path)) {
                    folderContent.addBackupFile(path);
                } else if (path.toString().endsWith(GALLERY_PLUGIN_GENERATED_FOLDER_DYNAMIC)) {
                    folderContent.setDynamicFolder(path);
                } else if (path.toString().endsWith(GALLERY_PLUGIN_GENERATED_FOLDER_THUMBS)) {
                    folderContent.setThumbsFolder(path);
                }
            });
        } catch (final IOException e) {
            LOGGER.error("Failed to analyse directory " + file.toString(), e);
            return null;
        }
        return folderContent;
    }

    private boolean isImageFile(final Path file) {
        return isFileExtension(file, IMAGE_EXTENSIONS);
    }

    private boolean isVideoFile(final Path file) {
        return isFileExtension(file, VIDEO_EXTENSIONS);
    }

    private boolean isFileExtension(final Path file, final List<String> searchExtensions) {
        final String filename = file.toString().toLowerCase();
        if (Files.isRegularFile(file) && filename.contains(".")) {
            final String fileExtension = filename.substring(filename.lastIndexOf('.'));
            return searchExtensions.contains(fileExtension);
        } else {
            return false;
        }
    }

    private boolean isBackupFile(final Path file) {
        final String filename = file.toString().toLowerCase();
        if (Files.isRegularFile(file) && filename.endsWith(IMAGE_BACKUP_ENDING)) {
            return true;
        } else {
            return false;
        }
    }

}
