package BranchAndBound;
import java.io.*;
import java.util.*;
import Item.*;

public class TestBranchAndBound {
	public static void main(String args[]) 
	{ 
		boolean repeatFlag = true;
		do {
			
			try {
				// Create a File instance
			    File file = new File("item_list.txt");
			    
			    // Create a Scanner for the file
			    Scanner inputFile = new Scanner(file);
			    ArrayList<Item> itemList = new ArrayList<>();
			    while (inputFile.hasNext()) {
			      String name = inputFile.nextLine();
			      String[] temp = inputFile.nextLine().split(" ");
			      int weight = Integer.parseInt(temp[0]);
			      int value = Integer.parseInt(temp[1]);
			      itemList.add(new Item (name, weight, value, 0));
			    }
			    inputFile.close();
			    
			    Scanner input = new Scanner(System.in);
			    System.out.print("Enter Maximum Knapsack Capacity: ");
			    int capacity = Integer.parseInt(input.next());
			    repeatFlag = false;
			    Item[] item = itemList.toArray(new Item[itemList.size()]);
			    BranchAndBound algorithm = new BranchAndBound(item.length, capacity);
			    double upper = algorithm.SolveKnapsack(item);  
			    
			    
				System.out.println("\nKnapsack Items"); 
				System.out.println("----------------------------------------------------------------------------------------------");
				System.out.println("Name\t\t\tWeight\t\tRating\t\tRating-Weight ratio\tQuantity");
				for (int i = 0; i < item.length; i++) { 
					String name = String.format("%-20s", item[i].getName());
					String weight = String.format("%-5d", item[i].getWeight());
					String rating = String.format("%-5d", item[i].getRating());
					String ratio = String.format("%-8.2f", (double)item[i].getRating() / item[i].getWeight());
					System.out.println(name + "\t" + weight + "\t\t" + rating + "\t\t\t" + ratio + 
							"\t   " + item[i].getQuantity()); 
				} 
				System.out.println("----------------------------------------------------------------------------------------------");
				System.out.print("Item Selected: | ");
				int totalWeight = 0;
				for (int i = 0; i < item.length; i++) {
					if (item[i].getQuantity() == 1) {
						totalWeight += item[i].getWeight();
						System.out.print(item[i].getName() +" | ");
					}
				}
				System.out.println("\nTotal Weight: " + totalWeight);
				System.out.println("Maximum rating: " + upper);
			}
			catch (IOException ex){
				System.out.println("File-related Error Found: " + ex.getMessage());
				repeatFlag = false;
			}
			catch (NumberFormatException ex) {
				System.out.println("Invalid Input! Only integer is accepted.");
			}
			catch (Exception ex) {
				System.out.println("Exception Occurs: " + ex.getMessage());
			}
			
		}while (repeatFlag);
		 
	}
}
