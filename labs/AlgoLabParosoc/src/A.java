import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

public class A {

    private static boolean[] mark;
    private static ArrayList<ArrayList<Integer>> out;
    private static int[] p;
    private static TreeSet<Integer> visitedX;
    private static TreeSet<Integer> visitedY;

    private static boolean dfs(int v) {
        if (mark[v]) {
            return false;
        }
        mark[v] = true;
        for (int i = 0; i < out.get(v).size(); ++i) {
            int u = out.get(v).get(i);
            if (p[u] == -1 || dfs(p[u])) {
                p[u] = v;
                return true;
            } else {
                if (dfs(p[u])) {
                    p[u] = v;
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        for (int ii = 0; ii < k; ii++) {
            int m = sc.nextInt();
            int n = sc.nextInt();
            out = new ArrayList<>();
            for (int i = 0; i <= m; i++) {
                out.add(new ArrayList<>());
            }
            for (int i = 1; i <= m; i++) {
                TreeSet<Integer> set = new TreeSet<>();
                for (int j = 1; j <= n; j++){
                    set.add(j);
                }
                TreeSet<Integer> exists = new TreeSet<>();
                int c = sc.nextInt();
                while (c != 0){
                    exists.add(c);
                    c = sc.nextInt();
                }
                set.removeAll(exists);
                out.get(i).addAll(set);
            }
            p = new int[n + 1];
            mark = new boolean[m + 1];
            Arrays.fill(p, -1);
            for (int vv = 1; vv <= m; vv++) {
                Arrays.fill(mark, false);
                dfs(vv);
            }
            TreeSet<Integer> set = new TreeSet<>();
            for (int j = 1; j <= m; j++){
                set.add(j);
            }
            TreeSet<Integer> exists = new TreeSet<>();
            for (int i : p){
                if (i > -1) {
                    exists.add(i);
                }
            }
            set.removeAll(exists);
            visitedX = new TreeSet<>();
            visitedY = new TreeSet<>();
            for (int i : set){
                bfs(i, 1);
            }
            set = new TreeSet<>();
            for (int j = 1; j <= n; j++){
                set.add(j);
            }
            set.removeAll(visitedY);
            System.out.println(visitedX.size() + set.size());
            System.out.println(visitedX.size() + " " + set.size());
            for (int i : visitedX){
                System.out.print(i + " ");
            }
            System.out.println();
            for (int i : set){
                System.out.print(i + " ");
            }
            System.out.println();
            System.out.println();
        }
    }

    private static void bfs(int i, int k) {
        if (k == 1){
            visitedX.add(i);
            for (int j : out.get(i)){
                if (p[j] != i){
                    bfs(j, 2);
                }
            }
        } else {
            visitedY.add(i);
            bfs(p[i], 1);
        }
    }
}
