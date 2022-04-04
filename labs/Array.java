import java.util.Scanner;

public class Array{
	public static boolean good(long x, int n, int k, int[] a){
		long sumNow = 0;
		int i = 0;
		int count = 0;
		while (i < n){
			while (sumNow <= x && i < n){
				sumNow += a[i];
				i++;
			}
			//System.out.println("i = " + i + ", sumNow = " + sumNow);
			if (sumNow > x){
				i--;
			}	
			sumNow = 0;
			count++;
		}
		//System.out.println("count = " + count);
		return (count <= k);
	}
	
	public static long search(long left, long right, int n, int k, int[] a){
		long l = left - 1;
		long r = right;
		while (r - l > 1){
			long m = l + (r - l) / 2;
			if (good(m, n, k, a)){
				r = m;
				//System.out.println("r = " + r);
			} else {
				l = m;
				//System.out.println("l = " + l);
			}
		}
		return r;
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		int[] a = new int[n];
		for (int i = 1; i <= n; i++){
			a[i-1] = sc.nextInt();
		}
		long r = 0;
		for (int temp = 0; temp < a.length; temp++){
			r += a[temp];
		}
		long l = 0;
		for (int temp = 0; temp < a.length; temp++){
			if (a[temp] > l){
				l = a[temp];
			}
		}
		//System.out.println("l = " + l + ", r= " + r);
		System.out.println(search(l, r, n, k, a));
	}
}