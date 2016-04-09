package sjc.dissertation.retailer;

/**
 * Every retailer has a number of stores or branches where
 * customers go to buy their weekly shop. Every Branch has a location, id and retailer they belong to
 *
 * @author Stefan Collier
 *
 */
public class Branch {
	private final int id, x, y;
	private final Retailer owner;

	protected Branch(final Retailer owner, final int id, final int x, final int y){
		this.owner = owner;
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public Retailer getRetailer(){
		return this.owner;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	@Override
	public String toString(){
		return String.format("%s Branch[%d] @ x,y: %d,%d", this.owner.getName(), this.id, this.x, this.y);
	}
}
