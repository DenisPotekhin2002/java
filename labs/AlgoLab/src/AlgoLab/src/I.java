import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class I {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        //Scanner sc = new Scanner(new File("input.txt"));
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] arr = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++){
            String temp1 = sc.next();
            for (int j = 0; j < m; j++) {
                char temp = temp1.charAt(j);
                if (temp == '.') {
                    arr[i][j] = 0;
                } else {
                    arr[i][j] = 1;
                }
            }
        }
        long[][][] d = new long[m + 1][n + 1][(int) Math.pow(2, n + 1) + 1];
        d[0][0][0] = 1;
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                for (int p = 0; p < Math.pow(2, n + 1); p++){
                    int pi;
                    int check = 0;
                    int ptemp = p;
                    for (int k = 0; k <= j; k++) {
                        if (arr[k][i + 1] == 1) {
                            if ((p & (1 << k)) > 0) {
                                check = 1;
                            } else {
                                ptemp += (1 << k);
                            }
                        }
                    }
                    for (int k = j; k < n; k++) {
                        if (arr[k][i] == 1) {
                            if ((p & (1 << (k + 1))) > 0) {
                                check = 1;
                            } else {
                                ptemp += (1 << (k + 1));
                            }
                        }
                    }
                    if (check == 1){
                        continue;
                    }
                    if ((ptemp & (1 << (j + 1))) > 0){
                        if ((p & (1 << (j + 1))) > 0){
                            pi = p - (1 << (j + 1));
                        }
                        else {
                            pi = p;
                        }
                        d[i][j + 1][pi] += d[i][j][p];
                    } else {
                        if (((ptemp & (1 << j)) == 0)) {
                            pi = p + (1 << j);
                            d[i][j + 1][pi] += d[i][j][p];
                        }
                        if (j < n - 1 && ((ptemp & (1 << (j + 2))) == 0)) {
                            pi = p + (1 << (j + 2));
                            d[i][j + 1][pi] += d[i][j][p];
                        }
                    }
                }
            }
            for (int p = 0; p < Math.pow(2, n); p++){
                d[i + 1][0][2 * p] = d[i][n][p];
            }
        }
        //PrintWriter writer = new PrintWriter(new File("output.txt"));
        if (arr[n - 1][m - 1] == 0) {
            System.out.println(d[m - 1][n - 1][1 << n]);
            //writer.println(d[m - 1][n - 1][1 << n]);
        } else {
            System.out.println(d[m - 1][n - 1][0]);
            //writer.println(d[m - 1][n - 1][0]);
        }
    }
}
