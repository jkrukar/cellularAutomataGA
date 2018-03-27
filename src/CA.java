import java.util.HashMap;
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
	HashMap<String,Integer> ruleMap = new HashMap(128);

   
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

	int rules (String key)
	{
		int index = ruleMap.get(key);
		System.out.println("map test: key=" + key + ", val= " + index);
		return ruleset[index];
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

	private void buildRuleMap(){

		ruleMap.put("0000000",0);
		ruleMap.put("0000001",1);
		ruleMap.put("0000010",2);
		ruleMap.put("0000011",3);
		ruleMap.put("0000100",4);
		ruleMap.put("0000101",5);
		ruleMap.put("0000110",6);
		ruleMap.put("0000111",7);
		ruleMap.put("0001000",8);
		ruleMap.put("0001001",9);
		ruleMap.put("0001010",10);
		ruleMap.put("0001011",11);
		ruleMap.put("0001100",12);
		ruleMap.put("0001101",13);
		ruleMap.put("0001110",14);
		ruleMap.put("0001111",15);
		ruleMap.put("0010000",16);
		ruleMap.put("0010001",17);
		ruleMap.put("0010010",18);
		ruleMap.put("0010011",19);
		ruleMap.put("0010100",20);
		ruleMap.put("0010101",21);
		ruleMap.put("0010110",22);
		ruleMap.put("0010111",23);
		ruleMap.put("0011000",24);
		ruleMap.put("0011001",25);
		ruleMap.put("0011010",26);
		ruleMap.put("0011011",27);
		ruleMap.put("0011100",28);
		ruleMap.put("0011101",29);
		ruleMap.put("0011110",30);
		ruleMap.put("0011111",31);
		ruleMap.put("0100000",32);
		ruleMap.put("0100001",33);
		ruleMap.put("0100010",34);
		ruleMap.put("0100011",35);
		ruleMap.put("0100100",36);
		ruleMap.put("0100101",37);
		ruleMap.put("0100110",38);
		ruleMap.put("0100111",39);
		ruleMap.put("0101000",40);
		ruleMap.put("0101001",41);
		ruleMap.put("0101010",42);
		ruleMap.put("0101011",43);
		ruleMap.put("0101100",44);
		ruleMap.put("0101101",45);
		ruleMap.put("0101110",46);
		ruleMap.put("0101111",47);
		ruleMap.put("0110000",48);
		ruleMap.put("0110001",49);
		ruleMap.put("0110010",50);
		ruleMap.put("0110011",51);
		ruleMap.put("0110100",52);
		ruleMap.put("0110101",53);
		ruleMap.put("0110110",54);
		ruleMap.put("0110111",55);
		ruleMap.put("0111000",56);
		ruleMap.put("0111001",57);
		ruleMap.put("0111010",58);
		ruleMap.put("0111011",59);
		ruleMap.put("0111100",60);
		ruleMap.put("0111101",61);
		ruleMap.put("0111110",62);
		ruleMap.put("0111111",63);
		ruleMap.put("1000000",64);
		ruleMap.put("1000001",65);
		ruleMap.put("1000010",66);
		ruleMap.put("1000011",67);
		ruleMap.put("1000100",68);
		ruleMap.put("1000101",69);
		ruleMap.put("1000110",70);
		ruleMap.put("1000111",71);
		ruleMap.put("1001000",72);
		ruleMap.put("1001001",73);
		ruleMap.put("1001010",74);
		ruleMap.put("1001011",75);
		ruleMap.put("1001100",76);
		ruleMap.put("1001101",77);
		ruleMap.put("1001110",78);
		ruleMap.put("1001111",79);
		ruleMap.put("1010000",80);
		ruleMap.put("1010001",81);
		ruleMap.put("1010010",82);
		ruleMap.put("1010011",83);
		ruleMap.put("1010100",84);
		ruleMap.put("1010101",85);
		ruleMap.put("1010110",86);
		ruleMap.put("1010111",87);
		ruleMap.put("1011000",88);
		ruleMap.put("1011001",89);
		ruleMap.put("1011010",90);
		ruleMap.put("1011011",91);
		ruleMap.put("1011100",92);
		ruleMap.put("1011101",93);
		ruleMap.put("1011110",94);
		ruleMap.put("1011111",95);
		ruleMap.put("1100000",96);
		ruleMap.put("1100001",97);
		ruleMap.put("1100010",98);
		ruleMap.put("1100011",99);
		ruleMap.put("1100100",100);
		ruleMap.put("1100101",101);
		ruleMap.put("1100110",102);
		ruleMap.put("1100111",103);
		ruleMap.put("1101000",104);
		ruleMap.put("1101001",105);
		ruleMap.put("1101010",106);
		ruleMap.put("1101011",107);
		ruleMap.put("1101100",108);
		ruleMap.put("1101101",109);
		ruleMap.put("1101110",110);
		ruleMap.put("1101111",111);
		ruleMap.put("1110000",112);
		ruleMap.put("1110001",113);
		ruleMap.put("1110010",114);
		ruleMap.put("1110011",115);
		ruleMap.put("1110100",116);
		ruleMap.put("1110101",117);
		ruleMap.put("1110110",118);
		ruleMap.put("1110111",119);
		ruleMap.put("1111000",120);
		ruleMap.put("1111001",121);
		ruleMap.put("1111010",122);
		ruleMap.put("1111011",123);
		ruleMap.put("1111100",124);
		ruleMap.put("1111101",125);
		ruleMap.put("1111110",126);
		ruleMap.put("1111111",127);

	}

}
