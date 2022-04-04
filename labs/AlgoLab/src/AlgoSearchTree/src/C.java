import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;
//import java.util.Scanner;

public class C {

    //private static long maxLen;

    private static class Node {
        private long key;
        private long n;
        private long element;
        private Node left;
        private Node right;

        public Node(long element) {
            this.element = element;
            this.key = (long)(Math.random() * Integer.MAX_VALUE);
            this.left = null;
            this.right = null;
            this.n = 1;
        }
    }

    private static class OrdNode {
        private long value;
        private long h;
        private OrdNode left;
        private OrdNode right;

        public OrdNode(long value, OrdNode left, OrdNode right, OrdNode parent) {
            this.value = value;
            this.left = left;
            this.right = right;
            if (parent == null){
                root2 = this;
            } else {
                if (this.value > parent.value){
                    parent.right = this;
                } else {
                    parent.left = this;
                }
            }
            if (this != null) {
                rotate(parent);
            }
        }

        public void rotate(OrdNode parent){
            if (parent != null){
                if (this.value > parent.value){
                    parent.right = this;
                } else {
                    parent.left = this;
                }
            }
            long hl = 0;
            long hr = 0;
            if (this.left != null){
                hl = this.left.h;
            }
            if (this.right != null){
                hr = this.right.h;
            }
            if (hr - hl > 1){
                if (this.right.right != null && this.right.right.h > hl){
                    OrdNode a = this.left;
                    OrdNode b = this.right.left;
                    OrdNode c = this.right.right;
                    OrdNode x = this;
                    OrdNode y = this.right;
                    long h = 0;
                    if (a != null){
                        h = a.h;
                    }
                    if (parent == null){
                        root2 = y;
                    } else {
                        if (x.value > parent.value){
                            parent.right = y;
                        } else {
                            parent.left = y;
                        }
                    }
                    y.left = x;
                    x.right = b;
                    hl = 0;
                    hr = 0;
                    if (x.left != null){
                        hl = x.left.h;
                    }
                    if (x.right != null){
                        hr = x.right.h;
                    }
                    x.h = Math.max(hl, hr) + 1;
                    hl = 0;
                    hr = 0;
                    if (y.left != null){
                        hl = y.left.h;
                    }
                    if (y.right != null){
                        hr = y.right.h;
                    }
                    y.h = Math.max(hl, hr) + 1;
                    hl = 0;
                    hr = 0;
                } else {
                    OrdNode a = this.left;
                    OrdNode b = this.right.left.left;
                    OrdNode c = this.right.left.right;
                    OrdNode d = this.right.right;
                    OrdNode x = this;
                    OrdNode y = this.right;
                    OrdNode z = this.right.left;
                    long h = 0;
                    if (a != null){
                        h = a.h;
                    }
                    if (parent == null){
                        root2 = z;
                    } else {
                        if (x.value > parent.value){
                            parent.right = z;
                        } else {
                            parent.left = z;
                        }
                    }
                    z.left = x;
                    z.right = y;
                    x.right = b;
                    y.left = c;
                    hl = 0;
                    hr = 0;
                    if (x.left != null){
                        hl = x.left.h;
                    }
                    if (x.right != null){
                        hr = x.right.h;
                    }
                    x.h = Math.max(hl, hr) + 1;
                    hl = 0;
                    hr = 0;
                    if (y.left != null){
                        hl = y.left.h;
                    }
                    if (y.right != null){
                        hr = y.right.h;
                    }
                    y.h = Math.max(hl, hr) + 1;
                    hl = 0;
                    hr = 0;
                    if (z.left != null){
                        hl = z.left.h;
                    }
                    if (z.right != null){
                        hr = z.right.h;
                    }
                    z.h = Math.max(hl, hr) + 1;
                }
            } else if (hl - hr > 1){
                if (this.left.left != null && this.left.left.h > hr){
                    OrdNode a = this.right;
                    OrdNode b = this.left.right;
                    OrdNode c = this.left.left;
                    OrdNode x = this;
                    OrdNode y = this.left;
                    long h = 0;
                    if (a != null){
                        h = a.h;
                    }
                    if (parent == null){
                        root2 = y;
                    } else {
                        if (x.value > parent.value){
                            parent.right = y;
                        } else {
                            parent.left = y;
                        }
                    }
                    y.right = x;
                    x.left = b;
                    hl = 0;
                    hr = 0;
                    if (x.left != null){
                        hl = x.left.h;
                    }
                    if (x.right != null){
                        hr = x.right.h;
                    }
                    x.h = Math.max(hl, hr) + 1;
                    hl = 0;
                    hr = 0;
                    if (y.left != null){
                        hl = y.left.h;
                    }
                    if (y.right != null){
                        hr = y.right.h;
                    }
                    y.h = Math.max(hl, hr) + 1;
                    hl = 0;
                    hr = 0;
                } else {
                    OrdNode a = this.right;
                    OrdNode b = this.left.right.right;
                    OrdNode c = this.left.right.left;
                    OrdNode d = this.left.left;
                    OrdNode x = this;
                    OrdNode y = this.left;
                    OrdNode z = this.left.right;
                    long h = 0;
                    if (a != null){
                        h = a.h;
                    }
                    if (parent == null){
                        root2 = z;
                    } else {
                        if (x.value > parent.value){
                            parent.right = z;
                        } else {
                            parent.left = z;
                        }
                    }
                    z.right = x;
                    z.left = y;
                    x.left = b;
                    y.right = c;
                    hl = 0;
                    hr = 0;
                    if (x.left != null){
                        hl = x.left.h;
                    }
                    if (x.right != null){
                        hr = x.right.h;
                    }
                    x.h = Math.max(hl, hr) + 1;
                    hl = 0;
                    hr = 0;
                    if (y.left != null){
                        hl = y.left.h;
                    }
                    if (y.right != null){
                        hr = y.right.h;
                    }
                    y.h = Math.max(hl, hr) + 1;
                    hl = 0;
                    hr = 0;
                    if (z.left != null){
                        hl = z.left.h;
                    }
                    if (z.right != null){
                        hr = z.right.h;
                    }
                    z.h = Math.max(hl, hr) + 1;
                }
            }
            hl = 0;
            hr = 0;
            if (this.left != null){
                hl = this.left.h;
            }
            if (this.right != null){
                hr = this.right.h;
            }
            this.h = Math.max(hl, hr) + 1;
            if (parent != null) {
                hl = 0;
                hr = 0;
                if (parent.left != null) {
                    hl = parent.left.h;
                }
                if (parent.right != null) {
                    hr = parent.right.h;
                }
                parent.h = Math.max(hl, hr) + 1;
            }
        }
    }

