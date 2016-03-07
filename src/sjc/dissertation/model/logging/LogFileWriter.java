package sjc.dissertation.model.logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogFileWriter {

	private BufferedWriter file;
	private boolean useFile;

	protected LogFileWriter(final String filePath){
		try {
			this.file = new BufferedWriter(new FileWriter(filePath));
			this.useFile = true;
		} catch (final IOException e) {
			this.useFile = false;
		}
	}

	public void writeLine(final String line) throws MasterLoggerException{
		if(!this.useFile) {
			return;
		}

		try {
			this.file.write(line);
			this.file.newLine();

		} catch (final IOException e) {
			this.useFile = false;
			throw new MasterLoggerException("There was an error writing to file.\n"
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
