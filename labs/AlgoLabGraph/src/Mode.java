import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Mode {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        HashMap<Integer, Integer> a = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int cur = sc.nextInt();
            if (a.containsKey(cur)) {
                a.put(cur, a.get(cur) + 1);
            } else {
                a.put(cur, 1);
            }
        }
        if (a.size() < 2){
            System.out.println(-1);
            return;
        }
        int max = 0;
        int max_key = 0;
        int sec = 0;
        int res = 0;
        boolean check_max = true;
        boolean check_sec = true;
        for (int key : a.keySet()) {
            if (a.get(key) > max) {
                res = max_key;
                sec = max;
                max = a.get(key);
                max_key = key;
                check_sec = check_max;
                check_max = true;
            } else if (a.get(key) == max) {
                check_max = false;
            } else if (a.get(key) == sec) {
                check_sec = false;
            } else if (a.get(key) < max && a.get(key) > sec) {
                sec = a.get(key);
                check_sec = true;
                res = key;
            }
        }
        if (check_max && check_sec) {
            System.out.println(res);
        } else {
            System.out.println(-1);
        }
    }
}
