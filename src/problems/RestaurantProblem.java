package problems;
/*
 * Shannon Duvall
 * The specific problem for whether or not to wait at a restaurant.
 * Taken from the Russell-Norvig book, page 700.
 */

import java.util.*;

import id3.Example;
import tree.Attribute;
import tree.Decision;


public class RestaurantProblem {
	// The attributes for the problem - will be initialized later
	private ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	// The training examples- will be initialized later
	private ArrayList<Example> examples = new ArrayList<Example>();
	
	// The decisions for this problem
	private Decision wait = new Decision("Wait");
	private Decision dont = new Decision("Don't Wait");
	
	// These are some String choice sets for various questions.
	private String[] yesNo = {"No", "Yes"};
	private String[] patrons = {"None", "Some", "Full"};
	private String[] money = {"$","$$","$$$"};
	private String[] types = {"French", "Italian", "Thai", "Burger"};
	private String[] waits = {"0-10", "10-30", "30-60", ">60"};
	
	public RestaurantProblem(){
		makeAttributes();
		makeExamples();
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public List<Example> getExamples() {
		return examples;
	}
	
	public Decision getDefaultDecision() {
		return wait;
	}
	
	/*
	 * Initialize the attributes for this problem.
	 */
	public void makeAttributes(){
		// Here are the attributes for the restaurant decision
		// Each attribute needs a String description and an array of answer 
		// possibilities.
		attributes.add(new Attribute("Alternate?", yesNo));
		attributes.add(new Attribute("Bar?", yesNo));
		attributes.add(new Attribute("Weekend?",yesNo));
		attributes.add(new Attribute("Hungry?",yesNo));
		attributes.add(new Attribute("Patrons?", patrons));
		attributes.add(new Attribute("Price?", money));
		attributes.add(new Attribute("Raining?",yesNo));
		attributes.add(new Attribute("Reservations?", yesNo));
		attributes.add(new Attribute("Restaurant Type?",types));
		attributes.add(new Attribute("Wait Estimate?",waits));
	}
	
	/*
	 * This initializes the Examples.  It depends on the 
	 * attributes being initialized already, so call that first.
	 */
	public void makeExamples(){
		// Shortcut for me to enter the examples...  
		// Each index tells the value of that attribute
		int[][] noExamples = {
				{1,0,0,1,2,0,0,0,2,2},
				{1,0,1,0,2,2,0,1,0,3},
				{0,1,0,0,0,0,1,0,3,0},
				{0,1,1,0,2,0,1,0,3,3},
				{1,1,1,1,2,2,0,1,1,1},
				{0,0,0,0,0,0,0,0,2,0}
		};
		int[][] yesExamples = {
				{1,0,0,1,1,2,0,1,0,0},
				{0,1,0,0,1,0,0,0,3,0},
				{1,0,1,1,2,0,1,0,2,1},
				{0,1,0,1,1,1,1,1,1,0},
				{0,0,0,1,1,1,1,1,2,0},
				{1,1,1,1,2,0,0,0,3,2}
		};

		// This will make my life easier later.  I'm making a little hashmap of all  
		// attributes to their list of possible answers. 
		HashMap<Attribute, String[]> attTypes = new HashMap<Attribute, String[]>();
		// For alternate, bar, weekend, and hungry
		for(int i = 0; i<4; i++){
			attTypes.put(attributes.get(i),yesNo);
		}
		attTypes.put(attributes.get(4), patrons);
		attTypes.put(attributes.get(5), money);
		attTypes.put(attributes.get(6), yesNo);
		attTypes.put(attributes.get(7), yesNo);
		attTypes.put(attributes.get(8), types);
		attTypes.put(attributes.get(9), waits);
		
		// Now actually populate the examples
		for(int i = 0; i<noExamples.length; i++){
			HashMap<Attribute, String> attValue = new HashMap<Attribute, String>();
			for(int j = 0; j< noExamples[i].length; j++){
				
				attValue.put(attributes.get(j),attTypes.get(attributes.get(j))[noExamples[i][j]]);

			}
			//count this
			examples.add(new Example(attValue, dont));
		}

		for(int i = 0; i<yesExamples.length; i++){
			HashMap<Attribute, String> attValue = new HashMap<Attribute, String>();
			for(int j = 0; j< yesExamples[i].length; j++){
				attValue.put(attributes.get(j),attTypes.get(attributes.get(j))[yesExamples[i][j]]);
			}
			//count this
			examples.add(new Example(attValue, wait));
		}
		// print examples to see if they look right to start with.
//		for(Example e: examples){
//			e.printMe();
//		}
	}
}
