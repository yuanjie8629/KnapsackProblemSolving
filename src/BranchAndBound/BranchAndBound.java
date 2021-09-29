package BranchAndBound;
import java.util.*;
import Item.*;

public class BranchAndBound {
	protected int numItem;
	protected double capacity;
	protected TreeNode root = new TreeNode();
	protected double upper = Integer.MAX_VALUE;
	
	public BranchAndBound(int numItem, double capacity) {
		this.numItem = numItem;
		this.capacity = capacity;
	}
	
	public double CalcUpperBound(double totalWeight, double totalRating, Item[] item,
			int level) {
		
		double weight = totalWeight;
		double rating = totalRating;

		for (int i = level; i < numItem; i++) { 
			if (item[i].getWeight() + weight <= capacity) { 
				/*Since this algorithm is to solve minimization problem
				 * I am converting maximization problem to minimization problem
				 * Thus, the rating and upperBound will be negative
				 * Instead of sum up the rating, I deduct the rating
				 */
				rating -= item[i].getRating(); 
				weight += item[i].getWeight(); 
			} 
			//Since this is 0/1 knapsack problem, the fractional part will not be taken
		}
		return rating;
	}
	
	public double CalcCost(double totalWeight, double totalRating, Item[] item,
			int level) {
		
		double weight = totalWeight;
		double rating = totalRating;

		for (int i = level; i < numItem; i++) { 
			if (item[i].getWeight() + weight <= capacity) { 
				rating -= item[i].getRating(); 
				weight += item[i].getWeight(); 
			} 
			else { 
				//For the Cost, the rating will take the fractional part of the items
				//so that the capacity is fully utilized
				rating -= (double) item[i].getWeight() / item[i].getRating() * 
						(capacity - weight); 
				break; 
			} 
		} 
		return rating;
	}
	
	public double SolveKnapsack(Item[] item) {
		Arrays.sort(item, new CompareItem()); //sort item by comparator
		TreeNode current = root; //Start from root
		//Create priority queue with sorted cost
		PriorityQueue<TreeNode> prioQueue = new PriorityQueue<>(new CompareCost());
		prioQueue.offer(current);
		
		while (!prioQueue.isEmpty()) {
			current = prioQueue.poll();
			current.left = new TreeNode();
			current.right = new TreeNode();
	
			/* Since upper store the optimal rating, 
			 * if the cost of current node is higher than overall upper bound,
			 * there is no use to explore the node because the node will 
			 * not give optimal rating.
			 * Remember that the rating is stored as negative rating.
			 * The smaller the upper bound, the more optimized rating can be obtained.
			 */
			if (current.cost > upper || current.level == numItem) {
				continue;
			}
			
			//Left Child TreeNode
			
			/*Check the total weight after the left child node includes the next item
			 * If the total weight < capacity, upper bound and cost will be calculated
			 */
			double totalWeight = current.cumWeight + item[current.level].getWeight();
			double totalRating = current.cumRating - item[current.level].getRating();
			//If the next item can be added
			if (totalWeight <= capacity) {
				current.left.level = current.level + 1;
				item[current.left.level - 1].setQuantity(item[current.left.level - 1].getQuantity() + 1); 
				current.left.cumWeight = totalWeight;
				current.left.cumRating = totalRating;
				current.left.upperBound = CalcUpperBound(totalWeight, totalRating, item, 
						current.left.level);
				current.left.cost = CalcCost(totalWeight, totalRating, item, current.left.level);
			}
			//If the next item cannot be added
			else {
				/*Make the upperBound and cost become maximum positive number, 
				 *so that this node will not be added to the priority queue
				 *because it is always larger than upper
				 */
				current.left.upperBound = Integer.MAX_VALUE;
				current.left.cost = Integer.MAX_VALUE;
			}
			
			
			//Right Child TreeNode
			
			//Set right child node to not include the next item
			current.right.level = current.level + 1;
			current.right.cumWeight = current.cumWeight;
			current.right.cumRating = current.cumRating;
			current.right.upperBound = CalcUpperBound(current.cumWeight, current.cumRating,
					item, current.right.level);
			current.right.cost = CalcCost(current.cumWeight, current.cumRating, item, 
					current.right.level);
			
			
			if (current.left.upperBound < upper)
				upper = current.left.upperBound;
			if (current.right.upperBound < upper)
				upper = current.right.upperBound;
			
			//if left child node may result in more optimized rating
			if (upper >= current.left.cost) 
				prioQueue.offer(current.left); //Add left child node to priority queue
			//if right child node may result in more optimized rating
			if (upper >= current.right.cost) 
				prioQueue.offer(current.right); //Add right child node to priority queue
			
		}
		return -upper;
	}
	
	//TreeNode class is used to store the information 
	private static class TreeNode {
		protected int level; //Level of the node in the state space tree
		protected double cumWeight, cumRating, upperBound, cost;
		protected TreeNode left,right;
		/*
		 * cumWeight is the cumulative weight of all items which is selected from root to this node
		 * cumRating is the cumulative rating of all items which is selected from root to this node
		 * upperBound is the possible optimized rating for the node path
		 */
		
		public TreeNode() {
			level = 0;
			cumWeight = 0;
			cumRating = 0;
			upperBound = 0;
			cost = 0; 
		}
		
	}
	
	private class CompareCost implements Comparator<TreeNode> {
		public int compare(TreeNode node1, TreeNode node2) {
			if (node1.cost > node2.cost)
				return 1;
			else 
				return -1;
		}
	}

}





