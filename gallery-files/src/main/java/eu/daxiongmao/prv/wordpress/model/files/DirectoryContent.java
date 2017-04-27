package eu.daxiongmao.prv.wordpress.model.files;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * Representation of a disk directory.
 *
 * @author Guillaume Diaz
 * @version 1.0 - April 2017
 */
public class DirectoryContent implements Serializable {

    private static final long serialVersionUID = 20170427L;

    private Path directory;

    private final Set<String> images = new HashSet<>();

    private final Set<String> videos = new HashSet<>();

    private final Set<String> backupFiles = new HashSet<>();

    private Path thumbsFolder;

    private Path dynamicFolder;

    public DirectoryContent(final Path directory) {
        this.directory = directory;
    }

    public synchronized void addImage(final Path image) {
        images.add(image.toString());
    }

    public synchronized void addBackupFile(final Path backupFile) {
        backupFiles.add(backupFile.toString());
    }

    public synchronized void addVideo(final Path video) {
        videos.add(video.toString());
    }

    public Set<String> getImages() {
        return images;
    }

    public Set<String> getBackupFiles() {
        return backupFiles;
    }

    public Set<String> getVideos() {
        return videos;
    }

    public Path getDirectory() {
        return directory;
    }

    public Path getThumbsFolder() {
        return thumbsFolder;
    }

    public synchronized void setThumbsFolder(final Path thumbsFolder) {
        this.thumbsFolder = thumbsFolder;
    }

    public Path getDynamicFolder() {
        return dynamicFolder;
    }

    public synchronized void setDynamicFolder(final Path dynamicFolder) {
        this.dynamicFolder = dynamicFolder;
    }

    @Override
    public String toString() {
        final StringBuilder log = new StringBuilder();
        log.append(String.format("%nDIRECTORY: %s%n---------------------------------------------------------------------------------", directory));
        log.append(String.format("%nhas Thumbs folder? %s%nhas Dynamic folder? %s", thumbsFolder == null ? "false" : "true", dynamicFolder == null ? "false" : "true"));
        getImages().stream().forEach(item -> log.append(String.format("%n   * Image : %s", item)));
        getVideos().stream().forEach(item -> log.append(String.format("%n   * Video : %s", item)));
        getBackupFiles().stream().forEach(item -> log.append(String.format("%n   * Backup: %s", item)));
        return log.toString();
    }

}
