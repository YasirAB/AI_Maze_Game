package logic;

import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.scene.text.*;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import view.PieceTracker;
import javafx.stage.Modality;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Platform;

public class MoveHandler implements EventHandler<KeyEvent> {
  private LogicRunner myLogic;
  private SurroundingsChecker surch;
  private GameState gameState;
  private boolean running = true;
  private Image background;
  private Image win;
  private Image lose;
  private Stage primaryStage;

  private StackPane root;
  private GridPane pieceView;
  private PieceTracker myTracker;
  

  public MoveHandler(LogicRunner myLogic, StackPane root, GridPane pieceView, PieceTracker myTracker, Stage primaryStage) {
    this.myLogic = myLogic;
    this.root = root;
    this.pieceView = pieceView;
    this.myTracker = myTracker;
    this.primaryStage = primaryStage;
    surch = new SurroundingsChecker();
    gameState = new GameState();
    background = new Image("Images/black.png",myLogic.getBoard().getColumns() * 32, myLogic.getBoard().getRows() * 32, false, false);
    win = new Image("Images/win.gif", myLogic.getBoard().getColumns() * 32, myLogic.getBoard().getRows() * 32 - 100, true, false); //image from https://gifer.com/en/1BEg
    lose = new Image("Images/gameOver.gif", myLogic.getBoard().getColumns() * 32, myLogic.getBoard().getRows() * 32 - 100, true, false);
  }

  public void handle(KeyEvent event) {
    // gets key input from the myLogic.getPlayer() if the myLogic.getPlayer() has not won or lost yet
    if (running) {
      if(event.getCode() == KeyCode.UP && surch.checkEmpty(myLogic.getBoard(), myLogic.getPlayer().getRow()-1, myLogic.getPlayer().getCol())) {
        myLogic.getPlayer().moveUp();
        checkState();
        handleEnemyMoves();
        refreshView();
      }
      else if(event.getCode() == KeyCode.DOWN && surch.checkEmpty(myLogic.getBoard(), myLogic.getPlayer().getRow()+1, myLogic.getPlayer().getCol())) {
        myLogic.getPlayer().moveDown();
        checkState();
        handleEnemyMoves();
        refreshView();
      }
      else if(event.getCode() == KeyCode.RIGHT && surch.checkEmpty(myLogic.getBoard(), myLogic.getPlayer().getRow(), myLogic.getPlayer().getCol()+1)) {
        myLogic.getPlayer().moveRight();
        checkState();
        handleEnemyMoves();
        refreshView();
      }
      else if(event.getCode() == KeyCode.LEFT && surch.checkEmpty(myLogic.getBoard(), myLogic.getPlayer().getRow(), myLogic.getPlayer().getCol()-1)) {
        myLogic.getPlayer().moveLeft();
        checkState();
        handleEnemyMoves();
        refreshView();
      } else if (event.getCode() == KeyCode.ESCAPE){
    	  final Stage dialog = new Stage();
    	  dialog.setTitle("Pause");
          HBox dialogVbox = new HBox(20);
          
          Button resumeButton = new Button("Resume");
          Button saveButton = new Button("Save and quit");
          Button quitButton = new Button("Quit");
          
          dialogVbox.getChildren().add(resumeButton);
          dialogVbox.getChildren().add(saveButton);
          dialogVbox.getChildren().add(quitButton);
          
          Scene dialogScene = new Scene(dialogVbox, 255, 30);
          dialog.setScene(dialogScene);
          dialog.show();
          
          saveButton.setOnAction(new HandleSave(myLogic, dialog, primaryStage));
          quitButton.setOnAction(e -> Platform.exit());
          resumeButton.setOnAction(new EventHandler<ActionEvent>() {
        	    @Override public void handle(ActionEvent e) {
        	        dialog.close();
        	    }
        	});
          
          
          
          
         
      }
    }
    event.consume();
  }

  /**
  * handles all enemy moves and checks for game win or lose
  * @param none
  */
  public void handleEnemyMoves() {
    if (running) {
      for (int i = 0; i < myLogic.getEnemies().length; i++) {
        myLogic.getEnemies()[i].takeTurn(myLogic.getPlayer().getRow(), myLogic.getPlayer().getCol());
      }
      checkState();
    }
  }

  /**
  * refreshes the pieceview gridpane so myLogic.getPlayer() and enemy positions are updated
  * @param none
  */
  public void refreshView() {
    if (running) {
      myTracker.updateTracker(myLogic.getBoard());
      pieceView.getChildren().clear();
      for (int x = 0; x < myLogic.getBoard().getRows(); x++) {
        for (int y = 0; y < myLogic.getBoard().getColumns(); y++) {
          pieceView.add(myTracker.getTracker()[x][y], y, x);
        }
      }
    }
  }

  public void checkState() {
    if (gameState.checkWin(myLogic.getBoard())) {
      running= false;
      root.getChildren().clear();
      pieceView.getChildren().clear();
      root.getChildren().add(new ImageView (background));
      root.getChildren().add(new ImageView (win));

      //Code from https://stackoverflow.com/questions/22166610/how-to-create-a-popup-windows-in-javafx
      final Stage loseMessage = new Stage();
      loseMessage.initModality(Modality.APPLICATION_MODAL);
      loseMessage.initOwner(primaryStage);
      GridPane endBox = new GridPane();
      final Text loseText = new Text("You Win! Play Again?");
      final Button retryButton = new Button("Retry");
      final Button quitButton = new Button("Quit");
      quitButton.setOnAction(e -> Platform.exit());
      retryButton.setOnAction(new HandleRetry(myLogic, loseMessage, primaryStage));
      loseText.setFont(new Font(20));
      loseText.setTextAlignment(TextAlignment.CENTER);
      endBox.add(loseText, 1, 0);
      endBox.add(quitButton, 0, 3);
      endBox.add(retryButton, 0, 1);
      Scene loseScene = new Scene(endBox, 320, 160);
      loseMessage.setScene(loseScene);
      loseMessage.show();
		File saveTxt = new File("save.txt");
		saveTxt.delete();
    } else if (gameState.checkLose(myLogic.getBoard()) ||
    (surch.checkAroundPlayer(myLogic.getBoard(), myLogic.getPlayer().getRow(), myLogic.getPlayer().getCol()).size() == 0)) {
      running = false;
      root.getChildren().clear();
      pieceView.getChildren().clear();
      root.getChildren().add(new ImageView (background));
      root.getChildren().add(new ImageView (lose));

      //Code from https://stackoverflow.com/questions/22166610/how-to-create-a-popup-windows-in-javafx
      final Stage loseMessage = new Stage();
      loseMessage.initModality(Modality.APPLICATION_MODAL);
      loseMessage.initOwner(primaryStage);
      GridPane endBox = new GridPane();
      final Text loseText = new Text("You Lose, try again?");
      final Button retryButton = new Button("Retry");
      final Button quitButton = new Button("Quit");
      quitButton.setOnAction(e -> Platform.exit());
      retryButton.setOnAction(new HandleRetry(myLogic, loseMessage, primaryStage));
      loseText.setFont(new Font(20));
      loseText.setTextAlignment(TextAlignment.CENTER);
      endBox.add(loseText, 1, 0);
      endBox.add(quitButton, 0, 3);
      endBox.add(retryButton, 0, 1);
      Scene loseScene = new Scene(endBox, 320, 160);
      loseMessage.setScene(loseScene);
      loseMessage.show();
	  File saveTxt = new File("save.txt");
	  saveTxt.delete();
    }
  }
}