    private static OrdNode root2;

    public static void add(long v, OrdNode n, OrdNode p){
        if (root2 == null){
            root2 = new OrdNode(v, null, null, null);
            return;
        }
        if (n == null){
            OrdNode w = new OrdNode(v, null, null, p);
            return;
        }
        if (v == n.value){
            return;
        }
        if (v > n.value){
            add(v, n.right, n);
        } else {
            add(v, n.left, n);
        }
        if (n != null) {
            n.rotate(p);
        }
    }

    public static boolean check(long v, OrdNode w){
        if (w == null){
            return false;
        }
        if (w.value == v){
            return true;
        }
        if (v > w.value){
            return check(v, w.right);
        } else {
            return check(v, w.left);
        }
    }

    public static long next(long v, OrdNode w, long temp){
        if (w == null){
            return temp;
        }
        if (v < w.value){
            return next(v, w.left, w.value);
        } else {
            return next(v, w.right, temp);
        }
    }

    public static long prev(long v, OrdNode w, long temp){
        if (w == null){
            return temp;
        }
        if (v > w.value){
            return prev(v, w.right, w.value);
        } else {
            return prev(v, w.left, temp);
        }
    }

    public static void delete(long v, OrdNode w, OrdNode p){
        if (w == null){
            return;
        }
        if (w.value == v){
            if (w.left == null){
                if (p == null){
                    root2 = w.right;
                    if (root2 != null) {
                        root2.rotate(null);
                    }
                } else {
                    if (w.value >= p.value) {
                        p.right = w.right;
                        if (p.right != null) {
                            p.right.rotate(p);
                        }
                    } else {
                        p.left = w.right;
                        if (p.left != null) {
                            p.left.rotate(p);
                        }
                    }
                }
                return;
            }
            if (w.right == null){
                if (p == null){
                    root2 = w.left;
                    if (root2 != null) {
                        root2.rotate(null);
                    }
                } else {
                    if (w.value >= p.value) {
                        p.right = w.left;
                        if (p.right != null) {
                            p.right.rotate(p);
                        }
                    } else {
                        p.left = w.left;
                        if (p.left != null) {
                            p.left.rotate(p);
                        }
                    }
                }
                return;
            }
            OrdNode temp = w.right;
            OrdNode parent = w;
            OrdNode parparent = p;
            long count = 0;
            if (temp.left != null){
                count = 1;
            }
            while (temp.left != null){
                parparent = parent;
                parent = temp;
                temp = temp.left;
            }
            if (count == 1) {
                parent.left = temp.right;
                if (parent.left != null) {
                    parent.left.rotate(parent);
                }
                if (parent != null && (parparent == null || parparent.left == parent || parparent.right == parent)){
                    parent.rotate(parparent);
                }
                w.value = temp.value;
                if (p == null){
                    root2 = w;
                    if (root2 != null) {
                        root2.rotate(null);
                    }
                }
            } else {
                w.value = temp.value;
                w.right = temp.right;
                if (w.right != null){
                    w.right.rotate(w);
                }
                if (p == null){
                    root2 = w;
                    if (root2 != null) {
                        root2.rotate(null);
                    }
                }
            }
            return;
        }
        if (v > w.value){
            delete(v, w.right, w);
        } else {
            delete(v, w.left, w);
        }
        if (w != null) {
            if (w.value == 7 && w.h == 4){
                int h = 0;
            }
            w.rotate(p);
        }
    }
    
