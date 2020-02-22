import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.Animation;

// class for moving images where they need to be on GUI based on their movements on the board
public class PieceTracker {
  public Board theMaze;
  public final Image character  = new Image("Images/Knight1.png");
  public final Image stillKnight = new Image("Images/newKnight1.png");
  public final Image badGuy  = new Image("Images/Enemy1.png");
  public final Image blank  = new Image("Images/Blank1.png");
  public Player myPlayer;
  public ImageView player = new ImageView(stillKnight);
  private SeekerEnemy[] enemies;
  public AnchorPane tracker = new AnchorPane();

  /**
   * constructor for pieceTracker setting board as current board
   * and crating a new ImageView for nodes
   * @param maze
   */
  public PieceTracker(Board maze, Player thePlayer, SeekerEnemy[] enemies) {
    theMaze = maze;
    player.setLayoutY(thePlayer.getRow()*32);
    player.setLayoutX(thePlayer.getCol()*32+1); //+1 is just to centre the image
    tracker.getChildren().add(player);
    this.enemies = enemies;
    for (int i = 0; i < enemies.length; i++) {
      enemies[i].myView.setLayoutY(enemies[i].getRow()*32);
      enemies[i].myView.setLayoutX(enemies[i].getCol()*32);
      tracker.getChildren().add(enemies[i].myView);
    }
    myPlayer = thePlayer;
  }
  /**
   *method for updating position of images in the mapNode
   *searches through the board for either enemy or player once one is found their image is updated to that place on the board
   *for every row column pair which does not hold a player or enemy a blank image is placed in its spot
   * @param maze
   */
  public AnchorPane getTracker() {
    return tracker;
  }

  public Animation upAnimation = new SpriteAnimation(player, Duration.millis(300), "up");
  public Animation downAnimation = new SpriteAnimation(player, Duration.millis(300), "down");
  public Animation leftAnimation = new SpriteAnimation(player, Duration.millis(300), "left");
  public Animation rightAnimation = new SpriteAnimation(player, Duration.millis(300), "right");



  public void movePlayer(String direction) {
    if (direction == "up") {
      upAnimation.play();
      //player.setLayoutY(player.getLayoutY() - 32);
    } else if (direction == "down") {
      downAnimation.play();
      //player.setLayoutY(player.getLayoutY() + 32);
    } else if (direction == "left") {
      leftAnimation.play();
      //player.setLayoutX(player.getLayoutX() + 32);
    } else if (direction == "right") {
      rightAnimation.play();
      //player.setLayoutX(player.getLayoutX() - 32);
    }

  }

  public void updateEnemyPositions() {
    System.out.println("enemies lenght: " + enemies.length);
    if (enemies.length > 0) {
      for (int i = 0; i < enemies.length; i++) {
        int oldRow = enemies[i].getRow();
        int oldCol = enemies[i].getCol();
        enemies[i].takeTurn(myPlayer.getRow(), myPlayer.getCol());
        if (oldRow < enemies[i].getRow()) {
          new SpriteAnimation(enemies[i].myView, Duration.millis(300), "down").play();
        } else if (oldRow > enemies[i].getRow()) {
          new SpriteAnimation(enemies[i].myView, Duration.millis(300), "down").play();
        } else if (oldCol < enemies[i].getCol()) {
          new SpriteAnimation(enemies[i].myView, Duration.millis(300), "down").play();
        } else if (oldCol > enemies[i].getCol()) {
          new SpriteAnimation(enemies[i].myView, Duration.millis(300), "down").play();
        }
      }
    }
  }
}
