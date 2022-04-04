import java.util.Scanner;

public class AW {

    private static class Node {
        private int value;
        private Node left;
        private Node right;
        private Node parent;

        public Node(int value, Node left, Node right, Node parent) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    private static Node root = null;

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
                    if (w.right != null){
                        w.right.parent = null;
                    }
                } else {
                    if (w.value >= w.parent.value) {
                        w.parent.right = w.right;
                    } else {
                        w.parent.left = w.right;
                    }
                    if (w.right != null){
                        w.right.parent = w.parent;
                    }
                }
                return;
            }
            if (w.right == null){
                if (w.parent == null){
                    root = w.left;
                    w.left.parent = null;
                } else {
                    if (w.value >= w.parent.value) {
                        w.parent.right = w.left;
                    } else {
                        w.parent.left = w.left;
                    }
                    w.left.parent = w.parent;
                }
                return;
            }
            Node temp = w.right;
            int count = 0;
            if (temp.left != null){
                count = 1;
            }
            while (temp.left != null){
                temp = temp.left;
            }
            if (count == 1) {
                temp.parent.left = temp.right;
            } else {
                temp.parent.right = temp.right;
            }
            if (temp.right != null){
                temp.right.parent = temp.parent;
            }
            w.value = temp.value;
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
            if (t.equals("insert")){
                add(temp, root, null);
            }
            if (t.equals("exists")){
                System.out.println(check(temp, root));
            }
            if (t.equals("next")){
                System.out.println(next(temp, root, "none"));
            }
            if (t.equals("prev")){
                System.out.println(prev(temp, root, "none"));
            }
            if (t.equals("delete")){
                delete(temp, root);
            }
        }
    }
}
