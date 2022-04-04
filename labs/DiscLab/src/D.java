import java.io.*;
import java.util.ArrayDeque;
import java.util.Scanner;

public class D {

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    /*public static int search(int x, int[] a){
        int l = 0;
        int r = a.length - 1;
        while (r - l > 1){
            int m = (l + r) / 2;
            System.out.println(1 + " " + a[m] + " " + x);
            Scanner sc = new Scanner(System.in);
            String next = sc.next();
            if (a[m] < x){
                l = m;
            } else {
                r = m;
            }
        }
        return r;
    }*/

    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter(System.out);
        ScannerD sc = new ScannerD(System.in, D::check);
        int n = Integer.parseInt(sc.nextElement());
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        int i = 1;
        queue.addLast(0);
        int count = 0;
        while (queue.size() < n) {
            ArrayDeque<Integer> queue2 = new ArrayDeque<>();
            boolean pop = true;
            /*int f;
            int l;
            if (!queue.isEmpty()) {
                f = queue.getFirst();
                l = queue.getLast();
            }*/
            double block = queue.size();
            int ss = queue.size();
            /*int[] arr = new int[ss];
            int ind = 0;
            while (!queue.isEmpty()){
                arr[ind] = queue.removeFirst();
                ind++;
            }
            */
            while (queue2.size() + queue.size() != ss + 1) {
                int si = queue.size();
                int si2 = queue2.size();
                block = block / 2;
                if (pop) {
                    for (int hf = 0; hf < Math.min(block, si); hf++) {
//                    while (queue.size() > (si + 1) / 2) {
                        queue2.addLast(queue.removeFirst());
                    }
                } else {
                    for (int hf = 0; hf < Math.min(block, si2); hf++) {
//                    while (queue2.size() > si2 / 2) {
                        queue.addFirst(queue2.removeLast());
                    }
                }
                int i1;
                if (queue.size() < queue2.size()) {
                    queue.addFirst(queue2.removeLast());
                }
                i1 = queue.removeFirst();
                writer.println(1 + " " + (i1 + 1) + " " + (i + 1));
                writer.flush();
                count++;
                if (count > 9900) {
                    throw new CharConversionException();
                }
                String next = sc.nextElement();
                if (next.equals("no")) {
                    if (queue2.size() > 0) {
                        int ii = queue2.getLast();
                        PrintWriter outwriter2 = new PrintWriter(System.out);
                        writer.println(1 + " " + (ii + 1) + " " + (i + 1));
                        writer.flush();
                        count++;
                        if (count > 9900) {
                            throw new CharConversionException();
                        }
                        String next2 = sc.nextElement();
                        queue.addFirst(i1);
                        if (next2.equals("yes")) {
                            queue.addFirst(i);
                            while (!queue2.isEmpty()) {
                                queue.addFirst(queue2.removeLast());
                            }
                            break;
                        } else {
                            pop = false;
                        }
                    } else {
                        queue.addFirst(i1);
                        queue.addFirst(i);
                        break;
                    }
                } else {
                    pop = true;
                    queue2.addLast(i1);
                    if (queue.isEmpty()) {
                        queue2.addLast(i);
                    }
                }
            }
            if (queue.isEmpty()) {
//                queue.addFirst(i);
                while (!queue2.isEmpty()) {
                    queue.addFirst(queue2.removeLast());
                }
            }
            i++;
        }
        writer.print(0 + " ");
        for (int h : queue) {
            writer.print(h + 1);
            writer.print(" ");
        }
        writer.close();
    }
}


class ScannerD {
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

    public ScannerD(String str, Checker checker) throws IOException {        // scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerD(InputStream inputStream, Checker checker) throws IOException {        // scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char) reader.read();
    }

    public ScannerD(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException {        //scanner from file
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