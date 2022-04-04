import java.util.Scanner;

public class Ferma{
	public static double check (double x, int vp, int vf, double a){
		return Math.sqrt(x * x + (1 - a) * (1 - a)) / vp + Math.sqrt((1 - x) * (1 - x) + a * a) / vf;
	}
	
	public static double searchTern (int vp, int vf, double a, double eps){
		double l = 0;
		double r = 1;
		double m1 = (2*l+r)/3;
		double m2 = (2*r+l)/3;
		while (m2 - m1 > eps){
			double cm2 = check(m2, vp, vf, a);
			double cm1 = check(m1, vp, vf, a);
			//System.out.println("m2 = " + m2 + ", cm2 = " + cm2);
			//System.out.println("m1 = " + m1 + ", cm1 = " + cm1);
			double d = cm2 - cm1;
			//System.out.println("d = " + d);
			if (d <= 0){
				l = m1;
				//System.out.println("l = " + l);
			} else {
				r = m2;
				//System.out.println("r = " + r);
			}
			m1 = (2*l+r)/3;
		    m2 = (2*r+l)/3;
		}
		return l;
	}
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int vp = sc.nextInt();
		int vf = sc.nextInt();
		double a = sc.nextDouble();
		double eps = 0.00001;
		//System.out.println(vp + " " + vf + " " + a + " " + eps);
		System.out.println(searchTern(vp, vf, a, eps));
	}
}