import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class SeekerEnemyTest {

	@Test
	public void testConstructor(){
		Board b = new Board(30,30);
		SeekerEnemy p = new SeekerEnemy(b, 1, 10, 10);
		boolean play = b.board[10][10].checkEnemy();
	    boolean pass = b.board[10][10].getPassability();
	    
		assertEquals("testing row", 10, p.getRow());
		assertEquals("testing column", 10, p.getCol());	
		assertEquals("testing passability boolean", true, pass);
		assertEquals("testing enemy boolean", true, play);
		
	}
	@Test
	public void testMoveToTileValid(){
		Board b = new Board(30,30);
		SeekerEnemy p = new SeekerEnemy(b,0, 9, 10);
		b.board[9][10].setPassability(false);
		b.board[8][10].setEnemy(false);
		p.moveToTile(8,10);
		assertEquals("testing row", 8, p.getRow());
			
	}
	
	@Test
	public void testMoveToTileInvalid(){
		Board b = new Board(30,30);
		SeekerEnemy p = new SeekerEnemy(b,0, 9, 10);
		b.board[9][10].setPassability(false);
		b.board[8][10].setEnemy(true);
		p.moveToTile(8,10);
		assertEquals("testing row", 9, p.getRow());
			
	}
	
	@Test
	public void testSeekMoveValid(){
		Board b = new Board(30,30);
		SeekerEnemy p = new SeekerEnemy(b,0, 9, 10);
		b.board[9][10].setPassability(false);
		PathCreator myPath =  new PathCreator(b);
		ArrayList<MapNode> pathToUse = myPath.findPath(9, 10, 20, 15);
		ArrayList<MapNode> old = pathToUse;
		p.seekMove(20,15);
		pathToUse = myPath.findPath(p.row, p.col, 20, 15);
		assertTrue("testing is size of new path less than old path", pathToUse.size() < old.size());
			
	}
	@Test
	public void testSeekMoveInvalid(){
		Board b = new Board(30,30);
		SeekerEnemy p = new SeekerEnemy(b,0, 9, 10);
		b.board[9][10].setPassability(false);
		b.board[9][11].setEnemy(true);
		b.board[10][10].setEnemy(true);
		PathCreator myPath =  new PathCreator(b);
		ArrayList<MapNode> pathToUse = myPath.findPath(9, 10, 11, 11);
		ArrayList<MapNode> old = pathToUse;
		p.seekMove(11,11);
		pathToUse = myPath.findPath(p.row, p.col, 11, 11);
		assertTrue("testing is size of new path greater or equal than old path", pathToUse.size() >= old.size());
			
	}
	
	@Test
	public void testRandomMove(){
		Board b = new Board(30,30);
		SeekerEnemy p = new SeekerEnemy(b,0, 9, 10);
		b.board[9][10].setPassability(false);
		
		p.randomMove();
		
		assertTrue("testing if the enemy has moved one place random",!b.board[9][10].checkEnemy());
			
	}
	@Test
	public void testTakeTurnOnlyRandom(){
		Board b = new Board(30,30);
		SeekerEnemy p = new SeekerEnemy(b,0, 9, 10);
		b.board[9][10].setPassability(false);
		p.speed = 1000;
		p.focus = 100;
		p.takeTurn(20,11);
		assertTrue("testing if the enemy has moved one place random",!b.board[9][10].checkEnemy());
			
	}
	@Test
	public void testTakeTurnOnlySeek(){
		Board b = new Board(30,30);
		SeekerEnemy p = new SeekerEnemy(b,0, 9, 10);
		b.board[9][10].setPassability(false);
		PathCreator myPath =  new PathCreator(b);
		ArrayList<MapNode> pathToUse = myPath.findPath(9, 10, 20, 15);
		ArrayList<MapNode> old = pathToUse;
		p.takeTurn(20,15);
		pathToUse = myPath.findPath(p.row, p.col, 20, 15);
		assertTrue("testing is size of new path less than old path", pathToUse.size() < old.size());
			
	}
	
	
	
}
