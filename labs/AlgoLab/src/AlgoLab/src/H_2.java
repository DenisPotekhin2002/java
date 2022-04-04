import java.io.FileNotFoundException;
import java.util.*;

public class H_2 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long[][] arr = new long[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                arr[i][j] = sc.nextInt();
            }
        }
        //long[][] d = new long[n][n]; //the shortest way to pass all the homes from 0 to i, finishing in j
        TreeMap<Set<Object>, Integer> map = new TreeMap<>();
        map.put(Set.of(Set.of(0), 0), 0);
        for (int i = 1; i < n; i++){
            long min = Integer.MAX_VALUE;
            int temp = 0;
            for (int j = 0; j < i; j++){
                Set s = new HashSet();
                for (int k = 0; k < i; k++){
                    if (k != j){
                        s.add(k);
                    }
                }

                /*if (d[i - 1][j] + arr[j][i] < min){
                    min = d[i - 1][j] + arr[j][i];
                    temp = j;
                }*/

            }
            //d[i][temp] = min;
        }
        //int ans = Integer.MAX_VALUE;
        //System.out.println(d[n - 1]);
    }
}
