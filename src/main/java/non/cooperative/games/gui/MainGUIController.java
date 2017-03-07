package non.cooperative.games.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by U560821 on 2017.03.05..
 */
@Log
public class MainGUIController {
  @FXML private TextField playerNrField;
  @FXML private TextField investmentField;
  @FXML private TextField multiplicationFactorField;

  public void initializeGameField() {
    String numberOfPlayers = playerNrField.getText();
    String investment = investmentField.getText();
    String multiplicationFactor = multiplicationFactorField.getText();

    if(StringUtils.isNotEmpty(numberOfPlayers) && StringUtils.isNotEmpty(investment) && StringUtils.isNotEmpty(multiplicationFactor)) {
      log.info("Player#:" + numberOfPlayers + " Investment: " + investment + " MultiplicationFactor: " + multiplicationFactor);
    }
  }

}
