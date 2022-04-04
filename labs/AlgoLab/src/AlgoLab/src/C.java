import java.util.*;

public class C {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n + 1];
        arr[0] = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++){
            arr[i] = sc.nextInt();
        }
        int[] d = new int[n + 1];
        int[] p = new int[n + 1];
        d[0] = 0;
        p[0] = 0;
        for (int i = 1; i <= n; i++){
            d[i] = Integer.MIN_VALUE;
            for (int j = 0; j < i; j++){
                if (arr[j] < arr[i] && d[j] + 1 > d[i]){
                    d[i] = d[j] + 1;
                    p[i] = j;
                }
            }
        }
        int maxOf = Integer.MIN_VALUE;
        int th = 0;
        for (int i = 1; i <= n; i++){
            if (d[i] > maxOf){
                maxOf = d[i];
                th = i;
            }
        }
        System.out.println(maxOf);
        int[] ans = new int[maxOf];
        int t = th;
        for (int c = 0; c < maxOf; c++){
            ans[c] = t;
            t = p[t];
        }
        for (int c = maxOf - 1; c >= 0; c--){
            System.out.print(arr[ans[c]]);
            if (c > 0){
                System.out.print(" ");
            }
        }
    }
}
