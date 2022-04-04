package Reverse;

import java.io.*;

public class ReverseAvg {
	public static boolean check(char c){		//checks if symbol is a digit
		return (Character.isLetter(c) ||
			Character.isDigit(c) || c == '-' || c == '+');
	}
	
	public static int num(String str) throws IOException{					// number of numbers in string
		Scanner scanTemp = new Scanner(str, ReverseAvg::check);
		int n = 0;
		String temp = "";
		//System.out.println(n);
		while (scanTemp.hasNext()){
			n++;
			temp = scanTemp.nextElement();
		}
		//System.out.println(n);
		return n;
	}
	
	public static long avg(int firstInd, int secondInd, int[][] arr, long[] horSum, int[] horNum){		//average of numbers in line and column
		int hor = 0;
		int vert = 0;
		long sum = 0;
		sum += horSum[firstInd];
		hor += horNum[firstInd];
		//System.err.println(hor+" "+sum);
		for (int ind = 0; ind < arr.length; ind++){
			if (arr[ind].length > secondInd){
				sum += arr[ind][secondInd];
				vert++;
				//System.out.println(s);
			}
		}
		sum -= arr[firstInd][secondInd];
		return sum / (hor + vert - 1);	
	}
	
	public static void main(String[] args) throws IOException{
		Scanner sc = new Scanner(System.in, ReverseAvg::check);
		int len = 10;
		String[] array = new String[len];
		int i = 0;
		while (sc.hasNext()){
			//System.err.println(i);
			String sT = sc.readNextLine();
			if (!check(sT.charAt(0))){
				sT = "";
			}
			if (i < array.length){
				array[i] = sT;
				i++;
				//System.err.println(array[i-1]+"ar");
			} else {
				String[] arrayTemp = new String[len];
				System.arraycopy(array, 0, arrayTemp, 0, len);
				//System.out.println(ArrayTemp[i-1]+"arr1");
				len *= 2;
				array = new String[len];
				System.arraycopy(arrayTemp, 0, array, 0, len/2);
				array[i] = sT;
				//System.err.print(array[i] + "arr2");
				i++;
			}	
		}
		int[][] arr = new int[i][];
		long[] horSum = new long[i];
		int[] horNum = new int[i];
		for (int ind1 = 0; ind1 < i; ind1++){
			Scanner scan = new Scanner(array[ind1], ReverseAvg::check);
			//System.out.println(num(Array[j]));
			arr[ind1] = new int[num(array[ind1])];
			int ind2 = 0;
			while (scan.hasNext()){
				String strT = scan.nextElement();
				if (check(strT.charAt(0))){
					//System.err.println(ind1 + " " + ind2 + " '" + strT + "'");
					arr[ind1][ind2] = Integer.parseInt(strT);
					//System.err.print(arr[ind1][ind2] + " ");
					horSum[ind1] += arr[ind1][ind2];
					horNum[ind1]++;
					ind2++;
				}
			}
			//System.err.println();
		}
		for (int count = 0; count < i; count++){
			for (int sec = 0; sec < arr[count].length; sec++){
				//System.err.print(avg(count, sec, arr, horSum, horNum) + " ");
				System.out.print(avg(count, sec, arr, horSum, horNum) + " ");
				//System.out.print(Arr[count][sec]+" ");
			}
			System.out.println();
		}
	}
}