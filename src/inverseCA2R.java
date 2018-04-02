import java.util.HashMap;
import java.util.Random;

public class inverseCA2R {

	public final int MAXTIMESTEPS = 320;
	public final int CELLPOPULATIONSIZE = 149;
	Random rand = new Random();
	public boolean correctClassification = false;
	HashMap<String,Integer> ruleMap = new HashMap(32);

	String KEY = "";

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

	public inverseCA2R(boolean[] rules, int[] initialConfiguration)
	{
		buildRuleMap();
		matrix = new int[MAXTIMESTEPS][CELLPOPULATIONSIZE];
		setInitialConfiguration(initialConfiguration);
		ruleset = convertBoolToInt(rules);
		generate1D();
		correctClassification = determineCorrectClassification();
//		System.out.println("\t\t\t\tCorrect Classification = " + correctClassification);
	}

	public inverseCA2R(int[] rules, int[] initialConfiguration)
	{
		buildRuleMap();
		matrix = new int[MAXTIMESTEPS][CELLPOPULATIONSIZE];
		setInitialConfiguration(initialConfiguration);
		ruleset = rules;
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
	void generate1D() {
		// For every spot, determine new state by examing current state, and neighbor states
		for (int j = 1; j < MAXTIMESTEPS; j++) {
			for (int i = 0; i < CELLPOPULATIONSIZE; i++) {
				int leftIndex1 = i - 1;
				int leftIndex2 = i - 2;

				int rightIndex1 = i + 1;
				int rightIndex2 = i + 2;

				if (leftIndex1 <= 0) {

					if(leftIndex1 == 0){
						leftIndex2 = CELLPOPULATIONSIZE - 1;
					}else{
						leftIndex1 = CELLPOPULATIONSIZE - 1;   // Left neighbor state
						leftIndex2 = CELLPOPULATIONSIZE - 2;   // Left neighbor state
					}
				}

				if (rightIndex1 >= CELLPOPULATIONSIZE-1) {

					if(rightIndex1 == CELLPOPULATIONSIZE-1){
						rightIndex2 = 0;  // Right neighbor state
					}else{
						rightIndex1 = 0;  // Right neighbor state
						rightIndex2 = 1;  // Right neighbor state
					}
				}

//				System.out.println("j=" + j + " , i=" + i);
//				System.out.println("leftIndex1=" + leftIndex1);
//				System.out.println("leftIndex2=" + leftIndex2);

				String left1 = String.valueOf(matrix[j - 1][leftIndex1]);
				String left2 = String.valueOf(matrix[j - 1][leftIndex2]);

				String middle = String.valueOf(matrix[j - 1][i]);

				String right1 = String.valueOf(matrix[j - 1][rightIndex1]);
				String right2 = String.valueOf(matrix[j - 1][rightIndex2]);

				String key = left2 + left1 + middle + right1 + right2;

				matrix[j][i] = rules(key);
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

		//Correct if it returns which state is least dense
		if(firstTimeStepAverage < 0.5 && lastTimeStepAverage == 1){
			return true;
		}else if(firstTimeStepAverage > 0.5 && lastTimeStepAverage == 0){
			return true;
		}else{
			return false;
		}
	}

	/*
	 * 
	 */

	int rules (String key)
	{
//		System.out.println("map test: key=" + key);
		int index = ruleMap.get(key);
//		System.out.println("map test: val= " + index);
		return ruleset[index];
	}
	
	public int[] convertBoolToInt(boolean r[])
	{
		int newR[] = new int[r.length];
		for(int i = 0; i < r.length; i++)
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

	private void buildRuleMap(){

		ruleMap.put("00000",0);
		ruleMap.put("00001",1);
		ruleMap.put("00010",2);
		ruleMap.put("00011",3);
		ruleMap.put("00100",4);
		ruleMap.put("00101",5);
		ruleMap.put("00110",6);
		ruleMap.put("00111",7);
		ruleMap.put("01000",8);
		ruleMap.put("01001",9);
		ruleMap.put("01010",10);
		ruleMap.put("01011",11);
		ruleMap.put("01100",12);
		ruleMap.put("01101",13);
		ruleMap.put("01110",14);
		ruleMap.put("01111",15);
		ruleMap.put("10000",16);
		ruleMap.put("10001",17);
		ruleMap.put("10010",18);
		ruleMap.put("10011",19);
		ruleMap.put("10100",20);
		ruleMap.put("10101",21);
		ruleMap.put("10110",22);
		ruleMap.put("10111",23);
		ruleMap.put("11000",24);
		ruleMap.put("11001",25);
		ruleMap.put("11010",26);
		ruleMap.put("11011",27);
		ruleMap.put("11100",28);
		ruleMap.put("11101",29);
		ruleMap.put("11110",30);
		ruleMap.put("11111",31);

	}

}
