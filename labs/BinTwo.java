import java.util.Scanner;

public class BinTwo{
	public static int searchMore(int x, int[] a){
		int l = 0;
		int r = a.length - 1;
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
		int l = 0;
		int r = a.length - 1;
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
		int num = sc.nextInt();
		for (int i = 0; i < n; i++){
			a[i] = sc.nextInt();
		}
		for (int i = 0; i < num; i++){
			int x = sc.nextInt();
			int min = searchLess(x, a);
			int max = searchMore(x, a);
			if (x - a[min] <= a[max] - x){
				System.out.println(a[min]);
			} else {
				System.out.println(a[max]);
			}
		}
	}
}	