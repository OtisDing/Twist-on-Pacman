//Written by Otis Ding
//Student ID: 251220811

//Pel class - an object that represents a pixel on the game screen
public class Pel {

	//Initialize variables
	private int colour;
	private Location Location;
	
	//Constructor - initializes instanced variables, the Pel's location and its colour
	public Pel(Location p, int colour) {
		this.colour = colour;
		this.Location = p;
	}
	
	//Returns the Location object stored in this Pel object
	public Location getLocus() {
		return this.Location;
	}
	
	//Returns the colour associated with this Pel object
	public int getColor() {
		return this.colour;
	}
	
	
}
