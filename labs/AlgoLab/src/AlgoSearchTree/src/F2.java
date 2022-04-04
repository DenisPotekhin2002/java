import java.io.*;
//import java.util.Scanner;

public class F2 {

    //private static long maxLen;

    private static class Node {
        private long key;
        private long n;
        private long element;
        private long isReversed;
        private Node left;
        private Node right;

        public Node(long element) {
            this.element = element;
            this.key = (long)(Math.random() * Integer.MAX_VALUE);
            this.left = null;
            this.right = null;
            this.n = 1;
            this.isReversed = 0;
        }
    }

    public static Node[] split(Node node, double x){
        reverse(node);
        if (node == null){
            return new Node[]{null, null};
        }
        long nnl = 0;
        if (node.left != null){
            nnl = node.left.n;
        }
        if (nnl < x){
            Node[] p = split(node.right, x - nnl - 1);
            node.right = p[0];
            long nl = 0;
            long nr = 0;
            if (node.left != null){
                nl = node.left.n;
            }
            if (node.right != null){
                nr = node.right.n;
            }
            node.n = nl + nr + 1;
            return new Node[]{node, p[1]};
        } else {
            Node[] p = split(node.left, x);
            node.left = p[1];
            long nl = 0;
            long nr = 0;
            if (node.left != null){
                nl = node.left.n;
            }
            if (node.right != null){
                nr = node.right.n;
            }
            node.n = nl + nr + 1;
            return new Node[]{p[0], node};
        }
    }

    public static Node merge(Node A, Node B){
        reverse(A);
        reverse(B);
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
            if (A.left != null){
                nl = A.left.n;
            }
            if (A.right != null){
                nr = A.right.n;
            }
            A.n = nl + nr + 1;
            return A;
        } else {
            B.left = merge(A, B.left);
            long nl = 0;
            long nr = 0;
            if (B.left != null){
                nl = B.left.n;
            }
            if (B.right != null){
                nr = B.right.n;
            }
            B.n = nl + nr + 1;
            return B;
        }
    }

    public static Node add(Node A, long pos, long val){
        Node[] p = split(A, pos);
        return merge(merge(p[0], new Node(val)), p[1]);
    }

    public static Node remove(Node A, double x){
        Node[] p1 = split(A, x);
        Node[] p2 = split(p1[1], 1);
        return merge(p1[0], p2[1]);
    }

    public static void reverse (Node A){
        if (A == null){
            return;
        }
        if (A.isReversed == 1){
            Node temp = A.right;
            A.right = A.left;
            A.left = temp;
            if (A.right != null) {
                A.right.isReversed = 1 - A.right.isReversed;
            }
            if (A.left != null) {
                A.left.isReversed = 1 - A.left.isReversed;
            }
            A.isReversed = 0;
        }
        //reverse(A.left);
        //reverse(A.right);
    }

    private static int count;
    private static Node root;
    private static PrintWriter writer;

    public static void print(Node A, long n, long isRev){
        if (A == null) {
            return;
        }
        if (isRev == 0) {
            if (A.left != null) {
                print(A.left, n, A.left.isReversed);
            }
            writer.print(A.element);
            count++;
            if (count < n) {
                writer.print(" ");
            }
            if (A.right != null) {
                print(A.right, n, A.right.isReversed);
            }
        } else {
            if (A.right != null) {
                print(A.right, n, Math.abs(A.right.isReversed - 1));
            }
            writer.print(A.element);
            count++;
            if (count < n) {
                writer.print(" ");
            }
            if (A.left != null) {
                print(A.left, n, Math.abs(A.left.isReversed - 1));
            }
        }
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        writer = new PrintWriter(System.out);
        ScannerF2 sc = new ScannerF2(System.in, F2::check);
        root = null;
        long n = Long.parseLong(sc.nextElement());
        long len = Long.parseLong(sc.nextElement());
        for (long i = 0; i < n; i++){
            root = add(root, i, i + 1);
        }
        for (long i = 0; i < len; i++){
            long l = Long.parseLong(sc.nextElement());
            long r = Long.parseLong(sc.nextElement());
            Node[] p1 = split(root, l - 1);
            Node[] p2 = split(p1[1], r - l + 1);
            p2[0].isReversed = 1 - p2[0].isReversed;
            root = merge(merge(p1[0], p2[0]), p2[1]);
        }
        count = 0;
        print(root, n, 0);
        writer.close();
    }
}


class ScannerF2 {
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

    public ScannerF2(String str, Checker checker) throws IOException{		// scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public ScannerF2(InputStream inputStream, Checker checker) throws IOException{		// scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public ScannerF2(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException{		//scanner from file
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