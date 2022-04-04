import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class G {
    private static ArrayList<ArrayList<Integer>> out;
    private static ArrayList<ArrayList<Integer>> out2;
    private static boolean[] used;
    private int n;
    private static ArrayList<Integer> order;
    private static ArrayList<Integer> component;

    private static void dfs1 (int v) {
        used[v] = true;
        for (int i : out.get(v)) {
            if (!used[i]) {
                dfs1(i);
            }
        }
        order.add(v);
    }

    private static void dfs2 (int v) {
        used[v] = true;
        component.add(v);
        for (int i : out2.get(v)) {
            if (!used[i]) {
                dfs2(i);
            }
        }
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        ScannerI sc = new ScannerI(System.in, G::check);
        int n = Integer.parseInt(sc.nextElement());
        int m = Integer.parseInt(sc.nextElement());
        out = new ArrayList<>();
        out2 = new ArrayList<>();
        order = new ArrayList<>();
        component = new ArrayList<>();
        TreeMap<String, Integer> map = new TreeMap<>();
        TreeMap<Integer, String> map2 = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            String name = sc.nextElement();
            map.put(name, i);
            map2.put(i, name);
            out.add(new ArrayList<>());
            out2.add(new ArrayList<>());
        }
        for (int i = n; i < 2 * n; i++) {
            out.add(new ArrayList<>());
            out2.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            String from_s = sc.nextElement();
            String to_s = sc.nextElement();
            int from;
            int to;
            if (from_s.charAt(0) == '+') {
                from = map.get(from_s.substring(1));
            } else {
                from = 2 * n - 1 - map.get(from_s.substring(1));
            }
            if (to_s.charAt(0) == '+') {
                to = map.get(to_s.substring(1));
            } else {
                to = 2 * n - 1 - map.get(to_s.substring(1));
            }
            if (!out.get(from).contains(to)) {
                out.get(from).add(to);
                out.get(2 * n - 1 - to).add(2 * n - 1 - from);
                out2.get(to).add(from);
                out2.get(2 * n - 1 - from).add(2 * n - 1 - to);
            }
        }
        used = new boolean[2 * n];
        for (int i = 0; i < 2 * n; ++i) {
            if (!used[i]) {
                dfs1(i);
            }
        }
        used = new boolean[2 * n];
        int[] comp = new int[2 * n];
        int curr_comp = 0;
        for (int i = 0; i < 2 * n; ++i) {
            int v = order.get(2 * n - 1 - i);
            if (!used[v]) {
                dfs2 (v);
                for (int ii : component){
                    comp[ii] = curr_comp;
                }
                component.clear();
                curr_comp++;
            }
        }
        for (int i = 0; i < n; i++){
            if (comp[i] == comp[2 * n - 1 - i]){
                System.out.println(-1);
                return;
            }
        }
        StringBuilder ans = new StringBuilder();
        int count = 0;
        for (int i = 0; i < n; i++){
            if (comp[i] > comp[2 * n - 1 - i]){
                count++;
                ans.append(map2.get(i)).append("\n");
            }
        }
        System.out.println(count);
        System.out.println(ans);
    }
}


class ScannerG {
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

    public ScannerG(String str, Checker checker) throws IOException {        // scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerG(InputStream inputStream, Checker checker) throws IOException {        // scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerG(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException {        //scanner from file
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