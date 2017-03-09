package non.cooperative.games.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Created by U560821 on 2017.03.05..
 */
public class MainApplication extends javafx.application.Application {
  public static void main(String[] args){
    launch(args);
  }

  public void start(Stage stage) throws Exception {
    String fxmlFile = "/fxml/GUI.fxml";
    FXMLLoader loader = new FXMLLoader();
    loader.setResources(ResourceBundle.getBundle("properties.initialize"));
    Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

    Scene scene = new Scene(rootNode, 1400, 950);

    stage.setTitle("Public Goods Game");
    stage.setScene(scene);
    stage.show();
  }
}
