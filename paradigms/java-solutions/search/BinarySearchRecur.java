package search;

public class BinarySearchRecur {

    //Pre: (i < j) -> (a[i] >= a[j]); -1 <= lx <= R <= rx <= a.length;
    public static int search(int x, int[] a, int lx, int rx){
        if (rx <= lx + 1){
            //-1 <= lx <= R <= rx <= a.length; rx <= lx + 1 -> rx = R
            return rx;
        }
        //-1 <= lx <= R <= rx <= a.length; rx > lx + 1
        int m = lx + (rx - lx) / 2;
        //-1 <= lx <= R <= rx <= a.length; rx > lx + 1; lx <= m < rx
        if (a[m] > x){
            //-1 <= lx <= m < R <= rx <= a.length
            return search(x, a, m, rx);
        } else {
            //-1 <= lx <= R <= m <= rx <= a.length
            return search(x, a, lx, m);
        }
    }
    //Post: R - наименьший индекс, для которого a[R] < x, или a.length, если таких в массиве нет
    //-> -1 < R <= a.length

    public static void main(String[] args){
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        //System.arraycopy(args, 1, a, 0, args.length - 1);
        for (int i = 1; i < args.length; i++){
            a[i - 1] = Integer.parseInt(args[i]);
        }
        System.out.println(search(x, a, -1, a.length));
    }
}
