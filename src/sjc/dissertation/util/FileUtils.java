package sjc.dissertation.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtils {
	private File base;

	public FileUtils(final String baseFolder){
		this.base = new File(baseFolder);
	}

	public void makeFolder(final String dirname){
		final String dir = this.base.getAbsolutePath()+"/"+dirname;
		new File(dir).mkdirs();
	}

	public void touchFile(final String filename){
		final String location = this.base.getAbsolutePath()+"/"+filename;
		final File file = new File(location);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeStringToFile(final String text, final String filename){
		Writer writer = null;
		final String location = this.base.getAbsolutePath()+"/"+filename;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(location), "utf-8"));
			writer.write(text);
		} catch (final IOException ex) {
			// report
		} finally {
			try {writer.close();} catch (final Exception ex) { ex.printStackTrace(); /*ignore*/}
		}
	}

	public void appendStringToFile(final String text, final String filename){
		final String location = this.base.getAbsolutePath()+"/"+filename;
		try {
			Files.write(Paths.get(location), text.getBytes(), StandardOpenOption.APPEND);
		}catch (final IOException e) {
			//exception handling left as an exercise for the reader
		}



	}

}
