import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ATemp {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long[] d = new long[n + 2];
        long[] d2 = new long[n + 2];
        long[] f = new long[n + 2];
        int[][] arr = new int[n][n];
        int[] ind = new int[n];
        for (int i = 0; i < n; i++){
            d[i] = sc.nextInt();
            d2[i] = Integer.MAX_VALUE;
            f[i] = 0;
            //arr[i] = new ArrayList<>();
        }
        d[n] = Integer.MAX_VALUE;
        d[n + 1] = Integer.MAX_VALUE;
        d2[n] = Integer.MAX_VALUE;
        d2[n + 1] = Integer.MAX_VALUE;
        Arrays.sort(d);
        int p1 = 0;
        int p2 = 0;
        int c = 0;
        while (p1 < n - 1 || p2 < n - 2){
            if (d[p1] + d[p1 + 1] <= d[p1] + d2[p2] && d[p1] + d[p1 + 1] <= d2[p2] + d2[p2 + 1]){
                d2[c] = d[p1] + d[p1 + 1];
                arr[c][ind[c]] = p1;
                ind[c]++;
                arr[c][ind[c]] = p1 + 1;
                ind[c]++;
                c++;
                f[p1]++;
                f[p1 + 1]++;
                p1 += 2;
            } else if (d[p1] + d2[p2] <= d[p1] + d[p1 + 1] && d[p1] + d2[p2] <= d2[p2] + d2[p2 + 1]){
                d2[c] = d[p1] + d2[p2];
                arr[c][ind[c]] = p1;
                ind[c]++;
                for (int a = 0; a < ind[p2]; a++) {
                    arr[c][ind[c]] = arr[p2][a];
                    ind[c]++;
                    f[arr[p2][a]]++;
                }
                f[p1]++;
                c++;
                p1++;
                p2++;
            } else {
                d2[c] = d2[p2] + d2[p2 + 1];
                for (int a = 0; a < ind[p2]; a++) {
                    arr[c][ind[c]] = arr[p2][a];
                    ind[c]++;
                    f[arr[p2][a]]++;
                }
                for (int a = 0; a < ind[p2 + 1]; a++) {
                    arr[c][ind[c]] = arr[p2 + 1][a];
                    ind[c]++;
                    f[arr[p2 + 1][a]]++;
                }
                c++;
                p2 += 2;
            }
        }
        long ans = 0;
        for (int i = 0; i < n; i++){
            ans += f[i] * d[i];
        }
        System.out.println(ans);
    }
}