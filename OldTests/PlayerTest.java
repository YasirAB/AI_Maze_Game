import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void testConstructor(){
		Board b = new Board(30,30);
		Player p = new Player(b, 10, 10);
		boolean play = b.board[10][10].checkPlayer();
	    boolean pass = b.board[10][10].getPassability();
	    
		assertEquals("testing row", 10, p.getRow());
		assertEquals("testing column", 10, p.getCol());	
		assertEquals("testing passability boolean", true, pass);
		assertEquals("testing player boolean", true, play);
		
	}
	
	@Test
	public void testUpdateSurrondings(){
		Board b = new Board(30,30);
		Player p = new Player(b, 10, 10);
		p.updateSurroundings();
		int size = p.possibleDirections.size();
	    
		assertTrue("size of array must be greater than 0", size > 0);
		
	}
	
	@Test
	public void testMoveUp(){
		Board b = new Board(30,30);
		Piece p = new Piece(b, 10, 10);
		 SurroundingsChecker surch = new SurroundingsChecker();
	    ArrayList<int[]> possibleDirections = surch.checkAround(b,10,10);
		b.board[10][10].setPassability(true);
		p.moveUp();
		if(surch.isInArray(possibleDirections, 9, 10)){
			assertEquals("testing row", 9, p.getRow());
		}
		else {
			assertEquals("testing row", 10, p.getRow());
		}
				
	}
	
	@Test
	public void testMoveDown(){
		Board b = new Board(30,30);
		Piece p = new Piece(b, 10, 10);
		 SurroundingsChecker surch = new SurroundingsChecker();
	    ArrayList<int[]> possibleDirections = surch.checkAround(b,10,10);
		b.board[10][10].setPassability(true);
		p.moveDown();
		if(surch.isInArray(possibleDirections, 11, 10)){
			assertEquals("testing row", 11, p.getRow());
		}
		else {
			assertEquals("testing row", 10, p.getRow());
		}
				
	}
	@Test
	public void testMoveLeft(){
		Board b = new Board(30,30);
		Piece p = new Piece(b, 10, 10);
		 SurroundingsChecker surch = new SurroundingsChecker();
	    ArrayList<int[]> possibleDirections = surch.checkAround(b,10,10);
		b.board[10][10].setPassability(true);
		p.moveLeft();
		if(surch.isInArray(possibleDirections, 10, 9)){
			assertEquals("testing column", 9, p.getCol());
		}
		else {
			assertEquals("testing column", 10, p.getCol());
		}
				
	}
	@Test
	public void testMoveRight(){
		Board b = new Board(30,30);
		Piece p = new Piece(b, 10, 10);
		 SurroundingsChecker surch = new SurroundingsChecker();
	    ArrayList<int[]> possibleDirections = surch.checkAround(b,10,10);
		b.board[10][10].setPassability(true);
		p.moveRight();
		if(surch.isInArray(possibleDirections, 10, 11)){
			assertEquals("testing column", 11, p.getCol());
		}
		else {
			assertEquals("testing column", 10, p.getCol());
		}
				
	}
	
	
	
}
