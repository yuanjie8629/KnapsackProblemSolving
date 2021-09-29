package BranchAndBound;
import java.util.Comparator;
import Item.*;

public class CompareItem implements Comparator<Item> {
	public int compare(Item item1, Item item2) {
		double ratio1 = (double)item1.getRating() / item1.getWeight();
		double ratio2 = (double)item2.getRating() / item2.getWeight();
		//sort item in descending order of ratio of rating per weight
		if (ratio1 > ratio2)
			return -1;
		else 
			return 1;
	}
}
