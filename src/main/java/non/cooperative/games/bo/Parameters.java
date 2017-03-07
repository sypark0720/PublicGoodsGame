package non.cooperative.games.bo;

/**
 * Created by U560821 on 2017.03.07..
 */
public abstract class Parameters {
  private int numberOfPlayers;
  private int investment;
  private int multiplicationFactor;

  public Parameters(int numberOfPlayers, int investment, int multiplicationFactor) {
    this.numberOfPlayers = numberOfPlayers;
    this.investment = investment;
    this.multiplicationFactor = multiplicationFactor;
  }

  public int getNumberOfPlayers() {
    return numberOfPlayers;
  }

  public void setNumberOfPlayers(int numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
  }

  public int getInvestment() {
    return investment;
  }

  public void setInvestment(int investment) {
    this.investment = investment;
  }

  public int getMultiplicationFactor() {
    return multiplicationFactor;
  }

  public void setMultiplicationFactor(int multiplicationFactor) {
    this.multiplicationFactor = multiplicationFactor;
  }
}
