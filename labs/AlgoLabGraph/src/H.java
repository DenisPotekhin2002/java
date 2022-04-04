import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class H {
    public static void main(String[] args) {
        PrintWriter writer = new PrintWriter(System.out);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            TreeMap<Integer, ArrayList<Integer>> arr = new TreeMap<>();
            for (int i = 0; i < n; i++) {
                arr.put(i, new ArrayList<>());
            }
            for (int i = 0; i < m; i++) {
                int f = sc.nextInt() - 1;
                int t = sc.nextInt() - 1;
                arr.get(f).add(t);
            }
            int[] res = new int[n];// 0 - ?, 1 - l, 2 - w
            boolean check = true;
            while (check) {
                check = false;
                for (int al = 0; al < n; al++) {
                    if (res[al] == 0) {
                        int win = 1;
                        boolean found = false;
                        for (int i : arr.get(al)) {
                            if (res[i] == 1) {
                                win = 2;
                                break;
                            }
                            if (res[i] == 0) {
                                found = true;
                            }
                        }
                        if (win == 1 && found){
                            win = 0;
                        }
                        if (win != 0){
                            check = true;
                            arr.remove(al);
                        }
                        res[al] = win;
                    }
                }
            }
            for (int i : res){
                if (i == 0){
                    writer.println("DRAW");
                }
                if (i == 1){
                    writer.println("SECOND");
                }
                if (i == 2){
                    writer.println("FIRST");
                }
            }
            writer.println();
            writer.flush();
        }
        writer.flush();
        writer.close();
    }
}
