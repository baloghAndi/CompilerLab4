package parser;

import java.util.Stack;

public class Config {
	
	private String state;
	private int index;
	private Stack<String> alpha;
	private Stack<String> beta;
	
	public Config(){
		alpha = new Stack<String>();
		beta = new Stack<String>();
	}
	
	public void initConfig(String startingSymbol){
		state="q";
		alpha.push("epsilon");
		beta.push(startingSymbol);
	}
	
	public boolean isFinal(Config config, int wordLength){
		if (config.state.equals("t") && (config.index == wordLength+1) && beta.size()==1 && beta.contains("epsilon")){
			return true;
		}
		return false;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the alpha
	 */
	public Stack<String> getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(Stack<String> alpha) {
		this.alpha = alpha;
	}

	/**
	 * @return the beta
	 */
	public Stack<String> getBeta() {
		return beta;
	}

	/**
	 * @param beta the beta to set
	 */
	public void setBeta(Stack<String> beta) {
		this.beta = beta;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Config [state=" + state + ", index=" + index + ", alpha="
				+ alpha + ", beta=" + beta + "]";
	}
	
	
	
}
