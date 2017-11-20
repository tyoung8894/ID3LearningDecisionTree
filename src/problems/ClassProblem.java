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
		private String[] weather = {"Nice","Rain","Cold"};
		private String[] sick = {"very","a little", "no"};
		
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
			attributes.add(new Attribute("Tired?",yesNo));
			attributes.add(new Attribute("Weather?",weather));
			attributes.add(new Attribute("Sick?", sick));
		}
		
		/*
		 * This initializes the Examples.  It depends on the 
		 * attributes being initialized already, so call that first.
		 */
		public void makeExamples(){
			// Shortcut for me to enter the examples...  
			// Each index tells the value of that attribute
			int[][] noExamples = {
					{0,1,0,1,0},  //1
					{1,1,0,1,0},  //2
					{1,1,1,0,0},  //3
					{0,0,0,0,1},  //4  
					{1,0,1,1,0},  //5
					{0,0,1,0,1},  //6
					{0,1,0,2,1},  //7
					{0,1,1,1,1},  //8
					{1,1,0,2,1},  //9
			};
			
			int[][] yesExamples = {
					{1,1,0,1,2},  //10 
					{1,1,1,1,2},  //11
					{0,0,1,1,2},  //12
					{0,1,0,0,1},  //13
					{0,1,0,0,1},  //14
					{0,1,0,0,1},  //15
					{0,1,0,2,1},  //16
					{0,1,0,1,1},  //17
					{0,1,0,2,1},  //18
			};
			



			// This will make my life easier later.  I'm making a little hashmap of all  
			// attributes to their list of possible answers. 
			HashMap<Attribute, String[]> attTypes = new HashMap<Attribute, String[]>();
			attTypes.put(attributes.get(0), yesNo);
			attTypes.put(attributes.get(1), yesNo);
			attTypes.put(attributes.get(2), yesNo);
			attTypes.put(attributes.get(3), weather);
			attTypes.put(attributes.get(4), sick);

			
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