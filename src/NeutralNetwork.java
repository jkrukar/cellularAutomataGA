import java.util.ArrayList;
import java.util.HashSet;

public class NeutralNetwork {

    final boolean DEBUG = false;
    HashSet<String> ruleSet = new HashSet<>(); //Keeps track of all the unique rules in the network so that duplicates are not added
    ArrayList<Node> network = new ArrayList<>();
    ArrayList<Node> leaves = new ArrayList<>();
    Node root;
//    public int globalMaxDistance = 0;
    public boolean FOUNDTARGETPHENOTYPE = false;
    public double TARGETPHENOTYPE = 0.5; //Want a phenotype with a fitness less than or equal to this value

    public NeutralNetwork(int[] rule){
        ruleSet.add(ruleToString(rule));
        boolean protectedGenes[] = new boolean[rule.length];
        root = new Node(rule,0,0,1, ruleSet, protectedGenes, leaves, this);

        System.out.print(",root," + root.rootDistance + "," + root.fitness + "," + root.lambda + "," + ruleToString(rule));

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

    public static void main(String[] args){

        int rule1[] = {1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1}; //0.51 fitness?
        int rule2[] = {0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1}; //0.55 fitness?
        int rule3[] = {0,0,0,0,0,0,1,1,0,1,1,1,0,0,1,1,0,0,0,1,1,1,1,1,1,1,1,1,0,1,1,1}; //0.57 fitness?
        int rule4[] = {0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,1,1,0,1,1,0,1,1,1,1,1,1,1}; //0.61 fitness?
        int rule5[] = {0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,1,0,1,1,0,1,1,0,1,1,1,0,1,1,1}; //0.64 fitness?
        int rule6[] = {0,0,0,0,0,0,1,1,0,1,0,1,0,0,1,1,0,1,0,1,1,0,0,1,0,1,1,1,0,1,1,1}; //0.71 fitness?
        int rule7[] = {0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,1,0,1,1,1,0,1,1,1,0,1,1,1}; //0.73 fitness?
        int rule8[] = {0,0,0,0,0,0,1,1,0,1,1,1,0,0,1,1,0,1,0,1,1,1,1,1,0,1,1,1,0,1,1,1}; //0.75 fitness?
        int rule9[] = {0,0,0,1,0,0,0,0,0,0,1,1,0,1,0,1,0,1,1,1,0,1,1,1,1,1,0,1,0,1,1,1}; //0.8 fitness
        int rule10[] = {0,0,0,0,0,0,1,1,0,0,1,1,0,1,1,1,0,1,1,1,0,1,1,1,1,1,0,1,0,1,1,1}; //0.9 fitness

        int[] testRules[] = {rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9, rule10};


//        for(int i=9; i>=5; i-- ){
            for(int j=0; j<20; j++){
                System.out.print(6 +",");
                NeutralNetwork neutralNetwork = new NeutralNetwork(testRules[6]);
                System.out.print("\n");
            }
//        }

        for(int j=0; j<20; j++){
            System.out.print(9 +",");
            NeutralNetwork neutralNetwork2 = new NeutralNetwork(testRules[9]);
            System.out.print("\n");
        }

//        System.out.println("Leaves");
//
//        for(int i=0; i<neutralNetwork.leaves.size(); i++){
//            Node nextLeaf = neutralNetwork.leaves.get(i);
//            System.out.println(nextLeaf.rootDistance + "," + nextLeaf.fitness + "," + nextLeaf.lambda + "," + nextLeaf.ruleToString(nextLeaf.rule));
//        }

    }
}
