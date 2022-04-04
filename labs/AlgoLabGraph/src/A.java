import java.util.Arrays;
import java.util.Scanner;

public class A {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        long[][] mat = new long[n][n];
        long[][] d = new long[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(d[i], Integer.MAX_VALUE);
            d[i][i] = 0;
            Arrays.fill(mat[i], Integer.MAX_VALUE);
        }
        for (int co = 0; co < m; co++) {
            int i = sc.nextInt() - 1;
            int j = sc.nextInt() - 1;
            mat[i][j] = sc.nextInt();
            mat[j][i] = mat[i][j];
            d[i][j] = mat[i][j];
            d[j][i] = mat[j][i];
        }
        int a = sc.nextInt() - 1;
        int b = sc.nextInt() - 1;
        int c = sc.nextInt() - 1;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(d[i][j] + " ");
//            }
//            System.out.println();
//        }
        long min = Integer.MAX_VALUE;
        int[] arr = new int[3];
        arr[0] = a;
        arr[1] = b;
        arr[2] = c;
        for (int a1 = 0; a1 < 3; a1++){
            for (int a2 = 0; a2 < 3; a2++){
                if (a1 != a2) {
                    for (int a3 = 0; a3 < 3; a3++) {
                        if (a1 != a3 && a2 != a3) {
                            if (d[arr[a1]][arr[a2]] < Integer.MAX_VALUE && d[arr[a2]][arr[a3]] < Integer.MAX_VALUE && d[arr[a1]][arr[a2]] + d[arr[a2]][arr[a3]] < min){
                                min = d[arr[a1]][arr[a2]] + d[arr[a2]][arr[a3]];
                            }
                        }
                    }
                }
            }
        }
        if (min < Integer.MAX_VALUE) {
            System.out.println(min);
        } else {
            System.out.println(-1);
        }
    }

}
