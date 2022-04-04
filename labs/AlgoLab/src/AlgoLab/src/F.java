import java.util.*;

public class F {
    private static long[][] mat;
    private static int[] arr;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (n == 0){
            System.out.println(0);
            System.out.println(0 + " " + 0);
        } else {
            mat = new long[n + 2][n + 2];
            arr = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                arr[i] = sc.nextInt();
            }
            for (int i = 0; i <= n + 1; i++) {
                for (int j = 0; j <= n + 1; j++) {
                    mat[i][j] = Integer.MAX_VALUE;
                }
            }
            for (int i = 0; i <= n + 1; i++) {
                if (i == 0 && arr[1] <= 100) {
                    mat[i][1] = arr[1];
                }
                if (i == 1 && arr[1] > 100) {
                    mat[i][1] = arr[1];
                }
            }
            for (int i = 1; i < n; i++) {
                for (int j = 0; j <= n; j++) {
                    if (arr[i + 1] <= 100) {
                        mat[j][i + 1] = Math.min(mat[j + 1][i], mat[j][i] + arr[i + 1]);
                    } else {
                        if (j > 0) {
                            mat[j][i + 1] = Math.min(mat[j + 1][i], mat[j - 1][i] + arr[i + 1]);
                        } else {
                            mat[j][i + 1] = mat[j + 1][i];
                        }
                    }
                }
            }
            long min = Integer.MAX_VALUE;
            int th = 0;
            int[] ans = new int[n];
            int it = 0;
            for (int i = 0; i <= n; i++) {
                if (mat[i][n] <= min) {
                    min = mat[i][n];
                    th = i;
                }
            }
            int res = th;
            for (int c = n; c > 0; c--) {
                if (arr[c] <= 100) {
                    if (mat[th][c - 1] != mat[th][c] - arr[c]) {
                        th++;
                        ans[it] = c;
                        it++;
                    }
                } else {
                    if (th == 0 || mat[th - 1][c - 1] != mat[th][c] - arr[c]) {
                        th++;
                        ans[it] = c;
                        it++;
                    } else {
                        th--;
                    }
                }
            }
            System.out.println(min);
            System.out.println(res + " " + (it - 1));
            for (int i = it - 2; i >= 0; i--) {
                System.out.println(ans[i]);
            }
        }
    }
}
