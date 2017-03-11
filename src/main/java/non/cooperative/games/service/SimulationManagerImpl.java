package non.cooperative.games.service;

import lombok.extern.java.Log;
import non.cooperative.games.bo.*;
import non.cooperative.games.non.cooperative.games.api.SimulationManager;

/**
 * Created by U560821 on 2017.03.09..
 */
@Log
public class SimulationManagerImpl implements SimulationManager {

  public GameField initializeGameField(Parameters parameters) {
    Payoffs payoffs = countPayoffs(parameters);
    Informations informations = countInformations(parameters);
    return new GameField(parameters, payoffs, informations);
  }

  /**
   * Pd = (r*nc*c)/N (Defectors' payoff) where
   * r = multiplication factor of the public good
   * nc = number of cooperators in the group
   * c = investment in the public good
   *
   * Pc = Pd-c (Contributors' payoff)
   */
  private Payoffs countPayoffs(Parameters parameters){
    int multiplicationFactor = parameters.getMultiplicationFactor();
    int numberOfPlayers = parameters.getNumberOfPlayers();
    int numberOfCooperators = getNumberOfCooperatorsFromCooperatorPercent(parameters.getCooperatorPercent(), numberOfPlayers);
    int investment = parameters.getInvestment();

    float defectorPayoff = (float) (multiplicationFactor * numberOfCooperators * investment) / numberOfPlayers;
    float contributorPayoff = defectorPayoff - investment;

    Payoffs payoffs = new Payoffs(contributorPayoff, defectorPayoff);
    return payoffs;
  }

  private Informations countInformations(Parameters parameters){
    int numberOfPlayers = parameters.getNumberOfPlayers();
    int numberOfCooperators = getNumberOfCooperatorsFromCooperatorPercent(parameters.getCooperatorPercent(), numberOfPlayers);
    int numberOfDefector = numberOfPlayers - numberOfCooperators;
    int moneyEarned = numberOfCooperators * parameters.getInvestment() * parameters.getMultiplicationFactor();

    Informations informations = new Informations(numberOfCooperators, numberOfDefector, moneyEarned);
    return informations;
  }

  private int getNumberOfCooperatorsFromCooperatorPercent(int cooperatorPercent, int numberOfPlayers){
    return numberOfPlayers * cooperatorPercent/ 100;
  }
}
