import java.io.*;
import java.util.Arrays;

public class K {
    private static long[] a;
    private static long[] b;
    private static long[] c;
    private static long[] left;
    private static long[] right;

    public static void set(int l, int r, int v, int x, int lx, int rx){
        if (rx < l || lx > r){
            return;
        }
        if (lx >= l && rx <= r){
            if (v == 0) {
                a[x] = -1;
                c[x] = v;
            } else {
                a[x] = v;
                c[x] = v;
            }
            b[x] = v * (rx - lx + 1);
            left[x] = v;
            right[x] = v;
            return;
        }
        propagate(x);
        int m = (lx + rx) / 2;
        set(l, r, v, 2 * x + 1, lx, m);
        set(l, r, v, 2 * x + 2, m + 1, rx);
        b[x] = Math.max(0, b[2 * x + 1]) + Math.max(0, b[2 * x + 2])/* + a[x]*/;
        c[x] = c[2 * x + 1] + c[2 * x + 2];
        if (right[2 * x + 1] == 1 && left[2 * x + 2] == 1){
            c[x] -= 1;
        }
        left[x] = left[2 * x + 1];
        right[x] = right[2 * x + 2];
    }

    private static void propagate(int x) {
        int temp = x;
        int temp2 = x;
        while (2 * temp + 2 < a.length){
            temp = 2 * temp + 2;
            temp2 = 2 * temp2 + 1;
        }
        int len = temp - temp2 + 1;
        if (a[x] != 0){
            a[2 * x + 1] = a[x];
            c[2 * x + 1] = Math.max(0, a[x]);
            left[2 * x + 1] = a[x];
            right[2 * x + 1] = a[x];
            b[2 * x + 1] = Math.max(0, a[x]) * len/2;
            a[2 * x + 2] = a[x];
            c[2 * x + 2] = Math.max(0, a[x]);
            left[2 * x + 2] = a[x];
            right[2 * x + 2] = a[x];
            b[2 * x + 2] = Math.max(0, a[x]) * len/2;
            a[x] = 0;
        }
    }

    public static long sum(int n, int lx, int rx, int l, int r){
        if (rx < l || lx > r){
            return 0;
        }
        if (lx >= l && rx <= r){
            return Math.max(0, a[n]) * (rx - lx + 1);
        }
        int m = (lx + rx) / 2;
        long s1 = sum(2 * n + 1, lx, m, l, r);
        long s2 = sum(2 * n + 2, m + 1, rx, l, r);
        return s1 + s2;
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        ScannerAA sc = new ScannerAA(System.in, K::check);
        int n = Integer.parseInt(sc.nextElement());
        int len = (int)Math.pow(2, (int)(Math.log(1000000 * 2 - 1)/Math.log(2)));
        a = new long[2 * len - 1];
        b = new long[2 * len - 1];
        c = new long[2 * len - 1];
        left = new long[2 * len - 1];
        right = new long[2 * len - 1];
        Arrays.fill(a, /*-1*/0);
        for (int i = 1; i <= n; i++){
            String t = sc.nextElement();
            int t1 = Integer.parseInt(sc.nextElement());
            int t2 = Integer.parseInt(sc.nextElement());
            if (t.equals("w")){
                set(500000 + t1, 500000 + t1 + t2 - 1, 0, 0, 0, len - 1);
            } else {
                set(500000 + t1, 500000 + t1 + t2 - 1, 1,0, 0, len - 1);
            }
            //System.out.println(sum(0, 0, len - 1, 0, n - 1));
            System.out.println(c[0] + " " + b[0]);
        }
    }
}


class ScannerAA {
    private Reader reader;
    private char last = '0';
    private char lastPrev = '0';

    private final Checker checker;

    public boolean check(char c){ 			//checks if symbol is "good"
        return checker.check(c);
    }

    public interface Checker {
        boolean check(char c);
    }

    public boolean checkLine(Reader reader) throws IOException {  		//checks if symbol at position j in string s is a line separator
        boolean bool = (last == '\r' || (last == '\n' && (lastPrev != '\r')));
        return bool;
    }

    public ScannerAA(String str, Checker checker) throws IOException{		// scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public ScannerAA(InputStream inputStream, Checker checker) throws IOException{		// scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public ScannerAA(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException{		//scanner from file
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf8"));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public boolean hasNext() throws IOException{ 					// checks if there is next element
        boolean b = false;
        while (last != (char)-1) {
            if (check(last) || checkLine(reader)){
                b = true;
                break;
            }
            lastPrev = last;
            last = (char)reader.read();
        }
        if (b == false){
            reader.close();
        }
        return b;
    }

    public boolean hasNextInLine() throws IOException{ 					// checks if there is next element
        boolean b = false;
        while (last != (char)-1 && !checkLine(reader)) {
            //System.err.println(last + " " + lastPrev + " " + checkLine(reader) + " " + check(last));
            //System.err.println(last == '\r');
            //System.err.println(last == '\n');
            if (check(last) /*|| checkLine(reader)*/){
                b = true;
                break;
            } else {
                lastPrev = last;
                last = (char)reader.read();
            }
        }
        return b;
    }

    public String nextElement() throws IOException{
        StringBuilder str = new StringBuilder();
        //last = (char)reader.read();
        //System.err.println(last);
        while (last != (char)-1 && !check(last)){
            //System.err.println(last);
            lastPrev = last;
            last = (char)reader.read();
        }
        while (last != (char)-1 && check(last)){
            //System.err.println(last);
            str.append(Character.toLowerCase(last));
            lastPrev = last;
            last = (char)reader.read();
        }
        //System.err.println(str.length() + " len");
        if (str.length() == 0){
            str.append(System.lineSeparator());
        }
        String res = "";
        if (str.length() > 2 && str.charAt(0) == '0' && str.charAt(1) == 'x'){
            String strTemp = str.substring(2, str.length());
            //System.err.println(strTemp);
            long longTemp = Long.parseLong(strTemp, 16);
            //System.err.println(longTemp);
            if (longTemp > (long) Integer.MAX_VALUE){
                longTemp -= ((long) Integer.MAX_VALUE + 1) * 2;
            }
            res = Long.toString(longTemp);
        } else {
            res = str.toString();
        }
        //System.err.println("res= " + res);
        return res;
    }

    public void nextLine() throws IOException{
        if (checkLine(reader)){
            lastPrev = last;
            last = (char)reader.read();
            //System.err.println(lastPrev + " " + last);
        }
    }

    public String readNextLine() throws IOException{
        StringBuilder str = new StringBuilder();
        while (last != (char)-1 && !checkLine(reader)){
            //System.err.println(last);
            str.append(Character.toLowerCase(last));
            lastPrev = last;
            last = (char)reader.read();
        }
        lastPrev = last;
        last = (char)reader.read();
        //System.err.println(str.length() + " len");
        if (str.length() == 0){
            str.append(System.lineSeparator());
        }
        return str.toString();
    }
}
