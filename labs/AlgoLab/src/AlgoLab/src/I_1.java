import java.util.*;
import java.io.*;

public class I_1 {
    private static int n;
    private static int[][] arr;

    public static boolean comp (int p, int q, StringBuilder i){
        /*
        StringBuilder ptemp = new StringBuilder(Integer.toBinaryString(p));
        while (ptemp.length() < n){
            ptemp.insert(0, '0');
        }
        for (int c = 0; c < ptemp.length(); c++){
            if (i.charAt(c) == '1'){
                if (ptemp.charAt(c) == '1') {
                    return false;
                }
                ptemp.setCharAt(c, '1');
            }
        }
        p = Integer.parseInt(ptemp.toString(), 2);
         */
        if ((p & Integer.parseInt(i.toString(), 2)) > 0){
            return false;
        }
        p = p + Integer.parseInt(i.toString(), 2);
        if ((p & q) != 0){
            return false;
        }
        int x = (1 << n) - 1 - (p|q);
        if (x % 3 != 0){
            return false;
        }
        int y = x / 3;
        if ((y & (y << 1)) != 0){
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        //Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(new File("input.txt"));
        n = sc.nextInt();
        int m = sc.nextInt();
        arr = new int[n][m];
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
        int[][] d = new int[m + 1][(int) Math.pow(2, n)];
        d[0][0] = 1;
        for (int i = 0; i < m; i++) {
            StringBuilder strtemp = new StringBuilder();
            for (int c = 0; c < n; c++){
                strtemp.append(arr[c][i]);
            }
            for (int p = 0; p < Math.pow(2, n); p++) {
                for (int pi = 0; pi < Math.pow(2, n); pi++) {
                    if (comp(p, pi, strtemp)){
                        d[i + 1][pi] += d[i][p];
                    }
                }
            }
        }
        PrintWriter writer = new PrintWriter(new File("output.txt"));
        System.out.println(d[m][0]);
        writer.println(d[m][0]);
    }
}
