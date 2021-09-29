package BruteForce;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import Item.*;

public class TestBruteForce {

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
		        BruteForce.getBestCombinations(item, item.length, capacity);
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
		} while (repeatFlag);
       
    }

}