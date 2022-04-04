import java.util.*;

public class G {
    private static int[][] d;
    private static StringBuilder[][] ans;
    private static String str;

    public static int count(int l, int r){
        if (l > r){
            return 0;
        }
        if (d[l][r] > -1){
            return d[l][r];
        }
        for (int rt = l; rt <= r; rt++){
            for (int lt = l; lt <= rt; lt++){
                if (lt == rt){
                    d[lt][rt] = 1;
                    ans[lt][rt] = new StringBuilder("");
                } else {
                    int best = Integer.MAX_VALUE;
                    StringBuilder temp = new StringBuilder();
                    if ((str.charAt(lt) == '(' && str.charAt(rt) == ')')
                            || (str.charAt(lt) == '[' && str.charAt(rt) == ']')
                            || (str.charAt(lt) == '{' && str.charAt(rt) == '}')){
                        best = count(lt + 1, rt - 1);
                        temp = new StringBuilder(Character.toString(str.charAt(lt)) + ans[lt + 1][rt - 1] + Character.toString(str.charAt(rt)));
                    }
                    for (int k = lt; k < rt; k++){
                        int t = count(lt, k) + count(k + 1, rt);
                        if (t < best){
                            best = t;
                            temp = new StringBuilder(ans[lt][k]);
                            temp.append(ans[k + 1][rt]);
                        }
                    }
                    d[lt][rt] = best;
                    ans[lt][rt] = temp;
                }
            }
        }
        return d[l][r];
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        str = sc.next();
        d = new int[str.length()][str.length()];
        ans = new StringBuilder[str.length()][str.length()];
        for (int i = 0; i < str.length(); i++){
            for (int j = 0; j < str.length(); j++){
                d[i][j] = -1;
                ans[i][j] = new StringBuilder("");
            }
        }
        int n = count(0, str.length() - 1);
        StringBuilder sb = new StringBuilder(str);
        System.out.println(ans[0][str.length() - 1]);
    }
}
