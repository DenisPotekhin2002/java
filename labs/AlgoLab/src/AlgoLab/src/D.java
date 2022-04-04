import java.util.*;

public class D {
    private static final int[] arr = new int[10];

    public static int count(int last, int n){
        if (n == 0){
            return 1;
        }
        if (n == 1){
            return arr[last];
        }
        else {
            int ans = 0;
            for (int i = 0; i <= 9; i++){
                ans += count(i, n - 1);
            }
            return ans;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        arr[0] = 2;
        arr[1] = 2;
        arr[2] = 2;
        arr[3] = 2;
        arr[4] = 3;
        arr[5] = 0;
        arr[6] = 3;
        arr[7] = 2;
        arr[8] = 2;
        arr[9] = 2;
        int n = sc.nextInt();
        int ans = 0;
        //int[] d = new int[n + 1];
        //d[0] = 0;
        for (int i = 1; i <= 9; i++){
            if (i != 8){
                ans += count(i, n - 1);
            }
        }
        System.out.println((int)(ans % 1e9));
    }
}
