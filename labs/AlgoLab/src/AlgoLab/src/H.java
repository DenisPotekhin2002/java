import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class H {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (n == 1){
            int t = sc.nextInt();
            System.out.println(0);
            System.out.println(1);
        } else {
            long[][][] d = new long[n][n][n + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k <= n; k++) {
                        d[i][j][k] = Integer.MAX_VALUE;
                    }
                }
            }
            ArrayList<Integer>[][][] mat = new ArrayList[n][n][n + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        d[i][j][2] = sc.nextInt();
                        mat[i][j][2] = new ArrayList<>();
                        mat[i][j][2].add(i);
                        mat[i][j][2].add(j);
                    } else {
                        int t = sc.nextInt();
                    }
                }
            }
            for (int k = 3; k <= n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        long min = Integer.MAX_VALUE;
                        int temp = -1;
                        for (int y = 0; y < n; y++) {
                            if (d[i][y][k - 1] + d[y][j][2] < min && !mat[i][y][k - 1].contains(j)) {
                                min = d[i][y][k - 1] + d[y][j][2];
                                temp = y;
                            }
                        }
                        if (i != j && temp > -1) {
                            d[i][j][k] = min;
                            mat[i][j][k] = new ArrayList<>();
                            for (int z : mat[i][temp][k - 1]) {
                                mat[i][j][k].add(z);
                            }
                            mat[i][j][k].add(j);
                        }
                    }
                }
            }
            long ans = Integer.MAX_VALUE;
            int tk = 0;
            for (int k = 1; k < n; k++) {
                if (d[0][k][n] < ans) {
                    ans = d[0][k][n];
                    tk = k;
                }
            }
            System.out.println(ans);
            for (int z = 0; z <= mat[0][tk][n].size() - 1; z++) {
                System.out.print((mat[0][tk][n].get(z) + 1) + " ");
            }
        }
    }
}
