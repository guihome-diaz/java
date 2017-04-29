package eu.daxiongmao.prv.wordpress.model.files;

import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

public class DirectoryContentTest {

    @Test
    public void testNullCases() {
        DirectoryContent localDir = new DirectoryContent(null);
        DirectoryContent otherDir = new DirectoryContent(null);
        Assert.assertEquals(0, localDir.compareTo(otherDir));

        localDir = new DirectoryContent(Paths.get("/mnt/temp/dev/"));
        Assert.assertEquals(1, localDir.compareTo(otherDir));

        localDir = new DirectoryContent(null);
        otherDir = new DirectoryContent(Paths.get("/mnt/temp/dev/"));
        Assert.assertEquals(-1, localDir.compareTo(otherDir));
    }

    @Test
    public void basicAssertion() {
        final DirectoryContent localDir = new DirectoryContent(Paths.get("/mnt/temp/dev/"));
        final DirectoryContent otherDir = new DirectoryContent(Paths.get("/mnt/temp/dev"));
        Assert.assertEquals(0, localDir.compareTo(otherDir));
    }

    @Test
    public void differentTree_sameDepth() {
        DirectoryContent localDir = new DirectoryContent(Paths.get("/mnt/temp/dev/"));
        DirectoryContent otherDir = new DirectoryContent(Paths.get("/mnt/download/pictures/"));
        Assert.assertEquals(1, localDir.compareTo(otherDir));

        localDir = new DirectoryContent(Paths.get("/cooking/desserts"));
        otherDir = new DirectoryContent(Paths.get("/cooking/vegetables/"));
        Assert.assertEquals(-1, localDir.compareTo(otherDir));
    }

    @Test
    public void differentTree_anotherDepth() {
        DirectoryContent localDir = new DirectoryContent(Paths.get("/mnt/temp/dev/"));
        DirectoryContent otherDir = new DirectoryContent(Paths.get("/mnt/temp/dev/project"));
        Assert.assertEquals(-1, localDir.compareTo(otherDir));

        localDir = new DirectoryContent(Paths.get("/cooking/desserts/french"));
        otherDir = new DirectoryContent(Paths.get("/cooking/desserts/"));
        Assert.assertEquals(1, localDir.compareTo(otherDir));
    }
}
