import java.util.*;

public class D_2 {
    private static final int[][] arr = new int[10][];
    private static long[][] mat;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        arr[0] = new int[]{4, 6};
        arr[1] = new int[]{6, 8};
        arr[2] = new int[]{7, 9};
        arr[3] = new int[]{4, 8};
        arr[4] = new int[]{0, 3, 9};
        arr[5] = new int[]{};
        arr[6] = new int[]{0, 1, 7};
        arr[7] = new int[]{2, 6};
        arr[8] = new int[]{1, 3};
        arr[9] = new int[]{2, 4};
        int n = sc.nextInt();
        mat = new long[10][n + 1];
        long ans = 0;
        for (int i = 1; i <= 9; i++){
            if (i != 8){
                mat[i][1] = 1;
            }
        }
        for (int j = 1; j <= n - 1; j++){
            for (int i = 0; i <= 9; i++){
                for (int k = 0; k < arr[i].length; k++) {
                    mat[arr[i][k]][j + 1] += mat[i][j] % 1e9;
                }
            }
        }
        for (int i = 0; i <= 9; i++){
            ans += mat[i][n] % 1e9;
        }
        System.out.println((int)(ans % 1e9));
    }
}
