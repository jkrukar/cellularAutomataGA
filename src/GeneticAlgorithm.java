
import java.util.*;

public class GeneticAlgorithm {

    public final boolean LAMDACONSTRAINTEXISTS = false;
    public final double LAMBDACONSTRAINT = 0.45;
    public final int CELLPOPULATIONSIZE = 149;
    Random random = new Random();
    ArrayList<RuleSet> currentRuleSetPopulation = new ArrayList<>(CELLPOPULATIONSIZE);
    ArrayList<ArrayList<RuleSet>> generationRuleSets = new ArrayList<>(320);
    ArrayList<int[]> initialConfigurationSet;
    boolean debug = false;


    /* lambda must be uniformly distributed between [0.0,1.0].
        There is no way to vary a rule set with lambda = 0.0 or 1.0. Both of these cases will be included in the initial population
        This population will have 14 variants of 7 uniformly distributed lambda values (remaining 98)
    */
    public void generateInitialRuleSetPopulation(){

        //Generate rules with less than half digits equal to 1
        for(int i=0; i<50; i++){

            int initialOnes = random.nextInt(64);
            boolean[] initialBoolConfig = new boolean[128];
            mutateGenotype(initialOnes, initialBoolConfig);

            //Add variant to the population
            RuleSet nextRuleSet = new RuleSet(initialBoolConfig);
            currentRuleSetPopulation.add(nextRuleSet);
        }

        //Generate rules with more than half digits equal to 1
        for(int i=0; i<50; i++){

            int initialOnes = random.nextInt(64) + 64;
            boolean[] initialBoolConfig = new boolean[128];
            mutateGenotype(initialOnes, initialBoolConfig);

            //Add variant to the population
            RuleSet nextRuleSet = new RuleSet(initialBoolConfig);
            currentRuleSetPopulation.add(nextRuleSet);
        }

        generationRuleSets.add(currentRuleSetPopulation);

        //Prints out the list of rule sets
        if(debug){
            printRuleSetPopulation(currentRuleSetPopulation);
        }
    }

