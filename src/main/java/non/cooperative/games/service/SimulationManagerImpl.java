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

  private Payoffs countPayoffs(Parameters parameters){
    return null;
  }

  private Informations countInformations(Parameters parameters){
    return null;
  }
}
