package sjc.dissertation.util;

public class VectorToolbox {

	private VectorToolbox(){}

	public static double multiplyVectors(final double[] v1, final double[] v2){
		if(v1.length != v2.length){
			String problem;
			if(v1.length > v2.length){
				problem = String.format("v1.length(%d) > v2.length(%d)",v1.length, v2.length);
			}else{
				problem = String.format("v1.length(%d) < v2.length(%d)",v1.length, v2.length);
			}
			throw new RuntimeException(problem);
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
	public static double[] multiplyByConst(final double[] v, final double c){
		final double[] newV = new double[v.length];
		for(int i = 0; i < v.length; i++){
			newV[i] = v[i] * c;
		}
		return newV;
	}

	public static double[] addVectors(final double[] v1, final double[] v2){
		if (v1.length != v2.length){
			String problem;
			if(v1.length > v2.length){
				problem = String.format("v1.length(%d) > v2.length(%d)",v1.length, v2.length);
			}else{
				problem = String.format("v1.length(%d) < v2.length(%d)",v1.length, v2.length);
			}
			throw new RuntimeException(problem);
		}
		final double[] res = new double[v1.length];
		for (int i = 0; i < v1.length; i++){
			res[i] = v1[i] + v2[i];
		}
		return res;
	}
}
