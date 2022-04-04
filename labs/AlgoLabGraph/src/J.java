import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class J {

    public static TreeMap<Integer, ArrayList<Integer>> arr;
    public static int n;
    public static int[] res;

    public static int gran(int s) {
        if (res[s] > -1) {
            return res[s];
        }
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i <= n; i++) {
            map.put(i, i);
        }
        for (int i : arr.get(s)) {
            map.remove(gran(i));
        }
        res[s] = map.firstKey();
        return res[s];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            map.put(i, i);
        }
        int m = sc.nextInt();
        arr = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            arr.put(i, new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int f = sc.nextInt() - 1;
            int t = sc.nextInt() - 1;
            if (!arr.get(f).contains(t)) {
                arr.get(f).add(t);
            }
            map.remove(t);
        }
        res = new int[n];
        Arrays.fill(res, -1);
        for (int i : map.keySet()) {
            gran(i);
        }
        for (int i : res) {
            System.out.println(i);
        }
    }
}
