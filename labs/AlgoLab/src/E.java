import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeMap;

public class E {
    private static ArrayList<ArrayList<Integer>> out;
    private static TreeMap<Integer, Integer> mult = new TreeMap<>();
    private static boolean[] mark;
    private static int[] t_in;
    private static int[] up;
    private static int[] comp;
//    private static int[][] hash;
    private static int T;
    private static TreeMap<Long, Integer> edges;
    private static final ArrayList<Integer> points = new ArrayList<>();
    private static Stack<Long> buf = new Stack<>();
    private static int m;
    private static int curr_comp = 0;

    private static void dfs(int v, int p) {
        mark[v] = true;
        t_in[v] = T++;
        up[v] = t_in[v];
        boolean ok = false;
        int c = 0;
        for (int u : out.get(v)) {
            if (u == p) {
                continue;
            }
            if (comp[edges.get((long) Math.min(v, u) * 2000000 + (long) Math.max(v, u))] == 0) {
                if (p != -1) {
                    comp[edges.get((long) Math.min(v, u) * 2000000 + (long) Math.max(v, u))] = comp[edges.get((long) Math.min(v, p) * 2000000 + (long) Math.max(v, p))];
                } else {
                    curr_comp++;
                    comp[edges.get((long) Math.min(v, u) * 2000000 + (long) Math.max(v, u))] = curr_comp;
                }
            }
            if (!mark[u]) {
                buf.push((long) Math.min(v, u) * 2000000 + (long) Math.max(v, u));
                dfs(u, v);
                c++;
                up[v] = Math.min(up[v], up[u]);
                if (up[u] >= t_in[v] && p != -1 && !mult.containsKey(edges.get((long) Math.min(v, u) * 2000000 + (long) Math.max(v, u)))){
                    ok = true;
                    curr_comp++;
                    while (true){
                        long e = buf.pop();
                        comp[edges.get(e)] = curr_comp;
                        if (e == (long) Math.min(v, u) * 2000000 + (long) Math.max(v, u)){
                            break;
                        }
                    }
                }
            } else {
                up[v] = Math.min(up[v], t_in[u]);
                if (t_in[u] < t_in[v]){
                    buf.push((long) Math.min(v, u) * 2000000 + (long) Math.max(v, u));
                }
            }
        }
        if (ok || p == -1 && c > 1){
            points.add(v);
        }
    }

    private static void find_points(int n) {
        T = 0;
        for (int i = 0; i < n; ++i) {
            if (!mark[i]) {
                dfs(i, -1);
            }
        }
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        ScannerE sc = new ScannerE(System.in, E::check);
        int n = Integer.parseInt(sc.nextElement());
        m = Integer.parseInt(sc.nextElement());
        out = new ArrayList<>();
        mark = new boolean[n];
        t_in = new int[n];
        up = new int[n];
        comp = new int[m];
//        hash = new int[m][2];
        edges = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            out.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int from = Integer.parseInt(sc.nextElement()) - 1;
            int to = Integer.parseInt(sc.nextElement()) - 1;
            if (!out.get(from).contains(to)) {
                out.get(from).add(to);
                out.get(to).add(from);
//                hash[i][0] = Math.min(from, to);
//                hash[i][1] = Math.max(from, to);
                edges.put((long) Math.min(from, to) * 2000000 + (long) Math.max(from, to), i);
            } else {
                mult.put(i, edges.get((long) Math.min(from, to) * 2000000 + (long) Math.max(from, to)));
            }
        }
        find_points(n);
        System.out.println(curr_comp);
        for (int i = 0; i < m; i++){
            if (comp[i] != 0) {
                System.out.print(comp[i] + " ");
            } else {
                System.out.print(comp[mult.get(i)] + " ");
            }
        }
    }
}

class ScannerE {
    private Reader reader;
    private char last = '0';
    private char lastPrev = '0';

    private final Checker checker;

    public boolean check(char c) {            //checks if symbol is "good"
        return checker.check(c);
    }

    public interface Checker {
        boolean check(char c);
    }

    public boolean checkLine(Reader reader) throws IOException {        //checks if symbol at position j in string s is a line separator
        boolean bool = (last == '\r' || (last == '\n' && (lastPrev != '\r')));
        return bool;
    }

    public ScannerE(String str, Checker checker) throws IOException {        // scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerE(InputStream inputStream, Checker checker) throws IOException {        // scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerE(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException {        //scanner from file
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf8"));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public boolean hasNext() throws IOException {                    // checks if there is next element
        boolean b = false;
        while (last != (char) -1) {
            if (check(last) || checkLine(reader)) {
                b = true;
                break;
            }
            lastPrev = last;
            last = (char) reader.read();
        }
        if (b == false) {
            reader.close();
        }
        return b;
    }

    public boolean hasNextInLine() throws IOException {                    // checks if there is next element
        boolean b = false;
        while (last != (char) -1 && !checkLine(reader)) {
            //System.err.println(last + " " + lastPrev + " " + checkLine(reader) + " " + check(last));
            //System.err.println(last == '\r');
            //System.err.println(last == '\n');
            if (check(last) /*|| checkLine(reader)*/) {
                b = true;
                break;
            } else {
                lastPrev = last;
                last = (char) reader.read();
            }
        }
        return b;
    }

    public String nextElement() throws IOException {
        StringBuilder str = new StringBuilder();
        //last = (char)reader.read();
        //System.err.println(last);
        while (last != (char) -1 && !check(last)) {
            //System.err.println(last);
            lastPrev = last;
            last = (char) reader.read();
        }
        while (last != (char) -1 && check(last)) {
            //System.err.println(last);
            str.append(Character.toLowerCase(last));
            lastPrev = last;
            last = (char) reader.read();
        }
        //System.err.println(str.length() + " len");
        if (str.length() == 0) {
            str.append(System.lineSeparator());
        }
        String res = "";
        if (str.length() > 2 && str.charAt(0) == '0' && str.charAt(1) == 'x') {
            String strTemp = str.substring(2, str.length());
            //System.err.println(strTemp);
            long longTemp = Long.parseLong(strTemp, 16);
            //System.err.println(longTemp);
            if (longTemp > (long) Integer.MAX_VALUE) {
                longTemp -= ((long) Integer.MAX_VALUE + 1) * 2;
            }
            res = Long.toString(longTemp);
        } else {
            res = str.toString();
        }
        //System.err.println("res= " + res);
        return res;
    }

    public void nextLine() throws IOException {
        if (checkLine(reader)) {
            lastPrev = last;
            last = (char) reader.read();
            //System.err.println(lastPrev + " " + last);
        }
    }

    public String readNextLine() throws IOException {
        StringBuilder str = new StringBuilder();
        while (last != (char) -1 && !checkLine(reader)) {
            //System.err.println(last);
            str.append(Character.toLowerCase(last));
            lastPrev = last;
            last = (char) reader.read();
        }
        lastPrev = last;
        last = (char) reader.read();
        //System.err.println(str.length() + " len");
        if (str.length() == 0) {
            str.append(System.lineSeparator());
        }
        return str.toString();
    }
}