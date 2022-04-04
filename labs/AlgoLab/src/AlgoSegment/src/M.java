import java.io.*;
import java.util.Arrays;

public class M {
    private static long[] a;
    private static long[][] aa;
    private static long[] b;
    private static long[][] bb;
    private static long[] cx;
    private static long[][] ccx;
    private static long[] cy;
    private static long[][] ccy;
    public static int len;

    public static void add(long[] at, long[] bt, long [] cxt, long [] cyt, int l, int r, int v, int x, int lx, int rx){
        if (rx < l || lx > r){
            return;
        }
        if (lx >= l && rx <= r){
            at[x] += v;
            bt[x] += v;
            return;
        }
        propagate(x, at, bt);
        int m = (lx + rx) / 2;
        add(at, bt, cxt, cyt, l, r, v, 2 * x + 1, lx, m);
        add(at, bt, cxt, cyt, l, r, v, 2 * x + 2, m + 1, rx);
        bt[x] = Math.max(bt[2 * x + 1], bt[2 * x + 2]) + at[x];
    }

    public static void addOuter(int lo, int ro, int li, int ri, int v, int x, int lx, int rx){
        if (rx < lo || lx > ro){
            return;
        }
        if (lx >= lo && rx <= ro){
            add(aa[x], bb[x], ccx[x], ccy[x], li, ri, v, 0, 0, len - 1);
            a[x] += v;
            b[x] = bb[x][0];
            return;
        }
        propagate(x, a, b);
        int m = (lx + rx) / 2;
        addOuter(lo, ro, li, ri, v, 2 * x + 1, lx, m);
        addOuter(lo, ro, li, ri, v, 2 * x + 2, m + 1, rx);
        b[x] = Math.max(b[2 * x + 1], b[2 * x + 2]) + a[x];
    }


    private static void propagate(int x, long[] ate, long[] bte) {
        if (ate[x] != 0){
            ate[2 * x + 1] += ate[x];
            bte[2 * x + 1] += ate[x];
            ate[2 * x + 2] += ate[x];
            bte[2 * x + 2] += ate[x];
            ate[x] = 0;
            bte[x] = Math.max(bte[2 * x + 1], bte[2 * x + 2]);
        }
    }

    public static long max(int n, int lx, int rx, int l, int r){
        if (rx < l || lx > r){
            return -1;
        }
        if (lx >= l && rx <= r){
            return b[n];
        }
        int m = (lx + rx) / 2;
        long s1 = max(2 * n + 1, lx, m, l, r);
        long s2 = max(2 * n + 2, m + 1, rx, l, r);
        if (s1 == -1){
            return s2 + a[n];
        }
        if (s2 == -1){
            return s1 + a[n];
        }
        return Math.max(s1, s2) + a[n];
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        ScannerM sc = new ScannerM(System.in, M::check);
        int n = Integer.parseInt(sc.nextElement());
        int k = 5;
        len = (int)Math.pow(2, (int)(Math.log(k * 2 - 1)/Math.log(2)));
        a = new long[2 * len - 1];
        b = new long[2 * len - 1];
        aa = new long[2 * len - 1][2 * len - 1];
        bb = new long[2 * len - 1][2 * len - 1];
        Arrays.fill(a, 0);
        Arrays.fill(b, 0);
        for (int i = 0; i < n; i++){
                int t1 = Integer.parseInt(sc.nextElement());
                int t2 = Integer.parseInt(sc.nextElement());
                int t3 = Integer.parseInt(sc.nextElement());
                int t4 = Integer.parseInt(sc.nextElement());
                addOuter(t1, t3, t2, t4, 1, 0, 0, len - 1);
        }
        System.out.println(b[0]);
    }
}


class ScannerM {
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

    public ScannerM(String str, Checker checker) throws IOException{		// scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public ScannerM(InputStream inputStream, Checker checker) throws IOException{		// scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public ScannerM(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException{		//scanner from file
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
