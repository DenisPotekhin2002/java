import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class B {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int n = s.length();
        StringBuilder sb = new StringBuilder(s);
        StringBuilder[] arr = new StringBuilder[n];
        for (int i = 0; i < n; i++){
            arr[i] = new StringBuilder(sb);
            sb.insert(0, sb.charAt(sb.length() - 1));
            sb.deleteCharAt(sb.length() - 1);
        }
        Arrays.sort(arr);
        for (int i = 0; i < n; i++){
            System.out.print(arr[i].charAt(n - 1));
        }
    }
}
