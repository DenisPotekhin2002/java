import java.util.*;

public class A1 {

    private static int n, m, s, t;
    private static int[] Q, d, q, ptr;

    static class Edge {
        int t, rev, cap, f, num;

        public Edge(int t, int rev, int cap, int num) {
            this.t = t;
            this.rev = rev;
            this.cap = cap;
            this.num = num;
        }
    }

    public static List<Edge>[] createGraph(int nodes) {
        List<Edge>[] graph = new List[nodes];
        for (int i = 0; i < nodes; i++)
            graph[i] = new ArrayList<>();
        return graph;
    }

    public static void addEdge(List<Edge>[] graph, int s, int t, int cap, int num) {
        graph[s].add(new Edge(t, graph[t].size(), cap, num));
        graph[t].add(new Edge(s, graph[s].size() - 1, 0, num));
    }

    static boolean dinicBfs(List<Edge>[] graph, int src, int dest, int[] dist) {
        Arrays.fill(dist, -1);
        dist[src] = 0;
        Q = new int[graph.length];
        int sizeQ = 0;
        Q[sizeQ++] = src;
        for (int i = 0; i < sizeQ; i++) {
            int u = Q[i];
            for (Edge e : graph[u]) {
                if (dist[e.t] < 0 && e.f < e.cap) {
                    dist[e.t] = dist[u] + 1;
                    Q[sizeQ++] = e.t;
                }
            }
        }
        return dist[dest] >= 0;
    }

    static int dinicDfs(List<Edge>[] graph, int[] ptr, int[] dist, int dest, int u, int f) {
        if (u == dest)
            return f;
        for (; ptr[u] < graph[u].size(); ++ptr[u]) {
            Edge e = graph[u].get(ptr[u]);
            if (dist[e.t] == dist[u] + 1 && e.f < e.cap) {
                int df = dinicDfs(graph, ptr, dist, dest, e.t, Math.min(f, e.cap - e.f));
                if (df > 0) {
                    e.f += df;
                    graph[e.t].get(e.rev).f -= df;
                    return df;
                }
            }
        }
        return 0;
    }

    public static int maxFlow(List<Edge>[] graph, int src, int dest) {
        int flow = 0;
        int[] dist = new int[graph.length];
        while (dinicBfs(graph, src, dest, dist)) {
            ptr = new int[graph.length];
            while (true) {
                int df = dinicDfs(graph, ptr, dist, dest, src, Integer.MAX_VALUE);
                if (df == 0)
                    break;
                flow += df;
            }
        }
        return flow;
    }


    private static void findWay(List<Edge>[] graph, int start){
        for (Edge e : graph[start]){
            if (e.f > 0) {
                System.out.print(start + 1 + " ");
                e.f = 0;
                findWay(graph, e.t);
                break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        s = sc.nextInt() - 1;
        t = sc.nextInt() - 1;
        List<Edge>[] graph = createGraph(n);
        d = new int[n];
        q = new int[n];
        ptr = new int[n];
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            addEdge(graph, a - 1, b - 1, 1, i);
        }
        double res = maxFlow(graph, s, t);
        if (res > 1){
            System.out.println("YES");
        } else {
            System.out.println("NO");
            return;
        }
        findWay(graph, s);
        System.out.println(t + 1);
        findWay(graph, s);
        System.out.println(t + 1);
    }
}
