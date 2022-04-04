import java.io.*;
import java.util.*;
//import java.util.Scanner;

public class D_2 {
    private static ArrayList<ArrayList<Integer>> out;
    private static ArrayList<Long> mult = new ArrayList<>();
    private static boolean[] mark;
    private static int[] t_in;
    private static int[] up;
    private static int[] comp;
    private static int T;
    private static int m;
    private static Stack<Integer> buf = new Stack<>();
    private static int curr_comp = 0;

    private static void dfs(int v, int p) {
        buf.add(v);
        mark[v] = true;
        t_in[v] = T++;
        up[v] = t_in[v];
        for (int to : out.get(v)) {
            if (to == p) {
                continue;
            }
            if (mark[to]) {
                up[v] = Math.min(up[v], t_in[to]);
            }
            else {
                comp[to] = comp[v];
                dfs(to, v);
                up[v] = Math.min(up[v], up[to]);
                if (up[to] > t_in[v] && !mult.contains((long) Math.min(v, to) * 2000000 + (long) Math.max(v, to))) {
                    while (true){
                        int x = buf.pop();
                        comp[x] = curr_comp;
                        if (x == to){
                            curr_comp++;
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void find_bridges(int n) {
        T = 0;
        for (int i = 0; i < n; ++i)
            mark[i] = false;
        for (int i = 0; i < n; ++i)
            if (!mark[i]) {
                comp[i] = curr_comp;
                curr_comp++;
                dfs(i, -1);
            }
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        ScannerI sc = new ScannerI(System.in, D_2::check);
        int n = Integer.parseInt(sc.nextElement());
        m = Integer.parseInt(sc.nextElement());
        out = new ArrayList<>();
        mark = new boolean[n];
        t_in = new int[n];
        up = new int[n];
        comp = new int[n];
        for (int i = 0; i < n; i++) {
            out.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int from = Integer.parseInt(sc.nextElement()) - 1;
            int to = Integer.parseInt(sc.nextElement()) - 1;
            if (!out.get(from).contains(to)) {
                out.get(from).add(to);
                out.get(to).add(from);
            } else {
                mult.add((long) Math.min(from, to) * 2000000 + (long) Math.max(from, to));
            }
        }
        find_bridges(n);
        HashSet<Long> cond = new HashSet<>();
        for (int i = 0; i < n; i++){
            for (int j : out.get(i)){
                if (comp[i] != comp[j]){
                    cond.add((long) Math.min(comp[i], comp[j]) * 2000000 + (long) Math.max(comp[i], comp[j]));
                }
            }
        }
        System.out.println(cond.size());
    }
}

class ScannerD_2 {
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

    public ScannerD_2(String str, Checker checker) throws IOException {        // scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerD_2(InputStream inputStream, Checker checker) throws IOException {        // scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerD_2(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException {        //scanner from file
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