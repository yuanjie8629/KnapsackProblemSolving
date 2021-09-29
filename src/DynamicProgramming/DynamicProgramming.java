package DynamicProgramming;
import java.util.*;
import java.io.*;
import Item.*;

public class DynamicProgramming 
{	
	public static void main(String[] args) 
	{
		boolean repeatFlag = true;
		do
		{
			try
			{
				Knapsack knapsack = new Knapsack();
				int max_capacity;
				int quantity = 0;
				File item_list = new File("item_list.txt");
				ArrayList<Item> itemList = new ArrayList<Item>();
				Scanner input = new Scanner (item_list);
				while(input.hasNextLine())
				{
					String name = input.nextLine();
					String[] temp = input.nextLine().split(" ");
					int weight = Integer.parseInt(temp[0]);
					int rating = Integer.parseInt(temp[1]);
					itemList.add(new Item (name, weight,rating, quantity));
				}
				input.close();	
				Item[] item = itemList.toArray(new Item[itemList.size()]);
				Scanner read = new Scanner (System.in);
				System.out.print("Enter the max capacity of your bag: ");
				max_capacity = Integer.parseInt(read.next());
				read.close();
				repeatFlag = false;
				System.out.println("Total maximum rating: " + knapsack.SolveKnapsack(item, max_capacity));
			}catch (FileNotFoundException e)
			{
				System.out.println("File error: " + e.getMessage());
				repeatFlag = false;
			}
			catch (NumberFormatException e)
			{
				System.out.println("Invalid input! Only number is accepted." + e.getMessage());
			}
			catch (Exception e)
			{
				System.out.println("Any other error");
			}

		}while (repeatFlag);
	}
}

class Knapsack
{

	public int SolveKnapsack(Item[] item, int max_capacity)
	{
		int num_item = item.length;
		//a table is created with the items in the i and the capacity in the j
		int table[][] = new int [num_item][max_capacity + 1];
		for (int i = 0; i < num_item; i++)
		{
			table[i][0] = 0;
			
		}
		
		for(int j = 0; j <= max_capacity; j++)
		{
			table[0][j] = item[0].getRating();
		}
		
		for (int i = 1 ; i < num_item; i++ )
			for(int j = 1; j <= max_capacity; j++)
			{
				int rating_case1 = 0;
				int rating_case2 = 0;
				if (item[i].getWeight() <= j)
				{
					// when the current item is included
					rating_case1 = item[i].getRating() + table[i - 1][j - item[i].getWeight()];
					
				}
				//when the current item is excluded
				rating_case2 = table[i -1][j];
				table[i][j] = Math.max(rating_case1, rating_case2);
			}
		selected_item(table, max_capacity,item);
		return table[num_item -1][max_capacity];
	}
	
	public void selected_item (int table[][], int max_capacity, Item[] item)
	{
		int num_item = item.length;
		num_item = num_item - 1;
		int a = num_item;
		while(num_item != 0)
		{

			if(table[num_item][max_capacity] != table[num_item - 1][max_capacity])
			{
				item[num_item].setQuantity(1); 
				max_capacity = max_capacity - item[num_item].getWeight();
			}
			num_item--;
		}
		if(item[0].getWeight() <= max_capacity)
			item[0].setQuantity(1);
		for(int i = a; i >=0; i--)
		{
			System.out.printf("%-15s", item[i].getName());
			System.out.printf("[ rating = %-3s, ", item[i].getRating());
			System.out.printf("weight = %-3s, ", item[i].getWeight());
			System.out.printf("quantity = %-3s]\n", item[i].getQuantity());
		}
	}
}
