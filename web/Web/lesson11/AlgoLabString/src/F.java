import java.io.*;

public class F {

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static long[] hash(String str, int i) {
        long[] res = new long[str.length() - i + 1];
        int p = 31;
        long div = Long.MAX_VALUE;
        long[] p_pow = new long[i + 1];
        p_pow[0] = 1;
        for (int ii = 1; ii <= i; ii++) {
            p_pow[ii] = p_pow[ii - 1] * p % div;
        }
        res[0] = str.charAt(0) - 'a' + 1;
        for (int j = 1; j < i; j++) {
            long temp = res[0];
            for (int k = 1; k < p; k++) {
                res[0] = (res[0] + temp) % div;
            }
            res[0] = (res[0] + (str.charAt(j) - 'a' + 1)) % div;
        }
        for (int j = 1; j <= str.length() - i; j++) {
            long temp = res[j - 1];
            for (int k = 1; k < p; k++) {
                temp = (res[j - 1] + temp) % div;
            }
            res[j] = (temp + (str.charAt(j + i - 1) - 'a' + 1) - p_pow[i] * (str.charAt(j - 1) - 'a' + 1)) % div;
        }
        return res;
    }

    public static String bin(String[] arr, int min) {
        int l = 0;
        int r = min + 1;
        String ans = "";
        while (r - l > 1) {
            int m = (l + r) / 2;
            long[][] hashes = new long[arr.length][];
            for (int i = 0; i < arr.length; i++) {
                hashes[i] = hash(arr[i], m);
            }
            boolean check_check = false;
            for (int lol = 0; lol < hashes[0].length; lol++) {
                long lo = hashes[0][lol];
                for (int j = 1; j < arr.length; j++) {
                    boolean check = false;
                    for (long h : hashes[j]) {
                        if (h == lo) {
                            check = true;
                            break;
                        }
                    }
                    if (!check){
                        break;
                    }
                    if (j == arr.length - 1) {
                        l = m;
                        ans = arr[0].substring(lol, lol + l);
                        check_check = true;
                        break;
                    }
                }
                if (check_check){
                    break;
                }
            }
            if (!check_check) {
                r = m;
            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        ScannerF sc = new ScannerF(System.in, F::check);
        int n = Integer.parseInt(sc.nextElement());
        String[] arr = new String[n];
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextElement();
            if (arr[i].length() < min) {
                min = arr[i].length();
            }
        }
        System.out.println(bin(arr, min));
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
            str.append(last);
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
            str.append(last);
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
