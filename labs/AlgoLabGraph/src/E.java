import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class E {
    public static long INF = (long) 1e20;
    public static int n, m, s;

    static class Edge {
        int u, v;
        long w;

        Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }


    public static ArrayList<Edge> edges;
    public static ArrayList<ArrayList<Integer>> out;
    public static TreeMap<Integer, Boolean> used;
    public static TreeMap<Integer, Long> dist;

    public static void findShortest() {
        dist.put(s, (long) 0);
        for (int i = 0; i < n; ++i) {
            for (Edge edge : edges) {
                int u = edge.u;
                int v = edge.v;
                long w = edge.w;
                if ((dist.containsKey(u)) && (dist.get(u) < INF) && (dist.get(v) > dist.get(u) + w)) {
                    dist.put(v, Math.max(-INF, dist.get(u) + w));
                }
            }
        }
    }

    public static void dfs(int v) {
        used.put(v, true);
        for (int i = 0; i < out.get(v).size(); ++i) {
            int to = out.get(v).get(i);
            if (!used.get(to)) {
                dfs(to);
            }
        }
    }

    public static void main(String[] args) {
        edges = new ArrayList<>();
        out = new ArrayList<>();
        dist = new TreeMap<>();
        used = new TreeMap<>();
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        s = sc.nextInt();
        s--;

        for (int i = 0; i < n; i++) {
            dist.put(i, INF);
            used.put(i, false);
            out.add(new ArrayList<>());
        }

        for (int i = 0; i < m; ++i) {
            int u, v;
            long w;
            u = sc.nextInt();
            v = sc.nextInt();
            w = sc.nextLong();
            u--;
            v--;
            out.get(u).add(v);
            edges.add(new Edge(u, v, w));
        }

        findShortest();

        for (int i = 0; i < n; ++i) {
            for (Edge edge : edges) {
                int u = edge.u;
                int v = edge.v;
                long w = edge.w;
                if ((dist.get(u) < INF) && (dist.get(v) > dist.get(u) + w) && !used.get(v)) {
                    dfs(v);
                }
            }
        }

        for (int i = 0; i < n; ++i) {
            if (dist.get(i) == INF) {
                System.out.println("*");
            } else if (used.get(i)) {
                System.out.println("-");
            } else {
                System.out.println(dist.get(i));
            }
        }
    }
}