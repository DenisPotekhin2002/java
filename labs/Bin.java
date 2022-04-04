import java.util.Scanner;

public class Bin{
	public static int searchMore(int x, int[] a){
		int l = -1;
		int r = a.length;
		while (r - l > 1){
			int m = (l + r) / 2;
			if (a[m] < x){
				l = m;
			} else {
				r = m;
			}
		}
		return r;
	}
	
	public static int searchLess(int x, int[] a){
		int l = -1;
		int r = a.length;
		while (r - l > 1){
			int m = (l + r) / 2;
			if (a[m] <= x){
				l = m;
			} else {
				r = m;
			}
		}
		return l;
	}
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] a = new int[n];
		for (int i = 1; i <= n; i++){
			a[i-1] = sc.nextInt();
		}
		SortAux sa = new SortAux(a);
		/*for (int i = 0; i < n; i++){
			System.out.print(sa.sortmerge(a)[i]+" ");
		}*/
		a = sa.sortmerge(a);
		int k = sc.nextInt();
		for (int i = 0; i < k; i++){
			int l = sc.nextInt();
			int r = sc.nextInt();
			/*int ltemp = 0;
			int rtemp = a.length - 1;
			while (a[ltemp] < l){
				ltemp++;
			}
			while (a[rtemp] > r){
				rtemp--;
			}*/
			int ltemp = searchMore(l, a);
			int rtemp = searchLess(r, a);
			System.out.print(rtemp - ltemp + 1 + " ");
		}
	}
}

class SortAux {
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
}