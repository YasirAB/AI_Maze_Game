//package logic;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import java.util.ArrayList;

public class DrawBase {
  // instance variables
  public Board myBoard;
  public SurroundingsChecker checker = new SurroundingsChecker();

  // instantiating all images needed for maze drawing
  public final Image tile = new Image("Images/BasicTile0000.png");
  public final Image finish = new Image("Images/ExitLadder1.png");
  public final Image fWall = new Image("Images/FullWall1.png");
  public final Image hWall = new Image("Images/HorizontalWall1.png");
  public final Image lWall = new Image("Images/LeftWall1.png");
  public final Image obWall = new Image("Images/OpenBottomWall1.png");
  public final Image olWall = new Image("Images/OpenLeftWall1.png");
  public final Image orWall = new Image("Images/OpenRightWall1.png");
  public final Image oWall = new Image("Images/OpenWall1.png");
  public final Image rWall = new Image("Images/RightWall1.png");
  public final Image vWall = new Image("Images/VerticalWall1.png");
  public final Image bWall = new Image("Images/BottomWall1.png");
  public final Image ouWall = new Image("Images/OpenUpWall1.png");
  public final Image uWall = new Image("Images/UpWall1.png");
  public final Image urWall = new Image("Images/UpRightWall1.png");
  public final Image ulWall = new Image("Images/UpLeftWall1.png");
  public final Image drWall = new Image("Images/DownRightWall1.png");
  public final Image dlWall = new Image("Images/DownLeftWall1.png");

 /**
  * The only constructor available. Initializes the instance varaible board.
  * @param board: takes in a board type parameter for the instance varaible.
  **/
  public DrawBase(Board board) {
    myBoard = board;
  }



  /**
  * method for creating board in GUI
  * searches through board to find finish and puts picture in place
  * places tiles everywhere the player and enemy are able to move
  * checks what kind of wall should be placed for rest of the maze and add the appropriate wall
  * @return completed board as a GUI
  */
  public GridPane drawBase() {
    GridPane base = new GridPane();
    for (int x = 0; x < myBoard.getRows(); x++) {
      for (int y = 0; y < myBoard.getColumns(); y++) {
        ArrayList<int[]> around = checker.checkAround(myBoard, x,y);
        if (myBoard.board[x][y].getFinish()) {
          base.add(new ImageView(finish), y, x);
        } else if (myBoard.board[x][y].getPassability()) {
          base.add(new ImageView(tile), y, x);
        } else {
          if (checker.isInArray(around, x+1, y) && checker.isInArray(around, x-1, y) && checker.isInArray(around, x, y+1) && checker.isInArray(around, x, y-1)) {
            base.add(new ImageView(oWall), y, x);
          } else if (checker.isInArray(around, x+1, y) && checker.isInArray(around, x-1, y) && checker.isInArray(around, x, y+1)) {
            base.add(new ImageView(orWall), y, x);
          } else if (checker.isInArray(around, x+1, y) && checker.isInArray(around, x-1, y) && checker.isInArray(around, x, y-1)) {
            base.add(new ImageView(olWall), y, x);
          } else if (checker.isInArray(around, x+1, y) && checker.isInArray(around, x, y+1) && checker.isInArray(around, x, y-1)) {
            base.add(new ImageView(obWall), y, x);
          } else if (checker.isInArray(around, x-1, y) && checker.isInArray(around, x, y+1) && checker.isInArray(around, x, y-1)) {
            base.add(new ImageView(ouWall), y, x);
          } else if (checker.isInArray(around, x+1, y) && checker.isInArray(around, x-1, y)) {
            base.add(new ImageView(hWall), y, x);
          } else if (checker.isInArray(around, x+1, y) && checker.isInArray(around, x, y+1)) {
            base.add(new ImageView(drWall), y, x);
          } else if (checker.isInArray(around, x+1, y) && checker.isInArray(around, x, y-1)) {
            base.add(new ImageView(dlWall), y, x);
          } else if (checker.isInArray(around, x-1, y) && checker.isInArray(around, x, y+1)) {
            base.add(new ImageView(urWall), y, x);
          } else if (checker.isInArray(around, x-1, y) && checker.isInArray(around, x, y-1)) {
            base.add(new ImageView(ulWall), y, x);
          } else if (checker.isInArray(around, x, y+1) && checker.isInArray(around, x, y-1)) {
            base.add(new ImageView(vWall), y, x);
          } else if (checker.isInArray(around, x+1, y)) {
            base.add(new ImageView(bWall), y, x);
          } else if (checker.isInArray(around, x-1, y)) {
            base.add(new ImageView(uWall), y, x);
          } else if (checker.isInArray(around, x, y+1)) {
            base.add(new ImageView(rWall), y, x);
          } else if (checker.isInArray(around, x, y-1)) {
            base.add(new ImageView(lWall), y, x);
          } else {
            base.add(new ImageView(fWall), y, x);
          }
        }
      }
    }
    return base;
  }
}
