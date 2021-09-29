package BruteForce;
import Item.*;

public class BruteForce {
	static int bestWeight = 0;
	static int bestRating = 0;
	static int[] bestSeq = {};
	static int[] A = {0, 0, 0, 0, 0, 0};
	static int numCombination = 0;
	static void getBestCombinations(Item[] item,int numItem, int capacity) 
    {
        int[] data = new int[numItem];
        int[] sequence = new int[numItem];
        int[] ratingData = new int[numItem];
        int[] weightData = new int[numItem];
        int[] rating = new int[numItem];
        int[] weight = new int[numItem];
        String[] name = new String[numItem];
        int tempA = 0;
          
        for (int i = 0; i < numItem;i++) {
        	sequence[i] = i;
        	ratingData[i] = item[i].getRating();
        	rating[i] = item[i].getRating();
        	weightData[i] = item[i].getWeight();
        	weight[i] = item[i].getWeight();
        	name[i] = item[i].getName();
        }
         
        for (int r = 0; r < numItem + 1; r++)
            bruteForce(sequence, data, 0, numItem -1, 0, r, ratingData, rating, weightData, weight, capacity);
        
        for(int q = 0; q < numItem; q++) {
    	
        	if(bestSeq[q] == 0) {
        		A[0] = 1;
        	}else {
        		tempA = bestSeq[q];
        		A[tempA] = 1;//Array A for chosen and not chosen object
        	}
        }
        System.out.println();
        System.out.println("There are [" + numCombination + "] combinations 'including combination where 0 item is selected' available\nfor a knapsack with " + numItem + " number of item to insert");
        System.out.println();
        System.out.print("The best combination of item and [Rating] is:");
        System.out.println();
        if(capacity < 8)
        	System.out.println("--------------------------------------------------");
        	else if(capacity < 17)
        		System.out.println("-------------------------------------------------------------------");
        	else if(capacity < 23)
        		System.out.println("-----------------------------------------------------------------------------------");
        	else
        		System.out.println("------------------------------------------------------------------------------------------------");

        for(int j = 0; j < numItem; j++) {
        	
        	if(A[j] == 1) {
        		System.out.print(item[j].getName() + " [" + item[j].getRating() + "] ");
        		if(j != numItem -1)
        		System.out.print("   ");
        	}
        }
        System.out.println();
        if(capacity < 8)
        	System.out.println("--------------------------------------------------");
        	else if(capacity < 17)
        		System.out.println("-------------------------------------------------------------------");
        	else if(capacity < 23)
        		System.out.println("-----------------------------------------------------------------------------------");
        	else
        		System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("With total rating of [" + bestRating + "] and total weight of [" + bestWeight + "]");
    }
	
	static void bruteForce(int[] sequence, int[] data, int start, int end,
            int index, int r, int[] ratingData, int[] rating, int[] weightData, int[] weight, int capacity) 
    {
		int tempRating = 0;
		int tempWeight = 0;
		int[] tempSeq = {0, 0, 0, 0, 0, 0};
		
        if (index == r) 
        {
            for (int j = 0; j < r; j++) {
            	tempWeight += weightData[j];
            	tempRating += ratingData[j];
            	tempSeq[j] = data[j];
            } 
            numCombination++;
            
            if (tempWeight <= capacity && bestRating < tempRating) {
            	bestWeight = tempWeight;
            	bestRating = tempRating;
            	bestSeq = tempSeq; 	
            }     
        }
 
        for (int i = start; i <= end && ((end - i + 1) >= (r - index)); i++) 
        {
            data[index] = sequence[i];
			ratingData[index] = rating[i];
			weightData[index] = weight[i];
			
            bruteForce(sequence, data, i + 1, end, index + 1, r, ratingData,rating, weightData,weight, capacity);
        }    
    }
}