    public static Node[] split(Node node, double x){
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

    private static int count;
    private static long max;
    private static Node root;
    private static PrintWriter writer;

    public static void print(Node A, long n){
        if (A.left != null) {
            print(A.left, n);
        }
        if (count < n) {
            writer.print(A.element);
            if (count < n - 1) {
                writer.print(" ");
            }
            count++;
        }
        if (A.right != null) {
            print(A.right, n);
        }
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        try {
            max = 0;
            writer = new PrintWriter(System.out);
            ScannerC sc = new ScannerC(System.in, C::check);
            root = null;
            root2 = null;
            long n = Long.parseLong(sc.nextElement());
            long len = Long.parseLong(sc.nextElement());
            for (int i = 0; i < n + len; i++) {
                root = add(root, 0, 0);
                add(i, root2, null);
            }
            for (long i = 0; i < n; i++) {
                int l = Integer.parseInt(sc.nextElement());
                root = add(root, l - 1, i + 1);
                long temp = next(l - 2, root2, Integer.MAX_VALUE);
                root = remove(root, temp + 1);
                if (temp > max) {
                    max = temp;
                }
                delete(temp, root2, null);
            }
            writer.println(max + 1);
            count = 0;
            print(root, max + 1);
            writer.println();
            writer.close();
        } catch (Exception e){
            int k = 0;
            throw e;
        }
    }
}


class ScannerC {
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

    public ScannerC(String str, Checker checker) throws IOException{		// scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public ScannerC(InputStream inputStream, Checker checker) throws IOException{		// scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public ScannerC(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException{		//scanner from file
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