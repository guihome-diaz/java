package eu.daxiongmao.blog.pictures.model;

import java.util.Objects;
import java.util.StringJoiner;


/**
 * Representation of a Wordpress NGG image.
 * Each image is linked to a particular gallery.
 * @param gallery Wordpress NGG gallery. The current image belongs to this gallery only.
 * @param imageName search image filename
 */
public record Image(
        Gallery gallery,
        String imageName) {

    @Override
    public String toString() {
        return new StringJoiner(", ", "[", "]")
                .add("gallery=" + gallery)
                .add("imageName='" + imageName + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image image)) return false;
        return Objects.equals(gallery, image.gallery) && Objects.equals(imageName, image.imageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gallery, imageName);
    }
}
