package non.cooperative.games.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by U560821 on 2017.03.07..
 */
@Data
@AllArgsConstructor
public class GameField {
  private Parameters parameters;
  private Payoffs payoffs;
  private Informations informations;
}
