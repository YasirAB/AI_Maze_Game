//package logic;
public class PathNode extends MapNode {
  private int hValue;
  private int gValue = 5;
  private int depth;
  private int cost;
  private PathNode parent;
  private boolean isGoal = false;

  // Constructor meant to initialize nodes to match the nodes of the base map
  public PathNode(MapNode twin) {
    super(twin.getRow(), twin.getRow());
    passible = twin.getPassability();
    isFinish = twin.getFinish();
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
	
	public void setGValue(int gValue) {
		this.gValue = gValue;
	}
	
	public double getHValue() {
		return hValue;
	}
}
