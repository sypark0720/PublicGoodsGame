package non.cooperative.games.service;

import lombok.extern.java.Log;
import non.cooperative.games.bo.*;
import non.cooperative.games.non.cooperative.games.api.RuleManager;
import non.cooperative.games.non.cooperative.games.api.SimulationManager;

import java.util.Random;

/**
 * Created by U560821 on 2017.03.09..
 */
@Log
public class SimulationManagerImpl implements SimulationManager {
  RuleManager ruleManager;

  public GameField initializeGameField(Parameters parameters) {
    Payoffs payoffs = countPayoffs(parameters);
    Informations informations = countInformations(parameters);
    return new GameField(parameters, payoffs, informations);
  }

  public void generateGraph(GameField gameField) {
    int numberOfPlayers = gameField.getParameters().getNumberOfPlayers();
    int numberOfEdges = gameField.getInformations().getNrOfEdgesInGraph();

    int[][] adjacencyMatrix = new int[numberOfPlayers][numberOfPlayers];
    int[][] graphEdgeList = new int[numberOfEdges][2];

    Random random = new Random();
    for (int i = 0; i < numberOfEdges; i++) {
      int to = random.nextInt(numberOfPlayers);
      int from = random.nextInt(numberOfPlayers);
      while (to == from) {
        to = random.nextInt(numberOfPlayers);
      }

      if (adjacencyMatrix[from][to] == 0 && adjacencyMatrix[to][from] == 0) {
        adjacencyMatrix[from][to] = 1;
        adjacencyMatrix[to][from] = 1;
        graphEdgeList[i][0] = from;
        graphEdgeList[i][1] = to;
      }
    }
    int[] playerContributionsVector = generatePlayerContributionVector(numberOfPlayers, gameField.getInformations());
    Graph graph = new Graph(adjacencyMatrix,graphEdgeList, playerContributionsVector);
    gameField.setGraph(graph);
  }

  public int[] generatePlayerContributionVector(int numberOfPlayers, Informations informations) {
    int numberOfCooperators = informations.getNrOfCooperators();
    int numberOfDefectors = informations.getNrOfDefectors();

    Random random = new Random();
    int[] playerContributionVector = new int[numberOfPlayers];
    for (int i = 0; i < numberOfPlayers; i++) {
      if (numberOfCooperators == 0) {
        numberOfDefectors--;
        playerContributionVector[i] = 1;
      }
      else if (numberOfDefectors == 0) {
        numberOfCooperators--;
        playerContributionVector[i] = 2;
      }
      else if (random.nextInt(2) == 0) {
          numberOfDefectors--;
          playerContributionVector[i] = 1;
      }
      else {
          numberOfCooperators--;
          playerContributionVector[i] = 2;
      }
    }
    return playerContributionVector;
  }

  public void incrementStepsCounter(GameField gameField) {
    int steps = gameField.getInformations().getSteps();
    gameField.getInformations().setSteps(++steps);
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

    /**
     * Max number of edges in an NxN graph is Nx(N-1)/2
     */
    Random random = new Random();
    int edgeNr = random.nextInt(numberOfPlayers * (numberOfPlayers - 1) / 2);

    Informations informations = new Informations(numberOfCooperators, numberOfDefector, moneyEarned,
        0, edgeNr, generateSelflessnessFactors(numberOfPlayers));
    return informations;
  }

  private int getNumberOfCooperatorsFromCooperatorPercent(int cooperatorPercent, int numberOfPlayers){
    return numberOfPlayers * cooperatorPercent/ 100;
  }

  private int[] generateSelflessnessFactors(int numberOfPlayers){
    Random random = new Random();
    int[] selflesness = new int[numberOfPlayers];
    for (int i = 0; i < numberOfPlayers; i++) {
      selflesness[i] = random.nextInt(100);
    }
    return selflesness;
  }
}
