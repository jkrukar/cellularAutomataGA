import java.util.Random;

/**
 * @author Brandon wade
 *
 */

public class CA  {

	public final int MAXTIMESTEPS = 600;
	public final int CELLPOPULATIONSIZE = 149;
	Random rand = new Random();
	public boolean correctClassification = false;

   
	/*
	 *  rule set example {0,1,1,0,1,1,0,1} 
	 *  or  {false,true,true,false,true,true,false,true}
	 */
	int[] ruleset;
	int[][] matrix;  // Store a history of generations in 2D array, not just one [timestep][cell states]

//	public int[] rule30 = {0,1,1,1,1,0,0,0};// Rule 30
//	public boolean[] rule30_b = {false,true,true,true,true,false,false,false};
//	public int[] randomRuleSet;

//	public CA(int[] rules)
//	{
//		matrix = new int[MAXTIMESTEPS][CELLPOPULATIONSIZE];
//		ruleset = rules;
//	    cells = new int[CELLPOPULATIONSIZE];
//		restart();
//	}
	
	public CA(boolean[] rules, int[] initialConfiguration)
	{
		matrix = new int[MAXTIMESTEPS][CELLPOPULATIONSIZE];
		setInitialConfiguration(initialConfiguration);
		ruleset = convertBoolToInt(rules);
		generate1D();
		correctClassification = determineCorrectClassification();
//		System.out.println("\t\t\t\tCorrect Classification = " + correctClassification);
	}

    /*
     *   I plan on modifying this constructor i dont believe its good
     *   coding practice for the way im generating this rule set
     */
//	public CA()
//	{
//		matrix = new int[MAXTIMESTEPS][CELLPOPULATIONSIZE];
//		randomRuleSet = new int[8];
//		generateRandomRule();
//	}

	private void setInitialConfiguration(int[] initialConfiguration){

		for(int i=0;i<CELLPOPULATIONSIZE;i++){
			matrix[0][i] = initialConfiguration[i];
		}
	}


//    // generate the cells with all 0's and a 1 in the middle of the grid
//	private void restart()
//	{
//
//	    for (int i = 0; i < CELLPOPULATIONSIZE; i++)
//	    {
//	    	boolean nextState = rand.nextBoolean();
//
//	        cells[i] = 0;
//	    }
//	    cells[cells.length/2] = 1;    // We arbitrarily start with just the middle cell having a state of "1" //TODO change to random 1s 0s
//		generation = 0;
//	}

	//Generate the entire matrix of cells from the initial configuration
	void generate1D()
	{
	    // For every spot, determine new state by examing current state, and neighbor states

		for(int j=1; j < MAXTIMESTEPS; j++){
			for (int i = 0; i < CELLPOPULATIONSIZE; i++) {

				int leftIndex = i-1;   // Left neighbor state
				int rightIndex = i+1;  // Right neighbor state

				if(leftIndex < 0){
					leftIndex = CELLPOPULATIONSIZE-1;   // Left neighbor state
				}

				if(rightIndex >= CELLPOPULATIONSIZE){
					rightIndex = 0;  // Right neighbor state
				}

				matrix[j][i] = rules(matrix[j-1][leftIndex], matrix[j-1][i], matrix[j-1][rightIndex]); // Compute next generation state based on ruleset
			}
		}
	}

	boolean determineCorrectClassification(){

		float firstTimeStepAverage = 0;
		float lastTimeStepAverage = 0;

		for(int i=0; i< CELLPOPULATIONSIZE; i++){
			firstTimeStepAverage += matrix[0][i];
			lastTimeStepAverage += matrix[MAXTIMESTEPS-1][i];
		}

		firstTimeStepAverage /= CELLPOPULATIONSIZE;
		lastTimeStepAverage /= CELLPOPULATIONSIZE;

//		System.out.println("\t\t\t\tfirstTimeStepAverage = " + firstTimeStepAverage);
//		System.out.println("\t\t\t\tlastTimeStepAverage = " + lastTimeStepAverage);

		if(firstTimeStepAverage < 0.5 && lastTimeStepAverage == 0){
			return true;
		}else if(firstTimeStepAverage > 0.5 && lastTimeStepAverage == 1){
			return true;
		}else{
			return false;
		}
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

//	// Make a random ruleset
//	public void generateRandomRule()
//	{
//		for (int i = 0; i < 8; i++)
//		{
//			int n = (int)Math.round( Math.random() )  ;
//			randomRuleSet[i] = n;
//		}
//
//	}
	
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
