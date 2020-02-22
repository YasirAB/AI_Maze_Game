//package logic;
//parent class for both the player piece and the enemy
//which places them on the board and handles movement
public abstract class Piece{

  protected int row;
  protected int col;
  public Board maze;
  public SurroundingsChecker surch = new SurroundingsChecker();

  /**
   * constructor to initialize starting position of piece on the game board
   * @param theBoard the board to use
   * @param startRow initial row of piece
   * @param startCol intital column of piece
   */
  public Piece(Board theBoard, int startRow, int startCol) {
    maze = theBoard;
    row = startRow;
    col = startCol;
  }
 /**
  * a getter method for the current row
  * @return current row
  */
  public int getRow() {
    return row;
  }
  /**
   * a getter method for current column of player
   * @return current column
   */
  public int getCol() {
    return col;
  }

  /**
   * boolean method for checking if the space the piece is trying to move to is empty
   * not occupied by a enemy
   * @param theRow row piece is trying to move to
   * @param theCol row piece is tring to move to
   * @return true if valid else false
   */
  public boolean checkSpace(int theRow, int theCol) {
    return surch.checkEmpty(maze, theRow, theCol);
  }

  /**
   * method checks if moving up is valid and if it is will move piece that placement on the board
   */
  public abstract void moveUp();
  /**
   * method checks if moving down is valid
   *  and if it is will move piece to that placement on the board
   */
  public abstract void moveDown();
   /**
    * method checks if moving right is valid
    * if it is will move piece to that placement on the board
    */

  public abstract void moveRight();
  /**
   * method checks if moving left is valid
   * and if it is will move piece that to placement on the board
   */
  public abstract void moveLeft();


}
