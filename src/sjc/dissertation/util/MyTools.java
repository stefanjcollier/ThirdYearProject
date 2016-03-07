package sjc.dissertation.util;

import java.util.LinkedList;
import java.util.List;

public class MyTools {

	public static <E> List<E> skipIndex(final int skip, final List<E> objs){
		final List<E> result = new LinkedList<E>();

		for(int i = 0; i < objs.size(); i++) {
			if(skip != i) {
				result.add(objs.get(i));
			}
		}

		return result;

	}

}
