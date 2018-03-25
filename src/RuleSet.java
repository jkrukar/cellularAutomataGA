public class RuleSet {

    public final double lambdaValue;   //The lambda value of this rule set - never changes
    public final boolean rule[];      //The rule values expressed as a boolean for each digit - never changes
    public boolean results[];   //The results of testing. Used to determine fitness by GA

    public RuleSet(double lambda, boolean[] rule){
        this.lambdaValue = lambda;
        this.rule = rule;
        this.results = new boolean[100];
    }
}
