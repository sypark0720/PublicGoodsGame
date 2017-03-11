package non.cooperative.games.gui;

import com.google.common.collect.Lists;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.extern.java.Log;
import non.cooperative.games.bo.*;
import non.cooperative.games.non.cooperative.games.api.SimulationManager;
import non.cooperative.games.service.SimulationManagerImpl;
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

  @FXML private TextField contributorsPayoffField;
  @FXML private TextField defectorsPayoffField;

  @FXML private TextField cooperatorPercentageField;
  @FXML private TextField numberOfCooperatorsField;
  @FXML private TextField numberOfDefectorsField;
  @FXML private TextField moneyEarnedField;

  SimulationManager simulationManager = new SimulationManagerImpl();

  GameField gameField;

  public void initialize(URL location, ResourceBundle resources) {
    initializationResources = resources;

    String rules = initializationResources.getString("rules");
    List<String> rulesList = Lists.newArrayList(rules.split(","));
    ObservableList rulesObservableList = FXCollections.observableList(rulesList);
    ruleComboBox.getItems().clear();
    ruleComboBox.setItems(rulesObservableList);

    ruleParamLabel.setMaxWidth(160);
    ruleParamLabel.setMinWidth(160);

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

    ruleParamLabel.setAlignment(Pos.CENTER_RIGHT);
  }

  public void onStartPushed() {
    Parameters params = getParametersFromGUI();

    gameField = simulationManager.initializeGameField(params);

    setPayoffsOnGui();
    setInformationsOnGui();
  }

  private boolean isEveryFieldFilled(String numberOfPlayers, String investment, String multiplicationFactor, String ruleParam, String cooperatorPercent){
    return StringUtils.isNotEmpty(numberOfPlayers) && StringUtils.isNotEmpty(investment)
        && StringUtils.isNotEmpty(multiplicationFactor) && StringUtils.isNotEmpty(ruleParam)
        && StringUtils.isNotEmpty(cooperatorPercent);
  }

  private Parameters getParametersFromGUI(){
    String numberOfPlayers = playerNrField.getText();
    String investment = investmentField.getText();
    String multiplicationFactor = multiplicationFactorField.getText();
    String ruleParam = ruleParamField.getText();
    String cooperatorPercent = initializationResources.getString("startingPercentOfCooperators");

    ParametersFactory parametersFactory = new ParametersFactory();
    Parameters gameParameters = null;

    boolean isRuleSelected = !ruleComboBox.getSelectionModel().isEmpty();
    if(isRuleSelected && isEveryFieldFilled(numberOfPlayers, investment, multiplicationFactor, ruleParam, cooperatorPercent)) {
      try {
        gameParameters = parametersFactory.getParameters(ruleComboBox.getValue().toString(), Integer.parseInt(ruleParam));
        gameParameters.setNumberOfPlayers(Integer.parseInt(numberOfPlayers));
        gameParameters.setInvestment(Integer.parseInt(investment));
        gameParameters.setMultiplicationFactor(Integer.parseInt(multiplicationFactor));
        gameParameters.setCooperatorPercent(Integer.parseInt(cooperatorPercent));
      } catch (IllegalArgumentException exception){
        log.severe(exception.getMessage());
      }
    }

    return gameParameters;
  }

  private void setInformationsOnGui(){
    Informations informations = gameField.getInformations();
    numberOfCooperatorsField.setText(Integer.toString(informations.getNrOfCooperators()));
    numberOfDefectorsField.setText(Integer.toString(informations.getNrOfDefectors()));
    moneyEarnedField.setText(Integer.toString(informations.getMoneyEarned()));

    cooperatorPercentageField.setText(Integer.toString(gameField.getParameters().getCooperatorPercent()));
  }

  private void setPayoffsOnGui(){
    Payoffs payoffs = gameField.getPayoffs();
    contributorsPayoffField.setText(Float.toString(payoffs.getContributorPayoff()));
    defectorsPayoffField.setText(Float.toString(payoffs.getDefectorPayoff()));
  }

}
