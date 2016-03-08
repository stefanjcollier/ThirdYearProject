package sjc.dissertation.model.logging.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import sjc.dissertation.util.FileUtils;

public class LogFileWriter {

	private BufferedWriter file;
	private String filename;
	private boolean useFile;

	public LogFileWriter(final String parentFilePath, final String filename, final String ext){
		final File logFile = FileUtils.createDatedFile(parentFilePath, filename, ext);
		this.filename =  logFile.getName();
		try {
			this.file = new BufferedWriter(new FileWriter(logFile));
			this.useFile = true;
		} catch (final IOException e) {
			this.useFile = false;
		}
	}

	public void writeLine(final String line) throws LogFileWritingException{
		if(!this.useFile) {
			return;
		}

		try {
			this.file.write(line);
			this.file.newLine();

		} catch (final IOException e) {
			this.useFile = false;
			throw new LogFileWritingException("There was an error writing to file: "+this.filename+".\n"
					+ "File writing will stop now.",e);
		}
	}

	public void shouldUseFile(final boolean useFile){
		this.useFile = useFile;
	}

	@Override
	public void finalize() throws Throwable{
		this.useFile = false;
		this.file.close();
		super.finalize();
	}


}
