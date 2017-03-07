package non.cooperative.games.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by U560821 on 2017.03.07..
 */
@Data
public class NeighborsDecideRuleParameters extends Parameters{
  private int contributingNeighborPercent;

  public NeighborsDecideRuleParameters(int numberOfPlayers, int investment, int multiplicationFactor, int contributingNeighborPercent) {
    super(numberOfPlayers, investment, multiplicationFactor);
    this.contributingNeighborPercent = contributingNeighborPercent;
  }
}
