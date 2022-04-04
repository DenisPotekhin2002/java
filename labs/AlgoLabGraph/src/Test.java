import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Test {

    public static void run(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int ks = sc.nextInt();
        int s = sc.nextInt();
        s--;
        int[][] mat = new int[n][n];
        long[][] d = new long[n][ks + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(d[i], Integer.MAX_VALUE);
            Arrays.fill(mat[i], Integer.MAX_VALUE);
        }
        d[s][0] = 0;
        for (int co = 0; co < m; co++) {
            int i = sc.nextInt() - 1;
            int j = sc.nextInt() - 1;
            int k = sc.nextInt();
            mat[i][j] = k;
//                if (i == s && ks > 0) {
//                    d[j][1] = mat[i][j];
//                }
        }
        for (int k = 1; k <= ks; k++) {
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    d[v][k] = Math.min(d[v][k], d[u][k - 1] + mat[u][v]);
                }
            }
        }
        for (int v = 0; v < n; v++) {
            if (d[v][ks] == Integer.MAX_VALUE) {
                System.out.println(-1);
            } else {
                System.out.println(d[v][ks]);
            }
        }
    }

    public static void main(String[] args) {

    }

}
