package non.cooperative.games.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by U560821 on 2017.03.07..
 */
@Data
@AllArgsConstructor
public class NeighborsDecideRuleParameters extends Parameters{
  private int contributingNeighborPercent;
}
