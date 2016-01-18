package grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CFGProduction {
	
	private Map<String,List<List<String>>> production;

	public CFGProduction() {
		production = new HashMap<String,List<List<String>>>();
	}
	
	public void addNonTerm(String temp) {
		production.put(temp, new ArrayList<List<String>>());
	}
	
	public Set<String> getKeys() {
		return production.keySet();
	}
	
	public List<List<String>> getValues(String key) {
		return production.get(key);
	}

	public void setProductions(String leftHandSide, String rightHandSide) {
		String[] rhside = rightHandSide.split("\\|");
		for (String prod : rhside){ //prod - one production with several terminal/nonTerminal
			String[] elements = prod.split(" ");
			List<String> temp = new ArrayList<String>();
			for (String elem : elements){
				temp.add(elem);
			}
			production.get(leftHandSide).add(temp);
		}
	}
	
    public List<List<String>> getRightHandSideOfProduction(String nonTerm) { //get all the right had sides of the production
        //Productions prod = new Productions();
        List<List<String>> temp = new ArrayList<List<String>>();
         if (production.containsKey(nonTerm)){
            temp = production.get(nonTerm);
            return temp;
        }
        return temp;
    }
	
    
	@Override
	public String toString() {
		String result = "";
		for (String key : production.keySet()){
			result +="\n" + key + "->" + production.get(key);
		}
		return result;
	}
}

	
