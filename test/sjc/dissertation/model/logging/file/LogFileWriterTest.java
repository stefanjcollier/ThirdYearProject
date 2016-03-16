package sjc.dissertation.model.logging.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sjc.dissertation.util.FileUtils;

public class LogFileWriterTest {
	static String dir = "./test_resources";
	static String fileName = "LogFileWriterTest";
	static String extension = ".test.txt";

	@Before
	@After
	public void setupAndTearDown() {
		final File file0 = new File(FileUtils.getFileName(new File(dir), fileName, 0, extension));
		final File file1 = new File(FileUtils.getFileName(new File(dir), fileName, 1, extension));
		final File file2 = new File(FileUtils.getFileName(new File(dir), fileName, 2, extension));

		if(file0.exists() && file0.isFile()){
			file0.delete();
			file0.deleteOnExit();
		}
		if(file1.exists() && file1.isFile()){
			file1.delete();
			file1.deleteOnExit();
		}
		if(file2.exists() && file2.isFile()){
			file2.delete();
			file2.deleteOnExit();
		}
	}

	/**
	 * Tests that after instantiation the writer is usable.
	 *
	 * Method Under Test: {@link LogFileWriter#LogFileWriter(String, String, String)},{@link LogFileWriter#canUseFile()}
	 */
	@Test
	public void testFileCanBeUsed() throws Throwable {
		//GIVEN a log file writer and an expected location for the file
		final LogFileWriter log = new LogFileWriter(dir, fileName, extension);

		//THEN the log writer should say the file is available to use
		assertTrue("The file should be able to use", log.canUseFile());

		log.finalize();
	}

	/**
	 * Test that writing to the file, actually writes to the file once.
	 *
	 * Method Under Test: {@link LogFileWriter#writeLine(String)}
	 */
	@Test
	public void testFileCanWrittenToOnce() throws Throwable {
		//GIVEN a log file writer and an expected location for the file
		final LogFileWriter log = new LogFileWriter(dir, fileName, extension);

		//AND the expected location of the file
		final File file = log.getFile();

		//AND a line to write
		final String line ="THIS IS A LINE";


		//WHEN writing to the file
		log.writeLine(line);


		//THEN the writer is still usable
		assertTrue("The file should be able to use", log.canUseFile());

		//AND the text was actually written
		final String fileContents = readFile(file);
		assertEquals("The contents should match what was written", line, fileContents);

		log.finalize();
	}

	/**
	 * Test that writing to the file, actually writes to the file multiple times.
	 *
	 * Method Under Test: {@link LogFileWriter#writeLine(String)}x3
	 */
	@Test
	public void testFileCanWrittenToMultiple() throws Throwable {
		//GIVEN a log file writer and an expected location for the file
		final LogFileWriter log = new LogFileWriter(dir, fileName, extension);

		//AND the expected location of the file
		final File file = log.getFile();

		//AND a line to write
		final String line ="THIS IS A LINE";


		//WHEN writing to the file
		log.writeLine(line+1);
		log.writeLine(line+2);
		log.writeLine(line+3);


		//THEN the writer is still usable
		assertTrue("The file should be able to use", log.canUseFile());

		//AND the text was actually written
		final String fileContents = readFile(file);
		final String exp = line+1+line+2+line+3;
		assertEquals("The contents should match what was written", exp ,fileContents);

		log.finalize();
	}



	private static String readFile(final File file){
		String contents = "";
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file));

			while ((sCurrentLine = br.readLine()) != null) {
				contents += sCurrentLine;
			}

		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
		return contents;


	}

}
