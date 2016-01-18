package grammar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammar {
	private List<String> nonTerm;
	private List<String> terminal;
	private CFGProduction productions;
	private String startingSymbol;

	public ContextFreeGrammar() {
		nonTerm = new ArrayList<String>();
		terminal = new ArrayList<String>();
	}
	
	public ContextFreeGrammar(List<String> nonTerm, List<String> symbols, CFGProduction productions, String startingSymbol) {
		this.nonTerm = nonTerm;
		this.terminal = symbols;
		this.productions = productions;
		this.startingSymbol = startingSymbol;
	}
	

	public void addNonTerm(String nonTerm) {
		this.nonTerm.add(nonTerm);
	}
	
	public void addTerminals(String symbols) {
		this.terminal.add(symbols);
	}
	
	/**
	 * @return the nonTerm
	 */
	public List<String> getNonTerm() {
		return nonTerm;
	}
	/**
	 * @param nonTerm the nonTerm to set
	 */
	public void setNonTerm(List<String> nonTerm) {
		this.nonTerm = nonTerm;
	}
	
	/**
	 * @return the productions
	 */
	public CFGProduction getProductions() {
		return productions;
	}
	/**
	 * @param productions the productions to set
	 */
	public void setProductions(CFGProduction productions) {
		this.productions = productions;
	}
	/**
	 * @param alphabet the alphabet to set
	 */
	public void setTerminals(List<String> alphabet) {
		this.terminal = alphabet;
	}
	/**
	 * @param startingSymbol the startingSymbol to set
	 */
	public void setStartingSymbol(String startingSymbol) {
		this.startingSymbol = startingSymbol;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Grammar : \n nonTerm=" + nonTerm + " \n alphabet=" + terminal
				+ "\n productions=" + productions.toString() + "\n startingSymbol="
				+ startingSymbol + "\n";
	}

	/*public List<CFGProduction> getProductionOf(String symbol) {
		return productions.getProductionsOf(symbol);
	}*/

	public List<String> getTerminals() {
		return terminal;
	}

	/*public boolean checkProductions() {
		//	Producţiile unei gramatici regulare G = (N, Σ, P, S) diferite de   sunt numai de forma A->aB,  A->b.
		for (String key : productions.getKeys()){
			if (!nonTerm.contains(key)){
				return false;
			}
			for (Map.Entry<String,String> rightSide : productions.getValues(key)){
				if (rightSide.getKey().equals("E") && productions.rightSideContains(key)){
					return false;
				}
				if (!symbols.contains(rightSide.getKey()) && !rightSide.getKey().equals("E")){ //right hand side is symbol or epsilon?
					return false;
				}
				if (!nonTerm.contains(rightSide.getValue()) && !rightSide.getValue().equals("")){ // righthand side second term is nonterm or nothing?
					return false;
				}
			}
		}
		return true;
	}
*/
	public String getStartingSymbol() {
		return this.startingSymbol;
	}
	
	public static ContextFreeGrammar readFromFileGr(String file) throws IOException{
		ContextFreeGrammar gr = new ContextFreeGrammar();
		CFGProduction prod = new CFGProduction();
		BufferedReader br = new BufferedReader(new FileReader("src\\"+file+".txt"));
		String[] tokens;
		try {
			String line = br.readLine();
			tokens = line.split(","); //fisrt row  = non terminals
			for (String temp : tokens ){
				temp.trim();
				gr.addNonTerm(temp);
				prod.addNonTerm(temp);
			}
			line = br.readLine();
			tokens = line.split(","); //second row  = terminals
			for (String temp : tokens ){
				temp.trim();
				gr.addTerminals(temp);
			}
			line = br.readLine();
			tokens = line.split(" ");
			gr.setStartingSymbol(tokens[0]);
			line = br.readLine();
			while (line != null){
				tokens = line.split("-",2);
				prod.setProductions(tokens[0], tokens[1]);
				gr.setProductions(prod);
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		return gr;
	}

	/*public boolean isStartingSymbolFinal() {
		return productions.isFinal(startingSymbol);
	}*/



}
