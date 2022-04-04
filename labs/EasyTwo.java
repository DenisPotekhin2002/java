import java.util.Scanner;

public class EasyTwo{
	public static boolean good(int check, int n, int x, int y){
		return (check/x + check/y >= n);
	}
	
	public static int search(int n, int x, int y, int min){
		int l = -1;
		int r = n*min;
		//System.out.println("r = " + r);
		while (r - l > 1){
			int m = l + (r - l) / 2;
			if (good(m, n, x, y)){
				r = m;
			} else {
				l = m;
			}
		}
		//System.out.println("r = " + r);
		return r;
	}
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int x = sc.nextInt();
		int y = sc.nextInt();
		int min = y;
		if (x < y){
			min = x;
		}
		n -= 1;
		//int temp = n*min;
		//System.out.println(temp);
		/*int[] a = new int[temp];
		for (int i = 0; i < a.length; i++){
			a[i] = i;
			//System.out.println("a = " + a[i]);
		}*/
		System.out.println(min + search(n, x, y, min));
	}
}