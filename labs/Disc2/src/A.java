import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class A {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (n == 1){
            int k = sc.nextInt();
            System.out.println(k);
        } else {
            long[] d = new long[n + 2];
            long[] d2 = new long[n + 2];
            for (int i = 0; i < n; i++) {
                d[i] = sc.nextInt();
                d2[i] = Long.MAX_VALUE / 2;
                //arr[i].add(d[i]);
            }
            d[n] = Long.MAX_VALUE / 2;
            d[n + 1] = Long.MAX_VALUE / 2;
            d2[n] = Long.MAX_VALUE / 2;
            d2[n + 1] = Long.MAX_VALUE / 2;
            Arrays.sort(d);
            int p1 = 0;
            int p2 = 0;
            int c = 0;
            while (p1 < n - 1 || p2 < n - 1) {
                if (d[p1] + d[p1 + 1] <= d[p1] + d2[p2] && d[p1] + d[p1 + 1] <= d2[p2] + d2[p2 + 1]) {
                    d2[c] = d[p1] + d[p1 + 1];
                    c++;
                    p1 += 2;
                } else if (d[p1] + d2[p2] <= d[p1] + d[p1 + 1] && d[p1] + d2[p2] <= d2[p2] + d2[p2 + 1]) {
                    d2[c] = d[p1] + d2[p2];
                    c++;
                    p1++;
                    p2++;
                } else {
                    d2[c] = d2[p2] + d2[p2 + 1];
                    c++;
                    p2 += 2;
                }
            }
            long ans = 0;
            int i = 0;
            while (d2[i] < Long.MAX_VALUE / 2) {
                ans += d2[i];
                i++;
            }
            System.out.println(ans);
        }
    }
}
