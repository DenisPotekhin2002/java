import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Gen {
    public static void main(String[] args) throws IOException {
        int iii = 0;
        while (true) {
            iii++;
            try {
                System.out.println(iii);
                try (PrintWriter writer = new PrintWriter(new File("in.txt"))) {
                    int n = (int) (Math.random() * 5) + 6;
                    while (n == 0) {
                        n = (int) (Math.random() * 5) + 6;
                    }
                    writer.println(n);
                    writer.println();
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < i; j++) {
                            int cur = (int) (Math.random() * 2);
                            while (cur == 2) {
                                cur = (int) (Math.random() * 2);
                            }
                            writer.print(cur);
                        }
                        writer.println();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                PrintWriter writer = new PrintWriter(new File("out.txt"));
                ScannerD sc = new ScannerD(new File("in.txt"), D::check);
                int n = Integer.parseInt(sc.nextElement());
                int[][] mat = new int[n][n];
                for (int i = 1; i < n; i++) {
                    String str = sc.nextElement();
                    mat[i][i] = 0;
                    for (int j = 0; j < i; j++) {
                        mat[i][j] = str.charAt(j) - 48;
                        mat[j][i] = 1 - mat[i][j];
                    }
                }
                if (n == 1) {
                    writer.print(1);
                    writer.close();
                    continue;
                }
                if (n == 2 && mat[0][1] == 1) {
                    writer.print(1 + " " + 2);
                    writer.close();
                    continue;
                }
                if (n == 2 && mat[0][1] == 0) {
                    writer.print(2 + " " + 1);
                    writer.close();
                    continue;
                } else {
                    ArrayDeque<Integer> queue = new ArrayDeque<>();
            /*int n1 = 0;
            int n2 = 0;
            int n3 = 0;
            boolean check = false;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        if (mat[i][j] == 1 && mat[j][k] == 1 && mat[k][i] == 1) {
                            n1 = i;
                            queue.addLast(i);
                            n2 = j;
                            queue.addLast(j);
                            n3 = k;
                            queue.addLast(k);
                            check = true;
                            break;
                        }
                    }
                    if (check) {
                        break;
                    }
                }
                if (check) {
                    break;
                }
            }*/
                    int i = 1;
                    queue.addLast(0);
//            int count = 0;
                    while (queue.size() < n) {
                /*while (i == n1 || i == n2 || i == n3) {
                    i++;
                }*/
                        ArrayDeque<Integer> queue2 = new ArrayDeque<>();
                        while (!queue.isEmpty()) {
                            int i1 = queue.removeFirst();
//                    System.out.println(1 + " " + (i1 + 1) + " " + (i + 1));
                    /*count++;
                    if (count > 9900){
                        throw new CharConversionException();
                    }*/
//                    String next = sc.nextElement();
//                    if (next.equals("yes")) {
                            if (mat[i1][i] == 0) {
                                queue.addFirst(i1);
                                queue.addFirst(i);
                                while (!queue2.isEmpty()) {
                                    queue.addFirst(queue2.removeLast());
                                }
                                break;
                            } else {
                                queue2.addLast(i1);
                            }
                        }
                        if (queue.isEmpty()) {
                            queue.addFirst(i);
                            while (!queue2.isEmpty()) {
                                queue.addFirst(queue2.removeLast());
                            }
                        }
                        i++;
                    }
                    ArrayList<Integer> cycle = new ArrayList<>();
                    int u1 = queue.removeFirst();
                    cycle.add(u1);
                    int u2 = queue.removeFirst();
                    cycle.add(u2);
                    int uk = queue.removeFirst();
                    boolean isempty = false;
                    while (mat[uk][cycle.get(0)] == 1) {
                        cycle.add(uk);
                        if (queue.isEmpty()) {
                            isempty = true;
                            break;
                        }
                        uk = queue.removeFirst();
                    }
                    while(cycle.size() < n) {
                        boolean check = true;
                        if (!isempty) {
                            check = false;
                        }
                        ArrayDeque<Integer> temp = new ArrayDeque<>();
                        while (!check) {
                            temp.addLast(uk);
                            for (int ui = 0; ui < cycle.size(); ui++) {
                                if (mat[uk][cycle.get(ui)] == 1 && mat[cycle.get((ui+cycle.size()-1)%(cycle.size()))][temp.getFirst()] == 1) {
                                    while (!temp.isEmpty()) {
                                        int ut = temp.pollLast();
                                        cycle.add(ui, ut);
                                    }
                                    check = true;
                                    break;
                                }
                            }
                            if (queue.isEmpty()){
                                break;
                            }
                            uk = queue.removeFirst();
                        }
                    }
                    for (int h : cycle) {
                        writer.print(h + 1);
                        writer.print(" ");
                    }
                    //}
                    writer.close();
                }
                Scanner scan = new Scanner(new File("out.txt"));
                int u1 = scan.nextInt();
                int uc = u1;
                while(scan.hasNext()){
                    int u2 = scan.nextInt();
                    if (mat[uc - 1][u2 - 1] == 0) {
                        System.out.println("Error " + u2);
                        return;
                    }
                    uc = u2;
                }
                if (mat[uc - 1][u1 - 1] == 0) {
                    System.out.println("Error");
                    return;
                }
            } catch (NoSuchElementException e){
                System.out.println("not strongly connected");
            }
        }
    }
}

