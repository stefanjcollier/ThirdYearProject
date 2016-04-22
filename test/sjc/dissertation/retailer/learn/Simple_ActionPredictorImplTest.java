package sjc.dissertation.retailer.learn;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import sjc.dissertation.retailer.state.RetailerAction;
import sjc.dissertation.retailer.state.profit.ProfitMarginChange;
import sjc.dissertation.retailer.state.quality.QualityChange;

/**
 * MANUAL TEST
 * In this simple test of {@link ActionPredictorImpl} we test that an instance can solve a simple equation
 *
 * @author Stefan Collier
 *
 */
public class Simple_ActionPredictorImplTest {

	final double[] intendedWeights = {4, 100,0,0};

	final StubAction action = new StubAction();

	final Random rand = new Random();

	/**
	 * Test that it can predict the value of the equation f(x,y) = ax + by
	 */
	@SuppressWarnings("unused")
	public double testThatItCanFindTheWeights() {
		//GIVEN some default weights
		final double[] initW = {1,1,0,0};

		//AND a ActioPredictorImpl to test
		final ActionPredictor lineReg = new ActionPredictorImpl(initW);

		//AND a number of iterations in which we intend to find the correct weights
		final int max_rounds = 300;

		//AND the values for the equation
		final double a = this.intendedWeights[0];
		final double b = this.intendedWeights[1];

		//WHEN training its results
		for (int round = 0;  round < max_rounds; round++){
			//Gen x and y
			final double x = this.rand.nextInt(10);
			final double y = this.rand.nextInt(10);
			final double z = this.rand.nextInt(10);
			final double w = this.rand.nextInt(10);

			//predict the result
			final double pred = lineReg.predictProfit(new double[]{x,y,z,w});
			lineReg.informOfAction(this.action, pred, new double[]{x,y,z,w});

			//find act
			final double act = a*x + b*y;

			//feedback
			final double[] oldW = lineReg.getWeights();
			lineReg.feedback(act);

			//find error
			final double error = pred -act;
		}

		final int maxTestRounds = 20000;
		double total = 0;
		for (int test = 0; test < maxTestRounds; test++){
			//Gen x and y
			final double x = this.rand.nextInt(10);
			final double y = this.rand.nextInt(10);
			final double z = this.rand.nextInt(10);
			final double w = this.rand.nextInt(10);

			//find act
			final double act = a*x + b*y;

			//predict the result
			final double pred = lineReg.predictProfit(new double[]{x,y,z,w});

			//find error
			final double error = Math.abs(pred-act)*Math.abs(pred-act);

			total += error;
		}
		final double mean  = total / maxTestRounds;
		System.out.println(String.format("Initial Weights: %s", Arrays.toString(initW)));
		System.out.println(String.format("Mean error after %d iterations: %f", max_rounds, mean));
		return mean;
	}

	@Test
	public void superTest(){
		double total = 0;
		for(int i = 0; i < 50; i++){
			total += testThatItCanFindTheWeights();
		}
		final double mean = total/50;
		System.out.println("Overall mean: "+mean);

	}

	class StubAction extends RetailerAction{
		protected StubAction() {
			super(QualityChange.MaintainQuality, ProfitMarginChange.MaintainProfitMargin);
		}

	}



}
