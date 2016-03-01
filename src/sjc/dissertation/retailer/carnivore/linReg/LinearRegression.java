package sjc.dissertation.retailer.carnivore.linReg;

import java.util.Arrays;

import sjc.dissertation.util.VectorToolbox;


//Consider this another time if we want to try implement "Error Gradient Descent" learning
public class LinearRegression {

	public final double w0 = 1;
	private final double[] a;

	public LinearRegression(final double[] w){
		this.a = Arrays.copyOf(w, w.length+1);
		this.a[w.length] = this.w0;
	}

	public double gen_f(final double[] x){
		return VectorToolbox.multiply(this.a, make_y(x));
	}

	private double[] make_y(final double[] x){
		if (x.length+1 != this.a.length){
			return null;
		}
		final double[] y = Arrays.copyOf(x, x.length+1);
		y[x.length] = 1;
		return y;
	}



}
