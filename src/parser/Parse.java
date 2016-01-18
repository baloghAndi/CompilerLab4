package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import grammar.ContextFreeGrammar;

public class Parse {

	private ContextFreeGrammar gr;
	private Config config;
	//private List<String> sequence;
	private Map<String,Integer> productionIndex; //currentNrOfProdOfNonTerminal;
	private Config finalConfig;
	private List<Entry<Integer, Integer>> pif;
	
	public Parse(ContextFreeGrammar grammar, List<Entry<Integer, Integer>> pif){
		this.gr = grammar;
	//	this.sequence = sequence2;
		this.config = new Config();
		this.productionIndex = new HashMap<String, Integer>();
		config.initConfig(gr.getStartingSymbol());
		for (String nonTerm : gr.getNonTerm()){
			productionIndex.put(nonTerm, 0);
		}
		finalConfig = new Config();
		finalConfig.setState("t");
		finalConfig.setIndex(pif.size()+1);
		finalConfig.getBeta().push("epsilon");
		
		this.pif = pif;
	}
	
	public Config expand(Config config){
        config.setState("q");
        config.setIndex(config.getIndex());
        String headOfBeta = config.getBeta().pop();
        config.getAlpha().push(headOfBeta+"0");
        int length = gr.getProductions().getRightHandSideOfProduction(headOfBeta).get(0).size();
        for (int i = length-1; i>=0; i--){
            config.getBeta().push(gr.getProductions().getRightHandSideOfProduction(headOfBeta).get(0).get(i));
        }
        return config;
    }

    public Config advance(Config config) {
        config.setIndex(config.getIndex()+1);
        config.getAlpha().push(config.getBeta().pop());
        return config;
    }
	
	public Config back(Config config){
		config.setIndex(config.getIndex()-1);
		String a = config.getAlpha().pop();
		config.getBeta().push(a);
		return config;
	}
	
	public Config success(Config config){
		config.setState("t");
		return config;
	}
	
	/*private  Integer getPIFValue(String pifcode ){
		for (Entry<Integer, Integer> temp : pif){
			if (temp.getKey()==Integer.parseInt(pifcode)){
				return temp.getValue();
			}
		}
		return -2;
	}*/
	
	
	  public Config anotherTry(Config config){ //each time we have to pop from beta the expandation of A
	        String headAlpha = config.getAlpha().pop();
	        int nrOfProduction = Character.getNumericValue(headAlpha.charAt(headAlpha.length()-1));
	        String A = headAlpha.substring(0, headAlpha.length()-1);
	        String beta = config.getBeta().peek(); //original pop
	        if (nrOfProduction < gr.getProductions().getRightHandSideOfProduction(A).size()-1) { //maybe not ok cuz first is a char second is a number
	            //first
	        	int nr = gr.getProductions().getRightHandSideOfProduction(A).get(nrOfProduction).size();
	    		for (int i=0;i<nr;i++){
	    			config.getBeta().pop();
	    		}
	            config.setState("q");
	            int j = nrOfProduction+1;
	            config.getAlpha().push(A+j);
	            List<String> temp = gr.getProductions().getRightHandSideOfProduction(A).get(j);
	            int length = temp.size();
	            for (int i = length-1; i>=0; i--){
	                config.getBeta().push(temp.get(i));
	            }
	        } else if ((!gr.getStartingSymbol().equals(A)) && config.getIndex()!=0){
	        	config.getAlpha().pop();
	            config.getAlpha().push(beta); //beta
	            
	        } else if(gr.getStartingSymbol().equals(A) && config.getIndex()==0){
	        	System.out.println("last");
	            config.setState("e");
	            config.getAlpha().push(A);
	        }
	        return config;

	    }

	    public Config momentaryInsuccess(Config config){
	        config.setState("b");
	        return config;
	    }

	    public Config descendingRecursiveParse() {
	        Config config = new Config();
	        config.initConfig(gr.getStartingSymbol()); //initialize configuration
	        config.getAlpha().pop();
	        expand(config);
	        while (  ! (config.getState().equals("t") || config.getState().equals("e") )) {
	        	if (config.getBeta().size()==0){
	            	success(config);
	            	break;
	            }
	        	if (config.getBeta().peek().equals("epsilon")){
	        		config.getBeta().pop();
	        	}
	        	config.getIndex();
	        	System.out.println(config.toString());
	            if(config.getState().equals("b")){
	            	String headAlpha = config.getAlpha().peek();
	            	//headAlpha = headAlpha.substring(0, headAlpha.length()-1);
	            	if (gr.getTerminals().contains(headAlpha)){      //if state is b and head of alpha is terminal
	            		System.out.println("back");
	            		back(config);
	            	} else if (gr.getNonTerm().contains(headAlpha.substring(0, headAlpha.length()-1))) { // if state is still b and head of alpha is nonterminal
	            		System.out.println("another try");
	            		anotherTry(config);
	            	}
	            } else if (config.getState().equals("q")){
	            	String headOfBeta = config.getBeta().peek();
	            	if (gr.getTerminals().contains(config.getBeta().peek()) ) { 
	            		System.out.println("pif size"+pif.size());
	            		if (config.getIndex()>=pif.size()){
	            			momentaryInsuccess(config);
	            			continue;
	            		}//if state is  q and if head of beta is terminal --if  && ( getPIFValue(headOfBeta)  == -1 ) -1 -> not const or id
	            		if (Integer.parseInt(headOfBeta)==(pif.get(config.getIndex()).getKey())){
	            			System.out.println("advance");
	            			advance(config);
	            		} else if (Integer.parseInt(headOfBeta)!=(pif.get(config.getIndex()).getKey())) {
	            			System.out.println("momentary insuccess");
	            			momentaryInsuccess(config);
	            		}
	            	} else if (gr.getNonTerm().contains(config.getBeta().peek())){ //if state is q head of beta nonTerm
	                	System.out.println("expand");
	            		expand(config);
	            	}
	            }
	            
	        }
	        return config;
	    }
	    
	    public List<ParseTreeRow> buildParseTree(){
	    	List<ParseTreeRow> parseTree = new ArrayList<ParseTreeRow>();
	    	Stack<String> input = descendingRecursiveParse().getAlpha();
	    	List<String> list = new ArrayList<String>();
	    	while (input.size()!=0){
	    		list.add(input.pop());
	    	}
	    	for(int i=list.size()-1;i>=0;i--){
	    		String temp = list.get(i);
	    		if (gr.getTerminals().contains(temp)){
	    			continue;
	    		}
	    		int prodNr = Character.getNumericValue(temp.charAt(temp.length()-1));
	    		String A = temp.substring(0, temp.length()-1);
	    		List<String> prods = gr.getProductions().getRightHandSideOfProduction(A).get(prodNr);
	    		parseTree.add( new ParseTreeRow(prodNr,A,prods));	
	    	}
	    	return parseTree;
	    }

	    public String printParseTree(){
	    	String res = "";
	    	List<ParseTreeRow> tree = buildParseTree();
	    	for (ParseTreeRow temp : tree){
	    		res+= temp.toString()+"\n";
	    	}
	    	return res;
	    }
	    
	    public  boolean isInteger(String s) {
	        try { 
	            Integer.parseInt(s); 
	        } catch(NumberFormatException e) { 
	            return false; 
	        }
	        // only got here if we didn't return false
	        return true;
	    }
}
