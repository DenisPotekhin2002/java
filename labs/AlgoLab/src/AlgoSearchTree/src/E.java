import java.util.Scanner;

public class E {

    private static class Node {
        private long value;
        private long h;
        private long n;
        private Node left;
        private Node right;

        public Node(long value, Node left, Node right, Node parent) {
            this.value = value;
            this.left = left;
            this.right = right;
            long hl = 0;
            long hr = 0;
            if (this.left != null){
                hl = this.left.h;
            }
            if (this.right != null){
                hr = this.right.h;
            }
            this.h = Math.max(hl, hr) + 1;
            this.recalcOne();
            if (parent == null){
                root = this;
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

        public void recalcOne(){
            long nl = 0;
            long nr = 0;
            if (this.left != null){
                nl = this.left.n;
            }
            if (this.right != null){
                nr = this.right.n;
            }
            this.n = nl + nr + 1;
        }

        public void rotate(Node parent){
            if (parent != null){
                if (this.value > parent.value){
                    parent.right = this;
                } else {
                    parent.left = this;
                }
            }
            this.recalcOne();
            long hl = 0;
            long hr = 0;
            if (this.left != null){
                hl = this.left.h;
            }
            if (this.right != null){
                hr = this.right.h;
            }
            this.h = Math.max(hl, hr) + 1;
            if (hr - hl > 1){
                if (this.right.right != null && this.right.right.h > hl){
                    Node a = this.left;
                    Node b = this.right.left;
                    Node c = this.right.right;
                    Node x = this;
                    Node y = this.right;
                    long h = 0;
                    if (a != null){
                        h = a.h;
                    }
                    if (parent == null){
                        root = y;
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
                    x.recalcOne();
                    y.recalcOne();
                } else {
                    Node a = this.left;
                    Node b = this.right.left.left;
                    Node c = this.right.left.right;
                    Node d = this.right.right;
                    Node x = this;
                    Node y = this.right;
                    Node z = this.right.left;
                    long h = 0;
                    if (a != null){
                        h = a.h;
                    }
                    if (parent == null){
                        root = z;
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
                    x.h = h + 1;
                    y.h = h + 1;
                    z.h = h + 2;
                    x.recalcOne();
                    y.recalcOne();
                    z.recalcOne();
                }
            } else if (hl - hr > 1){
                if (this.left.left != null && this.left.left.h > hr){
                    Node a = this.right;
                    Node b = this.left.right;
                    Node c = this.left.left;
                    Node x = this;
                    Node y = this.left;
                    long h = 0;
                    if (a != null){
                        h = a.h;
                    }
                    if (parent == null){
                        root = y;
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
                    x.recalcOne();
                    y.recalcOne();
                } else {
                    Node a = this.right;
                    Node b = this.left.right.right;
                    Node c = this.left.right.left;
                    Node d = this.left.left;
                    Node x = this;
                    Node y = this.left;
                    Node z = this.left.right;
                    long h = 0;
                    if (a != null){
                        h = a.h;
                    }
                    if (parent == null){
                        root = z;
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
                    x.recalcOne();
                    y.recalcOne();
                    z.recalcOne();
                }
            }
            this.recalcOne();
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
                parent.recalcOne();
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

        public void recalcN(){
            long nl = 0;
            long nr = 0;
            if (this.left != null){
                if (this.left.right == null && this.left.left == null){
                    this.left.n = 1;
                    nl = 1;
                } else {
                    this.left.recalcN();
                    nl = this.left.n;
                }
            }
            if (this.right != null){
                if (this.right.right == null && this.right.left == null){
                    this.right.n = 1;
                    nr = 1;
                } else {
                    this.right.recalcN();
                    nr = this.right.n;
                }
            }
            this.n = nl + nr + 1;
        }

        public void recalcLeft(){
            long nl = 0;
            long nr = 0;
            if (this.left != null){
                if (this.left.left == null){
                    if (this.left.right != null) {
                        this.left.n = this.left.right.n + 1;
                    } else {
                        this.left.n = 1;
                    }
                } else {
                    this.left.recalcLeft();
                }
                nl = this.left.n;
            }
            if (this.right != null){
                nr = this.right.n;
            }
            this.n = nl + nr + 1;
        }
    }

    private static Node root = null;

    public static void add(long v, Node n, Node p){
        if (root == null){
            root = new Node(v, null, null, null);
            return;
        }
        if (n == null){
            Node w = new Node(v, null, null, p);
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

    public static boolean check(long v, Node w){
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

    public static long next(long v, Node w, long temp){
        if (w == null){
            return temp;
        }
        if (v < w.value){
            return next(v, w.left, w.value);
        } else {
            return next(v, w.right, temp);
        }
    }

    public static long prev(long v, Node w, long temp){
        if (w == null){
            return temp;
        }
        if (v > w.value){
            return prev(v, w.right, w.value);
        } else {
            return prev(v, w.left, temp);
        }
    }

    public static void delete(long v, Node w, Node p){
        if (w == null){
            return;
        }
        if (w.value == v){
            if (w.left == null){
                if (p == null){
                    root = w.right;
                    if (root != null) {
                        root.rotate(null);
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
                    p.recalcOne();
                }
                return;
            }
            if (w.right == null){
                if (p == null){
                    root = w.left;
                    if (root != null) {
                        root.rotate(null);
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
                    p.recalcOne();
                }
                return;
            }
            Node temp = w.right;
            Node parent = w;
            Node parparent = p;
            long count = 0;
            if (temp.left != null){
                count = 1;
            }
            if (count == 1) {
                while (temp.left != null){
                    parparent = parent;
                    parent = temp;
                    temp = temp.left;
                }
                parent.left = temp.right;
                if (parent.left != null) {
                    parent.left.rotate(parent);
                }
                if (parent != null && (parparent == null || parparent.left == parent || parparent.right == parent)){
                    parent.rotate(parparent);
                }
                w.value = temp.value;
                if (w.right != null) {
                    w.right.recalcLeft();
                }
                if (p == null){
                    root = w;
                    if (root != null) {
                        root.rotate(null);
                    }
                } else {
                    w.rotate(p);
                    p.recalcOne();
                }
            } else {
                w.value = temp.value;
                w.right = temp.right;
                if (w.right != null){
                    w.right.rotate(w);
                }
                if (p == null){
                    root = w;
                    if (root != null) {
                        root.rotate(null);
                    }
                } else {
                    w.rotate(p);
                    p.recalcOne();
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

    public static long find(long k, Node w, Node p){
        try {
            if (k == 1) {
                while (w.right != null) {
                    w = w.right;
                }
                return w.value;
            }
            if (w.right == null) {
                return find(k - 1, w.left, w);
            } else if (w.right.n < k) {
                if (w.right.n == k - 1) {
                    return w.value;
                } else {
                    k -= (w.right.n + 1);
                    return find(k, w.left, w);
                }
            } else {
                return find(k, w.right, w);
            }
        } catch (Exception e){
            throw e;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        root = null;
        int range = sc.nextInt();
        for (int it = 0; it < range; it++){
            String t = sc.next();
            long temp = sc.nextLong();
            switch (t) {
                case "1":
                case "+1":
                    add (temp, root, null);
                    break;
                case "-1":
                    if (temp == -16511069){
                        int aaa = 1;
                    }
                    delete(temp, root, null);
                    break;
                case "0":
                    System.out.println(find(temp, root, null));
                    break;
            }
        }
    }
}
