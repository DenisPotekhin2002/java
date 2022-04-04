import java.io.*;
//import java.util.Scanner;

public class F {

    //private static long maxLen;

    private static class Node {
        private double value;
        private long key;
        private long element;
        private double min;
        private double max;
        private long n;
        private Node left;
        private Node right;
        private long h;

        public Node(double value, long element) {
            this.value = value;
            this.element = element;
            //this.key = (long)(Math.random() * Math.max(Integer.MAX_VALUE, maxLen + 10));
            //if (key > maxLen){
            //maxLen = key;
            //}
            this.key = (long)(Math.random() * 100000);
            this.left = null;
            this.right = null;
            this.n = 1;
            this.h = 1;
            this.min = value;
            this.max = value;
        }
    }

    public static Node[] split(Node node, double x){
        if (node == null){
            return new Node[]{null, null};
        }
        if (node.value < x){
            Node[] p = split(node.right, x);
            node.right = p[0];
            long nl = 0;
            long nr = 0;
            long hl = 0;
            long hr = 0;
            double minl = node.value;
            double maxr = node.value;
            if (node.left != null){
                nl = node.left.n;
                minl = node.left.min;
                hl = node.left.h;
            }
            if (node.right != null){
                nr = node.right.n;
                maxr = node.right.max;
                hr = node.right.h;
            }
            node.n = nl + nr + 1;
            node.min = minl;
            node.max = maxr;
            node.h = Math.max(hl, hr) + 1;
            return new Node[]{node, p[1]};
        } else {
            Node[] p = split(node.left, x);
            node.left = p[1];
            long nl = 0;
            long nr = 0;
            long hl = 0;
            long hr = 0;
            double minl = node.value;
            double maxr = node.value;
            if (node.left != null){
                nl = node.left.n;
                minl = node.left.min;
                hl = node.left.h;
            }
            if (node.right != null){
                nr = node.right.n;
                maxr = node.right.max;
                hr = node.right.h;
            }
            node.n = nl + nr + 1;
            node.min = minl;
            node.max = maxr;
            node.h = Math.max(hl, hr) + 1;
            return new Node[]{p[0], node};
        }
    }

    public static Node merge(Node A, Node B){
        if (A == null){
            return B;
        }
        if (B == null){
            return A;
        }
        if (A.key > B.key){
            A.right = merge(A.right, B);
            long nl = 0;
            long nr = 0;
            long hl = 0;
            long hr = 0;
            double minl = A.value;
            double maxr = A.value;
            if (A.left != null){
                nl = A.left.n;
                hl = A.left.h;
                minl = A.left.min;
            }
            if (A.right != null){
                nr = A.right.n;
                hr = A.right.h;
                maxr = A.right.max;
            }
            A.n = nl + nr + 1;
            A.h = Math.max(hl, hr) + 1;
            A.min = minl;
            A.max = maxr;
            return A;
        } else {
            B.left = merge(A, B.left);
            long nl = 0;
            long nr = 0;
            long hl = 0;
            long hr = 0;
            double minl = B.value;
            double maxr = B.value;
            if (B.left != null){
                nl = B.left.n;
                hl = B.left.h;
                minl = B.left.min;
            }
            if (B.right != null){
                nr = B.right.n;
                hr = B.right.h;
                maxr = B.right.max;
            }
            B.n = nl + nr + 1;
            B.h = Math.max(hl, hr) + 1;
            B.min = minl;
            B.max = maxr;
            return B;
        }
    }

