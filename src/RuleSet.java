public class RuleSet {

    public final double lambdaValue;   //The lambda value of this rule set - never changes
    public final boolean rule[];      //The rule values expressed as a boolean for each digit - never changes
    public boolean results[];   //The results of testing. Used to determine fitness by GA
    public double fitness;

    public RuleSet(double lambda, boolean[] rule){
        this.lambdaValue = lambda;
        this.rule = rule;
        this.results = new boolean[100];
    }

    public RuleSet(boolean[] rule){

        this.rule = rule;
        this.results = new boolean[100];

        double trueCount = 0;

        for(int i=0; i<rule.length; i++){
            if(rule[i]){
                trueCount++;
            }
        }

        this.lambdaValue = trueCount/rule.length;
    }

    public void calculateFitness(){

        fitness = 0;

        for(int i=0; i<100;i++){
            if(results[i]){
                fitness ++;
            }
        }

        fitness /= 100;
    }
}
