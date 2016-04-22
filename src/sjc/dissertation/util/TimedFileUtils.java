package sjc.dissertation.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimedFileUtils {

	private TimedFileUtils(){}

	public final static int FILE_LIMIT = 1000;

	/**
	 * Creates a file in the given directory, in the format of "ThirdYearProject_Log_<date>_<version>.txt"
	 *
	 *	Reasons for return of null:
	 *		- 'dir' does not exist
	 *		- 1000 files can be made per day, limit reached. (dictated by {@link TimedFileUtils#FILE_LIMIT}
	 *
	 * @param dir -- The folder for the file
	 * @return A {@link File} -- Referring to an existing file.
	 * 			null -- The file could not be created
	 */
	public static File createDatedFile(final String dir, final String name, final String extension){
		//Check parent dir exists
		final File parent = new File(dir);
		if(!(parent.exists() && parent.isDirectory())) {
			System.err.println("FileUtils<Fail>: The folder: "+dir+" does not exist");
			return null;
		}

		//Find an unmade version of the file
		for (int version = 0;  version < 100; version++) {
			final String path  = getFileName(parent, name, version, extension);
			final File testFile = new File(path);
			if(!testFile.exists()){
				try {
					testFile.createNewFile();
				} catch (final IOException e) {
					System.err.println("FileUtils<Fail>: Error creating file with path: "+path);
					return null;
				}
				return testFile;
			}
		}
		System.err.println("FileUtils<Fail>: Files exceeded limit of "+FILE_LIMIT);
		return null;
	}

	public static String getFileName(final File parent, final String name, final int version, final String extension){
		return parent.getAbsolutePath()+File.separator+getCurrentDate(name)+"_"+version+extension;
	}

	public static String getCurrentDate(final String name){
		final String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
		return String.format("%s_(%s.%s.%s)",
				name,
				date.substring(0, 2),
				date.substring(2,4),
				date.substring(4));
	}

}
