package eu.daxiongmao.prv.wordpress.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PathUtilsTest {

    @Test
    public void testNullCases() {
        Assert.assertEquals(0, PathUtils.comparePath(null, null));

        Path localDir = Paths.get("/mnt/temp/dev/");
        Assert.assertEquals(1, PathUtils.comparePath(localDir, null));

        localDir = null;
        final Path otherDir = Paths.get("/mnt/temp/dev/");
        Assert.assertEquals(-1, PathUtils.comparePath(localDir, otherDir));
    }

    @Test
    public void basicAssertion() {
        final Path localDir = Paths.get("/mnt/temp/dev/");
        final Path otherDir = Paths.get("/mnt/temp/dev");
        Assert.assertEquals(0, PathUtils.comparePath(localDir, otherDir));
    }

    @Test
    public void differentTree_sameDepth() {
        Path localDir = Paths.get("/mnt/temp/dev/");
        Path otherDir = Paths.get("/mnt/download/pictures/");
        Assert.assertEquals(1, PathUtils.comparePath(localDir, otherDir));

        localDir = Paths.get("/cooking/desserts");
        otherDir = Paths.get("/cooking/vegetables/");
        Assert.assertEquals(-1, PathUtils.comparePath(localDir, otherDir));
    }

    @Test
    public void differentTree_anotherDepth() {
        Path localDir = Paths.get("/mnt/temp/dev/");
        Path otherDir = Paths.get("/mnt/temp/dev/project");
        Assert.assertEquals(-1, PathUtils.comparePath(localDir, otherDir));

        localDir = Paths.get("/cooking/desserts/french");
        otherDir = Paths.get("/cooking/desserts/");
        Assert.assertEquals(1, PathUtils.comparePath(localDir, otherDir));
    }

    @Test
    public void testBasedOnRealCase() {
        // Given...
        final Path dir01 = Paths.get("2016_12_18_hopital");
        final Path dir02 = Paths.get("2017_01_08");
        final Path dir03 = Paths.get("2017_02_12");
        final Path dir04 = Paths.get("2017_03_12_vianden");
        final Path dir05 = Paths.get("2017_04_06_seance_photo");
        final Path dir06 = Paths.get("2016_12_20_hospital_departure");
        final Path dir07 = Paths.get("/2017_01_12_interactivity_start/");
        final Path dir08 = Paths.get("2017_02_16");
        final Path dir09 = Paths.get("2017_03_16_three_months");
        final Path dir10 = Paths.get("2017_04_07");
        final Path dir11 = Paths.get("2016_12_22_baby_and_xmas_market");
        final Path dir12 = Paths.get("2017_01_14_repas_and_schengen");
        final Path dir13 = Paths.get("/2017_02_18_marville");
        final Path dir14 = Paths.get("2017_03_18_preparation_depart_mamie");
        final Path dir15 = Paths.get("2017_04_08_1er_oeuf_paques/");
        final Path dir16 = Paths.get("2016_12_23_first_bath");
        final Path dir17 = Paths.get("2017_01_15_esch_sur_alzette");
        final Path dir18 = Paths.get("/2017_02_19_rodemack");
        final Path dir20 = Paths.get("2017_04_12");
        final Path dir21 = Paths.get("2016_12_24_noel");
        final List<Path> paths = new ArrayList<>();
        paths.add(dir01);
        paths.add(dir02);
        paths.add(dir03);
        paths.add(dir04);
        paths.add(dir05);
        paths.add(dir06);
        paths.add(dir07);
        paths.add(dir08);
        paths.add(dir09);
        paths.add(dir10);
        paths.add(dir11);
        paths.add(dir12);
        paths.add(dir13);
        paths.add(dir14);
        paths.add(dir15);
        paths.add(dir16);
        paths.add(dir17);
        paths.add(dir18);
        paths.add(dir20);
        paths.add(dir21);

        // business
        paths.sort(PathUtils.comparePath());
        paths.forEach(item -> System.out.println("    " + item.toString()));

        // assertions
        Assert.assertEquals(dir01, paths.get(0));
        Assert.assertEquals(dir06, paths.get(1));
        Assert.assertEquals(dir11, paths.get(2));
        Assert.assertEquals(dir16, paths.get(3));
        Assert.assertEquals(dir21, paths.get(4));
        Assert.assertEquals(dir02, paths.get(5));
        Assert.assertEquals(dir07, paths.get(6));
        Assert.assertEquals(dir12, paths.get(7));
        Assert.assertEquals(dir17, paths.get(8));
        Assert.assertEquals(dir03, paths.get(9));
        Assert.assertEquals(dir08, paths.get(10));
        Assert.assertEquals(dir13, paths.get(11));
        Assert.assertEquals(dir18, paths.get(12));
        Assert.assertEquals(dir04, paths.get(13));
        Assert.assertEquals(dir09, paths.get(14));
        Assert.assertEquals(dir14, paths.get(15));
        Assert.assertEquals(dir05, paths.get(16));
        Assert.assertEquals(dir10, paths.get(17));
        Assert.assertEquals(dir15, paths.get(18));
        Assert.assertEquals(dir20, paths.get(19));
    }

}
