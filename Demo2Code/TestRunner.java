import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
public class TestRunner {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Board myMaze = new Board(20,20);
    myMaze.emptyBoard();
    myMaze.randomBoard();

    //myMaze.board[10][20].isEnemy = true;
    //myMaze.board[10][1].isPlayer = true;

    myMaze.printBoard();
}
}
