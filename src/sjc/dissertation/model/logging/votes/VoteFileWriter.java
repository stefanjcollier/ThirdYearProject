package sjc.dissertation.model.logging.votes;

import java.util.ArrayList;
import java.util.List;

import sjc.dissertation.model.logging.file.LogFileWriter;
import sjc.dissertation.model.logging.file.LogFileWritingException;

/**
 * Creates a .csv file for each social class and stores the votes for each retailer in the files per round
 *
 * example file:
 * "Middle Class_Vote_Log_dd.mm.yyyy.csv":
 * 10,20,10,
 * 5,45,10,
 *
 * Note there are 40 people in middle class and 3 retailers that they can vote from
 *
 *
 * @author Stefan Collier
 *
 */
public class VoteFileWriter {

	final String fileExtension = ".csv";
	final List<LogFileWriter> allFiles;
	final int numOfRetailers;

	/**
	 * Create the VoteFileWriter instance.
	 *
	 * Assumes that the socialClasses list is the same given as in the VoteLogger
	 * @param socialClasses -- All the social classes in the models
	 */
	protected VoteFileWriter(final String filePath, final List<String> socialClasses, final int maxRetailers){
		//Fill with files ready for a good writing
		this.allFiles = new ArrayList<LogFileWriter>(socialClasses.size());
		for(final String socClass : socialClasses){
			this.allFiles.add(new LogFileWriter(filePath, makeName(socClass), this.fileExtension));
		}
		//Store max
		this.numOfRetailers = maxRetailers;
	}

	protected String makeName(final String socClass){
		return String.format("%s_Vote_Log", socClass);
	}

	public void writeVotesToFile(final int[][] votes){
		//Cycle through social class (and social class files)
		for(int socClass = 0; socClass < this.allFiles.size(); socClass++){
			final String line = constructCsvLine(votes[socClass]);

			try {
				this.allFiles.get(socClass).writeLine(line);
			} catch (final LogFileWritingException e) {
				e.printStackTrace();
			}
		}
	}

	protected String constructCsvLine(final int[] classVotes){
		String line = "";
		for(int retailer = 0; retailer < this.numOfRetailers; retailer++){
			line += String.format("%d,", classVotes[retailer]);
		}
		return line;

	}

}
