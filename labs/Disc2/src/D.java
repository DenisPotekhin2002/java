import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class D {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int n = s.length();
        StringBuilder sb = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
        for (int i = 0; i < n; i++){
            char c = s.charAt(i);
            int k = sb.indexOf(Character.toString(c));
            System.out.print((k + 1) + " ");
            sb.deleteCharAt(k);
            sb.insert(0, c);
        }
    }
}
