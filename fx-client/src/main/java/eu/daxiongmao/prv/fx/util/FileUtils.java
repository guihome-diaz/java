package eu.daxiongmao.prv.fx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public final class FileUtils {

	/** private factory design pattern. */
	private FileUtils() {
	}

	/**
	 * To read a text file 1 line at a time (Java7 try with resources).<br>
	 * Text file expected format:
	 * <ul>
	 * <li>There can be many ORIGINAL lines one after the other.</li>
	 * <li>There must be 1 empty line between ORIGINAL and REVISED line(s)</li>
	 * <li>There can be many REVISED lines one after the other.</li>
	 * <li>There must be 2 empty lines between last REVISED and next ORIGINAL</li>
	 * </ul>
	 *
	 * @param txtFile
	 *            source text file to read
	 * @param original
	 *            list of "original" content to populate (left hand column)
	 * @param revised
	 *            list of "revised" content to populate (right hand column)
	 */
	public static void readDiffingFile(final File txtFile, final List<String> original, final List<String> revised) {
		final String newLine = System.getProperty("line.separator");

		// Read file 1 line at a time
		try (BufferedReader br = new BufferedReader(new FileReader(txtFile))) {
			String line;
			boolean isOriginal = true;
			boolean isRevised = false;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) {
					if (isOriginal) {
						isOriginal = false;
						isRevised = true;
					} else if (isRevised) {
						isOriginal = false;
						isRevised = false;
					} else {
						isOriginal = true;
						isRevised = false;
					}
					continue;
				}

				if (isOriginal) {
					original.add(line + newLine);
				} else {
					revised.add(line + newLine);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static double getJavaVersion () {
	    String version = System.getProperty("java.version");
	    int pos = version.indexOf('.');
	    pos = version.indexOf('.', pos+1);
	    return Double.parseDouble (version.substring (0, pos));
	}
}
