package non.cooperative.games.bo;

/**
 * Created by U560821 on 2017.03.11..
 */
public class ParametersFactory {
  public Parameters getParameters(String rule, int ruleParam){
    if(rule.equals("Neighbors Decide")){
      return new NeighborsDecideRuleParameters(ruleParam);
    } else if(rule.equals("Multiplication Factor Grows")){
      return new MultiplicationFactorGrowsRuleParameters(ruleParam);
    } else if(rule.equals("Selflessness Factor")){
      return new SelflessnessFactorRuleParameters(ruleParam);
    }
    throw new IllegalArgumentException("Rule doesn't exist!");
  }
}
