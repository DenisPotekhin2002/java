import java.util.*;

public class G_2 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        int[][] d = new int[str.length()][str.length()];
        for (int r = 0; r < str.length(); r++){
            for (int l = 0; l <= r; l++){
                if (l == r){
                    d[l][r] = 1;
                } else {
                    int best = Integer.MAX_VALUE;
                    if ((str.charAt(l) == '(' && str.charAt(r) == ')')
                            || (str.charAt(l) == '[' && str.charAt(r) == ']')
                            || (str.charAt(l) == '{' && str.charAt(r) == '}')){
                        best = d[l + 1][r  - 1];
                    }
                    for (int k = 1; k < r; k++){
                        best = Math.min(best, d[0][k] + d[k + 1][r]);
                    }
                    d[l][r] = best;
                }
            }
        }
        System.out.println(d[0][str.length() - 1]);
    }
}
