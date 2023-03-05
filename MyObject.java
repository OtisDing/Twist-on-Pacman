//Written by Otis Ding
//Student ID: 251220811

//MyObject class - a class that describes any of the figures on the game screen, whether that be the player, enemies, or walls
public class MyObject implements MyObjectADT{
	
	//Declare some variables
	private int id, width, height;
	private String type;
	private Location pos;
	private BinarySearchTree tree;
	
	//Constructor - initializes the objects ID, its width, height, its type, and its locus
	public MyObject(int id, int width, int height, String type, Location pos) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.type = type;
		this.pos = pos;
		
		//Initializes a new binary search tree
		this.tree = new BinarySearchTree();	
	}
	
	//Set function for setting the objects type
	public void setType (String type) {
		this.type = type;
	}
	
	//Get function that returns the width of the object
	public int getWidth() {
		return this.width;
	}
	
	//Get function that returns the height of the object
	public int getHeight() {
		return this.height;
	}
	
	//Get function that returns the type of the object
	public String getType() {
		return this.type;
	}
	
	//Get function that returns the ID of the object
	public int getId() {
		return this.id;
	}
	
	//Get function that returns the Location object describing the locus of the object
	public Location getLocus() {
		return this.pos;
	}
	
	//Set function that sets the location of the locus of the object
	public void setLocus(Location value) {
		this.pos = value;
	}
	
	//AddPel function - adds a pixel into the binary search tree in which we store all the object's pixels
	public void addPel(Pel pix) throws DuplicatedKeyException{
		this.tree.put(this.tree.getRoot(), pix);
	}
	
	//Intersect method - checks if this object has any pixels that intersects with any of the pixels in the other object given in the parameters
	public boolean intersects(MyObject otherObject) {
		
		//Makes variables describing the coordinates describing the rectangle around this object
		int thisBegX = this.pos.getx();
		int thisEndX = this.pos.getx() + this.width;
		
		int thisBegY = this.pos.gety();
		int thisEndY = this.pos.gety() + this.height;
		
		
		//Makes variables describing the coordinates describing the rectangle around the other object
		int otherBegX = otherObject.getLocus().getx();
		int otherEndX = otherObject.getLocus().getx() + otherObject.getWidth();
		
		int otherBegY = otherObject.getLocus().gety();
		int otherEndY = otherObject.getLocus().gety() + otherObject.getHeight();
		
		
		//Checks if the rectangle of this object overlaps with the rectangle of the other object
		//Returns false if there is no overlap to improve the efficiency of our code
		if (thisBegX > otherEndX) {
			return false;
		} 
		if (thisEndX < otherBegX) {
			return false;
		}
		if (thisBegY > otherEndY) {
			return false;
		}
		if (thisEndY < otherBegY) {
			return false;
		}
		
		//Makes a new Pel object that we'll first set to null
		Pel thisPel = null;
		
		try {
			//We set thisPel to be the smallest Pel in this object's tree
			thisPel = this.tree.smallest(this.tree.getRoot());
		} catch (EmptyTreeException e) {
			System.out.println("Exception");
		}
			
		
		//While loop that continues until either the method returns, or we have gone through all the Pels in this object's tree
		while (thisPel != null) {
			
			//Using the formula given to us in the assignment pdf
			//We set temp to be the Location object with the results of the formula
			int x = thisPel.getLocus().getx() + this.getLocus().getx() - otherObject.getLocus().getx();
			int y = thisPel.getLocus().gety() + this.getLocus().gety() - otherObject.getLocus().gety();
			Location temp = new Location(x, y);
			
			//Using the findPel function, we see if temp exists in the other objects tree, if it does, then that means there is an intersection
			if (otherObject.findPel(temp)) {
				
				//If there is an intersection, we return true
				return true;
			} else {
				
				//Otherwise, we set thisPel to the next smallest Pel in this objects tree, effectively traversing the entire tree if need be
				thisPel = this.tree.successor(this.tree.getRoot(), thisPel.getLocus());
			}
		}
		
		//If there is no intersection even after going through the entire tree, then there is no intersection between the objects, so we return false
		return false;
	}
	
	//FindPel method - used to confirm if a Pel object exists in the tree
	private boolean findPel(Location p) {
		//Uses the get function, if it returns null, then p does not exist in the tree, otherwise it does.
		if (this.tree.get(this.tree.getRoot(), p) == null) {
			return false;
		} else {
			return true;
		}
	}

}
