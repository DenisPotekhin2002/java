import java.util.Scanner;

public class Diploms{
	public static boolean good(long x, int h, int w, int n){
		return ((x/h)*(x/w)>=n);
	}
	
	public static long search(int h, int w, int n, long left, long right){
		long l = left;
		long r = right;
		while (r - l > 1){
			long m = l + (r - l) / 2;
			if (good(m,h,w,n)){
				r = m;
			} else {
				l = m;
			}
		}
		return r;
	}
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int w = sc.nextInt();
		int h = sc.nextInt();
		int n = sc.nextInt();
		int max = h;
		if (w > h){
			max = w;
		}
		long check = (long) Math.floor(Math.sqrt(h)*Math.sqrt(w)*Math.sqrt(n));
		//System.out.println(check);
		//int check = 1;
		while (!good(check, h, w, n)){
			check *= 2;
		}
		//System.out.println(check);
		//int[] a = new int[check/2+1];
		/*for (int i = 0; i < a.length; i++){
			a[i] = i + check/2 + 1;
			//System.out.println("a = " + a[i]);
		}*/
		long res = search(h, w, n, check/2, check);
		System.out.println(res);
	}
}