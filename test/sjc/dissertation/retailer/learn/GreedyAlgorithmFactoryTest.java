package sjc.dissertation.retailer.learn;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sjc.dissertation.retailer.learn.GreedyAlgorithmFactory;
import sjc.dissertation.retailer.state.profit.ProfitMargin;
import sjc.dissertation.retailer.state.quality.Quality;

public class GreedyAlgorithmFactoryTest {

	@Test
	public void testInitialWeightsGeneration_isCorrectLength() {
		//GIVEN a factory where the UK pop is 1 million
		final int pop = 1000000;
		final GreedyAlgorithmFactory fac = new GreedyAlgorithmFactory(null, pop);

		//WHEN getting the weights for numbers of competitors
		final double[] w1 = fac.getInitWeights(1);
		final double[] w2 = fac.getInitWeights(2);
		final double[] w5 = fac.getInitWeights(5);


		//THEN there should be the appropriate length for each
		assertEquals("For 1 competitor there should be 6 elements",6,w1.length);
		assertEquals("For 2 competitors there should be 8 elements",8,w2.length);
		assertEquals("For 5 competitors there should be 14 elements",14,w5.length);
	}

	@Test
	public void testInitialWeightsGeneration_correctElementValues() {
		//GIVEN a factory where the UK pop is 1 million
		final int pop = 1000000;
		final GreedyAlgorithmFactory fac = new GreedyAlgorithmFactory(null, pop);

		//AND the cost of shopping (the same for all retailers)
		final double rawCost = Quality.MediumQuality.getCost();
		final double profitMultiplier = 1 + ProfitMargin.MediumProfitMargin.getProfitMargin();
		final double costOfShop = rawCost * profitMultiplier;


		//WHEN getting the weights for numbers of competitors
		final double[] w1 = fac.getInitWeights(1);
		final double[] w2 = fac.getInitWeights(2);
		final double[] w5 = fac.getInitWeights(5);


		//THEN there should be the appropriate length for each
		double retailers = 2;
		final double expElementValue1 = ((new Double(pop)*costOfShop)/(3*retailers))/retailers;
		assertEquals("The value of each element should be "+expElementValue1,expElementValue1,w1[0],0.01);

		retailers = 3;
		final double expElementValue2 = ((new Double(pop)*costOfShop)/(3*retailers))/retailers;
		assertEquals("The value of each element should be "+expElementValue2,expElementValue2,w2[0],0.01);

		retailers = 6;
		final double expElementValue5 = ((new Double(pop)*costOfShop)/(3*retailers))/retailers;
		assertEquals("The value of each element should be "+expElementValue5,expElementValue5,w5[0],0.01);

	}

}