    public static Node add(Node A, Node node){
        if (A == null){
            return node;
        }
        if (node.key > A.key){
            Node[] p = split(A, node.value);
            node.left = p[0];
            node.right = p[1];
            long nl = 0;
            long nr = 0;
            long hl = 0;
            long hr = 0;
            double minl = node.value;
            double maxr = node.value;
            if (node.left != null){
                nl = node.left.n;
                hl = node.left.h;
                minl = node.left.min;
            }
            if (node.right != null){
                nr = node.right.n;
                hr = node.right.h;
                maxr = node.right.max;
            }
            node.n = nl + nr + 1;
            node.h = Math.max(hl, hr) + 1;
            node.min = minl;
            node.max = maxr;
            return node;
        }
        if (node.value < A.value){
            A.left = add(A.left, node);
        } else {
            A.right = add(A.right, node);
        }
        /*A.n = A.n + 1;
        A.min = Math.min(A.min, node.value);
        A.max = Math.max(A.max, node.value);*/
        long nl = 0;
        long nr = 0;
        long hl = 0;
        long hr = 0;
        double minl = A.value;
        double maxr = A.value;
        if (A.left != null){
            nl = A.left.n;
            hl = A.left.h;
            minl = A.left.min;
        }
        if (A.right != null){
            nr = A.right.n;
            hr = A.right.h;
            maxr = A.right.max;
        }
        A.n = nl + nr + 1;
        A.h = Math.max(hl, hr) + 1;
        A.min = minl;
        A.max = maxr;
        return A;
    }

    public static Node remove(Node A, double x){
        if (A == null){
            return A;
        }
        if (A.value == x){
            return merge(A.left, A.right);
        }
        if (x < A.value){
            A.left = remove(A.left, x);
        } else {
            A.right = remove(A.right, x);
        }
        /*A.n = A.n - 1;*/
        long nl = 0;
        long nr = 0;
        long hl = 0;
        long hr = 0;
        double minl = A.value;
        double maxr = A.value;
        if (A.left != null){
            nl = A.left.n;
            hl = A.left.h;
            minl = A.left.min;
        }
        if (A.right != null){
            nr = A.right.n;
            hr = A.right.h;
            maxr = A.right.max;
        }
        A.n = nl + nr + 1;
        A.h = Math.max(hl, hr) + 1;
        A.min = minl;
        A.max = maxr;
        return A;
    }

    public static Node addAfter(Node A, long prev, long node, double left, double right){
        if (A == null){
            return new Node((double)Integer.MAX_VALUE/2, node);
        }
        if (A.left == null){
            if (prev == 0){
                A = add(A, new Node((left + A.value)/2, node));
            } else {
                if (A.right != null) {
                    A.right = addAfter(A.right, prev - 1, node, A.value, right);
                    A.n = A.n + 1;
                    long nl = 0;
                    long nr = 0;
                    long hl = 0;
                    long hr = 0;
                    double minl = A.value;
                    double maxr = A.value;
                    if (A.left != null){
                        nl = A.left.n;
                        hl = A.left.h;
                        minl = A.left.min;
                    }
                    if (A.right != null){
                        nr = A.right.n;
                        hr = A.right.h;
                        maxr = A.right.max;
                    }
                    A.n = nl + nr + 1;
                    A.h = Math.max(hl, hr) + 1;
                    A.min = minl;
                    A.max = maxr;
                } else {
                    A = add(A, new Node(A.value + (right - A.value)/2, node));
                }
            }
        } else if (A.left.n == prev){
            A = add(A, new Node((A.left.max + A.value)/2, node));
        } else if (A.left.n < prev){
            prev -= (A.left.n + 1);
            if (prev == 0){
                if (A.right != null){
                    A = add(A, new Node(A.value + (A.right.min - A.value) / 2, node));
                } else {
                    A = add(A, new Node(A.value + (right - A.value) / 2, node));
                }
            } else {
                A.right = addAfter(A.right, prev, node, A.value, right);
                A.n = A.n + 1;
                long nl = 0;
                long nr = 0;
                long hl = 0;
                long hr = 0;
                double minl = A.value;
                double maxr = A.value;
                if (A.left != null){
                    nl = A.left.n;
                    hl = A.left.h;
                    minl = A.left.min;
                }
                if (A.right != null){
                    nr = A.right.n;
                    hr = A.right.h;
                    maxr = A.right.max;
                }
                A.n = nl + nr + 1;
                A.h = Math.max(hl, hr) + 1;
                A.min = minl;
                A.max = maxr;
            }
        } else {
            A.left = addAfter(A.left, prev, node, left, A.value);
            A.n = A.n + 1;
            long nl = 0;
            long nr = 0;
            long hl = 0;
            long hr = 0;
            double minl = A.value;
            double maxr = A.value;
            if (A.left != null){
                nl = A.left.n;
                hl = A.left.h;
                minl = A.left.min;
            }
            if (A.right != null){
                nr = A.right.n;
                hr = A.right.h;
                maxr = A.right.max;
            }
            A.n = nl + nr + 1;
            A.h = Math.max(hl, hr) + 1;
            A.min = minl;
            A.max = maxr;
        }
        return A;
    }

