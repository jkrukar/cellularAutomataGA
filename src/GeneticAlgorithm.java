import javafx.css.Rule;

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
                int flipsLeft = nextLambda;

                while(flipsLeft > 0){
                    int nextFlipIndex = random.nextInt(8);

                    //If this index is false, flip it to true
                    if(!nextVariant[nextFlipIndex]){
                        nextVariant[nextFlipIndex] = true;
                        flipsLeft --;
                    }
                }

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

    public void generateRecombinant(){

    }

    public void mutateGenotype(){

    }

    public static void main(String[] args){
        GeneticAlgorithm GA = new GeneticAlgorithm();
        GA.generateInitialRuleSetPopulation();
    }
}


