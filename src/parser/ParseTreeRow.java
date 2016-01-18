package parser;

import java.util.List;

public class ParseTreeRow {
	
	private int prodNr;
	private String parent;
	private List<String> children;
	
	public ParseTreeRow(int nr , String p, List<String> ch){
		prodNr = nr;
		parent = p;
		children = ch;
	}

	/**
	 * @return the prodNr
	 */
	public int getProdNr() {
		return prodNr;
	}

	/**
	 * @param prodNr the prodNr to set
	 */
	public void setProdNr(int prodNr) {
		this.prodNr = prodNr;
	}

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public List<String> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<String> children) {
		this.children = children;
	}


	@Override
	public String toString() {
		return " [prodNr=" + prodNr + ", parent=" + parent
				+ ", children=" + children.toString() + "]";
	}
	
	

}
