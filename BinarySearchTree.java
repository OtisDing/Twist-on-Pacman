//Written by Otis Ding
//Student ID: 251220811

//Binary Search Tree class - The data structure we'll be using to store all of our our pixels
public class BinarySearchTree implements BinarySearchTreeADT{

	private BNode root;

	//Initialize the root of our tree with an empty node
	public BinarySearchTree() {
		this.root = new BNode();
	}
	
	//Get function - returns the Pel object with the location object specified in the parameters
	public Pel get(BNode r, Location key) {
		
		//If r is a leaf, then it means we've arrived at where the node containing key should have been, and since its not here, we return null
		if (r.isLeaf()) {
			return null;
		} else {
			
			//Checks if the node we're on has the key we're looking for, if it does, return the Pel object storing it
			if ((r.getData().getLocus().compareTo(key)) == 0) {
				return r.getData();
			} else {
				
				//If the node we're on has a location greater than the key, we go to the left child, since it will be lesser, and so closer to our destination
				if ((r.getData().getLocus().compareTo(key)) == -1) { // Might be 1 instead of -1
					return get(r.leftChild(), key);
				//Otherwise, we go to the right child, since the right child is larger, and our key is larger than whats in our current node
				} else {
					return get(r.rightChild(), key);
				}
			}
		}
	}
	
	//Put method - inserts a new node at its correct position in the tree given new data
	public void put(BNode r, Pel newData) throws DuplicatedKeyException {
		
		//Checks if we can get with this new data
		//If the get function returns anything other than null, it means there already exists a node with the data we're trying to insert. This is not allowed
		if (get(r, newData.getLocus()) != null) {
			throw new DuplicatedKeyException("Error: Key Already Exists");
		} else {
			
			//Using a private method, we find the specific leaf node where we would be inserting the new data, and thus making into an internal node
			BNode place = getPlacement(r, newData.getLocus());
			//Makes 2 new nodes to represent the new internal node's children
			BNode leftChild = new BNode();
			BNode rightChild = new BNode();
			
			//Sets the content of the new internal node to the new data
			place.setContent(newData);
			
			//Sets the node's left child and right child to the nodes we've just made above
			place.setLeftChild(leftChild);
			place.setRightChild(rightChild);
			
			//Sets the two nodes' parent to our new internal node
			leftChild.setParent(place);
			rightChild.setParent(place);
		}
	}
	
	//Remove method - removes a specific Node given its key
	public void remove(BNode r, Location key) throws InexistentKeyException{
		
		//Using a private method, assigns p to the node that is storing the key we're looking for
		BNode p = getNode(r, key);
		
		//If p == null, then that means there is no node in our tree that is storing the key, this results in an exception
		if (p == null) {
			throw new InexistentKeyException("Error: Key does not exists");
		} else {
			
			//Checks if either of p's children are leafs
			if (p.rightChild().isLeaf() || p.leftChild().isLeaf()) {
				
				//Checks if p's right child is a leaf
				if (p.rightChild().isLeaf()) {
					
					//Assigns c to be p's other child
					BNode c = p.leftChild();
					
					//Makes a temp node to represent p's parent
					BNode temp = new BNode();
					temp = p.parent();
					
					//If temp isn't null - as in if p isn't the root of the tree
					if (temp != null) {
						
						//We reassign c's parent to be temp to cut off p from the tree
						c.setParent(temp);
						temp.setRightChild(c);
						return;
					
					//If p is the root of the tree
					} else {
						//We reassign the root of the tree to be c
						this.root = c;
						this.root.setParent(null);
						return;
					}
				
				//Checks the same thing as above, only this time p is the right child
				} else if (p.leftChild().isLeaf()) {
					BNode c = p.rightChild();
					
					BNode temp = new BNode();
					
					temp = p.parent();
					if (temp != null) {
						temp.setLeftChild(c);
						c.setParent(temp);
						return;
					} else {
						this.root = c;
						this.root.setParent(null);
						return;
					}
				}
			
			//If neither of p's children are internal nodes
			} else {
				try {
					//Assigns s to the smallest node within the subtree with p as the root
					BNode s = getNode(p, smallest(p.rightChild()).getLocus());
					
					//Replaces p with s, then deletes s
					p.setContent(s.getData());
					remove(s, s.getData().getLocus());
				} catch (EmptyTreeException e) {
				}
			}
		}
	}
	
