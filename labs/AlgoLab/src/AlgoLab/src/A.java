import java.util.*;

public class A {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 1; i <= n - 2; i++){
            arr[i] = sc.nextInt();
        }
        int[] d = new int[n];
        int[] p = new int[n];
        p[0] = 1;
        d[1] = arr[1];
        int c = 1;
        for (int i = 2; i < n; i++){
            d[i] = Integer.MIN_VALUE;
            for (int j = 1; j <= k; j++){
                if (i - j >= 0){
                    if (d[i] <= d[i - j] + arr[i]){
                        p[c] = i - j + 1;
                    }
                    d[i] = Math.max(d[i], d[i - j] + arr[i]);
                }
            }
            if (p[c] != p[c - 1]){
                c++;
            }
        }
        p[c] = n;
        System.out.println(d[n - 1]);
        System.out.println(c);
        for (int i = 0; i <= c; i++){
            System.out.print(p[i] + " ");
        }
    }
}
