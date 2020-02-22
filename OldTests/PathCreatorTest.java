import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;


import org.junit.Test;

public class PathCreatorTest {

	
	@Test
	public void testConstructor(){
		
		Board b = new Board(30,30);
		PathCreator pc = new PathCreator(b);
		
		assertTrue("testing is nodes have been added to list", pc.nodes.length > 0);
	}
	
	
	@Test
	public void testUpdatePiecePostion(){
		Board b = new Board(30,30);
		PathCreator pc = new PathCreator(b);
		   
		  b.board[10][10].setEnemy(true);
		  pc.updatePiecePositions();
     
		
		SurroundingsChecker surch = new SurroundingsChecker();
		ArrayList<PathNode> open = new ArrayList<PathNode>();
	    ArrayList<PathNode> closed = new ArrayList<PathNode>();
		
	    
		assertTrue("testing row", pc.nodes[10][10].isEnemy == true);
}
	@Test
	public void testUpdateNodes(){
		
		Board b = new Board(30,30);
		PathCreator pc = new PathCreator(b);
		pc.goalColumn = 11;
		pc.goalRow = 13;
		pc.updateNodes(22, 24);
		
		
		assertTrue("new goal should be set to (22,24)", pc.nodes[22][24].isGoal == true);
		assertTrue("old goal should not exist", pc.nodes[13][11].isGoal == false);
		
	}
	
	@Test
	public void testAddToOpenFound(){
		Board b = new Board(30,30);
		PathCreator pc = new PathCreator(b);
		pc.nodes[11][11].makeGoal();
		pc.addToOpen(1, 1, 10, 10);
		
		
		assertTrue("found should be set to zero", pc.found == true);
	}
	
	@Test
	public void testAddToOpenArray(){
		Board b = new Board(30,30);
		PathCreator pc = new PathCreator(b);
		pc.goalColumn = 20;
		pc.goalRow = 20;
		pc.addToOpen(1, 1, 10, 10);
		
		
		assertEquals("parent should orignal row and column", pc.nodes[10][10] , pc.nodes[11][11].returnParent());
		assertEquals("should be calculated manhatten score", 18 , pc.nodes[11][11].hValue);
		assertEquals("cost calculateed should be", 59 , pc.nodes[11][11].getCost());
		assertTrue("open array list should now contain the point",  pc.open.contains(pc.nodes[11][11]));
	}
	
	@Test
	public void testAddToClosedArray(){
		Board b = new Board(30,30);
		PathCreator pc = new PathCreator(b);
		pc.goalColumn = 20;
		pc.goalRow = 20;
		pc.open.add(pc.nodes[10][10]);
		pc.addToClosed(10, 10);
		
		
		assertTrue("closed array list should now contain the point",  pc.closed.contains(pc.nodes[10][10]));
		assertTrue("open array list should not contain the point",  !pc.open.contains(pc.nodes[10][10]));
	}
	
	@Test
	public void testAddToClosedOpenNowContainsPoints(){
		Board b = new Board(30,30);
		PathCreator pc = new PathCreator(b);
		pc.goalColumn = 20;
		pc.goalRow = 20;
		pc.addToClosed(10, 10);
		
		
		assertTrue("open array list should now contain the point (11,10)",  pc.open.contains(pc.nodes[11][10]));
		assertTrue("open array list should now contain the point (10,11)",  pc.open.contains(pc.nodes[10][11]));
		assertTrue("open array list should now contain the point (9,10)",  pc.open.contains(pc.nodes[9][10]));
		assertTrue("open array list should now contain the point (10,9)",  pc.open.contains(pc.nodes[10][9]));
	
	}
	
	@Test
	public void testPathFinder(){
		
		Board b = new Board(30,30);
		PathCreator pc = new PathCreator(b);
		
		for (int x = 0; x < 30; x++) {
		      for (int y = 0; y < 30; y++) {
		        pc.nodes[x][y].setPassability(false);
		      }
		}
		pc.nodes[11][10].setPassability(true);
		pc.nodes[12][10].setPassability(true);
		pc.nodes[12][11].setPassability(true);
		pc.nodes[12][12].setPassability(true);
		pc.nodes[13][12].setPassability(true);
		pc.nodes[13][13].setPassability(true);
		pc.nodes[14][13].setPassability(true);
		pc.nodes[14][14].setPassability(true);
		pc.goalColumn = 14;
		pc.goalRow = 14;
		ArrayList<MapNode> result = pc.findPath(10, 10, 14, 14);
		
		
		assertTrue("found should be true", pc.found == true);
		assertTrue("testing if first nodes have been added to list 0", result.get(0) == pc.nodes[11][10]);
	
	}
	@Test
	public void testCheckPathFalse(){
		
		Board b = new Board(30,30);
		PathCreator pc = new PathCreator(b);
		
		for (int x = 0; x < 30; x++) {
		      for (int y = 0; y < 30; y++) {
		        pc.nodes[x][y].setPassability(false);
		      }
		}
		
		  pc.checkForPath(10, 10, 23, 23);
		assertTrue("testing no path should be found", !pc.found);
	}
	
	@Test
	public void testCheckPathTrue(){
		
		Board b = new Board(30,30);
		PathCreator pc = new PathCreator(b);
		
		for (int x = 0; x < 30; x++) {
		      for (int y = 0; y < 30; y++) {
		        pc.nodes[x][y].setPassability(true);
		      }
		}
		
		  pc.checkForPath(10, 10, 23, 23);
		assertTrue("testing path should be found", pc.found);
	}
	
}
