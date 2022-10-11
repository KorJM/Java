import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Map.Entry;

public class UpgradeSearch {
	
	String str;
	
	public UpgradeSearch(String str) {
		this.str = str;
	}
	
	public void go() {
		Scanner scanner = new Scanner(System.in);
		StringTokenizer token = new StringTokenizer(str , "\n", false);
		HashMap<Integer, HashMap<String, Integer>> hs = new HashMap<Integer, HashMap<String,Integer>>();
		
		int line = 0;
		
		while(token.hasMoreTokens()) {
			String input = token.nextToken();
			StringTokenizer token2 = new StringTokenizer(input, " ", false);
			HashMap<String, Integer> hs2 = new HashMap<>();
			line++;
			
			while (token2.hasMoreTokens()) {
				String st = token2.nextToken();
				hs2.put(st, (hs2.containsKey(st)) ? hs2.get(st) + 1 : 1);
			}
			
			hs.put(line, hs2);
		}
		 System.out.println("찾을실 단어를 입력하세요:");
		 
		 String word = scanner.next();		
		 
		 Iterator<Map.Entry<Integer, HashMap<String, Integer>>> it = hs.entrySet().iterator();
		 
		 int sum = 0, idx = 0;
		 int[] getL = new int[1];
		 
		 while(it.hasNext()) {
			 Entry<Integer, HashMap<String, Integer>> entry = it.next();
			 if (entry.getValue().containsKey(word)) {
				 sum += entry.getValue().get(word);
				 try {
					getL[idx] = entry.getKey();
				} catch (ArrayIndexOutOfBoundsException e) {
					getL = Arrays.copyOf(getL, getL.length + 1);
					getL[idx] = entry.getKey();
				}
				 idx++;
			 }
		 }
		 if(sum == 0) System.out.println("자료의 개수가 없습니다.");
		 else {
			 System.out.print(sum + " - ");	
			 for (int i = 0; i < getL.length; i++) 
				 if (i != getL.length - 1)
				 System.out.print(getL[i] + ", ");
				 else System.out.println(getL[i]);
		 }
		 }
		 
}
	

