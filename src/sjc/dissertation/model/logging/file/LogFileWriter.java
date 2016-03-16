package sjc.dissertation.model.logging.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import sjc.dissertation.util.FileUtils;

public class LogFileWriter {

	private BufferedWriter buffer;
	private boolean useFile;
	private final String filename;
	private final File file;

	public LogFileWriter(final String parentFilePath, final String filename, final String ext){
		this.file = generateFile(parentFilePath, filename, ext);
		this.filename = this.file.getName();
		//Try make the buffered writer
		try {
			this.buffer = new BufferedWriter(new FileWriter(this.file));
			this.useFile = true;
			System.out.println(String.format("LogFileWriter(%s):: Instantiated @ %s", filename, this.file.getAbsolutePath()));

		} catch (final IOException e) {
			e.printStackTrace();
			this.useFile = false;
			System.err.println(String.format("LogFileWriter(%s)::FAILED:: Did not instantiate @ %s", filename, this.file.getAbsolutePath()));
		}
	}

	public void writeLine(final String line) throws LogFileWritingException{
		if(!this.useFile) {
			return;
		}

		try {
			this.buffer.write(line);
			this.buffer.newLine();
			this.buffer.flush();

		} catch (final IOException e) {
			this.useFile = false;
			throw new LogFileWritingException("There was an error writing to file: "+this.filename+".\n"
					+ "File writing will stop now.",e);
		}
	}


	protected File generateFile(final String parentFilePath, final String filename, final String ext){
		return FileUtils.createDatedFile(parentFilePath, filename, ext);

	}

	public File getFile(){
		return this.file;
	}
	public boolean canUseFile(){
		return this.useFile;
	}

	public void shouldUseFile(final boolean useFile){
		this.useFile = useFile;
	}

	@Override
	public void finalize() throws Throwable{
		this.useFile = false;
		this.buffer.close();
		super.finalize();
	}


}
