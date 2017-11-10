package tree;
/*
 * Shannon Duvall
 * A Tree node will either be an Attribute or a Decision.  
 * The root Tree Node represents an entire tree. 
 */
public abstract class TreeNode {
	public String myName;
	
	public TreeNode(String name){
		myName = name;
	}
	
	public String getName(){
		return myName;
	}
	
	public void printMe(){
		
	}
}
