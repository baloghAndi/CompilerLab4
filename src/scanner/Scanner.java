package scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

	private Map<String,Integer> codificationMap;
	private Map<Integer, String> codificationMapInverse;
	private final String[] reservedWords = {"if","then","else","while","do","int","char","String","read","write","Main"};
	private final List<Map.Entry<Integer, Integer>> pif; 
	private final SymbolTable symbolTable;

	public Scanner() {
		symbolTable = new SymbolTable();
		pif = new ArrayList<>();
		try {
			initCodification();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the pif
	 */
	public List<Map.Entry<Integer, Integer>> getPif() {
		return pif;
	}
	
	public String getWord(Integer code){
		return codificationMapInverse.get(code);
	}



	private void initCodification() throws IOException {
		codificationMap = new HashMap< String, Integer>();
		codificationMapInverse = new HashMap<Integer, String>();
		BufferedReader br = new BufferedReader(new FileReader(
				"src\\codification.txt"));
		try {
			String line = br.readLine();
			while (line != null) {
				String[] tokens = line.split(" ");
				codificationMap.put(tokens[1],Integer.parseInt(tokens[0]));
				codificationMapInverse.put(Integer.parseInt(tokens[0]),tokens[1]);
				line = br.readLine();
			}
		} finally {
			br.close();
		}

	}
	

	public void scan() throws IOException {
		/*the source code; detect lexical errors, construct pif and symbol table*/
		BufferedReader br = new BufferedReader(new FileReader("src\\sourceProgram.txt"));
		int counter = 1;
		try {
			String line = br.readLine();
			while (line != null) {
				ArrayList<String> tokens = detect(line);
				for (String token : tokens) {
//					System.out.println(token);
					if (isReservedWord(token) || isOperator(token) || isSeparator(token)) {
//						System.out.println(token+" - "+getCode(token));
						generateEntryPIF(getCode(token), -1);
					} else if (isIdentifier(token) || isConstant(token)) {
						int index = positionInST(token);
						generateEntryPIF(getCode(token), index);
//						System.out.println(token+" - "+getCode(token));
					} else {
						System.out.println("Lexical error at line "+ counter +" at token: "+ token);
					}
				}
				counter++;
				line = br.readLine();
			}
		} finally {
			br.close();
		}
	}

	private ArrayList<String> detect(String line) {
		/*splits a source code lines into tokens*/
		ArrayList<String> tokens = new ArrayList<String>();
		String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
		String[] temp = line.split("(\\s)+"); // ,|;|\\(|\\)|\\{|\\}|\\[|\\]|\\s
		for (String t : temp) {
			String[] temp2 = t.split(String.format(WITH_DELIMITER,"\\(|\\)|\\{|\\}|\\[|\\]|<=|>=|==|\\+|-|/|\\*|;|,"));
			for (String a : temp2) {
				if (a.equals("")) {
					continue;
				} 
				if (a.equals("<=") || a.equals(">=") || a.equals("==")){
					tokens.add(a);					
				} else if (a.matches(".*<.*|.*>.*|.*=.*")) {
					String[] aToken = a.split(String.format(WITH_DELIMITER,"<|>|="));
					for (String s: aToken){
						if (!s.equals("")) {
							tokens.add(s);
						} 
					}
				} else {
				tokens.add(a);
				}
			}
		}
		return tokens;
	}

	private boolean isConstant(String token) {
		if (token.matches("-*[0-9]+|\".+\"")){
			return true;
		}
		return false;
	}

	private int positionInST(String token) {
		symbolTable.add(token);
		int pos = symbolTable.getElementPosition(token);
		return pos;
	}

	private void generateEntryPIF(int code, int i) {
		pif.add(new AbstractMap.SimpleEntry<Integer, Integer>(code, i));
//		pif.put(code, i);
	}

	private boolean isIdentifier(String token) {
		if (token.matches("[a-z]|[A-z]([a-z]|[A-Z]|[0-9]|_){0,249}")){
			return true;
		}
		return false;
	}

	private int getCode(String token) {
		/*returns the code associated to the token or o for identifiers and 1 for constants*/
		try{
			return codificationMap.get(token);
		} catch (NullPointerException e){
			if (isIdentifier(token)){
				return 0;
			} else if (isConstant(token)){
				return 1;
			} else {
				return -1;
			}
		}
		
	}

	private boolean isOperator(String token) {
		if (token.matches("\\+|-|\\*|/") || token.matches("==") || token.matches("<=") || token.matches(">=")||token.matches("=")){
			return true;
		}	
		return false;
	}

	private boolean isReservedWord(String token) {
		for (String word : reservedWords){
			if (token.equals(word)){
				return true;
			}
		}
		return false;
	}

	private boolean isSeparator(String token) {
		if (token.matches("\\(|\\)|\\{|\\}|\\[|\\]|;|,")) {
			return true;
		}
		return false;
	}
	
	public void printTables(){
		System.out.println(pif);
		symbolTable.printST();
		/*System.out.println("ST: ");
		for (int i=0;i<simpleSymbolTable.size();i++){
			System.out.println("pos: "+ i + simpleSymbolTable.get(i));
		}*/
	}

}
