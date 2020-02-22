import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
  private ImageView imageView;
  private int frames = 3;
  private int offSet = 32;
  private String direction;
  public final Image stillKnight  = new Image("Images/newKnight1.png");
  public final Image lfKnight  = new Image("Images/newKnight2.png");
  public final Image rfKnight  = new Image("Images/newKnight3.png");

  private int lastIndex = -1;

  public SpriteAnimation(ImageView imageView, Duration duration, String direction) {
    this.imageView = imageView;
    this.direction = direction;
    setCycleDuration(duration);
    setInterpolator(Interpolator.LINEAR);
  }

  protected void interpolate(double k) {
    int index = (int)(15*k);
    //System.out.println(k);
    if (index != lastIndex) {
      if (direction.equals("up")) {
        imageView.setLayoutY(imageView.getLayoutY() - 2);
        //System.out.println(imageView.getLayoutY());
      } else if (direction.equals("down")) {
        imageView.setLayoutY(imageView.getLayoutY() + 2);
        //System.out.println(imageView.getLayoutY());
      } else if (direction.equals("left")) {
        imageView.setLayoutX(imageView.getLayoutX() + 2);
        //System.out.println(imageView.getLayoutX());
      } else if (direction.equals("right")) {
        imageView.setLayoutX(imageView.getLayoutX() - 2);
        //System.out.println(imageView.getLayoutX());
      }
      lastIndex = index;
    }

    if (((index > 8 && index <= 16) || (index > 24)) && imageView.getImage() != stillKnight) {
      imageView.setImage(stillKnight);
    } else if (index <= 8 && imageView.getImage() != lfKnight) {
      imageView.setImage(lfKnight);
    } else if ((index > 16 && index <= 24) && imageView.getImage() != rfKnight) {
      imageView.setImage(rfKnight);
    }

    if (index == 15) {
      System.out.println("graphics: " +(int)(imageView.getLayoutY()/32) + ", " + ((int)(imageView.getLayoutX()/32)));
      System.out.println("##############################");
    }
  }
}
