//package graphics;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
public class Runner {
  public static void main(String[] args) {
    Board myMaze = new Board(20,20);
    myMaze.randomBoard();
    Player player = new Player(myMaze, 10, 1);
    SeekerEnemy enemy1 = new SeekerEnemy(myMaze, 1.0, 5, 20);
    SeekerEnemy enemy2 = new SeekerEnemy(myMaze, 1.0, 15, 20);
    SeekerEnemy enemy3 = new SeekerEnemy(myMaze, 1.0, 7, 20);
    GameState gameState = new GameState();
    myMaze.printBoard();

    boolean quit = false;

    while (!quit && !gameState.checkWin(myMaze) && !gameState.checkLose(myMaze)) {
      player.inputMove();
      // The random chance of moves should be moved to inside of the enemy class eventually
      if (Math.random() > 0.95) {
        enemy1.randomMove();
      }
      if (Math.random() > 0.2 && !gameState.checkLose(myMaze)) {
        enemy1.seekMove(player.getRow(), player.getCol());
      }

      if (Math.random() > 0.95) {
        enemy2.randomMove();
      }
      if (Math.random() > 0.2 && !gameState.checkLose(myMaze)) {
        enemy2.seekMove(player.getRow(), player.getCol());
      }

      if (Math.random() > 0.95) {
        enemy3.randomMove();
      }
      if (Math.random() > 0.2 && !gameState.checkLose(myMaze)) {
        enemy3.seekMove(player.getRow(), player.getCol());
      }

      //enemy1.hearingMove(player.currentColumn, player.currentRow);
      myMaze.printBoard();
    }
    if (gameState.checkWin(myMaze)) {
      System.out.println("You Won!");
    } else {
      System.out.println("You Lost!");
    }

  }
}
