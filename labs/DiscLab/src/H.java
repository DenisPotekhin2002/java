import java.io.*;
import java.util.ArrayList;

public class H {

    private static ArrayList<Long> out;
    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static int[] chrom(ArrayList<Long> arr, int n){
        if (arr.isEmpty()){
            int[] ret = new int[n];
            if (n > 0) {
                ret[0] = 1;
            }
            return ret;
        }
        int u = (int)(arr.get(0) % 100);
        int v = (int)(arr.get(0) / 100);
        ArrayList<Long> arr1 = new ArrayList<>(arr);
        for (long i : arr){
            if (i % 100 == u || i % 100 == v || i / 100 == u || i / 100 == v){
                arr1.remove(i);
            }
        }
        int[] chrom1 = chrom(arr1, n);
        ArrayList<Long> arr2 = new ArrayList<>(arr);
        for (long i : arr){
            if (i % 100 == v){
                arr2.remove(i);
                if (i / 100 != u) {
                    int j = (int) (i / 100);
                    arr2.add((long) Math.min(u, j) * 100 + Math.max(u, j));
                }
            }
            if (i / 100 == v){
                arr2.remove(i);
                if (i % 100 != u) {
                    int j = (int) (i % 100);
                    arr2.add((long) Math.min(u, j) * 100 + Math.max(u, j));
                }
            }
        }
        int[] chrom2 = chrom(arr2, n - 1);
        int[] ret = new int[Math.max(chrom1.length, chrom2.length)];
        for (int i = 0; i < Math.min(chrom1.length, chrom2.length); i++){
            ret[i] = chrom1[i] - chrom2[i];
        }
        if (chrom1.length > chrom2.length) {
            for (int i = chrom2.length; i < ret.length; i++) {
                ret[i] = chrom1[i];
            }
        } else {
            for (int i = chrom1.length; i < ret.length; i++) {
                ret[i] = -chrom2[i];
            }
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter(System.out);
        ScannerH sc = new ScannerH(System.in, H::check);
        int n = Integer.parseInt(sc.nextElement());
        int m = Integer.parseInt(sc.nextElement());
        out = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int from = Integer.parseInt(sc.nextElement()) - 1;
            int to = Integer.parseInt(sc.nextElement()) - 1;
            long hash = Math.min(from, to) * 100 + Math.max(from,to);
            if (!out.contains(hash)) {
                out.add(hash);
            }
        }
        int[] res = chrom(out, n);
        writer.println(res.length);
        for (int i = res.length - 1; i >= 0; i--){
            writer.print(res[i] + " ");
        }
        writer.close();
    }
}


class ScannerH {
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

    public ScannerH(String str, Checker checker) throws IOException {        // scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerH(InputStream inputStream, Checker checker) throws IOException {        // scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerH(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException {        //scanner from file
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