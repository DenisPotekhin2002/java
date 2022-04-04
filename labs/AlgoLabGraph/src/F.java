import java.io.*;
import java.util.*;

public class F {

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        ScannerF sc = new ScannerF(System.in, F::check);
        int n = Integer.parseInt(sc.nextElement());
        int m = Integer.parseInt(sc.nextElement());
        TreeMap<Integer, ArrayList<Long>> out = new TreeMap<>();
        for (int co = 0; co < m; co++) {
            int i = Integer.parseInt(sc.nextElement()) - 1;
            int j = Integer.parseInt(sc.nextElement()) - 1;
            int mat = Integer.parseInt(sc.nextElement());
            if (out.containsKey(i)) {
                out.get(i).add((long) mat * 2000000000 + j);
            } else {
                ArrayList<Long> al = new ArrayList<>();
                al.add((long) mat * 2000000000 + j);
                out.put(i, al);
            }
            if (out.containsKey(j)) {
                out.get(j).add((long) mat * 2000000000 + i);
            } else {
                ArrayList<Long> al = new ArrayList<>();
                al.add((long) mat * 2000000000 + i);
                out.put(j, al);
            }
        }
        for (int i = 0; i < n; i++) {
            if (!out.containsKey(i)) {
                out.put(i, new ArrayList<>());
            }
        }
        if (out.isEmpty()) {
            System.out.println(-1);
            return;
        }
        int a = Integer.parseInt(sc.nextElement()) - 1;
        int b = Integer.parseInt(sc.nextElement()) - 1;
        int c = Integer.parseInt(sc.nextElement()) - 1;
        TreeMap<Long, ArrayList<Integer>> w = new TreeMap<>();
        ArrayList<Integer> all = new ArrayList<>();
        all.add(a);
        w.put((long) 0, all);
        long[] ww = new long[n];
        ww[a] = 0;
        ArrayList<Integer> alr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i != a) {
                alr.add(i);
                ww[i] = (long)2e15;
            }
        }
        w.put((long) 2e15, alr);
        while (!w.isEmpty()) {
            Map.Entry<Long, ArrayList<Integer>> entry = w.firstEntry();
            long curr = entry.getKey();
            boolean[] used = new boolean[n];
            for (int i : entry.getValue()) {
                used[i] = true;
                for (long jj : out.get(i)) {
                    int j = (int) (jj % 2000000000);
                    int mat = (int) (jj / 2000000000);
                    if (!used[j] && curr + mat < ww[j]) {
                        w.get(ww[j]).remove((Integer) j);
                        if (w.get(ww[j]).isEmpty()) {
                            w.remove(ww[j]);
                        }
                        if (w.containsKey(curr + mat)) {
                            w.get(curr + mat).add(j);
                        } else {
                            ArrayList<Integer> aaa = new ArrayList<>();
                            aaa.add(j);
                            w.put(curr + mat, aaa);
                        }
                        ww[j] = curr + mat;
                    }
                }
            }
            w.remove(curr);
        }
        long res1 = ww[b];
        long res2 = ww[c];

        w = new TreeMap<>();
        all = new ArrayList<>();
        all.add(b);
        w.put((long) 0, all);
        ww = new long[n];
        ww[b] = 0;
        alr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i != b) {
                alr.add(i);
                ww[i] = (long)2e15;
            }
        }
        w.put((long) 2e15, alr);
        while (!w.isEmpty()) {
            Map.Entry<Long, ArrayList<Integer>> entry = w.firstEntry();
            long curr = entry.getKey();
            boolean[] used = new boolean[n];
            for (int i : entry.getValue()) {
                used[i] = true;
                for (long jj : out.get(i)) {
                    int j = (int) (jj % 2000000000);
                    long mat = (int) (jj / 2000000000);
                    if (!used[j] && curr + mat < ww[j]) {
                        w.get(ww[j]).remove((Integer) j);
                        if (w.get(ww[j]).isEmpty()) {
                            w.remove(ww[j]);
                        }
                        if (w.containsKey(curr + mat)) {
                            w.get(curr + mat).add(j);
                        } else {
                            ArrayList<Integer> aaa = new ArrayList<>();
                            aaa.add(j);
                            w.put(curr + mat, aaa);
                        }
                        ww[j] = curr + mat;
                    }
                }
            }
            w.remove(curr);
        }
        long res3 = ww[c];
        long sum = res1 + res2 + res3;
        long min = sum - Math.max(Math.max(res1, res2), res3);
        PrintWriter writer = new PrintWriter(System.out);
        if (min < 2e15) {
            writer.println((long) min);
        } else {
            writer.println(-1);
        }
        writer.close();
    }
}


class ScannerF {
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

    public ScannerF(String str, Checker checker) throws IOException {        // scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerF(InputStream inputStream, Checker checker) throws IOException {        // scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerF(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException {        //scanner from file
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
            if (longTemp > (long) 300000000) {
                longTemp -= ((long) 300000000 + 1) * 2;
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