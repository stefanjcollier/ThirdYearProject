package sjc.dissertation.model.logging.votes;

import java.util.ArrayList;
import java.util.List;

import sjc.dissertation.model.logging.file.LogFileWriter;
import sjc.dissertation.model.logging.file.LogFileWritingException;
import sjc.dissertation.retailer.branch.Branch;

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
	final int numOfBranches;

	/**
	 * Create the VoteFileWriter instance.
	 *
	 * Assumes that the socialClasses list is the same given as in the VoteLogger
	 * @param socialClasses -- All the social classes in the models
	 */
	protected VoteFileWriter(final String filePath, final String[] socialClasses, final  List<Branch> branches){
		//Fill with files ready for a good writing
		this.allFiles = new ArrayList<LogFileWriter>(socialClasses.length);
		for(final String socClass : socialClasses){
			this.allFiles.add(new LogFileWriter(filePath, makeName(socClass), this.fileExtension));
		}
		//Store max
		this.numOfBranches = branches.size();

		////write first line
		//Find all names
		String nameLine = "";
		for(final Branch b : branches){
			nameLine += b.getBranchName()+",";
		}
		nameLine += "No Vote";
		//write the name line to each file
		for(int socClass = 0; socClass < this.allFiles.size(); socClass++){
			try {
				this.allFiles.get(socClass).writeLine(nameLine);
			} catch (final LogFileWritingException e) {
				e.printStackTrace();
			}
		}
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
		//We go to +1 as the last (extra) entry is a no vote
		for(int retailer = 0; retailer < classVotes.length; retailer++){
			line += String.format("%d,", classVotes[retailer]);
		}
		line = line.substring(0, line.length()-1);
		return line;

	}

}
