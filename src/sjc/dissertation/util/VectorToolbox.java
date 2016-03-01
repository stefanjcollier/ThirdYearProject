package sjc.dissertation.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

	public static double[] multiplyByConst(final double[] v, final double c){
		final double[] newV = new double[v.length];
		for(int i = 0; i < v.length; i++){
			newV[i] = v[i] * c;
		}
		return newV;
	}

	public static Object skipIndex(final int skip, final Object[] objs){
		if (skip == 0){
			return Arrays.copyOfRange(objs, 1,  objs.length);
		}
		final List<Object> result = new LinkedList<Object>();

		for(int i = 0; i < objs.length; i++) {
			if(skip != i) {
				result.add(objs[i]);
			}
		}

		return result.toArray(objs);

	}


}