	//Successor method - finds the Pel object with the smallest location value greater than the one provided in the parameters
	public Pel successor(BNode r, Location key) {
		
		//Checks if r is a leaf, if it is, returns null
		if (r.isLeaf()) {
			return null;
		} else {
			
			//Makes a temp node that represents the node storing the key
			BNode temp = getNode(r, key);
			
			//If temp == null, as in there is no node storing the key object we're looking for
			if (temp == null) {
				
				//We make a new Pel object, store that pel object in the tree and run the successor method again recursively
				//This will allow us to find the result we're looking for
				//Then we remove that additional node we added so we return the tree to how it was before
				Pel tempPel = new Pel(key, 2);
				try {
					put(r, tempPel);
				} catch (DuplicatedKeyException e) {
				}
				Pel res = successor(r, key);
				
				try {
					remove(r, key);
				} catch (InexistentKeyException e) {
				}
				
				return res;
			}
			
			//Checks to see if temp has children that aren't leaves - As in both of temp's children are internal nodes
			if ((temp.rightChild().getData() != null) || (temp.leftChild().getData() != null)) {
				try {
					//We return the smallest Pel from temp's right side, which is guaranteed to be the next smallest after temp
					return smallest(temp.rightChild());
				} catch (EmptyTreeException e) {
				}
				try {
					//If the above didn't work, we get the largest of temp's left child
					return largest(temp.leftChild());
				} catch (EmptyTreeException e) {
				}
			}else {
				
				//If temp has leaves as children, then we gradually move up the tree until we find the next smallest Pel object
				temp = temp.parent();
				while ((temp != null) && (temp.getData().getLocus().compareTo(key) == -1)){
					temp = temp.parent();
				}
				if (temp == null) {
					return null;
				} else {
					return temp.getData();
				}
			}
		}
		return null;
	}
	
	//Predecessor method - Finds the largest Pel smaller than the given key
	public Pel predecessor(BNode r, Location key) {
		
		//If r is a leaf then it means there is no predecessor for Pel, and so null is returned
		if (r.isLeaf()) {
			return null;
		} else {
			
			//Makes a node Temp that represents the node in which key is stored
			BNode temp = getNode(r, key);
			
			//Checks if temp is not null, and that temp has children that aren't leaves
			if ((temp != null) && ((temp.leftChild().getData() != null) || (temp.rightChild().getData() != null))) {
				try {
					//Returns the largest Pel from temp's left subtree
					return largest(temp.leftChild());
				} catch (EmptyTreeException e) {
				}
				try {
					//Returns the smallest Pel from the left substree
					return smallest(temp.rightChild());
				}catch (EmptyTreeException e) {
					
				}
			} else {
				
				//If temp's children are leaves, we reassign temp to be temp's own parent until we find the a Pel that is smaller than temp
				temp = temp.parent();
				while ((temp != null) && (temp.getData().getLocus().compareTo(key) == 1)){
					temp = temp.parent();
				}
				if (temp == null) {
					return null;
				} else {
					return temp.getData();
				}
			}
		}
		return null;
	}
	
	//Smallest method - returns the smallest Pel in the tree
	public Pel smallest(BNode r) throws EmptyTreeException{
		//If r is leaf, then the tree is empty since r should be the root of the tree
		if (r.isLeaf()) {
			throw new EmptyTreeException("Error: Empty tree");
		} else {
			
			//We set temp = r, and we continue going to temp's left child until temp equals a leaf
			//Upon which we return the Pel stored in temp's parent, since that will be the tree's left most node, and thus the smallest Pel object
			BNode temp = r;
			while (temp.isLeaf() == false) {
				temp = temp.leftChild();
			}
			return temp.parent().getData();
		}
	}
	
	//Largest Method - returns the Largest Pel object in the tree
	public Pel largest(BNode r) throws EmptyTreeException{
		//Checks if r is a leaf, if it is then the tree is empty since r should be the root of the tree
		if (r.isLeaf()) {
			throw new EmptyTreeException("Error: Empty tree");
		} else {
			
			//Sets temp = r, and we continue going to temps right child until temp equals a leaf
			//When this happens, we return the Pel stored in temp's parent, since that will be the tree's right most node, and thus the greatest Pel object
			BNode temp = r;
			while(temp.isLeaf() == false) {
				temp = temp.rightChild();
			}
			return temp.parent().getData();
		}
	}
	
	//Get method that returns the root of the binary search tree
	public BNode getRoot() {
		return this.root;
	}
	
	
	//Private method getNode - returns the node that contains the specified key
	private BNode getNode(BNode r, Location key) {
		
		//If r is a leaf, then it means that there is no node storing the specified key, so we return null
		if (r.isLeaf()) {
			return null;
		} else {
			
			//We navigate the tree until we find a node storing a Pel object containing key, upon which we return the whole node
			if ((r.getData().getLocus().compareTo(key)) == 0) {
				return r;
			} else {
				if ((r.getData().getLocus().compareTo(key)) == -1) { // Might be 1 instead of -1
					return getNode(r.leftChild(), key);
				} else {
					return getNode(r.rightChild(), key);
				}
			}
		}
	}
	
	
	////Private method getPlacement - returns the correct leaf node that we would be inserting the given key
	private BNode getPlacement(BNode r, Location key) {
		
		//If r is a leaf, then we return it, since that is where key should be placed in the tree
		if (r.isLeaf()) {
			return r;
		} else {
			
			//We navigate the tree until we get to the right leaf node
			if ((r.getData().getLocus().compareTo(key)) == -1) { // Might be 1 instead of -1
				return getPlacement(r.leftChild(), key);
			} else {
				return getPlacement(r.rightChild(), key);
			}
		}
	}

	
}
