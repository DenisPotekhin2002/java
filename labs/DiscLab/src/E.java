import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class E {

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        ScannerH sc = new ScannerH(System.in, E::check);
        int n = Integer.parseInt(sc.nextElement());
        int[] deg = new int[n];
        ArrayList<Integer>[] out = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            out[i] = new ArrayList<>();
        }
        for (int i = 0; i < n - 1; i++) {
            int f = Integer.parseInt(sc.nextElement()) - 1;
            int s = Integer.parseInt(sc.nextElement()) - 1;
            deg[f]++;
            deg[s]++;
            out[s].add(f);
            out[f].add(s);
        }
        TreeMap<Integer, Integer> map = new TreeMap<>();
        boolean[] used = new boolean[n];
        for (int v = 0; v < n; v++) {
            if (deg[v] == 1) {
                map.put(v, out[v].get(0));
            }
        }
        int count = 0;
        while (count < n - 2) {
            int x = map.firstEntry().getKey();
            System.out.print((map.remove(x) + 1) + " ");
            count++;
            used[x] = true;
            int y = 0;
            for (int i : out[x]){
                if (!used[i]){
                    y = i;
                    break;
                }
            }
            deg[y]--;
            if (!used[y] && deg[y] == 1) {
                int v = 0;
                for (int i : out[y]){
                    if (!used[i]){
                        v = i;
                        break;
                    }
                }
                map.put(y, v);
            }
        }
        /*
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (deg[i] == 1) {
                deg[i]--;
                count++;
                System.out.print((out[i].get(0) + 1) + " ");
                if (count == n - 2) {
                    break;
                }
                int j = out[i].get(0);
                deg[j]--;
                out[j].remove((Integer) i);
                while (deg[j] == 1 && j < i){
                    deg[j]--;
                    count++;
                    System.out.print((out[j].get(0) + 1) + " ");
                    if (count == n - 2) {
                        break;
                    }
                    j = out[j].get(0);
                    deg[j]--;
                    out[j].remove((Integer) i);
                }
                if (count == n - 2) {
                    break;
                }
            }
        }
         */
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