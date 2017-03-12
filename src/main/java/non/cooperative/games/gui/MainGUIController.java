package non.cooperative.games.gui;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import lombok.extern.java.Log;
import non.cooperative.games.bo.*;
import non.cooperative.games.non.cooperative.games.api.SimulationManager;
import non.cooperative.games.service.SimulationManagerImpl;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.List;
import java.util.Random;
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

  @FXML private Button startButton;

  @FXML private Canvas neighborsGraph;

  @FXML private LineChart contributorsDefectorsChart;
  @FXML private NumberAxis stepsAxis;
  @FXML private NumberAxis contributorsAndDefectorsAxis;
  XYChart.Series<String, Integer> numberOfContributorsSeries;
  XYChart.Series<String, Integer> numberOfDefectorsSeries;

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
    if(startButton.getText().equals("Start")) {
      Parameters params = getParametersFromGUI();
      if(params != null) {
        gameField = simulationManager.initializeGameField(params);

        setPayoffsOnGui();
        setInformationsOnGui();

        initializeContributorsDefectorsLineChart();
        drawOnContributorsDefectorsLineChart();

        simulationManager.generateGraph(gameField);
        drawGraph();

        startButton.setText("Step");
      }
    } else {
      onStepPushed();
    }
    simulationManager.incrementStepsCounter(gameField);
  }

  public void onStepPushed(){

  }

  private Parameters getParametersFromGUI(){
    String numberOfPlayers = playerNrField.getText();
    String investment = investmentField.getText();
    String multiplicationFactor = multiplicationFactorField.getText();
    String ruleParam = ruleParamField.getText();

    Random random = new Random();
    int coopPercentMin = Integer.parseInt(initializationResources.getString("startingPercentOfCooperatorsMin"));
    int coopPercentMax = Integer.parseInt(initializationResources.getString("startingPercentOfCooperatorsMax"));
    int cooperatorPercent = random.nextInt(coopPercentMax-coopPercentMin) + coopPercentMin;

    ParametersFactory parametersFactory = new ParametersFactory();
    Parameters gameParameters = null;

    boolean isRuleSelected = !ruleComboBox.getSelectionModel().isEmpty();
    if(isRuleSelected && isEveryFieldFilled(numberOfPlayers, investment, multiplicationFactor, ruleParam)) {
      try {
        gameParameters = parametersFactory.getParameters(ruleComboBox.getValue().toString(), Integer.parseInt(ruleParam));
        gameParameters.setNumberOfPlayers(Integer.parseInt(numberOfPlayers));
        gameParameters.setInvestment(Integer.parseInt(investment));
        gameParameters.setMultiplicationFactor(Integer.parseInt(multiplicationFactor));
        gameParameters.setCooperatorPercent(cooperatorPercent);
      } catch (IllegalArgumentException exception){
        log.severe(exception.getMessage());
      }
    }

    return gameParameters;
  }

  private boolean isEveryFieldFilled(String numberOfPlayers, String investment, String multiplicationFactor, String ruleParam){
    return StringUtils.isNotEmpty(numberOfPlayers) && StringUtils.isNotEmpty(investment)
        && StringUtils.isNotEmpty(multiplicationFactor) && StringUtils.isNotEmpty(ruleParam);
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

  private void initializeContributorsDefectorsLineChart(){
    stepsAxis.setLabel("Number of steps taken");

    numberOfContributorsSeries = new XYChart.Series<String, Integer>();
    numberOfDefectorsSeries = new XYChart.Series<String, Integer>();
    numberOfContributorsSeries.setName("Contributors");
    numberOfDefectorsSeries.setName("Defectors");
  }

  private void drawOnContributorsDefectorsLineChart(){
    Informations informations = gameField.getInformations();
    int numberOfContributors = informations.getNrOfCooperators();
    int numberOfDefectors = informations.getNrOfDefectors();
    int stepsTaken = informations.getSteps();

    numberOfContributorsSeries.getData().add(new XYChart.Data(stepsTaken, numberOfContributors));
    numberOfDefectorsSeries.getData().add(new XYChart.Data(stepsTaken, numberOfDefectors));

    contributorsDefectorsChart.getData().addAll(numberOfContributorsSeries, numberOfDefectorsSeries);
  }

  /**
   * (x1,y1)
   * x1 = r/2 * cos(a)
   * y1 = r/2 * sin(a)
   *
   * a = 360 / N
   * where N is the number of players
   */
  private void drawGraph(){
    GraphicsContext graphics = neighborsGraph.getGraphicsContext2D();

    int numberOfPlayers = gameField.getParameters().getNumberOfPlayers();
    int[] playerContributionVector = gameField.getGraph().getPlayerContributionVector();

    float r = Float.parseFloat(initializationResources.getString("graphCircleRadius"));
    float alpha = 360 / numberOfPlayers;
    float nodeWidth = Float.parseFloat(initializationResources.getString("graphNodeWidth"));

    double x, y;
    Point[] coordinates = new Point[numberOfPlayers];
    for (int i = 0; i < numberOfPlayers; i++) {
      x = r * Math.cos(i * alpha * Math.PI / 180);
      y = r * Math.sin(i * alpha * Math.PI / 180);

      // Move graph to center
      x += neighborsGraph.getWidth()/2;
      y += r + nodeWidth/2;

      coordinates[i] = new Point((int)x + nodeWidth/2, (int)y + nodeWidth/2);
      if (playerContributionVector[i] == 1){
        graphics.setFill(Color.RED);
      }
      else if (playerContributionVector[i] == 2){
        graphics.setFill(Color.GREEN);
      }
      else if (playerContributionVector[i] == 3){
        graphics.setFill(Color.YELLOW);
      }
      else{
        graphics.setFill(Color.BLUE);
      }
      graphics.fillOval(x, y, nodeWidth, nodeWidth);
    }

    graphics.setFill(Color.BLACK);

    int[][] edgeList = gameField.getGraph().getEdgeList();
    for (int i = 0; i < gameField.getInformations().getNrOfEdgesInGraph(); i++) {
      if (edgeList[i][0] != edgeList[i][1])
      {
        graphics.strokeLine(coordinates[edgeList[i][0]].getX(),coordinates[edgeList[i][0]].getY(), coordinates[edgeList[i][1]].getX(), coordinates[edgeList[i][1]].getY());
      }
    }
  }

}
