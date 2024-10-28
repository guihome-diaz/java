package eu.daxiongmao.blog.pictures.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Representation of a Wordpress NGG Gallery.
 * These are the main attributes to perform image restoration.
 * @param galleryId gallery database ID
 * @param galleryName gallery name (as it appears on the website)
 * @param galleryPathOnServer gallery path on server
 * @param galleryPathOneDrive gallery path on OneDrive backup
 */
public record Gallery(
        int galleryId,
        String galleryName,
        String galleryPathOnServer,
        String galleryPathOneDrive) {
    @Override
    public String toString() {
        return new StringJoiner(", ", "[", "]")
                .add("galleryId=" + galleryId)
                .add("galleryName='" + galleryName + "'")
                .add("galleryPathOnServer='" + galleryPathOnServer + "'")
                .add("galleryPathOneDrive='" + galleryPathOneDrive + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gallery gallery)) return false;
        return galleryId == gallery.galleryId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(galleryId);
    }
}
