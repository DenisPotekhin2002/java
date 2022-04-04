import java.io.*;
import java.util.Arrays;

public class E {
    private static long[] a;
    private static long[] b;

    public static void set(int i, int v, int x, int lx, int rx){
        if (rx - lx == 1){
            a[x] = v;
            b[x] = 1;
        } else {
            int m = (lx + rx) / 2;
            if (i < m){
                set (i, v, 2 * x + 1, lx, m);
            } else {
                set(i, v, 2 * x + 2, m, rx);
            }
            a[x] = Math.max(a[2 * x + 1], a[2 * x + 2]);
            b[x] = b[2 * x + 1] + b[2 * x + 2];
        }
    }

    public static long ind(int n, int lx, int rx, int l, int r, int x){
        if (rx == lx){
            if (a[rx + (a.length + 1)/2 - 1] >= x) {
                return 0;
            } else {
                return Integer.MAX_VALUE;
            }
        }
        int m = (lx + rx) / 2;
        int temp = 2 * n + 1;
        int temp2 = 2 * n + 1;
        while (2 * temp + 2 < a.length){
            temp = 2 * temp + 2;
            temp2 = 2 * temp2 + 1;
        }
        if (a[2 * n + 1] < x || temp < l + (a.length + 1)/2 - 1){
            return b[2 * n + 1] + ind(2 * n + 2, m + 1, rx, l, r, x);
        } else if (temp2 >= l + (a.length + 1)/2 - 1){
            return ind(2 * n + 1, lx, m, l, r, x);
        } else {
            return Math.min(b[2 * n + 1] + ind(2 * n + 2, m + 1, rx, l, r, x), ind(2 * n + 1, lx, m, l, r, x));
        }
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        Scanner11 sc = new Scanner11(System.in, E::check);
        int n = Integer.parseInt(sc.nextElement());
        int m = Integer.parseInt(sc.nextElement());
        int len = (int)Math.pow(2, (int)(Math.log(n * 2 - 1)/Math.log(2)));
        a = new long[2 * len - 1];
        b = new long[2 * len - 1];
        Arrays.fill(a, 0);
        Arrays.fill(b, 0);
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
                long res = ind(0, 0, len - 1, t2, len - 1, t1);
                if (res > 1000000000){
                    System.out.println(-1);
                } else {
                    System.out.println(res);
                }
            }
        }
    }
}
