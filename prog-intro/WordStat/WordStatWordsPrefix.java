package WordStat;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class WordStatWordsPrefix {
	
	public static boolean check(char c) {		//checks if symbol is a letter
		return Character.getType(c) == Character.DASH_PUNCTUATION || Character.isLetter(c) || (c == '\'');
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		try {	
			File input = new File(args[0]);
			File output = new File(args[1]);
			Scanner sc = new Scanner(input, WordStatWordsPrefix::check);
			Map<String, Integer> map = new TreeMap<String, Integer>();
			int w = 0; 													// w - позиция последнего пробела (от whitespace)
			while(sc.hasNext()){
				String strtemp = sc.nextElement();
				//System.err.println(strtemp);
				if (strtemp.length() >= 3) {
					String substring = strtemp.substring(0, 3).toLowerCase();
					if (map.containsKey(substring)){
						map.put(substring, map.get(substring) + 1);
					} else {
						map.put(substring, 1);
					}
				}
			}	
			try {
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(
								new FileOutputStream(output), "utf8"));			
				try{
					for (String str : map.keySet()){
						writer.println(String.format("%s %d", str, map.get(str)));
					}	
				} finally {
					writer.close();
				}
			} catch (UnsupportedEncodingException e){
				System.out.println(e.getMessage());
			}
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}		
	}
}
