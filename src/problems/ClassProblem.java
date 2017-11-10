package problems;
//*Author Tyler Young


import java.util.*;

import id3.Example;
import tree.Attribute;
import tree.Decision;


public class ClassProblem {
	// The attributes for the problem - will be initialized later
		private ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		// The training examples- will be initialized later
		private ArrayList<Example> examples = new ArrayList<Example>();
		
		// The decisions for this problem
		private Decision attend = new Decision("Attend");
		private Decision skip = new Decision("Skip");
		
		// These are some String choice sets for various questions.
		private String[] yesNo = {"No", "Yes"};
		private String[] tired = {"Extremely", "Tired", "Awake"};
		private String[] weather = {"Nice","Rain","Cold"};
	
		
		public ClassProblem(){
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
			return attend;
		}
		
		/*
		 * Initialize the attributes for this problem.
		 */
		public void makeAttributes(){
			// Here are the attributes for the restaurant decision
			// Each attribute needs a String description and an array of answer 
			// possibilities.
			attributes.add(new Attribute("Friday?", yesNo));
			attributes.add(new Attribute("Test Day?", yesNo));
			attributes.add(new Attribute("Tired?",tired));
			attributes.add(new Attribute("Weather?",weather));
			attributes.add(new Attribute("Sick?", yesNo));
		}
		
		/*
		 * This initializes the Examples.  It depends on the 
		 * attributes being initialized already, so call that first.
		 */
		public void makeExamples(){
			// Shortcut for me to enter the examples...  
			// Each index tells the value of that attribute
			int[][] noExamples = {
					{1,0,0,0,1},  //x3
					{1,0,0,2,1},  //x5
					{1,0,1,0,1},  //x6
					{1,0,1,2,0},  //x7
					{0,1,0,1,1},  //x10
					{1,0,2,0,0}   //x12
			};
			
			int[][] yesExamples = {
					{0,1,0,0,0},  //x1 
					{1,0,1,1,0},  //x2
					{1,1,2,2,0},  //x4
					{1,1,2,0,1},  //x8
					{0,1,1,1,0},  //x9
					{0,0,1,0,0}   //x11
			};
			

			// This will make my life easier later.  I'm making a little hashmap of all  
			// attributes to their list of possible answers. 
			HashMap<Attribute, String[]> attTypes = new HashMap<Attribute, String[]>();
			attTypes.put(attributes.get(0), yesNo);
			attTypes.put(attributes.get(1), yesNo);
			attTypes.put(attributes.get(2), tired);
			attTypes.put(attributes.get(3), weather);
			attTypes.put(attributes.get(4), yesNo);

			
			// Now actually populate the examples
			for(int i = 0; i<noExamples.length; i++){
				HashMap<Attribute, String> attValue = new HashMap<Attribute, String>();
				for(int j = 0; j< noExamples[i].length; j++){
					
					attValue.put(attributes.get(j),attTypes.get(attributes.get(j))[noExamples[i][j]]);

				}
				//count this
				examples.add(new Example(attValue, skip));
			}

			for(int i = 0; i<yesExamples.length; i++){
				HashMap<Attribute, String> attValue = new HashMap<Attribute, String>();
				for(int j = 0; j< yesExamples[i].length; j++){
					attValue.put(attributes.get(j),attTypes.get(attributes.get(j))[yesExamples[i][j]]);
				}
				//count this
				examples.add(new Example(attValue, attend));
			}
			// print examples to see if they look right to start with.
//			for(Example e: examples){
//				e.printMe();
//			}
		}
}
