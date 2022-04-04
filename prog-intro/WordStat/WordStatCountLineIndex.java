package WordStat;

import java.io.*;
import java.util.*;

public class WordStatCountLineIndex {

	private static class WordList extends IntList {

		private final String word;

		public WordList(String word) {
			super();
			this.word = word;
		}

		public String getWord() {
			return word;
		}
	}

	public static boolean check(char c) {	
		return Character.getType(c) == Character.DASH_PUNCTUATION || Character.isLetter(c) || (c == '\'');
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		try {	
			File input = new File(args[0]);
			File output = new File(args[1]);
			Scanner sc = new Scanner(input, WordStatCountLineIndex::check);
			int column = 0;
			int row = 1;

			Map<String, WordList> map = new HashMap<>();

			int w = 0; 													// w - позиция последнего пробела (от whitespace)
			while(sc.hasNext()) {
				if (!sc.hasNextInLine()) {
					column = 0;
					row++;
					sc.nextLine();
				} else {
					String substring = sc.nextElement().toLowerCase();
					column++;
					if (map.containsKey(substring)) {
						WordList list = map.get(substring);
						list.add(row);
						list.add(column);
					} else {
						WordList il = new WordList(substring);
						il.add(row);
						il.add(column);
						map.put(substring,il);
					}
				}
			}

			List<WordList> result = new ArrayList<>();
			result.addAll(map.values());

			result.sort((first, second) -> {
				if (first.size() != second.size()) {
					return Integer.compare(first.size(), second.size());
				}
				if (first.get(0) != second.get(0)) {
					return Integer.compare(first.get(0), second.get(0));
				}
				return Integer.compare(first.get(1), second.get(1));
			});

			try {
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(output), "utf8"));
				try {
                    for (WordList value : result) {
                        writer.print(String.format("%s %d ", value.getWord(), value.size() / 2));
                        for (int j = 0; j < value.size(); j++) {
                            if (j % 2 == 0) {
                                writer.print(String.format("%d:", value.get(j)));
                            }    else {
                                writer.print(String.valueOf(value.get(j)));
                                if (j < value.size() - 1) {
                                    writer.print(" ");
                                }
                            }
                        }
                        writer.println();
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
