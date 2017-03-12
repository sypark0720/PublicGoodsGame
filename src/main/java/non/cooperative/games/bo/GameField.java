package non.cooperative.games.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by U560821 on 2017.03.07..
 */
@Data
public class GameField {
  private Parameters parameters;
  private Payoffs payoffs;
  private Informations informations;
  private Graph graph;

  public GameField(Parameters parameters, Payoffs payoffs, Informations informations) {
    this.parameters = parameters;
    this.payoffs = payoffs;
    this.informations = informations;
  }
}
