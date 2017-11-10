package id3;

/*
 * Shannon Duvall
 * An Example is one set of values and a decision that will be 
 * used for training.
 */
import java.util.*;

import main.StringWriter;
import tree.Attribute;
import tree.Decision;

public class Example {
	// The parts of the example are the decision and the attribute/value pairs.
	private Decision myDecision;
	private HashMap<Attribute, String> myAttValues;
	
	public Example(HashMap<Attribute, String> pairs, Decision d){
		myDecision =d;
		myAttValues = pairs;
	}
	
	public String getDecisionName(){
		return myDecision.getName();
	}
	
	public Decision getDecision(){
		return myDecision;
	}
	
	// For this example, find the value of a certain attribute based on the attribute's name.
	public String getValue(Attribute attName){
		return myAttValues.get(attName);
	}
	
	public String toString() {
		String answer = "Example: \n";
		for(Attribute a: myAttValues.keySet()){
			answer += (a.getName()+ "="+myAttValues.get(a)+"\t");
		}
		answer += myDecision.getName()+"\n";
		return answer;
	}
	
	// Print the example
	public void printMe(){
		StringWriter.println(this.toString());
	}
}
