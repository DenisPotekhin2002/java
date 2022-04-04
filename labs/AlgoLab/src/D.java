import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class D {
    private static ArrayList<Integer>[] out;
    private static boolean[] mark;
    private static int[] t_in;
    private static int[] up;
    private static int[] comp;
    private static int T;
    private static long[] edges;
    private static ArrayList<Long> bridges = new ArrayList<>();
    private static Stack<Integer> buf = new Stack<>();
    private static int curr_comp = 1;

    private static void dfs(int v, int p){
        buf.add(v);
        t_in[v] = T++;
        up[v] = t_in[v];
        mark[v] = true;
        for (int u : out[v]){
            if (u == p){
                continue;
            }
            if (!mark[u]){
                dfs(u, v);
                up[v] = Math.min(up[v], up[u]);
            } else {
                up[v] = Math.min(up[v], t_in[u]);
            }
        }
        if (up[v] == t_in[v]){
            bridges.add((long) Math.min(v, p) * 2000000 + (long) Math.max(v, p));
            while (true){
                int x = buf.pop();
                comp[x] = curr_comp;
                if (x == v){
                    curr_comp++;
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        out = new ArrayList[n];
        mark = new boolean[n];
        t_in = new int[n];
        up = new int[n];
        comp = new int[n];
        edges = new long[m];
        for (int i = 0; i < n; i++){
            out[i] = new ArrayList<>();
        }
        int[] deg = new int[n];
        for (int i = 0; i < m; i++) {
            int from = sc.nextInt() - 1;
            int to = sc.nextInt() - 1;
            if (!out[from].contains(to)) {
                deg[to]++;
                out[from].add(to);
                out[to].add(from);
                edges[i] = (long) Math.min(from, to) * 2000000 + (long) Math.max(from, to);
            }
        }
        for (int i = 0; i < n; i++){
            if (!mark[i]){
                buf.add(i);
                t_in[i] = 0;
                mark[i] = true;
                T = 1;
                dfs(out[i].get(0), i);
            }
        }
        System.out.println(curr_comp);
        for (int i : comp){
            System.out.print(i + " ");
        }
    }
}