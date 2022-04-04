import java.util.Scanner;

public class Easy{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		long n = sc.nextLong();
		int x = sc.nextInt();
		int y = sc.nextInt();
		int min = y;
		if (x < y){
			min = x;
		}
		double tempdouble = Math.floor(((n-1)*x*y+x+y-1)/(x+y));
		long temp = (long) tempdouble;
		//System.out.println(min);
		//System.out.println(tempdouble);
		//System.out.println(temp);
		System.out.println(min + temp);
	}
}