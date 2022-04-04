import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class Int {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int l = sc.nextInt();
        int r = sc.nextInt();
        int ans = 0;
        for (int i = l; i <= r; i++){
            for (int j = 0; j < (Math.log(i) / Math.log(10)) + 1; j++){
                if (Math.floor(i % Math.pow(10, (j + 1)) / Math.pow(10, j)) == 1){
                    ans++;
                    break;
                }
            }
        }
        System.out.println(ans);
    }
}
