import java.io.*;
import java.util.*;

class CT {

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
                    x.h = h + 1;
                    y.h = h + 2;
                } else {
                    try {
                        OrdNode a = this.left;
                        OrdNode b = this.right.left.left;
                        OrdNode c = this.right.left.right;
                        OrdNode d = this.right.right;
                        OrdNode x = this;
                        OrdNode y = this.right;
                        OrdNode z = this.right.left;
                        long h = 0;
                        if (a != null) {
                            h = a.h;
                        }
                        if (parent == null) {
                            root2 = z;
                        } else {
                            if (x.value > parent.value) {
                                parent.right = z;
                            } else {
                                parent.left = z;
                            }
                        }
                        z.left = x;
                        z.right = y;
                        x.right = b;
                        y.left = c;
                        x.h = h + 1;
                        y.h = h + 1;
                        z.h = h + 2;
                    } catch (Exception e){
                        int ter = 0;
                        throw e;
                    }
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
                    x.h = h + 1;
                    y.h = h + 2;
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
                    x.h = h + 1;
                    y.h = h + 1;
                    z.h = h + 2;
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
    private static StringBuilder sb;

    public static void print(Node A, long n){
        if (A.left != null) {
            print(A.left, n);
        }
        if (count < n) {
            sb.append(A.element);
            if (count < n - 1) {
                sb.append(" ");
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

    public static String main(String s) {
        max = 0;
        Scanner sc = new Scanner(s);
        sb = new StringBuilder();
        root = null;
        root2 = null;
        long n = sc.nextLong();
        long len = sc.nextLong();
        for (int i = 0; i < n + len; i++){
            root = add(root, 0, 0);
            add(i, root2, null);
        }
        for (long i = 0; i < n; i++){
            int l = sc.nextInt();
            root = add(root, l - 1, i + 1);
            long temp = next(l - 2, root2, Integer.MAX_VALUE);
            root = remove(root, temp + 1);
            if (temp > max){
                max = temp;
            }
            delete(temp, root2, null);
        }
        sb.append(max + 1);
        sb.append("\n");
        count = 0;
        print(root, max + 1);
        sb.append("\n");
        return sb.toString();
    }
}

class CTT {

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

    public static Node add(Node A, long pos, long val, long plus){
        if (A == null){
            return new Node(val);
        }
        Node[] p1 = split(A, pos);
        Node[] p2 = split(p1[1], 1);
        if (p2[0].element == 0 && val > 0){
            p2[0].element = val;
            if (plus + pos + 1 > max){
                max = plus + pos + 1;
            }
            return merge(merge(p1[0], p2[0]), p2[1]);
        } else {
            long temp = p2[0].element;
            p2[0].element = val;
            return merge(merge(p1[0], p2[0]), add(p2[1], 0, temp, plus + pos + 1));
        }
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
            sb.append(A.element);
            if (count < n - 1) {
                sb.append(" ");
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

    private static StringBuilder sb;

    public static String main(String s) {
        max = 0;
        Scanner sc = new Scanner(s);
        sb = new StringBuilder();
        root = null;
        long n = sc.nextLong();
        long len = sc.nextLong();
        for (int i = 0; i < n + len; i++){
            root = add(root, 0, 0, 0);
        }
        for (long i = 0; i < n; i++){
            long l = sc.nextLong();
            root = add(root, l - 1, i + 1, 0);
        }
        sb.append(max);
        sb.append("\n");
        count = 0;
        print(root, max);
        sb.append("\n");
        return sb.toString();
    }
}

public class Test {
    public static void main(String[] args) {
        int j = 0;
        while (true) {
            j++;
            System.out.println("test " + j);
            StringBuilder s = new StringBuilder("");
            int len = (int) (Math.random() * 15) + 1;
            int max = (int) (Math.random() * 15) + 1;
            s.append(len);
            s.append(" ");
            s.append(max);
            s.append("\n");
            for (int i = 0; i < len; i++){
                int cur = (int) (Math.random() * (max - 1)) + 1;
                s.append(cur);
                s.append(" ");
            }
            try {
                String a = CT.main(s.toString());
                String b = CTT.main(s.toString());
                if (!a.equals(b)){
                    System.out.println("~" + s + "~");
                    System.out.println();
                    System.out.println("*" + a + "*");
                    System.out.println();
                    System.out.println("@" + b + "@");
                    throw new AssertionError();
                }
            } catch (Exception e){
                System.out.println(s);
                throw e;
            }
        }
    }
}
