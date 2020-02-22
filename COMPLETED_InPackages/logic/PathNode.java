package logic;

//package logic;

/**
* This class object stores information about the node/piece of the map for the
* AI pathing algorithm to work with
**/

public class PathNode extends MapNode {

  //instance variables
  private int hValue;
  private int gValue = 5;
  private int depth;
  private int cost;
  private PathNode parent;
  private boolean isGoal = false;

  // Constructor meant to initialize nodes to match the nodes of the base map
  public PathNode(MapNode twin) {
    super(twin.getRow(), twin.getCol());
    this.setPassability(twin.getPassability());
    this.finish(twin.getFinish());
  }

  /**
  * for returning the cost.
  * @return "cost" of the node.
  */
  public int getCost() {
    return cost;
  }

  /**
  * for setting the cost based on hValue and gValue, must be used after both have been set already.
  */
  public void setCost(){
    cost = (3 * hValue) + gValue;
  }

  /**
  * for setting a nodes parent and calculating it's movement cost (gValue)
  * @param par the node which will be made the parent of this node.
  */
  public void setParent(PathNode par) {
    parent = par;
    depth = par.depth + 1;
    gValue = depth * 5;
  }

  /**
  * Returns value of parent
  * @return
  */
  public PathNode returnParent() {
    return parent;
  }
  /**
  * makes this node the goal
  */
  public void makeGoal() {
    isGoal = true;
  }
  /**
  * makes this node no longer the goal
  */
  public void makeNotGoal() {
    isGoal = false;
  }
  /**
  * Returns value of isGoal
  * @return
  */
  public boolean getGoal() {
    return isGoal;
  }

	/**
	* Sets new value of hValue
	* @param
	*/
	public void setHValue(int hValue) {
		this.hValue = hValue;
	}

	 /**
	    * Sets new value of hValue
	    * @param
	    */

     public void setGValue(int gValue) {
	        this.gValue = gValue;
	    }

	 public double getHValue() {
	        return hValue;
	    }

	public boolean isGoal() {
		return isGoal;
	}

	public void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}

	public int gethValue() {
		return hValue;
	}

	public void sethValue(int hValue) {
		this.hValue = hValue;
	}

}
