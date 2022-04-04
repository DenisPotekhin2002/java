import java.util.Scanner;

public class Root{
	public static boolean good(double x, double c){
		return (x*x + Math.sqrt(x) >= c);
	}
	
	public static double search(double c, double eps){
		double l = -1;
		double r = c;
		while (r - l > eps){
			double m = (l + r) / 2;
			if (good(m, c)){
				r = m;
			} else {
				l = m;
			}
		}
		return r;
	}
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		double c = sc.nextDouble();
		double eps = 0.000001;
		System.out.println(search(c,eps));
	}
}