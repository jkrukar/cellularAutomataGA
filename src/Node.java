import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Node {

    final double FITNESSLOWERDEVIATIONBOUND = 0.95;
    final double FITNESSUPPERDEVIATIONBOUND = 1.05;
    final int MAXROOTDISTANCE = 10;
    final int CELLPOPULATIONSIZE = 149;
    final int FITNESSTESTS = 100; //Indicates how many initial configurations this rule will be tested on to evaluate fitness
    int rule[];
    boolean leaf;   //True if this node has a different phenotype from the root (It's fitness is not within the bound of the root's fitness)
    double lambda;
    boolean fitnessResults[] = new boolean[FITNESSTESTS];
    double fitness;
    double fitnessMin;
    double fitnessMax;
    Random random = new Random();
    HashSet<String> ruleSet;
    ArrayList<Node> neighbors;
    ArrayList<Node> leaves;


    int rootDistance; //The distance of this node from the root
    Node progeny[]; //??????????? Need a way to keep track of edges

    public Node(int[] rule, int rootDistance, double fitnessMin, double fitnessMax, HashSet<String> ruleSet, ArrayList<Node> leaves){
        //TODO probably need progeny or something
        this.rule = rule;
        this.rootDistance = rootDistance;
        this.lambda = calculateLambda();
        this.fitness = calculateFitness();
//        System.out.println("fitness = " + fitness + ", rootDistance= " + rootDistance);
        this.fitnessMin = fitnessMin;
        this.fitnessMax = fitnessMax;
        this.ruleSet = ruleSet;
        this.leaves = leaves;
        this.leaf = isLeaf();
//        System.out.println("ruleSet size = " + ruleSet.size());

        //TODO: ifleaf...
        if(!leaf && rootDistance <= MAXROOTDISTANCE){
            System.out.println(rootDistance + "," + fitness + "," + lambda + "," + ruleToString(rule) + "," + fitnessMin + "," + fitnessMax);
            neighbors = findAdjacentGenotypes();
        }else{
            leaves.add(this);
        }
    }

    private double calculateLambda(){

        double count = 0;

        for(int i=0; i<rule.length; i++){
            if(rule[i] == 1){
                count++;
            }
        }

        return count/rule.length;
    }

    private double calculateFitness(){

        ArrayList<int[]> nextConfigurationSet = generateInitialCellConfiguration();

        for(int i=0; i < nextConfigurationSet.size(); i++){

            CA2R nextCA = new CA2R(rule,nextConfigurationSet.get(i));
            fitnessResults[i] = nextCA.correctClassification;
        }

        double correctClassifications = 0;

        for(int j=0; j < fitnessResults.length; j++){
            if(fitnessResults[j]){
                correctClassifications ++;
            }
        }

        return correctClassifications/FITNESSTESTS;
    }

    private boolean isLeaf(){

        //TODO it is a leaf if its fitness is a new phenotype or if it does not have any possible mutants...?

        if(fitness < fitnessMin || fitness > fitnessMax){
//            System.out.println("found leaf!");
            return true;
        }

//        System.out.println("Found neutral neighbor!");
        return false;
    }

    public ArrayList<int[]> generateInitialCellConfiguration(){

        ArrayList<int[]> newConfigurationSet = new ArrayList<>(100);

        //Generate ICs with less than half cells equal to 1
        for(int i=0; i<FITNESSTESTS/2; i++){

            int initialBlackCells = random.nextInt(CELLPOPULATIONSIZE /2);
            int initialConfig[] = new int[CELLPOPULATIONSIZE];
            generateMutations(initialBlackCells, initialConfig);
            newConfigurationSet.add(initialConfig);
        }

        //Generate ICs with more than half cells equal to 1
        for(int i=0; i<FITNESSTESTS/2; i++){

            int initialBlackCells = random.nextInt(CELLPOPULATIONSIZE /2) + CELLPOPULATIONSIZE /2;
            int initialConfig[] = new int[CELLPOPULATIONSIZE];
            generateMutations(initialBlackCells, initialConfig);
            newConfigurationSet.add(initialConfig);
        }

        return newConfigurationSet;
    }

    private void generateMutations(int mutations, int[] originalRule){
        //Keep track of which indexes have been flipped so that we don't try to flip the same one again
        boolean mutatedIndices[] = new boolean[originalRule.length];
        int mutationsLeft = mutations;

        for(int i=0; i<originalRule.length;i++){
            mutatedIndices[i] = false;
        }

        while(mutationsLeft > 0){
            int nextMutationIndex = random.nextInt(originalRule.length);

            //If this index is false, flip it to true
            if(!mutatedIndices[nextMutationIndex]){
                mutatedIndices[nextMutationIndex] = true;

                if(originalRule[nextMutationIndex] == 1){
                    originalRule[nextMutationIndex] = 0;
                }else{
                    originalRule[nextMutationIndex] = 1;
                }
                mutationsLeft --;
            }
        }
    }

    ArrayList<Node> findAdjacentGenotypes(){

//        System.out.println("findAdjacentGenotypes()...");

        ArrayList<Node> mutants = new ArrayList<>();

        for(int i=0; i < rule.length; i++){

            String nextGenotype = "";
            int nextRule[] = new int[rule.length];

            for(int j=0; j < rule.length; j++){

                if(j==i){
                    if(rule[j] == 1){
                        nextGenotype = nextGenotype + "0";
                        nextRule[j] = 0;
                    }else{
                        nextGenotype = nextGenotype + "1";
                        nextRule[j] = 1;
                    }
                }else{
                    nextGenotype = nextGenotype + rule[j];
                    nextRule[j] = rule[j];
                }
            }

            if(!ruleSet.contains(nextGenotype)){
                ruleSet.add(nextGenotype);

                Node nextNode;

                if(rootDistance == 0){
                    nextNode = new Node(nextRule, rootDistance+1,fitness*FITNESSLOWERDEVIATIONBOUND,fitness*FITNESSUPPERDEVIATIONBOUND,ruleSet, leaves);
                }else{
                    nextNode = new Node(nextRule, rootDistance+1,fitnessMin,fitnessMax,ruleSet, leaves);
                }
                mutants.add(nextNode);
            }
        }

        return mutants;
    }

    String ruleToString(int[] rule){

        String ruleString = "";

        for(int i=0; i < rule.length; i++){
            ruleString = ruleString + rule[i];
        }

        return ruleString;
    }

}
