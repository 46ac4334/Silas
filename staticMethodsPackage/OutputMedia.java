package staticMethodsPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * Creates a {@link PrintWriter} which writes to a file with a given name in a
 * given directory. Creates all required directories if they don't exist, and
 * creates the file with a new name. Has methods for obtaining references to the
 * file and to the <code>PrintWriter</code>.
 * 
 * @author bakis
 *
 */
public class OutputMedia {

	/**
	 * The output will be encoded as UTF-8.
	 */
	private Charset charset = Charset.forName("UTF-8");

	/**
	 * The stream to which output will be directed.
	 */
	private PrintWriter out;

	/**
	 * The file to which the output stream will be connected.
	 */
	private File outFile;

	/**
	 * Constructor from given directory and file name. Creates the directory as a
	 * sub=directory of "data".
	 * 
	 * @param dir  The directory in which the output file is to reside.
	 * @param name The base name of the output file. A timestamp will be appended to
	 *             that name.
	 * @throws IOException
	 */
	public OutputMedia(File dir, String name) throws IOException {
		dir = new File("data");
		if (!dir.exists()) {
			boolean b = dir.mkdirs();
			if (b)
				System.out.printf("Created new data directory:  %s%n", dir.getAbsolutePath());
			else
				System.out.printf("Failed to create data directory %s%n", dir.getAbsolutePath());
		}
		if (!dir.exists()) {
			throw new RuntimeException("Unable  to create or access the data directory " + dir.getAbsolutePath());
		}
		File outDir = new File(dir, "out");
		if (!outDir.exists()) {
			if (outDir.mkdirs())
				System.out.printf("Created new output directory:  %s%n", outDir.getAbsolutePath());
			else
				System.out.printf("Failed to create output directory %s%n", outDir.getAbsolutePath());
		}
		if (!dir.exists()) {
			throw new RuntimeException("Unable  to create or access the output directory " + outDir.getAbsolutePath());
		}

		String outFileName;

		long t = System.currentTimeMillis();// Start with current clock time, but increment it in 1-ms steps as needed
											// to obtain a unique file name.
		do {// Create an output file with a unique name. Increment the time stamp in 1-ms
			// steps until the file name differs from all previous versions.
			outFileName = name + "_" + StaticMethods.compactTimeStamp(t) + ".txt";
			outFile = new File(outDir, outFileName);
			t++;
		} while (outFile.exists());

		System.out.format("outFile: \"%s\"%n", outFile.getAbsolutePath());
		BufferedWriter outWriter = new BufferedWriter(new FileWriter(outFile, charset, true));// Requires Java 11.
		out = new PrintWriter(outWriter);
		out.format("Output file:  %s%nCharset:  %s%n", outFile.getAbsolutePath(), charset);
	}

	/**
	 * @return the charset
	 */
	public Charset getCharset() {
		return charset;
	}

	/**
	 * @return the out
	 */
	public PrintWriter getOut() {
		return out;
	}

	/**
	 * @return the outFile
	 */
	public File getOutFile() {
		return outFile;
	}

	public PrintWriter getPrintWriter() {
		return out;
	}

}
