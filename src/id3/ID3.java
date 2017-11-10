package id3;
/*
 * Author: Tyler Young
 * ID3 Algorithm Implemented
 */
import java.util.*;

import tree.Attribute;
import tree.Decision;
import tree.TreeNode;
public class ID3 {

	// The ID3 Algorithm takes a collection of examples, the collection of Attributes used for making 
	// the decision, and a default Decision.  
	// It returns the decision tree.
	public TreeNode id3(List<Example> examples, List<Attribute> attributes, Decision defaultDecision){
		//if examples is empty, return default decision
		if (examples.isEmpty()){
			return defaultDecision;
		}
		//if all examples have same classification, return the classification
		else if(equalList(examples)){
			return examples.get(0).getDecision();
		}
		//if attributes is empty, then return the majority value of the examples
		else if(attributes.isEmpty()){
			return majorityValue(examples);
		}
		//best 
		Attribute best = chooseAttribute(attributes,examples,defaultDecision);
		TreeNode root = best;
		//downcast the treenode root to an attribute so a tree can be made using attribute methods
		Attribute tree = (Attribute) root;
		Set<String> bestAnswers= best.getPossibleAnswers(); //values for best
		Iterator<String> iterator = bestAnswers.iterator();
		//for each value of bestAnswers
		while (iterator.hasNext()){
			String next = iterator.next();
			//examples with value of best
			//System.out.println(iterator.next());
			List<Example> bestExamples = new ArrayList<Example>();
			for (Example e: examples){
				//if the the value for the attribute in the example is equal to the value of the best attribute
				//getValue(patrons) = full
				if(e.getValue(best).equals(next)){
					bestExamples.add(e);
				}
			}
			//remove best attribute from list of attributes
			attributes.remove(best);
			TreeNode subtree = id3(bestExamples,attributes,majorityValue(examples));
			tree.addChild(next, subtree);
		}
		return tree;
	}


	public Attribute chooseAttribute(List<Attribute> attributes, List<Example> examples, Decision defaultDecision){
		double bestOverallAttributeGain =0;
		//set default bestAttribute
		Attribute bestAttribute = attributes.get(0);
		//HashMap<String, Integer> attributeValueMap = new HashMap<String, Integer>(); //create a map of each attribute's values and their count of occurrences
		//find overall gain for each attribute
		for(Attribute A: attributes){
			double attributeGain = getGain(A, examples, defaultDecision);
			if(attributeGain > bestOverallAttributeGain){
				bestOverallAttributeGain = attributeGain;
				bestAttribute = A;
			}
		}
		//System.out.println("BEST ATTRIBUTE ====  " + bestAttribute.getName());
		return bestAttribute;
	}
	
	//find the information gain for an attribute
	public double getGain(Attribute attribute, List<Example> examples, Decision defaultDecision){
		int intNumExamples = examples.size();
		double numExamples = (double) intNumExamples;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		double gain = 0;
		for(Example e: examples){
			//find value of attribute
			String value = e.getValue(attribute);
			//if the value(key) of the attribute is already in the map, add one to its counter(value)
			if(map.containsKey(value)){
				int temp = map.get(value);
				temp++;
				map.put(value, temp);
			}
			//if the value(key) isn't in the map already, add it to the map with a counter of 1
			else{
				map.put(value, 1);
			}
		}
		//loop through each
		for(Map.Entry<String, Integer> entry:map.entrySet()){
			String key = entry.getKey();
			int intValue = entry.getValue(); //valueCounter
			double value = (double) intValue;
			double valueRatio = value/numExamples;
			//System.out.println(attribute.getName() + " " + key + " " + value);
			//get the next amount to add to gain
			double next = getGain(key, attribute, examples, valueRatio, value, defaultDecision);
			gain = gain+next;
		}
		//Entropy-expected info remainder = total gain for attribute
		gain = 1-gain;
		//System.out.println("OVERALL GAIN = " + gain);
		//return overall information gain for the attribute
		return gain;
	}

	public Double getGain(String key, Attribute attribute, List<Example> examples, double ratio, double valueCounter, Decision defaultDecision){
		Decision firstDecision = defaultDecision;//set the default decision(wait)
		double counterFirst = 0; //wait
		double counterSecond = 0; //dont wait
		double firstRatio = 0;
		double secondRatio = 0;
		for (Example e: examples){
			String valueExample = e.getValue(attribute);// KEY(some,none,full)
			//if value of the attribute of the example is the same as the key/input value
			if(valueExample.equals(key)){
				//get the decision
				Decision decision = e.getDecision();
				//if equals "wait" default decision object
				if(decision.equals(firstDecision)){
					counterFirst++;
				}
				//otherwise add to don't wait counter
				else{
					counterSecond++;
				}
			}
		}
		//System.out.println("wait amount : " + counterFirst + " " + "dont wait amount: " + counterSecond + " " + "total amount of value = " + valueCounter);
		//ratios of each decision count to the count of the value in the attribute
		firstRatio = counterFirst/valueCounter; //(2/6)
		secondRatio = counterSecond/valueCounter; //(4/6)
		//fixes infinity and negative infinity problem with math.log of 0
		if(firstRatio == 1.0 || secondRatio == 1.0){
			double answer = 0.0;
			//System.out.println("1 TO 0 ANSWER= " + answer);
			return answer;
		}
		else{
			//logs of each value ratio
			double firstLog = Math.log10(firstRatio) / Math.log10(2.);
			double secondLog = Math.log10(secondRatio) / Math.log10(2.);
			double logs = -((firstRatio*firstLog) + (secondRatio * secondLog));
			double answer = ratio*logs;
			//System.out.println("firstRatio = " + firstRatio + " " + "secondRatio = " + secondRatio + "firstLog = " + firstLog + "secondLog= " + secondLog);
			//System.out.println("Logs = " + logs);
			//System.out.println("ANSWER = " + answer);
			return answer;
		}
	}
	//if all examples have same decision/classification
	public boolean equalList(List<Example> examples){
		Decision first = examples.get(0).getDecision();
		for(int i=1;i<examples.size();i++){
			if(!examples.get(i).getDecision().equals(first)){
				return false;
			}
		}
		return true;
	}

	//find majority value(decision) of examples
	public Decision majorityValue(List<Example> examples){
		int counterFirst = 1;
		int counterSecond = 0;
		Decision first = examples.get(0).getDecision();
		int indexSecond = 0;
		for(int i=1;i<examples.size();i++){
			if(examples.get(i).getDecision().equals(first)){
				counterFirst++;
			}
			else{
				counterSecond++;
				indexSecond = i;
			}
		}
		if(counterFirst>counterSecond){
			return first;
		}
		else{
			return examples.get(indexSecond).getDecision();
		}
	}
	

}
