/*

This file is just me playing arund with how graphics could work, definitely not useable
It's a mess and is missing so many pieces

I'm gonna experiment with this file, the usable graphics file will be the Display class


*/


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import java.util.ArrayList;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;

public class GraphicsTesting extends Application {
    @Override
    public void start(Stage primaryStage) {
      Board myMaze = new Board(20,20);
      myMaze.emptyBoard();
      myMaze.randomBoard();
      myMaze.createNodeBoard();
      Player player = new Player(myMaze, 10, 1);
      SeekerEnemy enemy1 = new SeekerEnemy(myMaze, 1.4, 1, 18);
      //SeekerEnemy enemy2 = new SeekerEnemy(myMaze, 1.4, myMaze.getRows() - 2, 20);
      //SeekerEnemy enemy3 = new SeekerEnemy(myMaze, 1.4, (int)(myMaze.getRows()/2), myMaze.getColumns() - 2);
      GameState gameState = new GameState();
      StackPane root = new StackPane();
      GridPane base = new DrawBase(myMaze).drawBase();
      AnchorPane pieceView = new AnchorPane();
      PieceTracker myTracker = new PieceTracker(myMaze,player,new SeekerEnemy[]{enemy1});
      SurroundingsChecker surch = new SurroundingsChecker();

      pieceView = myTracker.getTracker();

      root.getChildren().add(base);
      root.getChildren().add(pieceView);
      Scene scene = new Scene(root);
      scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        boolean running = true;
        @Override
        public void handle(KeyEvent event) {
          // halts the running if the player is stuck
          if (surch.checkAroundPlayer(myMaze, player.getRow(), player.getCol()).size() == 0) {
            running = false;
            Label loseText = new Label("You Lose!");
            loseText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 100));
            base.add(loseText, 0, 0);
          }
          // gets key input from the player if the player has not won or lost yet
          if (running) {
            if(event.getCode() == KeyCode.UP && surch.checkEmpty(myMaze, player.getRow()-1, player.getCol())) {
              myTracker.movePlayer("up");
              player.moveUp();
              myTracker.updateEnemyPositions();
            }
            else if(event.getCode() == KeyCode.DOWN && surch.checkEmpty(myMaze, player.getRow()+1, player.getCol())) {
              myTracker.movePlayer("down");
              player.moveDown();
              myTracker.updateEnemyPositions();
            }
            else if(event.getCode() == KeyCode.RIGHT && surch.checkEmpty(myMaze, player.getRow(), player.getCol()+1)) {
              myTracker.movePlayer("left");
              player.moveRight();
              myTracker.updateEnemyPositions();
            }
            else if(event.getCode() == KeyCode.LEFT && surch.checkEmpty(myMaze, player.getRow(), player.getCol()-1)) {
              myTracker.movePlayer("right");
              player.moveLeft();
              myTracker.updateEnemyPositions();
            }
          }
          event.consume();
          }
      });
      primaryStage.setTitle("Maze Game");
      primaryStage.setScene(scene);
      primaryStage.show();
    }
  }
