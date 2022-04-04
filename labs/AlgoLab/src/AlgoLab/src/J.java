import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class J {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        //Scanner sc = new Scanner(new File("input.txt"));
        int n1 = sc.nextInt();
        int m1 = sc.nextInt();
        if (n1 == 1 && m1 == 1){
            System.out.println(2);
        } else {
            int n;
            int m;
            if (n1 < m1) {
                n = n1;
                m = m1;
            } else {
                n = m1;
                m = n1;
            }
            long[][][] d = new long[m + 1][n + 1][(int) Math.pow(2, n + 1) + 1];
            for (int i = 0; i < Math.pow(2, n + 1); i++) {
                d[0][0][i] = 1;
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    for (int p = 0; p < Math.pow(2, n + 1); p++) {
                        int pi;
                        if ((p & (1 << j)) == 0 || (p & (1 << (j + 1))) == 0 || (p & (1 << (j + 2))) == 0) {
                            if ((p & (1 << (j + 1))) == 0) {
                                pi = p + (1 << (j + 1));
                            } else {
                                pi = p;
                            }
                            d[i][j + 1][pi] += d[i][j][p];
                        }
                        if ((p & (1 << j)) > 0 || (p & (1 << (j + 1))) > 0 || (p & (1 << (j + 2))) > 0) {
                            if ((p & (1 << (j + 1))) > 0) {
                                pi = p - (1 << (j + 1));
                            } else {
                                pi = p;
                            }
                            d[i][j + 1][pi] += d[i][j][p];
                        }
                    }
                }
            /*for (int p = 0; p < Math.pow(2, n); p++){
                d[i + 1][0][2 * p] = d[i][n][p] + d[i][n][p + (1 << n)] + d[i][n][p + (1 << (n + 1))] + d[i][n][p + (3 << n)];
                d[i + 1][0][2 * p + 1] = d[i][n][p] + d[i][n][p + (1 << n)] + d[i][n][p + (1 << (n + 1))] + d[i][n][p + (3 << n)];
            }*/
                for (int p = 0; p < Math.pow(2, n); p++) {
                    d[i + 1][0][2 * p] = d[i][n - 1][p] + d[i][n - 1][p + (1 << n)];
                    d[i + 1][0][2 * p + 1] = d[i][n - 1][p] + d[i][n - 1][p + (1 << n)];
                }
            }
            //PrintWriter writer = new PrintWriter(new File("output.txt"));
            int ans = 0;
            for (int i = 0; i < Math.pow(2, n + 1); i++) {
                ans += d[m - 2][n - 1][i];
            }
            System.out.println(ans);
            //writer.println(d[m - 1][n - 1][0]);
        }
    }
}