    public ArrayList<int[]> generateInitialCellConfiguration(){

        ArrayList<int[]> newConfigurationSet = new ArrayList<>(100);

        //Generate ICs with less than half cells equal to 1
        for(int i=0; i<50; i++){

            int initialBlackCells = random.nextInt(CELLPOPULATIONSIZE /2);
            boolean[] initialBoolConfig = new boolean[CELLPOPULATIONSIZE];
            mutateGenotype(initialBlackCells, initialBoolConfig);
            newConfigurationSet.add(convertBoolToInt(initialBoolConfig));
        }

        //Generate ICs with more than half cells equal to 1
        for(int i=0; i<50; i++){

            int initialBlackCells = random.nextInt(CELLPOPULATIONSIZE /2) + CELLPOPULATIONSIZE /2;
            boolean[] initialBoolConfig = new boolean[CELLPOPULATIONSIZE];
            mutateGenotype(initialBlackCells, initialBoolConfig);
            newConfigurationSet.add(convertBoolToInt(initialBoolConfig));
        }


        return newConfigurationSet;
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

    public void printRuleSetPopulation(ArrayList<RuleSet> population){

        for(int i= 0; i< population.size(); i++){
            System.out.print("Next RuleSet = [");
            boolean rule[] = population.get(i).rule;

            for(int j=0; j<8;j++){
                if(rule[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("]\n");
        }
    }

    //Genotypes will be mutated 2 times as indicated in the paper
    //Also used to generate the initial population of rule sets
    public void mutateGenotype(boolean[] originalRule){
        generateMutations(2, originalRule);
    }

    //Mutate a specified number of times
    public void mutateGenotype(int mutations, boolean[] originalRule){
        generateMutations(mutations, originalRule);
    }

    private void generateMutations(int mutations, boolean[] originalRule){
        //Keep track of which indexes have been flipped so that we don't try to flip the same one again
        boolean mutatedIndices[] = new boolean[originalRule.length];
        int mutationsLeft = mutations;

        for(int i=0; i<originalRule.length;i++){
            mutatedIndices[i] = false;
        }

        if(debug){
            System.out.println("mutateGenotype()... " + mutations + " mutations");
            System.out.print("\tPre -mutation: [");

            for(int j=0; j<originalRule.length;j++){
                if(originalRule[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("]\n");
        }

        while(mutationsLeft > 0){
            int nextMutationIndex = random.nextInt(originalRule.length);

            //If this index is false, flip it to true
            if(!mutatedIndices[nextMutationIndex]){
                mutatedIndices[nextMutationIndex] = true;
                originalRule[nextMutationIndex] = !originalRule[nextMutationIndex];
                mutationsLeft --;
            }
        }

        if(debug){
            System.out.print("\tPost-mutation: [");

            for(int j=0; j<originalRule.length;j++){
                if(originalRule[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("]\n");
        }
    }

    public boolean[][] generateRecombinants(boolean[] rule1, boolean[] rule2){

        int ruleLength = rule1.length;
        int crossOverIndex = random.nextInt(ruleLength-2) +1;

        boolean newRule1[] = new boolean[ruleLength];
        boolean newRule2[] = new boolean[ruleLength];

        for(int j=0; j < ruleLength; j++){
            if(j<crossOverIndex){
                newRule1[j] = rule2[j];
                newRule2[j] = rule1[j];
            }else{
                newRule1[j] = rule1[j];
                newRule2[j] = rule2[j];
            }
        }

        if(debug){
            System.out.println("generateRecombinant()...");
            System.out.println("\tcrossOverIndex= " + crossOverIndex);

            System.out.print("Rule1 pre: [");

            for(int j=0; j<ruleLength;j++){
                if(rule1[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("] post: [");

            for(int j=0; j<ruleLength;j++){
                if(newRule1[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("]\n");

            System.out.print("Rule2 pre: [");

            for(int j=0; j<ruleLength;j++){
                if(rule2[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("] post: [");

            for(int j=0; j<ruleLength;j++){
                if(newRule2[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("]\n");
        }

        boolean recombinants[][] = {newRule1,newRule2};

        return recombinants;
    }

    public PriorityQueue<RuleSet> sortRuleFitness(ArrayList<RuleSet> population){

        PriorityQueue<RuleSet> priorityRuleSet = new PriorityQueue<>(new FitnessComparator());
        int populationSize = population.size();

        //Calculate fitness for every member of population and add it to priority queue
        for(int i=0; i< populationSize;i++){
            RuleSet nextRuleSet = population.get(i);

            if(LAMDACONSTRAINTEXISTS){

                double nextLambda = nextRuleSet.lambdaValue;

                if(nextLambda < LAMBDACONSTRAINT){
                    nextRuleSet.calculateFitness();
                    priorityRuleSet.add(nextRuleSet);
                }

            }else{
                nextRuleSet.calculateFitness();
                priorityRuleSet.add(nextRuleSet);
            }
        }

        return priorityRuleSet;
    }

    public ArrayList<RuleSet> getNextGeneration(ArrayList<RuleSet> currentSet){

        ArrayList<RuleSet> nextRuleSetGeneration = new ArrayList<>(100);
        PriorityQueue<RuleSet> eliteRules = sortRuleFitness(currentSet);
        int eliteCount = 0;

        while(eliteCount < 20){

            RuleSet nextElite = eliteRules.poll();
//            System.out.println("\t\tElite:  fitness = " + nextElite.fitness + ", lambda = " + nextElite.lambdaValue);
//            System.out.print("\t\t\t Rule= [");

            if(eliteCount == 0){
//            if(true){
                System.out.print("" + nextElite.fitness + "," + nextElite.lambdaValue + ",");

                int[] eliteRuleInt = convertBoolToInt(nextElite.rule);

                for(int i=0; i< eliteRuleInt.length; i++){
                    System.out.print(eliteRuleInt[i]);
                }
                System.out.print("\n");
            }

            nextRuleSetGeneration.add(new RuleSet(nextElite.rule));
            eliteCount ++;
        }

        //Generate 80 recombinants for remaining population
        for(int i=0; i< 40; i++){

            int randomIndex1 = random.nextInt(20);
            int randomIndex2 = random.nextInt(20);

            RuleSet randomParent1 = nextRuleSetGeneration.get(randomIndex1);
            RuleSet randomParent2 = nextRuleSetGeneration.get(randomIndex2);

            boolean[][] recombinantRules = generateRecombinants(randomParent1.rule, randomParent2.rule);

            mutateGenotype(recombinantRules[0]);
            mutateGenotype(recombinantRules[1]);

            RuleSet recombinant1 = new RuleSet(recombinantRules[0]);
            RuleSet recombinant2 = new RuleSet(recombinantRules[1]);

            nextRuleSetGeneration.add(recombinant1);
            nextRuleSetGeneration.add(recombinant2);

//            if(i%4==0){
//                System.out.println("recombinant test: ");
//                System.out.println("\tIndices = " + randomIndex1 + ", " + randomIndex2);
//
//            }
        }

        return nextRuleSetGeneration;
    }

    public void runGA(){

//        System.out.println("runGA()...");

        //For each generation of rule sets
        for(int i=0; i < 25; i++){ //99 generations + initial generation = 100
//        for(int i=0; i < 1; i++){ //99 generations + initial generation = 100
//            System.out.println("\tCurrent Generation: " + (i+1) + "/100");

            ArrayList<RuleSet> nextGenerationRuleSet = generationRuleSets.get(i);

            System.out.print(i+ ",");

            //For each rule in the rule set population
            for(int j=0; j < 100; j++){
//                System.out.println("\t\tTesting Rule: " + (j+1) + "/100");

                boolean nextRule[] = nextGenerationRuleSet.get(j).rule;     //Get the actual rule to use on the CA
                boolean nextRuleResults[] = nextGenerationRuleSet.get(j).results;   //Get the results array to store the result of the CA classification

                //Generate a set of 100 random initial configurations
                ArrayList<int[]> nextConfigurationSet = generateInitialCellConfiguration();

                //Test this rule on each of 100 initial configurations
                for(int k=0; k < 100; k++){
//                    System.out.println("\t\t\tTesting Initial Condition: " + (k+1) + "/100");

                    int[] nextInitialConfiguration = nextConfigurationSet.get(k);
//                    System.err.println("testing!!!!!!!!!!!!");
//                    for(int l=0; l< nextInitialConfiguration.length; l++){
//                        System.err.print(nextInitialConfiguration[l]);
//                    }
//                    System.err.print("\n");

                    CA nextCA = new CA(nextRule,nextInitialConfiguration);    //Make a CA with this rule and initial configuration
                    nextRuleResults[k] = nextCA.correctClassification;  //Record the result in the RuleSet
                }
            }

            generationRuleSets.add(getNextGeneration(generationRuleSets.get(i))); //Get next generation of rulesets from the results of the current generation

        }
    }

    public static void main(String[] args){
        GeneticAlgorithm GA = new GeneticAlgorithm();
        GA.generateInitialRuleSetPopulation();
        GA.generateInitialCellConfiguration();
        GA.runGA();

        GeneticAlgorithm GA2 = new GeneticAlgorithm();
        GA2.generateInitialRuleSetPopulation();
        GA2.generateInitialCellConfiguration();
        GA2.runGA();

        GeneticAlgorithm GA3 = new GeneticAlgorithm();
        GA3.generateInitialRuleSetPopulation();
        GA3.generateInitialCellConfiguration();
        GA3.runGA();

        GeneticAlgorithm GA4 = new GeneticAlgorithm();
        GA4.generateInitialRuleSetPopulation();
        GA4.generateInitialCellConfiguration();
        GA4.runGA();

        GeneticAlgorithm GA5 = new GeneticAlgorithm();
        GA5.generateInitialRuleSetPopulation();
        GA5.generateInitialCellConfiguration();
        GA5.runGA();
    }

    private class FitnessComparator implements Comparator<RuleSet>{

        public int compare(RuleSet a, RuleSet b){

            if(a.fitness == b.fitness){
                return 0;
            }else if(a.fitness > b.fitness){
                return -1;
            }else{
                return 1;
            }
        }
    }
}


