import java.util.Scanner;

public class Sort {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] a = new int[n];
		for (int i = 1; i <= n; i++){
			a[i-1] = sc.nextInt();
		}
		SortAux sa = new SortAux(a);
		//a = sortmerge(a);
		//for (int i = 0; i < n; i++){
		//	System.out.println(a[i]+" ");
		//}
		System.out.println(sa.num());
	}
}

class SortAux {
	private int s = 0;
	
	public SortAux(int[] a){
		sortmerge(a);
	}
	
	public int[] merge(int[] a, int[] b){
		int n = a.length;
		int m = b.length;
		int i = 0;
		int j = 0;
		int k = 0;
		int[] c = new int[n+m];
		while (i < n || j < m){
			if (j == m || (i < n && a[i] <= b[j])){
				c[k] = a[i];
				i++;
				k++;
			} else {
				c[k] = b[j];
				j++;
				k++;
				s += n-i;
				//System.out.println(s);
			}
		}
		return c;
	}
	
	public int[] sortmerge(int[] a){
		int n = a.length;
		if (n <= 1){
			return a;
		} else {
			int[] al = new int[n/2];
			System.arraycopy(a, 0, al, 0, n/2);
			int nn;
			if (n % 2 == 0) {
				nn = n/2;
			} else {
				nn = n/2 + 1;
			}
			int[] ar = new int[nn];
			System.arraycopy(a, n/2, ar, 0, nn);
			al = sortmerge(al);
			ar = sortmerge(ar);
			return merge(al, ar);
		}
	}
	
	public int num() {
		return s;
	}
}