import java.io.*;
import java.util.ArrayList;

public class M {

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        ScannerM sc = new ScannerM(System.in, M::check);
        String str;
        String str1 = sc.nextElement();
        String str2 = sc.nextElement();
        str = str1 + str2 + "$";
//    cout << str << endl;
        int[] p = new int[str.length()];
        int[] coll = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            p[i] = 0;
            coll[i] = 0;
        }
        ArrayList<ArrayList<Integer>> count = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            count.add(new ArrayList<>());
        }
        for (int i = 0; i < str.length(); i++) {
            char curr = str.charAt(i);
            if (curr == '$') {
                count.get(0).add(i);
            } else {
                count.get(curr - 'a' + 1).add(i);
            }
        }
        int cl = 0;
        int counter = 0;
        for (int i = 0; i < 27; i++) {
            if (!count.get(i).isEmpty()) {
                for (int j : count.get(i)) {
                    p[counter] = j;
                    coll[p[counter]] = cl;
                    counter++;
                }
                cl++;
            }
        }
//    for (int i : coll){
//        cout << i << " ";
//    }
//    cout << endl;
//    for (int i : p){
//        cout << i << " ";
//    }
//    cout << endl;
//    cout << endl;
//        System.out.println("here " + (System.currentTimeMillis() - t));
//        t = System.currentTimeMillis();
        for (int k = 0; k < Math.log(str.length()) / Math.log(2) + 1; k++) {
            int[] coll2 = new int[str.length()];
            for (int i = 0; i < str.length(); i++) {
                coll2[i] = 0;
                p[i] = (int) (p[i] - Math.pow(2, k) + str.length()) % str.length();
            }
//        cout << "1: " << endl;
//        for (int i : p){
//            cout << i << " ";
//        }
//        cout << endl;
            //count.sort(p, c)
            count = new ArrayList<>();
            for (int i = 0; i < str.length(); i++) {
                count.add(new ArrayList<>());
            }
            for (int i = 0; i < str.length(); i++) {
                count.get(coll[p[i]]).add(p[i]);
            }
            cl = 0;
            counter = 0;
            for (int i = 0; i < str.length(); i++) {
                if (!count.get(i).isEmpty()) {
                    for (int j : count.get(i)) {
                        p[counter] = j;
                        counter++;
                    }
                    cl++;
                }
            }
            boolean check = true;
            coll2[p[0]] = 0;
            for (int i = 1; i < str.length(); i++) {
                if (coll[p[i]] == coll[p[i - 1]] && coll[(p[i] +
                        (int) Math.pow(2, k)) % str.length()] == coll[(p[i - 1] + (int) Math.pow(2, k)) % str.length()]) {
                    coll2[p[i]] = coll2[p[i - 1]];
                    check = false;
                } else {
                    coll2[p[i]] = coll2[p[i - 1]] + 1;
                }
            }
            coll = coll2;
            if (check) {
//                System.out.println("done");
                break;
            }
//        for (int i : coll){
//            cout << i << " ";
//        }
//        cout << endl;
//        for (int i : p){
//            cout << i << " ";
//        }
//        cout << endl;
//        cout << endl;
        }
//        System.out.println("here " + (System.currentTimeMillis() - t));
//        t = System.currentTimeMillis();
        int k = 0;
        int[] l = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            l[i] = 0;
        }
//        int[] p = new int[str.length()];
//        for (int ii = 0; ii < str.length(); ii++) {
//            p[c[ii]] = ii;
//        }
        for (int i = 0; i < str.length(); i++) {
            int x = coll[i];
            if (k > 0) {
                k--;
            }
            while (x > 0 && p[x - 1] + k < str.length() && p[x] + k < str.length() &&
                    str.charAt(p[x - 1] + k) == str.charAt(p[x] + k)) {
                k++;
            }
            if (x > 0) {
                l[x - 1] = k;
            }
        }
//        System.out.println(System.currentTimeMillis() - t);
//    for (int i = 1; i < str.length(); i++) {
//        cout << p[i] << " ";
//    }
//    cout << endl;
//    for (int i = 1; i < str.length() - 1; i++) {
//        cout << l[i] << " ";
//    }
//    cout << endl;
        int max = 0;
        int max_ind = 0;
        for (int i = 1; i < str.length() - 1; i++) {
            if (l[i] > max && ((p[i] < str1.length() && p[i + 1] >= str1.length()) || (p[i] >= str1.length() && p[i + 1] < str1.length()))) {
                int temp = str1.length() - Math.min(p[i], p[i + 1]);
                if (Math.min(l[i], temp) > max) {
                    max = Math.min(l[i], temp);
                    max_ind = p[i];
//                cout << "max: " << max << endl;
//                cout << "max_ind: " << max_ind << endl;
                }
            }
        }
        PrintWriter writer = new PrintWriter(System.out);
        writer.println(str.substring(max_ind, max_ind + max));
        writer.close();
    }
}


class ScannerM {
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

    public ScannerM(String str, Checker checker) throws IOException {        // scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerM(InputStream inputStream, Checker checker) throws IOException {        // scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerM(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException {        //scanner from file
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
