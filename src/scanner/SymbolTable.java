package scanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
	private  Map<Integer,Integer> indexList; //key= sorted pos , value = index
	private List<String> sortedList;
	private static int currentIndex = 0;
	
	public SymbolTable(){
		indexList = new HashMap<Integer,Integer>();
		sortedList = new ArrayList<String>();
	}
	
	public void add(String data){
		/*adds a new identifier/constant into the symbol table if it is not already there
		 * after each insertion sortedList is ordered lexicographically */
		int pos = Arrays.binarySearch((Object[])sortedList.toArray(), data);
		if(pos<0){ //need to insert
			pos = Math.abs(pos)-1;
			indexList.put(currentIndex,pos);
			for(int i=0;i<currentIndex;i++){
				if(indexList.get(i)>=pos){
					indexList.put(i, indexList.get(i)+1);
				}
			}
			sortedList.add(pos, data);
			currentIndex++;
		}  // else already elemnet of st
 		
		
	}
	
	public int getElementPosition(String data){
		/*returns the index of the token(index is in order of identifying the elements )*/
		int pos = Arrays.binarySearch((Object[])sortedList.toArray(), data);
		return indexList.get(pos);
	}
	
	public void printST(){
		System.out.println("ST: ");
		for (int i=0;i<sortedList.size();i++){
			System.out.println("pos: "+ i + "--" +sortedList.get(i));
		}
		System.out.println("Unordered list:");
		System.out.println(indexList.toString());
//		for (int i=0;i<indexList.size();i++){
//			System.out.println("pos "+i+" pos in ordered: "+indexList.get(i));
//		}
	}
}
