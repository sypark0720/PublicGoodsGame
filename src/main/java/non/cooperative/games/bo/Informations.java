package non.cooperative.games.bo;

/**
 * Created by U560821 on 2017.03.07..
 */
public class Informations {
  private int cooperatorPercent;
  private int nrOfCooperators;
  private int nrOfDefectors;
  private int moneyEarned;

  public Informations(int cooperatorPercent, int nrOfCooperators, int nrOfDefectors, int moneyEarned) {
    this.cooperatorPercent = cooperatorPercent;
    this.nrOfCooperators = nrOfCooperators;
    this.nrOfDefectors = nrOfDefectors;
    this.moneyEarned = moneyEarned;
  }

  public int getCooperatorPercent() {
    return cooperatorPercent;
  }

  public void setCooperatorPercent(int cooperatorPercent) {
    this.cooperatorPercent = cooperatorPercent;
  }

  public int getNrOfCooperators() {
    return nrOfCooperators;
  }

  public void setNrOfCooperators(int nrOfCooperators) {
    this.nrOfCooperators = nrOfCooperators;
  }

  public int getNrOfDefectors() {
    return nrOfDefectors;
  }

  public void setNrOfDefectors(int nrOfDefectors) {
    this.nrOfDefectors = nrOfDefectors;
  }

  public int getMoneyEarned() {
    return moneyEarned;
  }

  public void setMoneyEarned(int moneyEarned) {
    this.moneyEarned = moneyEarned;
  }
}
