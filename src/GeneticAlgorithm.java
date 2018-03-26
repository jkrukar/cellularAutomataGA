
import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

    Random random = new Random();
    ArrayList<RuleSet> currentRuleSetPopultion = new ArrayList<>(100);
    boolean debug = true;


    /* lambda must be uniformly distributed between [0.0,1.0].
        There is no way to vary a rule set with lambda = 0.0 or 1.0. Both of these cases will be included in the initial population
        This population will have 14 variants of 7 uniformly distributed lambda values (remaining 98)
    */
    public void generateInitialRuleSetPopulation(){

        //These rule sets are hard coded in since there's no way to randomly vary their rulset.
        boolean allFalse[] = {false, false, false, false, false, false, false, false};
        RuleSet allFalseRuleSet = new RuleSet(0.0, allFalse);

        boolean allTrue[] = {true, true, true, true, true, true, true, true};
        RuleSet allTrueRuleSet = new RuleSet(1.0, allTrue);

        currentRuleSetPopultion.add(allFalseRuleSet);
        currentRuleSetPopultion.add(allTrueRuleSet);

        //Next lambda refers to the numerator of the next lambda value. Ex: nextLambda = 1 refers to 1/8 true, 7/8 false. Actual lambda value = 0.125
        for(int nextLambda = 1; nextLambda <= 7; nextLambda ++){

            double lambdaVal = nextLambda/8;

            for(int variantCount = 0; variantCount < 14; variantCount++){

                //Keep track of which indexes have been flipped so that we don't try to flip the same one again
                boolean nextVariant[] = {false, false, false, false, false, false, false, false};

                mutateGenotype(nextLambda, nextVariant);

                //Add variant to the population
                RuleSet nextRuleSet = new RuleSet(lambdaVal, nextVariant);
                currentRuleSetPopultion.add(nextRuleSet);
            }
        }

        //Prints out the list of rulesets
        if(debug){
            printRuleSetPopulation(currentRuleSetPopultion);
        }
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
        boolean mutatedIndices[] = {false, false, false, false, false, false, false, false};
        int mutationsLeft = mutations;

        if(debug){
            System.out.println("mutateGenotype()...");
            System.out.print("\tPre -mutation: [");

            for(int j=0; j<8;j++){
                if(originalRule[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("]\n");
        }

        while(mutationsLeft > 0){
            int nextMutationIndex = random.nextInt(8);

            //If this index is false, flip it to true
            if(!mutatedIndices[nextMutationIndex]){
                mutatedIndices[nextMutationIndex] = true;
                originalRule[nextMutationIndex] = !originalRule[nextMutationIndex];
                mutationsLeft --;
            }
        }

        if(debug){
            System.out.print("\tPost-mutation: [");

            for(int j=0; j<8;j++){
                if(originalRule[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("]\n");
        }
    }

    public void generateRecombinant(boolean[] rule1, boolean[] rule2){

        int crossOverIndex = random.nextInt(6) +1;

        boolean newRule1[] = new boolean[8];
        boolean newRule2[] = new boolean[8];

        for(int j=0; j<8;j++){
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

            for(int j=0; j<8;j++){
                if(rule1[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("] post: [");

            for(int j=0; j<8;j++){
                if(newRule1[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("]\n");

            System.out.print("Rule2 pre: [");

            for(int j=0; j<8;j++){
                if(rule2[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("] post: [");

            for(int j=0; j<8;j++){
                if(newRule2[j]){
                    System.out.print("1,");
                }else{
                    System.out.print("0,");
                }
            }

            System.out.print("]\n");

        }
    }

    public static void main(String[] args){
        GeneticAlgorithm GA = new GeneticAlgorithm();
        GA.generateInitialRuleSetPopulation();

//        boolean test[] = {false, true, false, true, false, true, false, true};
//        GA.mutateGenotype(test);

        GA.generateRecombinant(GA.currentRuleSetPopultion.get(0).rule, GA.currentRuleSetPopultion.get(1).rule);
    }
}


