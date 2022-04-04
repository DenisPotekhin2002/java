import java.util.Scanner;

public class Diploms{
	public static boolean good(int x, int h, int w, int n){
		return ((x/h)*(x/w)>=n);
	}
	
	public static int search(int h, int w, int n, int left, int right){
		int l = left;
		int r = right;
		while (r - l > 1){
			int m = l + (r - l) / 2;
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
		int check = (int) Math.floor(Math.sqrt(h*w*n));
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
		int res = search(h, w, n, check/2, check);
		System.out.println(res);
	}
}