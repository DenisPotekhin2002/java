import java.util.Scanner;

public class A {

    private static class Node {
        private int value;
        private int h;
        private Node left;
        private Node right;
        private Node parent;

        public Node(int value, Node left, Node right, Node parent) {
            this.value = value;
            this.left = left;
            this.right = right;
            if (this.left != null){
                this.left.parent = this;
            }
            if (this.right != null){
                this.right.parent = this;
            }
            this.parent = parent;
            if (this.parent != null) {
                if (this.value > this.parent.value){
                    this.parent.right = this;
                } else {
                    this.parent.left = this;
                }
            } else {
                root = this;
            }
            rotate();
        }

        public void rotate(){
            if (this.parent != null){
                if (this.value > this.parent.value){
                    this.parent.right = this;
                } else {
                    this.parent.left = this;
                }
            }
            int hl = 0;
            int hr = 0;
            if (this.left != null){
                hl = this.left.h;
            }
            if (this.right != null){
                hr = this.right.h;
            }
            if (hr - hl > 1){
                if (this.right.right != null && this.right.right.h > hl){
                    int ha = 0;
                    int hb = 0;
                    int hc = this.right.right.h;;
                    if (this.left != null){
                        ha = this.left.h;
                    }
                    if (this.right.left != null){
                        hb = this.right.left.h;
                    }
                    this.right.parent = this.parent;
                    this.right.h = hc + 1;
                    if (this.parent != null) {
                        if (this.value > this.parent.value) {
                            this.parent.right = this.right;
                        } else {
                            this.parent.left = this.right;
                        }
                    } else {
                        root = this.right;
                    }
                    this.parent = this.right;
                    this.h = ha + 1;
                    if (this.right.left != null) {
                        this.right.left.parent = this;
                    }
                    this.right = this.right.left;
                    this.parent.left = this;
                } else {
                    int ha = 0;
                    int hb = 0;
                    int hc = 0;
                    int hd = 0;
                    if (this.left != null){
                        ha = this.left.h;
                    }
                    if (this.right.left.left != null){
                        hb = this.right.left.left.h;
                    }
                    if (this.right.left.right != null){
                        hc = this.right.left.right.h;
                    }
                    if (this.right.right != null){
                        hd = this.right.right.h;
                    }
                    this.right.left.parent = this.parent;
                    this.right.left.h = hd + 2;
                    if (this.parent != null) {
                        if (this.value > this.parent.value) {
                            this.parent.right = this.right.left;
                        } else {
                            this.parent.left = this.right.left;
                        }
                    } else {
                        root = this.right.left;
                    }
                    this.parent = this.right.left;
                    this.h = ha + 1;
                    this.right.left = this.parent.right;
                    if (this.parent.right != null) {
                        this.parent.right.parent = this.right;
                    }
                    this.parent.right = this.right;
                    this.parent.right.h = hd + 1;
                    this.right.parent = this.parent;
                    this.right = this.parent.left;
                    this.parent.left = this;
                    if (this.right != null) {
                        this.right.parent = this;
                    }
                }
            } else if (hl - hr > 1){
                if (this.left.left != null && this.left.left.h > hr){
                    int ha = 0;
                    int hb = 0;
                    int hc = this.left.left.h;;
                    if (this.right != null){
                        ha = this.right.h;
                    }
                    if (this.left.right != null){
                        hb = this.left.right.h;
                    }
                    this.left.parent = this.parent;
                    this.left.h = hc + 1;
                    if (this.parent != null) {
                        if (this.value > this.parent.value) {
                            this.parent.right = this.left;
                        } else {
                            this.parent.left = this.left;
                        }
                    } else {
                        root = this.left;
                    }
                    this.parent = this.left;
                    this.h = ha + 1;
                    if (this.left.right != null) {
                        this.left.right.parent = this;
                    }
                    this.left = this.left.right;
                    this.parent.right = this;
                } else {
                    int ha = 0;
                    int hb = 0;
                    int hc = 0;
                    int hd = 0;
                    if (this.left != null){
                        ha = this.left.h;
                    }
                    if (this.right.left.left != null){
                        hb = this.right.left.left.h;
                    }
                    if (this.right.left.right != null){
                        hc = this.right.left.right.h;
                    }
                    if (this.right.right != null){
                        hd = this.right.right.h;
                    }
                    this.left.right.parent = this.parent;
                    this.left.right.h = hd + 2;
                    if (this.parent != null) {
                        if (this.value > this.parent.value) {
                            this.parent.right = this.left.right;
                        } else {
                            this.parent.left = this.left.right;
                        }
                    } else {
                        root = this.left.right;
                    }
                    this.parent = this.left.right;
                    this.h = ha + 1;
                    this.left.right = this.parent.left;
                    if (this.parent.left != null) {
                        this.parent.left.parent = this.left;
                    }
                    this.parent.left = this.left;
                    this.parent.left.h = hd + 1;
                    this.left.parent = this.parent;
                    this.left = this.parent.right;
                    this.parent.right = this;
                    if (this.left != null) {
                        this.left.parent = this;
                    }
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
        }
    }

    private static Node root = null;

    public static void recalc(Node w){
        while (w != null){
            w.rotate();
            w = w.parent;
        }
    }

    public static void add(int v, Node n, Node p){
        if (root == null){
            root = new Node(v, null, null, null);
            return;
        }
        if (n == null){
            Node w = new Node(v, null, null, p);
            if (p != null) {
                if (v >= p.value) {
                    p.right = w;
                } else {
                    p.left = w;
                }
            }
            recalc(w);
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
    }

    public static boolean check(int v, Node w){
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

    public static Object next(int v, Node w, Object temp){
        if (w == null){
            return temp;
        }
        if (v < w.value){
            return next(v, w.left, w.value);
        } else {
            return next(v, w.right, temp);
        }
    }

    public static Object prev(int v, Node w, Object temp){
        if (w == null){
            return temp;
        }
        if (v > w.value){
            return prev(v, w.right, w.value);
        } else {
            return prev(v, w.left, temp);
        }
    }

    public static void delete(int v, Node w){
        if (w == null){
            return;
        }
        if (w.value == v){
            if (w.left == null){
                if (w.parent == null){
                    root = w.right;
                    if (root != null){
                        root.parent = null;
                    }
                    recalc(root);
                } else {
                    if (w.value >= w.parent.value) {
                        w.parent.right = w.right;
                        if (w.parent.right != null){
                            w.parent.right.parent = w.parent;
                        }
                        recalc(w.parent.right);
                    } else {
                        w.parent.left = w.right;
                        if (w.parent.left != null){
                            w.parent.left.parent = w.parent;
                        }
                        recalc(w.parent.left);
                    }
                }
                return;
            }
            if (w.right == null){
                if (w.parent == null){
                    root = w.left;
                    root.parent = null;
                    recalc(root);
                } else {
                    if (w.value >= w.parent.value) {
                        w.parent = new Node(w.parent.value, w.parent.left, w.left, w.parent.parent);
                    } else {
                        w.parent = new Node(w.parent.value, w.left, w.parent.right, w.parent.parent);
                    }
                    w.left.parent = w.parent;
                    recalc(w.left.parent);
                }
                return;
            }
            Node temp = w.right;
            temp.parent = null;
            int count = 0;
            if (temp.left != null){
                count = 1;
            }
            while (temp.left != null){
                temp = temp.left;
            }
            if (count == 1) {
                temp.parent.left = temp.right;
                if (temp.parent.left != null){
                    temp.parent.left.parent = temp.parent;
                }
                w.value = temp.value;
                while (temp.parent != null){
                    temp = temp.parent;
                }
                w = new Node(w.value, w.left, temp, w.parent);
                if (w.right != null){
                    w.right.parent = w;
                }
                if (w.left != null){
                    w.left.parent = w;
                }
                if (w.parent == null){
                    root = w;
                }
            } else {
                w = new Node(temp.value, w.left, temp.right, w.parent);
                if (w.right != null){
                    w.right.parent = w;
                }
                if (w.left != null){
                    w.left.parent = w;
                }
                if (w.parent == null){
                    root = w;
                }
            }
            recalc(w);
            return;
        }
        if (v > w.value){
            delete(v, w.right);
        } else {
            delete(v, w.left);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String t = sc.next();
            int temp = sc.nextInt();
            switch (t) {
                case "insert":
                    add(temp, root, null);
                    break;
                case "exists":
                    System.out.println(check(temp, root));
                    break;
                case "next":
                    System.out.println(next(temp, root, "none"));
                    break;
                case "prev":
                    System.out.println(prev(temp, root, "none"));
                    break;
                case "delete":
                    delete(temp, root);
                    break;
            }
        }
    }
}
