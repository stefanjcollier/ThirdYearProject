package sjc.dissertation.util;

public class VectorToolbox {

	private VectorToolbox(){}

	public static double multiply(final double[] v1, final double[] v2){
		if(v1.length != v2.length){
			return Double.MIN_VALUE;
		}

		double total = 0;
		for(int i = 0; i < v1.length; i++){
			total += v1[i]*v2[i];
		}
		return total;
	}

	public static double[] addByConst(final double[] v, final double c){
		final double[] newV = new double[v.length];
		for(int i = 0; i < v.length; i++){
			newV[i] = v[i] + c;
		}
		return newV;
	}

}
