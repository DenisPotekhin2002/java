import java.util.*;

public class B {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] mat = new int[n + 1][m + 1];
        int[][] sum = new int[n + 1][m + 1];
        String[][] str = new String[n + 1][m + 1];
        for (int i = 1; i <= n; i++){
            for (int j = 1; j <= m; j++) {
                mat[i][j] = sc.nextInt();
            }
        }
        for (int i = 1; i <= n; i++){
            for (int j = 1; j <= m; j++) {
                if (i == j && i == 1){
                    sum[i][j] = mat[i][j];
                    str[i][j] = "";
                } else {
                    sum[i][j] = Integer.MIN_VALUE;
                    if (j > 0){
                        if (i == 1){
                            str[i][j] = "R";
                        } else if (j == 1){
                            str[i][j] = "D";
                        } else if (sum[i - 1][j] + mat[i][j] >= sum[i][j - 1] + mat[i][j]){
                            str[i][j] = "D";
                        } else {
                            str[i][j] = "R";
                        }
                        if (i > 1 && j > 1) {
                            sum[i][j] = Math.max(sum[i - 1][j] + mat[i][j], sum[i][j - 1] + mat[i][j]);
                        } else if (i > 1){
                            sum[i][j] = sum[i - 1][j] + mat[i][j];
                        } else {
                            sum[i][j] = sum[i][j - 1] + mat[i][j];
                        }
                    }
                }
            }
        }
        StringBuilder ans = new StringBuilder();
        int c1 = n;
        int c2 = m;
        while ((c1 != 1) || (c2 != 1)){
            ans.insert(0, str[c1][c2]);
            if (str[c1][c2].charAt(0) == 'R'){
                c2--;
            } else {
                c1--;
            }
        }
        System.out.println(sum[n][m]);
        System.out.println(ans);
    }
}
