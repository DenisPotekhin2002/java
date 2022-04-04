import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class E {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        TreeMap<String, Integer> map = new TreeMap<>();
        for (int i = 0; i < 26; i++){
            map.put(Character.toString('a' + i), i);
        }
        int len = 26;
        StringBuilder s = new StringBuilder(sc.next());
        s.append('/');
        int n = s.length() - 1;
        StringBuilder t = new StringBuilder(Character.toString(s.charAt(0)));
        int code = 0;
        int i = 1;
        while (i <= n + 1){
            if (map.get(t.toString()) != null){
                code = map.get(t.toString());
                t.append(s.charAt(i));
                i++;
            } else {
                System.out.print(code + " ");
                map.put(t.toString(), len);
                len++;
                t = new StringBuilder(Character.toString(s.charAt(i - 1)));
                if (i == n + 1){
                    i++;
                }
            }
        }
    }
}