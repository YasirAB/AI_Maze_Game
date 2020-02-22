package logic;

//package logic;
/**A Class for storing basic data on map nodes object used in creating the path
* for the ai to follow.
**/

public class MapNode {

  // instance variables
  private int row;
  private int column;
  private boolean passible = true;
  private boolean isFinish = false;
  private boolean isPlayer = false;
  private boolean isEnemy = false;

  /** Constructor which sets the position of mapnode object
  * @param x: the x coordinate of the mapnode
  * @param y: the y coordinate of the mapnode
  */
  public MapNode(int x, int y) {
    row = x;
    column = y;
  }

  // the getters for the instance variable
  public int getRow() {
    return row;
  }

  public int getCol() {
    return column;
  }

  public boolean getFinish() {
    return isFinish;
  }

  public boolean checkEnemy() {
    return isEnemy;
  }

  public boolean getPassability() {
    return passible;
  }

  public boolean checkPlayer() {
    return isPlayer;
  }

  //The setters for the instance variable
  public void setPassability(boolean pass) {
    passible = pass;
  }

  public void setFinish() {
    isFinish = true;
  }
  public void  finish(boolean finish) {
	 isFinish = finish;
  }

  public void setEnemy(boolean state) {
    isEnemy = state;
  }

  public void setPlayer(boolean state) {
    isPlayer = state;
  }

public boolean isEnemy() {
	return isEnemy;
}


}
