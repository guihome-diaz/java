package eu.daxiongmao.blog.pictures.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Representation of a Wordpress NGG image and corresponding gallery.
 * @param galleryId gallery database ID
 * @param galleryName gallery name (as it appears on the website)
 * @param galleryPathOnServer gallery path on server
 * @param galleryPathOneDrive gallery path on OneDrive backup
 * @param imageName search image filename
 */
public record Image(
        int galleryId,
        String galleryName,
        String galleryPathOnServer,
        String galleryPathOneDrive,
        String imageName) implements Comparable<Image> {

    @Override
    public int compareTo(Image other) {
        int result = Integer.compare(this.galleryId, other.galleryId);
        if (result != 0) { return result; }

        return this.imageName.compareTo(other.imageName);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("galleryId=" + galleryId)
                .add("galleryName=" + galleryName)
                .add("galleryPathOnServer=" + galleryPathOnServer)
                .add("galleryPathOneDrive=" + galleryPathOneDrive)
                .add("imageName=" + imageName)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image image)) return false;
        return galleryId == image.galleryId && Objects.equals(imageName, image.imageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(galleryId, imageName);
    }

    public String getServerGalleryName() {
        String galleryName = galleryPathOnServer;
        if (galleryName.endsWith("/")) {
            galleryName = galleryName.substring(0, galleryName.length() - 1);
        }
        return galleryName.substring(galleryName.lastIndexOf('/') + 1);
    }
}
