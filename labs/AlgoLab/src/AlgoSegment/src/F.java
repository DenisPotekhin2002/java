import java.io.*;
import java.util.Arrays;

public class F {
    private static long[] a;
    private static long[] b;

    public static void add(int l, int r, int v, int x, int lx, int rx){
        if (rx < l || lx > r){
            return;
        }
        if (lx >= l && rx <= r){
            a[x] += v;
            b[x] += v;
            return;
        }
        propagate(x);
        int m = (lx + rx) / 2;
        add(l, r, v, 2 * x + 1, lx, m);
        add(l, r, v, 2 * x + 2, m + 1, rx);
        b[x] = Math.min(b[2 * x + 1], b[2 * x + 2]) + a[x];
    }

    private static void propagate(int x) {
        if (a[x] != 0){
            a[2 * x + 1] += a[x];
            b[2 * x + 1] += a[x];
            a[2 * x + 2] += a[x];
            b[2 * x + 2] += a[x];
            a[x] = 0;
            b[x] = Math.min(b[2 * x + 1], b[2 * x + 2]);
        }
    }

    public static long min(int n, int lx, int rx, int l, int r){
        if (rx < l || lx > r){
            return -1;
        }
        if (lx >= l && rx <= r){
            return b[n];
        }
        int m = (lx + rx) / 2;
        long s1 = min(2 * n + 1, lx, m, l, r);
        long s2 = min(2 * n + 2, m + 1, rx, l, r);
        if (s1 == -1){
            return s2 + a[n];
        }
        if (s2 == -1){
            return s1 + a[n];
        }
        return Math.min(s1, s2) + a[n];
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        Scanner11 sc = new Scanner11(System.in, F::check);
        int n = Integer.parseInt(sc.nextElement());
        int m = Integer.parseInt(sc.nextElement());
        int len = (int)Math.pow(2, (int)(Math.log(n * 2 - 1)/Math.log(2)));
        a = new long[2 * len - 1];
        b = new long[2 * len - 1];
        Arrays.fill(a, 0);
        Arrays.fill(b, 0);
        for (int i = 0; i < m; i++){
            int t = Integer.parseInt(sc.nextElement());
            if (t == 1){
                int t1 = Integer.parseInt(sc.nextElement());
                int t2 = Integer.parseInt(sc.nextElement());
                int t3 = Integer.parseInt(sc.nextElement());
                add(t1, t2 - 1, t3,0, 0, len - 1);
            } else {
                int t1 = Integer.parseInt(sc.nextElement());
                int t2 = Integer.parseInt(sc.nextElement());
                System.out.println(min(0, 0, len - 1, t1, t2 - 1));
            }
        }
    }
}
