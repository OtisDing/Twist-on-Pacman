//Written by Otis Ding
//Student ID: 251220811

//Class BNode - an object meant to represent the node of a binary search tree
public class BNode {

	//Initialize variables
	private Pel value;
	private BNode leftChild, rightChild, parent;

	//Constructor - initalizes the Pel value that will be held in the node, the node's left and right children, and the node's parent
	public BNode(Pel value, BNode left, BNode right, BNode parent) {
		this.value = value;
		this.leftChild = left;
		this.rightChild = right;
		this.parent = parent;
	}
	
	//Constructor - In the case no parameters are given, initializes all the instanced variables to null
	public BNode() {
		this.value = null;
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
	}
	
	//Returns the parent node
	public BNode parent() {
		return this.parent;
	}
	
	//Set function that allows us to change this specific node's parent node
	public void setParent(BNode newParent) {
		this.parent = newParent;
	}
	
	//Set function that allows us to change this node's left child
	public void setLeftChild(BNode p) {
		this.leftChild = p;
	}
	
	//Set function that allows us to change this node's right child
	public void setRightChild(BNode p) {
		this.rightChild = p;
	}
	
	//Set function that allows us to change the node's content, as in its Pel object that it stores
	public void setContent(Pel value) {
		this.value = value;
	}
	
	//Function isLeaf() checks if this node is a leaf node
	public boolean isLeaf() {
		//A leaf node is one that has no content, and no children
		if ((this.value == null) && (this.leftChild == null) && (this.rightChild == null)) {
			return true;
		} else {
			return false;
		}
	}
	
	//Get function that returns the Pel object stored in this node
	public Pel getData() {
		return this.value;
	}
	
	//Essentially a get function that returns this node's left child
	public BNode leftChild() {
		return this.leftChild;
	}
	
	//Essentially a get function that returns this node's right child
	public BNode rightChild() {
		return this.rightChild;
	}
	
	
	
}
