import java.io.*;
//import java.util.Arrays;

public class H {
    private static long[] a;
    private static long[] b;
    private static long[] c;

    public static void add(long l, long r, long v, int x, long lx, long rx){
        if (rx < l || lx > r){
            return;
        }
        if (lx >= l && rx <= r){
            c[x] += v;
            b[x] += v * (rx - lx + 1);
            return;
        }
        propagate(x);
        long m = (lx + rx) / 2;
        add(l, r, v, 2 * x + 1, lx, m);
        add(l, r, v, 2 * x + 2, m + 1, rx);
        if (a[x] == 0) {
            b[x] = b[2 * x + 1] + b[2 * x + 2] + c[x] * (rx - lx + 1);
        } else {
            b[x] = (Math.max(0, a[x]) + c[x]) * (rx - lx + 1);
        }
    }

    public static void set(long l, long r, long v, int x, long lx, long rx){
        if (rx < l || lx > r){
            return;
        }
        propagate(x);
        if (lx >= l && rx <= r){
            if (v == 0){
                a[x] = -1;
                b[x] = 0;
            } else {
                a[x] = v;
                b[x] = v * (rx - lx + 1);
            }
            c[x] = 0;
            return;
        }
        long m = (lx + rx) / 2;
        set(l, r, v, 2 * x + 1, lx, m);
        set(l, r, v, 2 * x + 2, m + 1, rx);
        if (a[x] == 0) {
            b[x] = b[2 * x + 1] + b[2 * x + 2] + c[x] * (rx - lx + 1);
        } else {
            b[x] = (Math.max(0, a[x]) + c[x]) * (rx - lx + 1);
        }
    }

    private static void propagate(int x) {
        if (2 * x + 1 >= a.length){
            return;
        }
        long temp = x;
        long temp2 = x;
        while (2 * temp + 2 < a.length){
            temp = 2 * temp + 2;
            temp2 = 2 * temp2 + 1;
        }
        long len = temp - temp2 + 1;
        if (a[x] != 0){
            a[2 * x + 1] = a[x];
            b[2 * x + 1] = Math.max(0, a[x]) * len/2;
            c[2 * x + 1] = 0;
            a[2 * x + 2] = a[x];
            b[2 * x + 2] = Math.max(0, a[x]) * len/2;
            c[2 * x + 2] = 0;
            a[x] = 0;
        }
        if (c[x] != 0){
            c[2 * x + 1] += c[x];
            b[2 * x + 1] += c[x] * len/2;
            c[2 * x + 2] += c[x];
            b[2 * x + 2] += c[x] * len/2;
            c[x] = 0;
        }
    }

    public static long sum(int n, long lx, long rx, long l, long r){
        if (rx < l || lx > r){
            return -2;
        }
        if (lx >= l && rx <= r){
            return /*Math.max(0,*/ b[n]/*)*/ /*+ c[n] * (rx - lx + 1)*/;
        }
        propagate(n);
        long m = (lx + rx) / 2;
        long s1 = sum(2 * n + 1, lx, m, l, r);
        long s2 = sum(2 * n + 2, m + 1, rx, l, r);
        long temp = n;
        long temp2 = n;
        while (2 * temp + 2 < a.length){
            temp = 2 * temp + 2;
            temp2 = 2 * temp2 + 1;
        }
        temp = Math.min(temp, r);
        temp2 = Math.max(temp2, l);
        long len = temp - temp2 + 1;
        //int len = rx - lx + 1;
        if (a[n] == -1){
            return c[n] * len;
        }
        if (a[n] > 0){
            return a[n] * len + c[n] * len;
        }
        if (s1 == -2){
            return /*Math.max(0,*/ s2/*)*/ + c[n] * len;
        }
        if (s2 == -2){
            return /*Math.max(0,*/ s1/*)*/ + c[n] * len;
        }
        return /*Math.max(0,*/ s1 + s2/*)*/ + c[n] * len;
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        ScannerM sc = new ScannerM(System.in, H::check);
        int n = Integer.parseInt(sc.nextElement());
        int m = Integer.parseInt(sc.nextElement());
        int len = (int)Math.pow(2, (int)(Math.log(n * 2 - 1)/Math.log(2)));
        a = new long[2 * len - 1];
        b = new long[2 * len - 1];
        c = new long[2 * len - 1];
        //Arrays.fill(a, 0);
        //Arrays.fill(b, 0);
        //Arrays.fill(c, 0);
        for (int i = 0; i < m; i++){
            int t = Integer.parseInt(sc.nextElement());
            if (t == 1){
                int t1 = Integer.parseInt(sc.nextElement());
                int t2 = Integer.parseInt(sc.nextElement());
                int t3 = Integer.parseInt(sc.nextElement());
                set(t1, t2 - 1, t3,0, 0, len - 1);
            } else if (t == 2){
                int t1 = Integer.parseInt(sc.nextElement());
                int t2 = Integer.parseInt(sc.nextElement());
                int t3 = Integer.parseInt(sc.nextElement());
                add(t1, t2 - 1, t3,0, 0, len - 1);
            } else {
                int t1 = Integer.parseInt(sc.nextElement());
                int t2 = Integer.parseInt(sc.nextElement());
                System.out.println(sum(0, 0, len - 1, t1, t2 - 1));
            }
        }
    }
}


class ScannerThis {
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

    public ScannerThis(String str, Checker checker) throws IOException{		// scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public ScannerThis(InputStream inputStream, Checker checker) throws IOException{		// scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public ScannerThis(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException{		//scanner from file
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
