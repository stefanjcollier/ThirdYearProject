package sjc.dissertation.model.logging.results;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import sjc.dissertation.model.ModelRunner;
import sjc.dissertation.model.logging.wrappers.WrappedAlgorithm;
import sjc.dissertation.model.logging.wrappers.WrappedRetailer;
import sjc.dissertation.retailer.Retailer;
import sjc.dissertation.util.FileUtils;

public class ResultCollator {
	private static List<Integer> range(final int min, final int max){
		return IntStream.rangeClosed(min, max)
				.boxed().collect(Collectors.toList());
	}

	public static void generateQualitiesCsv(final FileUtils futil, final List<WrappedAlgorithm> algos){
		// each element is the agent and the next is the
		final List<String> names = new ArrayList<>();
		final List<List<Double>> markups = new ArrayList<>();
		final List<List<Double>> costs = new ArrayList<>();

		for(final WrappedAlgorithm algo : algos){
			names.add(algo.getOwnerersName());
			markups.add(algo.weeklyMarkup);
			costs.add(algo.weeklyCost);
		}
		//The text for files
		String MARKUP_csv = "";
		String COST_Csv = "";
		String PROFIT_Csv = "";


		//Do the names
		for(final String name : names){
			MARKUP_csv += name+",";
		}
		MARKUP_csv = MARKUP_csv.substring(0, MARKUP_csv.length()-1)+System.lineSeparator();
		COST_Csv = MARKUP_csv;
		PROFIT_Csv = MARKUP_csv;


		//Do the round by round score
		for(final int round : range(0,ModelRunner.ROUNDS-1)){
			for(int agent = 0; agent < names.size(); agent++){
				MARKUP_csv += markups.get(agent).get(round)+",";
				COST_Csv += costs.get(agent).get(round)+",";
				PROFIT_Csv += markups.get(agent).get(round)*costs.get(agent).get(round)+",";
			}
			MARKUP_csv = MARKUP_csv.substring(0, MARKUP_csv.length()-1)+System.lineSeparator();
			COST_Csv = COST_Csv.substring(0, COST_Csv.length()-1)+System.lineSeparator();
			PROFIT_Csv = PROFIT_Csv.substring(0, PROFIT_Csv.length()-1)+System.lineSeparator();
		}

		final String dir = "retailers/";
		futil.makeFolder(dir);

		futil.writeStringToFile(MARKUP_csv, dir+"All_makups.csv");
		futil.writeStringToFile(COST_Csv, dir+"All_costs.csv");
		futil.writeStringToFile(PROFIT_Csv, dir+"All_Profits_per_sale.csv");
	}

	public static void generateRetailerCompare(final FileUtils futil, final List<Retailer> retailers){
		final List<String> names = new ArrayList<>();
		final List<List<Double>> earning = new ArrayList<>();

		for (final Retailer re : retailers){
			names.add(re.getName());
			final WrappedRetailer wrap = (WrappedRetailer) re;
			final List<Double> weeklySum = new ArrayList<>(ModelRunner.ROUNDS);
			for(final Double[] week : wrap.weeklyBreakdown) {
				final double total = Arrays.asList(week).stream().mapToDouble(Double::doubleValue).sum();
				weeklySum.add(total);
			}
			earning.add(weeklySum);
		}

		String PROFIT_csv = "";

		//Do the names
		for(final String name : names){
			PROFIT_csv += name+",";
		}
		PROFIT_csv = PROFIT_csv.substring(0, PROFIT_csv.length()-1)+System.lineSeparator();

		//Do the profits
		for(final int round : range(0,ModelRunner.ROUNDS-1)){
			for(int retailer = 0; retailer < names.size(); retailer++){
				PROFIT_csv += earning.get(retailer).get(round)+",";
			}
			PROFIT_csv = PROFIT_csv.substring(0, PROFIT_csv.length()-1)+System.lineSeparator();
		}
		final String dir = "retailers/";
		futil.makeFolder(dir);
		futil.writeStringToFile(PROFIT_csv, dir+"All_Earnings.csv");


	}



}
