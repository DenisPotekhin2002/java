public class Entropy {
    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        int n = sc.nextInt();
        double res = 0;
        for (int i = 0; i < n; i++){
            double temp = sc.nextDouble();
            temp /= 100;
            res += temp*Math.log(temp)/Math.log(2);
        }
        System.out.println(-1 * res);
    }
}
