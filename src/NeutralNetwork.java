import java.util.ArrayList;
import java.util.HashSet;

public class NeutralNetwork {

    final boolean DEBUG = false;
//    final int MAXDEPTH;
//    final double FITNESSMIN;
//    final double FITNESSMAX;
    HashSet<String> ruleSet = new HashSet<>(); //Keeps track of all the unique rules in the network so that duplicates are not added
    ArrayList<Node> network = new ArrayList<>();
    ArrayList<Node> leaves = new ArrayList<>();
    Node root;

    public NeutralNetwork(int[] rule, int maxDepth){
//        MAXDEPTH = maxDepth;
        ruleSet.add(ruleToString(rule));
        root = new Node(rule,0,0,1, ruleSet, leaves);
//        FITNESSMAX = root.fitness * 1.01; //1% higher than root fitness
//        FITNESSMIN = root.fitness * 0.99; //1% lower than root fitness

        System.out.println("root= " + root.rootDistance + "," + root.fitness + "," + root.lambda + "," + ruleToString(rule));

        network.add(root);
    }

    String ruleToString(int[] rule){

        String ruleString = "";

        for(int i=0; i < rule.length; i++){
            ruleString = ruleString + rule[i];
        }

        if(DEBUG){
            System.out.println("ruleToString()...");
            System.out.println("\t ruleString= " + ruleString);
        }

        return ruleString;
    }


    //Take a rule

    //Mutate it every possible way to get all rules 1 mutation away

    //Check the fitness of mutant rules - remove those not in the fitness bin

    //Mutate mutants recursively

    //I want data output to look like:
        // 00000->00001-10001
        //        00001-00011

    //When a leaf is discovered, keep track of the new phenotype discovered

    //Could fitness function be that lambda must be less than 0.5?

    //Has evolution come up with neutral network as a buffer against unfair/arbitrary environmental changes

    //TODO Make sure that the new mutated node does not already exist in the network!! Otherwise DFS might never terminate!

    public static void main(String[] args){

        int rule[] = {0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1};
        int depth = 1;

        NeutralNetwork neutralNetwork = new NeutralNetwork(rule, depth);

        System.out.println("Leaves");

        for(int i=0; i<neutralNetwork.leaves.size(); i++){
            Node nextLeaf = neutralNetwork.leaves.get(i);
            System.out.println(nextLeaf.rootDistance + "," + nextLeaf.fitness + "," + nextLeaf.lambda + "," + nextLeaf.ruleToString(nextLeaf.rule));
        }

    }
}
