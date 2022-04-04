import javax.imageio.metadata.IIOMetadataNode;
import java.util.*;

public class C {
    public static int V;
    static final long INF = Integer.MAX_VALUE;

    public static long shortestPath(long[][] mat, int u, int v, int k) {
        if (k == 0 && u == v) {
            return 0;
        }
        if (k == 1 && mat[u][v] != INF) {
            return mat[u][v];
        }

        if (k <= 0) {
            return INF;
        }

        long res = INF;
        for (int i = 0; i < V; i++) {
            if (mat[u][i] != INF && u != i && v != i) {
                long rec_res = shortestPath(mat, i, v, k - 1);
                if (rec_res != INF) {
                    res = Math.min(res, mat[u][i] + rec_res);
                }
            }
        }
        return res;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        V = n;
        int m = sc.nextInt();
        int ks = sc.nextInt();
        int s = sc.nextInt();
        s--;
        long[][] mat = new long[n][n];
        long[][] d = new long[n][ks + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(d[i], 110000000);
            Arrays.fill(mat[i], Integer.MAX_VALUE);
        }
        d[s][0] = 0;
        for (long co = 0; co < m; co++) {
            int i = sc.nextInt() - 1;
            int j = sc.nextInt() - 1;
            long k = sc.nextLong();
            mat[i][j] = k;
        }
        for (int v = 0; v < n; v++) {
            long ans = shortestPath(mat, s, v, ks);
            if (ans > 100000000) {
                System.out.println(-1);
            } else {
                System.out.println(ans);
            }
        }
    }

}
