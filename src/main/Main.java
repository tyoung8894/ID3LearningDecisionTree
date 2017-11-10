package main;

import java.util.HashMap;
import java.util.List;

import id3.*;
import problems.ClassProblem;
import problems.RestaurantProblem;
import tree.*;

/*
 * Shannon Duvall
 * Main.java
 */
public class Main {
	public static void main(String[] args) {
		//RestaurantProblem rest = new RestaurantProblem();
		ClassProblem rest = new ClassProblem();
		rest.makeExamples();
		//List<Example> examples = rest.getExamples();
		//for(Example e: examples){
			//System.out.println(e.toString());
		//}
		ID3 id3 = new ID3();
		StringWriter.turnOff();
		TreeNode root = id3.id3(rest.getExamples(), rest.getAttributes(), rest.getDefaultDecision());
		System.out.println("Print Tree: ");
		StringWriter.turnOn();
		root.printMe();
	}

}
