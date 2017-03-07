package non.cooperative.games.bo;

/**
 * Created by U560821 on 2017.03.07..
 */
public class NeighborsDecideRuleParameters extends Parameters{
  private int contributingNeighborPercent;

  public NeighborsDecideRuleParameters(int numberOfPlayers, int investment, int multiplicationFactor, int contributingNeighborPercent) {
    super(numberOfPlayers, investment, multiplicationFactor);
    this.contributingNeighborPercent = contributingNeighborPercent;
  }

  public int getContributingNeighborPercent() {
    return contributingNeighborPercent;
  }

  public void setContributingNeighborPercent(int contributingNeighborPercent) {
    this.contributingNeighborPercent = contributingNeighborPercent;
  }
}
