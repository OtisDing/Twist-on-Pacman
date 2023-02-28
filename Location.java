//Written by Otis Ding
//Student ID: 251220811

//Class location: used for storing the coordinates of a Pel object
public class Location {
	
	private int x, y;
	
	//Constructor - initializes the instanced variables of the location's x and y values
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//Get function
	//Returns the x value of the location
	public int getx() {
		return this.x;
	}
	
	//Get function
	//Returns the y value of the location
	public int gety() {
		return this.y;
	}
	
	
	//compareTo function
	//Compares this location with another one, returns 1 if this location is greater, 0 if they are equal, and -1 if this location is lesser
	public int compareTo(Location p) {
		if ((this.gety() > p.gety()) || ((this.gety() == p.gety()) && (this.getx() > p.getx()))) {
			return 1;
		} else if ((this.getx() == p.getx()) && (this.gety() == p.gety())) {
			return 0;
		} else if ((this.gety() < p.gety()) || ((this.gety() == p.gety()) && this.getx() < p.getx())) {
			return -1;
		}
		return 404;
	}

}
