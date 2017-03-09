package non.cooperative.games.gui;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by U560821 on 2017.03.05..
 */
@Log
public class MainGUIController implements Initializable {
  ResourceBundle initializationResources;

  @FXML private TextField playerNrField;
  @FXML private TextField investmentField;
  @FXML private TextField multiplicationFactorField;
  @FXML private TextField ruleParamField;
  @FXML private Label ruleParamLabel;
  @FXML private ComboBox ruleComboBox;
  @FXML private ComboBox graphTypeComboBox;

  public void initialize(URL location, ResourceBundle resources) {
    initializationResources = resources;

    String rules = initializationResources.getString("rules");
    List<String> rulesList = Lists.newArrayList(rules.split(","));
    ObservableList rulesObservableList = FXCollections.observableList(rulesList);
    ruleComboBox.getItems().clear();
    ruleComboBox.setItems(rulesObservableList);

    String graphTypes = initializationResources.getString("graphTypes");
    List<String> graphTypesList = Lists.newArrayList(graphTypes.split(","));
    ObservableList graphTypesObservableList = FXCollections.observableList(graphTypesList);
    graphTypeComboBox.getItems().clear();
    graphTypeComboBox.setItems(graphTypesObservableList);
  }

  public void onRuleSelected(){
    String ruleComboBoxValue = ruleComboBox.getValue().toString().replaceAll("\\s+","");
    String ruleParamLabelText = initializationResources.getString(ruleComboBoxValue);
    ruleParamLabel.setText(ruleParamLabelText);
  }

  public void initializeGameField() {
    String numberOfPlayers = playerNrField.getText();
    String investment = investmentField.getText();
    String multiplicationFactor = multiplicationFactorField.getText();

    if(StringUtils.isNotEmpty(numberOfPlayers) && StringUtils.isNotEmpty(investment) && StringUtils.isNotEmpty(multiplicationFactor)) {
      log.info("Player#:" + numberOfPlayers + " Investment: " + investment + " MultiplicationFactor: " + multiplicationFactor);
    }
  }
}
