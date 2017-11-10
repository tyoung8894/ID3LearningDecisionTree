package tree;
/*
 * Shannon Duvall
 * 
 *  An Attribute models one "question" - like "Is there a wait time?"
 *  It has an array of possible answers, and a corresponding
 *  array of children that represent the next attribute.  For example,
 *  "Yes" will be mapped to the next question
 *  to ask if the answer is yes.  
 */
 
import java.util.*;
import main.*;

public class Attribute extends TreeNode{
	
	// Keeps  the mapping from a String answer to what TreeNode to go to next.
	private HashMap<String, TreeNode> myChildren;
	
	// This constructor is for when I don't know the possible answers yet.
	public Attribute(String name){
		// The name of the question is given as the name of the tree node
		super(name);
		myChildren = new HashMap<String,TreeNode>();
	}
	
	// This constructor is used when the answers are known
	public Attribute(String name, String[] answers){
		super(name);		
		myChildren = new HashMap<String,TreeNode>();
		for(String answer: answers){
			myChildren.put(answer, null);
		}
	}
	
	// Print the node, recursively printing the subtrees of the node.
	public void printMe(){
		StringWriter.println("Question: "+myName);
		for(String answer: myChildren.keySet()){
			StringWriter.println("\t if "+answer+" goto "+myChildren.get(answer).getName());
		}
		for(TreeNode node: myChildren.values()){
			node.printMe();
		}
	}
	
	// Add a child node at the specific index of the answer it corresponds to
	public void addChild(String value, TreeNode tree){
		myChildren.put(value, tree);
	}
	
	public int getNumValues(){
		return myChildren.keySet().size();
	}
	
	
	public Set<String> getPossibleAnswers(){
		return myChildren.keySet();
	}
	
	public boolean equals(Attribute another){
		return this.getName().equals(another.getName());
	}
}
