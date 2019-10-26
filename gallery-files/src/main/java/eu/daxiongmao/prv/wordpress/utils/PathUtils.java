package eu.daxiongmao.prv.wordpress.utils;

import java.nio.file.Path;
import java.util.Comparator;

/**
 * Utility class to perform operations on {@link Path}
 *
 * @author Guillaume Diaz
 * @version 1.0 - April 2017
 */
public class PathUtils {

    /** Factory design pattern. */
    private PathUtils() {
    }

    /**
     * To cast a given Path into a String[] that represent the tree.
     *
     * @param path
     *            path to analyse
     * @return tree as an array where [0] is '/'.
     */
    public static String[] extractPathStructure(final Path path) {
        if (path == null) {
            return new String[0];
        }

        String pathStr = path.toString();
        if (System.getProperty("os.name").toLowerCase().indexOf("win") > 0) {
            pathStr.replaceAll("\\", "/");
        }

        if (pathStr.startsWith("/") || pathStr.startsWith("\\")) {
            pathStr = pathStr.substring(1);
        }
        if (pathStr.endsWith("/") || pathStr.endsWith("\\")) {
            pathStr = pathStr.substring(0, pathStr.length() - 1);
        }

        return pathStr.split("/");
    }

    /**
     * Hierarchical sort: we order according to the tree level
     *
     * @return comparator criteria
     */
    public static Comparator<Path> comparePath() {
        final Comparator<Path> treeComparator = (final Path original, final Path other) -> PathUtils.comparePath(original, other);
        return treeComparator;
    }

    /**
     * Hierarchical sort: we order according to the tree level
     *
     * @param original
     *            original path
     * @param other
     *            other [challenger] path
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    public static int comparePath(final Path original, final Path other) {
        // Extract paths structures
        final String[] originalStruct = extractPathStructure(original);
        final String[] otherStruct = extractPathStructure(other);

        // Compare paths
        int result = 0;
        int depth = 0;
        // --- First comparison round is based on original length
        while (result == 0 && originalStruct.length > depth) {
            if (otherStruct.length <= depth) {
                // Original path and other share the same root ; but original path is longer
                // Original path must be marked as SHORTER into the tree
                result = 1;
            } else {
                // Same tree level comparison
                final String localPath = originalStruct[depth];
                final String otherPath = otherStruct[depth];

                // other path
                final int lexicographically = localPath.compareTo(otherPath);
                if (lexicographically > 0) {
                    result = 1;
                } else if (lexicographically < 0) {
                    result = -1;
                }
            }
            depth++;
        }
        // --- End conditions
        if (result == 0 && otherStruct.length > depth) {
            // Original path and other share the same root ; but original path is longer
            // Original path must be marked as DEEPER into the tree
            result = -1;
        }

        System.out.println(String.format("Original: %45s | Other: %45s | Result: %d", original, other, result));
        return result;
    }

}
