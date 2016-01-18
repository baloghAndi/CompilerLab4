package grammar;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import parser.Config;
import parser.Parse;
import scanner.Scanner;



public class Controller {
	
	private ContextFreeGrammar cfg;
	private Scanner scanner;
	private List<Map.Entry<Integer, Integer>> pif;
	private Parse parser;
	
	public Controller() {
		//cfg = new ContextFreeGrammar();
		try {
			scanner = new Scanner();
			scanner.scan();
			pif = scanner.getPif();
			scanner.printTables();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	/*private List<String> getSequenceFromPIF() {
		List<String> seq = new ArrayList<String>();
		for (Map.Entry<Integer, Integer> entry : pif){
			seq.add(scanner.getWord(entry.getKey()));
		}
		return seq;
	}*/

	/**
	 * @return the cfg
	 */
	public ContextFreeGrammar getCfg() {
		return cfg;
	}


	public void setContextFreeGrammar(ContextFreeGrammar gr2) {
		cfg = gr2;
		
	} 

	public Config parse(){
	//	List<String> sequence = getSequenceFromPIF(); 
		parser = new Parse(cfg, pif);
		return parser.descendingRecursiveParse();
	}
	
	public String printParseTree(){
		parser = new Parse(cfg, pif);
		return parser.printParseTree();
	}
}
