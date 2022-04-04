import java.io.*;
import java.util.*;

public class A {

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
//        long t = System.currentTimeMillis();
        StringBuilder str = new StringBuilder(sc.nextLine());
//        str.append("$");
        int[] p = new int[str.length()];
        int[] coll = new int[str.length()];
        ArrayList<ArrayList<Integer>> count = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            count.add(new ArrayList<>());
        }
        for (int i = 0; i < str.length(); i++) {
            char curr = str.charAt(i);
            count.get(curr - ' ' + 1).add(i);
        }
        int cl = 0;
        int counter = 0;
        for (int i = 0; i < 200; i++) {
            if (count.get(i).size() > 0) {
                for (int j : count.get(i)) {
                    p[counter] = j;
                    coll[p[counter]] = cl;
                    counter++;
                }
                cl++;
            }
        }
//        System.out.println("here " + (System.currentTimeMillis() - t));
//        t = System.currentTimeMillis();
        for (int k = 0; k < Math.log(str.length()) / Math.log(2) + 1; k++) {
            int[] coll2 = new int[str.length()];
            for (int i = 0; i < str.length(); i++) {
                p[i] = (int) ((p[i] - Math.pow(2, k)) % str.length());
                if (p[i] < 0) {
                    p[i] += str.length();
                }
            }
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
                if (count.get(i).size() > 0) {
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
        }
        int max = str.length();
        boolean isSec = false;
        int ind = 0;
        for (int i = 0; i < str.length(); i++){
            if (coll[i] == 0 && isSec){
                max = i - ind;
                break;
            } else if (coll[i] == 0){
                isSec = true;
                
            }
        }
        int per = str.length() / max;
//        System.out.println("here " + (System.currentTimeMillis() - t));
//        t = System.currentTimeMillis();
//        System.out.println(System.currentTimeMillis() - t);
        PrintWriter writer = new PrintWriter(System.out);
        int k = sc.nextInt();
        if (per * k - 1 < p.length) {
            writer.print(str.substring(p[per * k - 1]));
            writer.print(str.substring(0, p[per * k - 1]));
        } else {
            writer.print("IMPOSSIBLE");
        }
        writer.close();
    }
}

class ScannerA {
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

    public ScannerA(String str, Checker checker) throws IOException {        // scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerA(InputStream inputStream, Checker checker) throws IOException {        // scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerA(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException {        //scanner from file
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
