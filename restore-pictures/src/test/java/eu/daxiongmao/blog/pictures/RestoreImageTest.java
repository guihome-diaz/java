package eu.daxiongmao.blog.pictures;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RestoreImageTest {

    public void restoreImages() throws IOException {
        Path targetFolder = Paths.get("E:/Temp/Blog");
        Path excelFile = Paths.get("src", "test", "resources", "family_blog_ngg_gallery.xlsx");
        RestoreImage restoreImage = new RestoreImage();
        restoreImage.restoreImages(excelFile.toString(), targetFolder.toString());
    }
}
