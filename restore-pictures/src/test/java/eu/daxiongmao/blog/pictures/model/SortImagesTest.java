package eu.daxiongmao.blog.pictures.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SortImagesTest {

    @Test
    void sortImages() {
        // Given
        Image image01 = new Image(10, "Gallery_01", "testGalleryServer_01", "testGalleryOneDrive_01", "image_01");
        Image image02 = new Image(5, "Gallery_02", "testGalleryServer_02", "testGalleryOneDrive_02", "image_03");
        Image image03 = new Image(5, "Gallery_02", "testGalleryServer_02", "testGalleryOneDrive_02", "image_01");
        Image image04 = new Image(5, "Gallery_02", "testGalleryServer_02", "testGalleryOneDrive_02", "image_02");
        Image image05 = new Image(10, "Gallery_01", "testGalleryServer_01", "testGalleryOneDrive_01", "image_03");
        Image image06 = new Image(10, "Gallery_01", "testGalleryServer_01", "testGalleryOneDrive_01", "image_02");
        List<Image> images = new ArrayList<>();
        images.add(image01);
        images.add(image02);
        images.add(image03);
        images.add(image04);
        images.add(image05);
        images.add(image06);

        // Do
        Collections.sort(images);

        // assertions
        Assertions.assertEquals(image03, images.get(0));
        Assertions.assertEquals(image04, images.get(1));
        Assertions.assertEquals(image02, images.get(2));
        Assertions.assertEquals(image01, images.get(3));
        Assertions.assertEquals(image06, images.get(4));
        Assertions.assertEquals(image05, images.get(5));
    }

    @Test
    void testGalleryPath() {
        Image image01 = new Image(10, "Gallery_01", "/opt/blog/wp-content/gallery/2024/testGalleryServer_01", "testGalleryOneDrive_01", "image_01");
        Image image02 = new Image(5, "Gallery_02", "/opt/blog/wp-content/gallery/2024/testGalleryServer_02/", "testGalleryOneDrive_02", "image_03");
        Assertions.assertEquals("testGalleryServer_01", image01.getServerGalleryName());
        Assertions.assertEquals("testGalleryServer_02", image02.getServerGalleryName());
    }
}


