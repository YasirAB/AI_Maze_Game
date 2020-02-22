// Import statements
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;

public class Display extends Application {
    // javafx method required to run the graphics
    private StackPane root;
    private GridPane base;
    private GridPane pieceView;
    private Scene scene;
    private PieceTracker myTracker;
    private LogicRunner myLogic;
    private SurroundingsChecker surch;
    private GameState gameState;

    public void makeNewLevel(Stage primaryStage) {
        System.out.println("running makeNewLevel");
        // all instance variables and objects required to run program
        myLogic = new LogicRunner();
        myTracker = new PieceTracker(myLogic.getBoard());

        gameState = new GameState();
        surch = new SurroundingsChecker();
        // creation of root and gridpanes for displaying graphics
        root = new StackPane();
        base = new DrawBase(myLogic.getBoard()).drawBase();
        pieceView = new GridPane();
        // creating the initial view of all pieces on maze
        myTracker.updateTracker(myLogic.getBoard());
        for (int x = 0; x < myLogic.getBoard().getRows(); x++) {
          for (int y = 0; y < myLogic.getBoard().getColumns(); y++) {
            pieceView.add(myTracker.tracker[x][y], y, x);
          }
        }
        // adding to the root so gridpanes are displayed
        root.getChildren().add(base);
        root.getChildren().add(pieceView);
        scene = new Scene(root);
        // setting stage variables
        primaryStage.setTitle("Maze Game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void start(Stage primaryStage) {
      makeNewLevel(primaryStage);
      System.out.println("running start");
      // setting the on key pressed for the scene to be an event handler object
      scene.setOnKeyPressed(new MoveHandler(myLogic, root, pieceView, myTracker, primaryStage));
    }
  }
