import java.util.*;
import java.util.Random;
import java.lang.Math;


public class Move{

  public static int currentColumn = (int)(Math.random() * 21);
  public static int currentRow = 1;

/**
* returns true if the column and row in the board object is empty, false if not empty.
**/
  public static boolean postionNotOccupied(int column, int row, Board gameboard){
    if(gameboard.board[column][row] == "#" || gameboard.board[column][row] == "X")
      return true;
    else
      return false;
  }

/**
* moves player to the new column and row in the Board object, deletes their old
* position and makes it into an empty spot, then updates the current position
**/
  public static void movePlayerToPosition(int column, int row, Board gameboard){
    gameboard.board[column][row] = "!";
    deletePlayerOldPostion(currentColumn, currentRow, gameboard);
    currentColumn = column;
    currentRow = row;
  }

/**
*  turns the position specified into an empty spot
*/
  public static void deletePlayerOldPostion(int column, int row, Board gameboard){
    gameboard.board[column][row] = "#";
  }

/**
* Will ask user for a move(AWSD), if invalid then will prompt again. Will then
* check if the move the user wants to make is empty, if not then will prompt again.
* If the move the user wants to make is doable, will make the move through
* movePlayerToPosition method.
**/
  public static void makeAMove(Board gameboard) // would add Board as an argument
  {
    Scanner kbr = new Scanner(System.in);
    boolean correctInput;

    do{
      correctInput = true;
      System.out.println("Please enter the direction you would like to move in (AWSD): ");
      String direction = kbr.next();
      //System.out.println("direction: " + direction);
      if(direction.equals("A") || direction.equals("a"))
      {
        //System.out.println("is position occupied? " + postionNotOccupied(currentColumn, currentRow-1, gameboard));
        if(postionNotOccupied(currentColumn, currentRow-1, gameboard))
          movePlayerToPosition(currentColumn, currentRow-1, gameboard);
        else
          correctInput = false;
      }
      else if(direction.equals("W") || direction.equals("w"))
      {
        //System.out.println("is position occupied? " + postionNotOccupied(currentColumn-1 , currentRow, gameboard));
        if(postionNotOccupied(currentColumn-1 , currentRow, gameboard))
          movePlayerToPosition(currentColumn-1, currentRow, gameboard);
        else
          correctInput = false;
      }
      else if(direction.equals("S") || direction.equals("s"))
      {
        //System.out.println("is position occupied? " + postionNotOccupied(currentColumn+1, currentRow, gameboard));
        if(postionNotOccupied(currentColumn+1, currentRow, gameboard))
          movePlayerToPosition(currentColumn+1, currentRow, gameboard);
        else
          correctInput = false;
      }
      else if(direction.equals("D") || direction.equals("d"))
      {
        //System.out.println("is position occupied? " + postionNotOccupied(currentColumn, currentRow+1, gameboard));
        if(postionNotOccupied(currentColumn, currentRow+1, gameboard))
          movePlayerToPosition(currentColumn, currentRow+1, gameboard);
        else
          correctInput = false;
      }
      else
        correctInput = false;

      if(!correctInput){
        //System.out.println("That is an invalid move, try again.");
      }
    }while(!correctInput);

  }

}
