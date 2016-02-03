package sjc.dissertation.retailer.state.quality;

/**
 * An exception to indicated that a {@link Quality} cannot be changed by a certain {@link QualityChange}.
 *
 * e.g. If a high quality was told to increase, this exception would be thrown.
 *
 * @author Stefan Collier
 *
 */
public class InvalidQualityException extends Exception{

	private static final long serialVersionUID = 1L;

	protected InvalidQualityException(final Quality q, final QualityChange qc){
		super(String.format("The quality %s cannot be told to %s", q.toString(), qc.toString()));
	}
}
