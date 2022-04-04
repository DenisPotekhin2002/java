import java.util.*;

public class D_3 {
    private static int[][] mat = new int[10][];

    public static int count(int last, int n){
        if (n == 0){
            return 1;
        }
        if (n == 1){
            return mat[last].length;
        }
        else {
            int ans = 0;
                for (int j = 0; j < mat[last].length; j++) {
                    ans += count(mat[last][j], n - 1);
                }
            return ans;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        mat[0] = new int[]{4, 6};
        mat[1] = new int[]{6, 8};
        mat[2] = new int[]{7, 9};
        mat[3] = new int[]{4, 8};
        mat[4] = new int[]{0, 3, 9};
        mat[5] = new int[]{};
        mat[6] = new int[]{0, 1, 7};
        mat[7] = new int[]{2, 6};
        mat[8] = new int[]{1, 3};
        mat[9] = new int[]{2, 4};
        int n = sc.nextInt();
        /*
        for (int i = 0; i <= 9; i++){
            for (int j = 0; j < mat[i].length; j++){
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
         */
        int ans = 0;
        for (int i = 1; i <= 9; i++){
            if (i != 8){
                ans += count(i, n - 1);
            }
        }
        System.out.println((int)(ans % 1e9));
    }
}
