import java.io.*;
import java.util.Arrays;

public class J {
    private static int[] a; //max
    private static int[] b; //min
    private static int[] c; //n
    private static int[] d; //set

    public static void set(int i, int v, int x, int lx, int rx){
        propagate(x);
        if (rx - lx == 1){
            a[x] = v;
            b[x] = v;
            c[x] = 1;
        } else {
            int m = (lx + rx) / 2;
            if (i < m){
                set (i, v, 2 * x + 1, lx, m);
            } else {
                set(i, v, 2 * x + 2, m, rx);
            }
            a[x] = Math.max(a[2 * x + 1], a[2 * x + 2]);
            b[x] = Math.min(b[2 * x + 1], b[2 * x + 2]);
            c[x] = c[2 * x + 1] + c[2 * x + 2];
        }
    }

    public static int sum(int n, int lx, int rx, int l, int r, int x) {
        if (rx < l || lx > r){
            return 0;
        }
        propagate(n);
        if (lx >= l && rx <= r){
            if (b[n] > x) {
                return 0;
            }
            if (a[n] <= x) {
                a[n] = 0;
                b[n] = Integer.MAX_VALUE;
                int temp = c[n];
                c[n] = 0;
                d[n] = 0;
                return temp;
            }
        }
        int m = (lx + rx) / 2;
        int ans = sum(2 * n + 1, lx, m, l, r, x) + sum(2 * n + 2, m + 1, rx, l, r, x);
        c[n] = c[2 * n + 1] + c[2 * n + 2];
        a[n] = Math.max(a[2 * n + 1], a[2 * n + 2]);
        b[n] = Math.min(b[2 * n + 1], b[2 * n + 2]);
        return ans;
    }

    private static void propagate(int x) {
        if (2 * x + 1 >= a.length){
            return;
        }
        if (d[x] == 0){
            d[2 * x + 1] = 0;
            c[2 * x + 1] = 0;
            b[2 * x + 1] = Integer.MAX_VALUE;
            a[2 * x + 1] = 0;
            d[2 * x + 2] = 0;
            a[2 * x + 2] = 0;
            b[2 * x + 2] = Integer.MAX_VALUE;
            c[2 * x + 2] = 0;
            d[x] = -1;
        }
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        Scanner1 sc = new Scanner1(System.in, J::check);
        int n = Integer.parseInt(sc.nextElement());
        int m = Integer.parseInt(sc.nextElement());
        int len = (int)Math.pow(2, (int)(Math.log(n * 2 - 1)/Math.log(2)));
        a = new int[2 * len - 1];
        b = new int[2 * len - 1];
        c = new int[2 * len - 1];
        d = new int[2 * len - 1];
        Arrays.fill(b, Integer.MAX_VALUE);
        Arrays.fill(d, -1);
        for (int i = 0; i < m; i++){
            int t = Integer.parseInt(sc.nextElement());
            if (t == 1){
                int t1 = Integer.parseInt(sc.nextElement());
                int t2 = Integer.parseInt(sc.nextElement());
                set(t1, t2, 0, 0, len);
            } else {
                int t1 = Integer.parseInt(sc.nextElement());
                int t2 = Integer.parseInt(sc.nextElement());
                int t3 = Integer.parseInt(sc.nextElement());
                int res = sum(0, 0, len - 1, t1, t2 - 1, t3);
                System.out.println(res);
            }
        }
    }
}

class Scanner1 {
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

    public Scanner1(String str, Checker checker) throws IOException{		// scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public Scanner1(InputStream inputStream, Checker checker) throws IOException{		// scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public Scanner1(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException{		//scanner from file
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
