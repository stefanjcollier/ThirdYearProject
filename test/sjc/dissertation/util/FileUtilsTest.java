package sjc.dissertation.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileUtilsTest {

	private static final String path = "test_resources";
	private static final File dir = new File(path);

	@Before
	public void setup(){
		final File oldFile = new File(FileUtils.getFileName(dir, 0));
		if(oldFile.exists() && oldFile.isFile()){
			oldFile.delete();
		}
	}

	@Test
	public void test() {
		// GIVEN a path
		// WHEN making a file
		final File createdFile  = FileUtils.createDatedFile(path);

		//THEN ...
		//The returned file is not null
		assertNotNull("There should be a file created", createdFile);

		//A file has been created
		assertTrue("There should be a file created", createdFile.exists());

		//A file has been created in the right place
		assertEquals("The file name is not correct",
				createdFile.getAbsolutePath(), FileUtils.getFileName(dir, 0));

	}

	@After
	public void breakDown(){
		final File oldFile = new File(FileUtils.getFileName(dir, 0));
		if(oldFile.exists() && oldFile.isFile()){
			oldFile.delete();
		}
	}

}
