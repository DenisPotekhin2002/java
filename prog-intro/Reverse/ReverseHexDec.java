package Reverse;

import java.io.*;
import java.util.*;

public class ReverseHexDec {
	public static boolean check(char c) {		//checks if symbol is a digit
		return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
	}
		public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in, ReverseHexDec::check);
		int len = 10;
		String[] array = new String[len];
		int i = 0;
		while (sc.hasNext()){
			while (sc.hasNextInLine()){
				//System.err.println(i);
				if (i < array.length){
					array[i] = sc.nextElement();
					i++;
					//System.err.println(array[i-1]+" ar " + (i - 1));
				} else {
					len *= 2;
					array = Arrays.copyOf(array, len);
					array[i] = sc.nextElement();
					//System.err.println(array[i]+" arr2 " + i);
					i++;
				}	
			}
			sc.nextLine();
			//System.err.println(sc.hasNextInLine() + "1");
			if (i < array.length){
				array[i] = System.lineSeparator();
				i++;
				//System.err.println(array[i-1]+"ar");
			} else {
				len *= 2;
				array = Arrays.copyOf(array, len);
				array[i] = System.lineSeparator();
				//System.err.println(array[i]+"arr2");
				i++;
			}
			//System.err.println(sc.hasNextInLine() + "2");
		}
		for (int j = i - 2; j >= 0; j--){
			//System.err.print(array[j] + " ");
			System.out.print(array[j] + " ");
		}
	}
}