    public static Node removeInd(Node A, long ind){
        if (A.left == null){
            if (ind == 1){
                A = remove(A, A.value);
            } else {
                A.right = removeInd(A.right, ind - 1);
                A.n = A.n - 1;
            }
        } else if (A.left.n == ind - 1){
            A = remove(A, A.value);
        } else if (A.left.n < ind - 1){
            ind -= (A.left.n + 1);
            A.right = removeInd(A.right, ind);
            //A.n = A.n - 1;
            long nl = 0;
            long nr = 0;
            long hl = 0;
            long hr = 0;
            double minl = A.value;
            double maxr = A.value;
            if (A.left != null){
                nl = A.left.n;
                hl = A.left.h;
                minl = A.left.min;
            }
            if (A.right != null){
                nr = A.right.n;
                hr = A.right.h;
                maxr = A.right.max;
            }
            A.n = nl + nr + 1;
            A.h = Math.max(hl, hr) + 1;
            A.min = minl;
            A.max = maxr;
        } else {
            A.left = removeInd(A.left, ind);
            //A.n--;
            long nl = 0;
            long nr = 0;
            long hl = 0;
            long hr = 0;
            double minl = A.value;
            double maxr = A.value;
            if (A.left != null){
                nl = A.left.n;
                hl = A.left.h;
                minl = A.left.min;
            }
            if (A.right != null){
                nr = A.right.n;
                hr = A.right.h;
                maxr = A.right.max;
            }
            A.n = nl + nr + 1;
            A.h = Math.max(hl, hr) + 1;
            A.min = minl;
            A.max = maxr;
        }
        return A;
    }

    private static int count;
    private static Node root;
    private static PrintWriter writer;

    public static void print(Node A, long n){
        if (A == null){
            return;
        }
        if (A.left != null){
            print(A.left, n);
        }
        writer.print(A.element);
        count++;
        if (count < n){
            writer.print(" ");
        }
        if (A.right != null){
            print(A.right, n);
        }
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        //maxLen = Integer.MAX_VALUE/8;
        writer = new PrintWriter(System.out);
        ScannerThis sc = new ScannerThis(System.in, F::check);
        root = null;
        long n = Long.parseLong(sc.nextElement());
        long len = Long.parseLong(sc.nextElement());
        for (long i = 1; i <= n; i++){
            long x = Long.parseLong(sc.nextElement());
            root = add(root, new Node(i * (double)Integer.MAX_VALUE/2/n, x));
        }
        for (long i = 0; i < len; i++){
            String t = sc.nextElement();
            long temp = Long.parseLong(sc.nextElement());
            switch (t) {
                case "add":
                    n++;
                    long temp2 = Long.parseLong(sc.nextElement());
                    root = addAfter(root, temp, temp2, 0, Integer.MAX_VALUE);
                    break;
                case "del":
                    n--;
                    root = removeInd(root, temp);
                    break;
            }
        }
        count = 0;
        writer.println(n);
        print(root, n);
        writer.close();
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

    public String readNextLine() throws IOException {
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