import java.util.*;

public class E {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String a = sc.next();
        String b = sc.next();
        int n1 = a.length();
        int n2 = b.length();
        int[][] mat = new int[n1 + 1][n2 + 1];
        for (int i = 0; i <= n1; i++){
            for (int j = 0; j <= n2; j++){
                if (i == 0 || j == 0){
                    mat[i][j] = i + j;
                    continue;
                }
                if (a.charAt(i - 1) == b.charAt(j - 1)){
                    mat[i][j] = mat[i - 1][j - 1];
                } else {
                    mat[i][j] = Math.min(Math.min(mat[i - 1][j - 1], mat[i - 1][j]), mat[i][j - 1]) + 1;
                }
            }
        }
        System.out.println(mat[n1][n2]);
    }
}
