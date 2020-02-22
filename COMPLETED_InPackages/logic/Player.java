package logic;

//package logic;
import java.util.ArrayList;
import java.util.Scanner;

//subclass of piece which is responsible for all player movement and recieving user input

public class Player extends Piece{
  //Instance variables possible directions is an array of all the possible movements for the player
 // correctInput makes sure user entry is valid
  private ArrayList<int[]> possibleDirections = surch.checkAround(maze, getRow(), getCol());
  private boolean correctInput = true;

  /**
   * method to update all of the player's possible movements at the start of every turn for the player
   */
  public void updateSurroundings() {
    possibleDirections = surch.checkAround(maze, getRow(), getCol());
  }
 /**
  * constructor to initialize the position of the player and set their passability to true
  * calls on the parent class to place player on board
  * @param myBoard current board
  * @param startRow starting row of player
  * @param startCol starting column of player
  */
  public Player(Board myBoard, int startRow, int startCol) {
    super(myBoard, startRow, startCol);
    maze.board[getRow()][getCol()].setPlayer(true);
    maze.board[getRow()][getCol()].setPassability(true);
  }
/**
 * method for player moving left
 * first make an array of all possible moves if the user enter a possible move, it will call
 * on the parent to run moveLeft and it will set player true to its new position
 * if the input is not a valid move the user will be prompted for another input
 */
  public void moveLeft() {
    updateSurroundings();
    if(surch.isInArray(possibleDirections, getRow(), getCol()-1)) {
      maze.board[getRow()][getCol()].setPlayer(false);
      if (maze.board[getRow()][getCol()-1].getPassability()) {
    	  setCol(getCol()-1);
      }
      maze.board[getRow()][getCol()].setPlayer(true);
      } else {
      correctInput = false;
    }
  }
  /**
   * method for player moving up
   * first make an array of all possible moves. If the user enter a possible move, it will call
   * on the parent to run moveUP and it will set player true to its new position
   * if the input is not a valid move the user will be prompted for another input
   */
  public void moveUp(){
    updateSurroundings();
    if(surch.isInArray(possibleDirections, getRow()-1, getCol())) {
      maze.board[getRow()][getCol()].setPlayer(false);
      if (maze.board[getRow()-1][getCol()].getPassability()) {
    	  setRow(getRow()-1);
      }
      maze.board[getRow()][getCol()].setPlayer(true);
    } else {
      correctInput = false;
    }
  }
  /**
   * method for player moving down
   * first make an array of all possible moves. If the user enter a possible move, it will call
   * on the parent to run moveUP and it will set player true to its new position
   * if the input is not a valid move the user will be prompted for another input
   */
  public void moveDown(){
    updateSurroundings();
    if(surch.isInArray(possibleDirections, getRow()+1, getCol())) {
      maze.board[getRow()][getCol()].setPlayer(false);
      if (maze.board[getRow()+1][getCol()].getPassability()) {
        setRow(getRow()+1);
      }
      maze.board[getRow()][getCol()].setPlayer(true);
    } else {
      correctInput = false;
    }
  }
  /**
   * method for player moving right
   * first make an array of all possible moves. If the user enter a possible move, it will call
   * on the parent to run moveRight and it will set player true to its new position
   * if the input is not a valid move the user will be prompted for another input
   */
  public void moveRight(){
    updateSurroundings();
    if(surch.isInArray(possibleDirections, getRow(), getCol()+1)) {
      maze.board[getRow()][getCol()].setPlayer(false);
      if (maze.board[getRow()][getCol()+1].getPassability()) {
        setCol(getCol()+1);
      }
      maze.board[getRow()][getCol()].setPlayer(true);
    } else {
      correctInput = false;
    }
  }
  /**
   * method for receiving the user input and call the correct move function according to the input
   * if the input is not a valid direction user will be asked again for input
   */
  public void inputMove() {
    Scanner kbr = new Scanner(System.in);

    do{
      correctInput = true;
      System.out.println("Please enter the direction you would like to move in (AWSD): ");
      String direction = kbr.next();

      if(direction.equals("A") || direction.equals("a"))
        moveLeft();             //System.out.println("is position occupied? " + postionNotOccupied(currentColumn, currentRow-1, gameboard));
      else if(direction.equals("W") || direction.equals("w"))
        moveUp();     //System.out.println("is position occupied? " + postionNotOccupied(currentColumn-1 , currentRow, gameboard));
      else if(direction.equals("S") || direction.equals("s"))
        moveDown();//System.out.println("is position occupied? " + postionNotOccupied(currentColumn+1, currentRow, gameboard));
      else if(direction.equals("D") || direction.equals("d"))
        moveRight();//System.out.println("is position occupied? " + postionNotOccupied(currentColumn, currentRow+1, gameboard));
      else
        correctInput = false;

      if(!correctInput)
        System.out.println("That is an invalid move, try again.");

    }while(!correctInput);
  }
public ArrayList<int[]> getPossibleDirections() {
	return possibleDirections;
}
public void setPossibleDirections(ArrayList<int[]> possibleDirections) {
	this.possibleDirections = possibleDirections;
}
}
