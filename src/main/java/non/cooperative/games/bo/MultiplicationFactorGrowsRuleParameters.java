package non.cooperative.games.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by U560821 on 2017.03.11..
 */
@Data
@AllArgsConstructor
public class MultiplicationFactorGrowsRuleParameters extends Parameters {
  private int multiplicationGrowthPercent;
}
