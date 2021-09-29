package Item;

public class Item
{
	private String name;
	private int weight;
	private int rating;
	private int quantity;
	
	public Item(String name, int weight, int rating, int quantity)
	{
		this.name = name;
		this.weight = weight;
		this.rating = rating;
		this.quantity = quantity;
	}
	public String getName()
	{
		return name;
	}
	
	public int getWeight()
	{
		return weight;
	}
	
	public int getRating()
	{
		return rating;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
}