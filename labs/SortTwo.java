import java.util.Scanner;

public class SortTwo {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] a = new int[101];
		int min = 101;
		for (int i = 0; i < n; i++){
			int next = sc.nextInt();
			a[next] += 1;
			if (next < min){
				min = next;
			}
		}
		int i = 0;
		int k = min;
		while (i < n){
			for (int j = 0; j < a[k]; j++){
				System.out.println(k);
				i++;
			}
			k++;
		}
	}
}