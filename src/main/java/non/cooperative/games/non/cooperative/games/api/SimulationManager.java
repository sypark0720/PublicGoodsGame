package non.cooperative.games.non.cooperative.games.api;

import non.cooperative.games.bo.GameField;
import non.cooperative.games.bo.Parameters;

/**
 * Created by U560821 on 2017.03.09..
 */
public interface SimulationManager  {
  public GameField initializeGameField(Parameters parameters);
}
