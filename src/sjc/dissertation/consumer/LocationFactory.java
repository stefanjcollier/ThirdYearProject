package sjc.dissertation.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sjc.dissertation.model.logging.LoggerFactory;
import sjc.dissertation.util.RandomToolbox;

public class LocationFactory {
	private final double mapWidth;
	private final List<Double> xs, ys;
	private final List<Integer> populations;
	private final Random rng;

	public static final double PI = 3.14;
	public static final double TWO_PI = 6.28;

	protected LocationFactory(final double mapWidth){
		this.mapWidth = mapWidth;

		this.populations = new ArrayList<>(5);
		this.xs = new ArrayList<>(5);
		this.ys = new ArrayList<>(5);

		this.rng = new Random();
	}

	public void addSettlement(final double x, final double y, final int population){
		this.populations.add(population);
		this.xs.add(x);
		this.ys.add(y);
	}

	public double getMapWidth(){
		return this.mapWidth;
	}

	public int getNumberOfSettlements(){
		return this.populations.size();
	}

	public int selectSettlement(){
		final double[] chances = new double[this.xs.size()];
		//find total pop
		final double total  = this.populations.stream().mapToInt(Integer::intValue).sum();

		//use total pop to find the % that each settlement has of the population
		for(int index = 0; index < this.xs.size(); index++){
			final double pop = this.populations.get(index);
			chances[index] = pop / total;
		}

		//choose a settlement based on these odds
		return RandomToolbox.probabilisticlyChoose(chances);
	}

	/**
	 * @return an angle in radians between pi & -pi
	 */
	private double genRandomAngle(){
		return this.rng.nextDouble()*TWO_PI - PI;
	}

	/**
	 * Distance = gaussian(0,r) where r = root(pop/700)
	 *
	 * @param settlement
	 * @return
	 */
	private double genDistance(final int settlement){
		final double pop = this.populations.get(settlement);
		final double r = Math.sqrt(pop/10000);
		return Math.abs(this.rng.nextGaussian()*r);
	}


	public double[] generateLocationArroundSettlement(final int settlement){
		if(this.populations.size()==0){
			throw new RuntimeException("There are no settlements for a consumer to populate.");
		}

		final double angle = genRandomAngle();
		final double distance = genDistance(settlement);

		final double midX = this.xs.get(settlement);
		final double midY = this.ys.get(settlement);

		final double x = midX + distance*Math.cos(angle);
		final double y = midY + distance*Math.sin(angle);

		//Ensure consumer
		if (inRange(x) && inRange(y)){
			LoggerFactory.getSingleton().getMasterLogger().trace(String.format(
					"Location Factory:: Generated x,y: %f, %f", x,y));
			return new double[]{x, y};
		}else{
			LoggerFactory.getSingleton().getMasterLogger().trace(String.format(
					"Location Factory:: Generated x,y: %f, %f :: OUT OF RANGE", x,y));
			return generateLocationArroundSettlement(settlement);
		}
	}

	public int getTotalPopulation(){
		return this.populations.stream().mapToInt(Integer::intValue).sum();
	}
	public int getPopulation(final int settlementId){
		return this.populations.get(settlementId);
	}

	private boolean inRange(final double scalar){
		return 0 < scalar && scalar < ConsumerFactory.getSingleton().getLocationFactory().getMapWidth();
	}

	public List<Integer> getPopulations(){
		return this.populations;
	}



}
