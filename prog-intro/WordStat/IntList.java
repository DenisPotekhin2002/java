package WordStat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

class IntList {
	private int[] array;
	private int len = 100;
	private int count = 0;

	public IntList(){
		array = new int[len];
	}
	
	public void add(int i){
		if (count >= len) {
			len *= 2;
			array = Arrays.copyOf(array, len);
		}
		array[count] = i;
		count++;
	}
	
	public boolean contains(int i){
		for (int temp = 0; temp < count; temp++){
				if (array[temp] == i) {
					 return true;
				}
		}
		return false;
	}
	
	public int indexOf (int i){
		int check = 0;
		for (int temp = 0; temp < count; temp++){
			if (array[temp] == i && check == 0) {
				check = 1;
				return temp;
			}
		}
		return -1;
	}

	public void set(int i, int j){
		if (i < len && i > -1){
			array[i] = j;
		}	
	}
	
	public int get (int i){
		if (i < len && i > -1){
			return array[i];
		} else {
			return -1;
		}
	}
	
	public int size(){
		return count;
	}
	
	public void remove(){
		array[count - 1] = 0;
		count--;
	}
}
