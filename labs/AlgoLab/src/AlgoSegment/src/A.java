import java.io.*;
import java.util.Arrays;

public class A {
    private static long[] a;

    public static void set(int i, int v, int x, int lx, int rx){
        if (rx - lx == 1){
            a[x] = v;
        } else {
            int m = (lx + rx) / 2;
            if (i < m){
                set (i, v, 2 * x + 1, lx, m);
            } else {
                set(i, v, 2 * x + 2, m, rx);
            }
            a[x] = a[2 * x + 1] + a[2 * x + 2];
        }
    }

    public static long sum(int n, int lx, int rx, int l, int r){
        if (rx < l || lx > r){
            return 0;
        }
        if (lx >= l && rx <= r){
            return a[n];
        }
        int m = (lx + rx) / 2;
        long s1 = sum(2 * n + 1, lx, m, l, r);
        long s2 = sum(2 * n + 2, m + 1, rx, l, r);
        return s1 + s2;
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        Scanner11 sc = new Scanner11(System.in, A::check);
        int n = Integer.parseInt(sc.nextElement());
        int m = Integer.parseInt(sc.nextElement());
        int len = (int)Math.pow(2, (int)(Math.log(n * 2 - 1)/Math.log(2)));
        a = new long[2 * len - 1];
        Arrays.fill(a, 0);
        for (int i = 0; i < n; i++){
            int t = Integer.parseInt(sc.nextElement());
            set(i, t, 0, 0, len);
        }
        for (int i = 0; i < m; i++){
            int t = Integer.parseInt(sc.nextElement());
            int t1 = Integer.parseInt(sc.nextElement());
            int t2 = Integer.parseInt(sc.nextElement());
            if (t == 1){
                set(t1, t2, 0, 0, len);
            } else {
                System.out.println(sum(0, 0, len - 1, t1, t2 - 1));
            }
        }
    }
}