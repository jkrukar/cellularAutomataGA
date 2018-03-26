import java.util.Random;

/**
 * @author Brandon wade
 *
 */

public class CA  {

	int rows;
	int cols;
   
	/*
	 *  rule set example {0,1,1,0,1,1,0,1} 
	 *  or  {false,true,true,false,true,true,false,true}
	 */
	int[] ruleset; 

	int generation = 0; // the start generation

	int[][] matrix;  // Store a history of generations in 2D array, not just one
	int[] cells;     // An array of 0s and 1s 

	public int[] rule30 = {0,1,1,1,1,0,0,0};// Rule 30
	public boolean[] rule30_b = {false,true,true,true,true,false,false,false};
	
	public int[] randomRuleSet;

	public CA(int[] rules,int width, int height)
	{
		ruleset = rules;
	    cells = new int[width/5];
		restart();
	}
	
	public CA(boolean[] rules,int width, int height)
	{
		
		ruleset = convertBoolToInt(rules);
	    cells = new int[width/5];
		restart();
	}

    /*
     *   I plan on modifying this constructor i dont believe its good
     *   coding practice for the way im generating this rule set
     */
	public CA()
	{
		randomRuleSet = new int[8];
		generateRandomRule();

	}


    // generate the cells with all 0's and a 1 in the middle of the grid
	private void restart() 
	{

	    for (int i = 0; i < cells.length; i++) 
	    {
	        cells[i] = 0;
	    }
	    cells[cells.length/2] = 1;    // We arbitrarily start with just the middle cell having a state of "1"
		generation = 0;
	}

	void generate1D()
	{
		// First we create an empty array for the new values
	    int[] nextgeneration = new int[cells.length];
	    // For every spot, determine new state by examing current state, and neighbor states
	    // Ignore edges that only have one neighor
	    for (int i = 1; i < cells.length-1; i++) {
	      int left = cells[i-1];   // Left neighbor state
	      int middle = cells[i];       // Current state
	      int right = cells[i+1];  // Right neighbor state
	      nextgeneration[i] = rules(left, middle, right); // Compute next generation state based on ruleset
	    }
	    // The current generation is the new generation
	    cells = nextgeneration;
	    generation++;
	}
	/*
	 * 
	 */
	int rules (int a, int b, int c) 
	{
		if (a == 1 && b == 1 && c == 1)
		{
			return ruleset[7];
		}
		if (a == 1 && b == 1 && c == 0)
		{
			return ruleset[6];
		}
		if (a == 1 && b == 0 && c == 1)
		{
			return ruleset[5];
		}
		if (a == 1 && b == 0 && c == 0)
		{
			return ruleset[4];
		}
		if (a == 0 && b == 1 && c == 1)
		{
			return ruleset[3];
		}
		if (a == 0 && b == 1 && c == 0)
		{
			return ruleset[2];
		}
		if (a == 0 && b == 0 && c == 1)
		{
			return ruleset[1];
		}
		if (a == 0 && b == 0 && c == 0)
		{
			return ruleset[0];
		}
		return 0;
	}

	// Make a random ruleset
	public void generateRandomRule() 
	{
		Random rand = new Random();


		for (int i = 0; i < 8; i++) 
		{  	
			int n = (int)Math.round( Math.random() )  ;
			randomRuleSet[i] = n;    
		}

	}	 
	
	public int[] convertBoolToInt(boolean r[])
	{
		int newR[] = new int[8];
		for(int i = 0; i < 8; i++)
		{
			if(r[i] == true)
			{
				newR[i] = 1;
			}
			
			else
			{
				newR[i] = 0;
			}
		}
		
		return newR;		
	}


}
