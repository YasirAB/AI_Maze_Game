//package logic;
import javafx.scene.image.*;
import javafx.scene.layout.*;

// class for moving images where they need to be on GUI based on their movements on the board
public class PieceTracker {
  public Board theMaze;
  public Image character  = new Image("Images/newKnight2.png");
  public Image badGuy  = new Image("Images/Enemy1.png");
  public Image blank  = new Image("Images/Blank1.png");
  //public Image path = new Image("images/path1.png");
  public ImageView[][] tracker;

  /**
   * constructor for pieceTracker setting board as current board
   * and crating a new ImageView for nodes
   * @param maze
   */
  public PieceTracker(Board maze) {
    theMaze = maze;
    tracker = new ImageView[theMaze.ROWS][theMaze.COLUMNS];
  }
  /**
   *method for updating position of images in the mapNode
   *searches through the board for either enemy or player once one is found their image is updated to that place on the board
   *for every row column pair which does not hold a player or enemy a blank image is placed in its spot
   * @param maze
   */
  void updateTracker(Board maze) {
    theMaze = maze;
    for (int x = 0; x < theMaze.getRows(); x++) {
      for (int y = 0; y < theMaze.getColumns(); y++) {
        if (theMaze.board[x][y].checkEnemy()) {
          tracker[x][y] = new ImageView(badGuy);
        } else if (theMaze.board[x][y].checkPlayer()) {
          tracker[x][y] = new ImageView(character);
        } else {
          tracker[x][y] = new ImageView(blank);
        }
      }
    }
  }
}
