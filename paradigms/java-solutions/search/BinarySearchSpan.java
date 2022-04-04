package search;

public class BinarySearchSpan {

    //Pre: массив a упорядочен по невозрастанию и a != null
    public static int searchFirst(int x, int[] a){
        assert a != null;
        //(i < j) -> (a[i] >= a[j])
        int l = -1;
        //(i < j) -> (a[i] >= a[j]); l = -1
        int r = a.length;
        //(i < j) -> (a[i] >= a[j]); l = -1; r = a.length
        //Inv: -1 <= l < R <= r <= a.length
        while (r - l > 1){
            //-1 <= l < R <= r <= a.length
            int m = l + (r - l) / 2;
            //-1 <= l < R <= r <= a.length; -1 <= l < m <= r <= a.length
            if (a[m] > x){
                l = m;
                //-1 <= l < R <= r <= a.length
            } else {
                r = m;
                //-1 <= l < R <= r <= a.length
            }
            //-1 <= l < R <= r <= a.length
        }
        //-1 <= l < R <= r <= a.length; r - l <= 1 -> r = l + 1 -> R = r
        return r;
    }
    //Post: R - наименьший индекс, для которого a[R] <= x, или a.length, если таких в массиве нет
    //-> -1 < R <= a.length

    //Pre: массив a упорядочен поневозрастанию, a != null; -1 <= lx < rx <= a.length;
    public static int searchLast(int x, int[] a, int lx, int rx){
        assert a != null;
        if (rx <= lx + 1){
            //-1 <= lx <= R < rx <= a.length; rx <= lx + 1 -> lx = R
            return lx;
        }
        //-1 <= lx <= R < rx <= a.length; rx > lx + 1
        int m = lx + (rx - lx) / 2;
        //-1 <= lx <= R < rx <= a.length; rx > lx + 1; lx <= m < rx
        if (a[m] >= x){
            //-1 <= lx <= m <= R < rx <= a.length
            return searchLast(x, a, m, rx);
        } else {
            //-1 <= lx <= R < m < rx <= a.length
            return searchLast(x, a, lx, m);
        }
    }
    //Post: R - наибольший индекс, для которого a[R] >= x, или -1, если таких в массиве нет
    //-> -1 <= R < a.length
    //-1 <= lx <= R < rx <= a.length;

    //Pre: args != null; args[i] instanceof int, (args.length > 2 && args.length > j > i > 0) -> (args[j] <= args[i])
    public static void main(String[] args){
        int x = Integer.parseInt(args[0]);
        //x == Integer.parseInt(args[0])
        int[] a = new int[args.length - 1];
        //a.length == args.length - 1
        for (int i = 1; i < args.length; i++){
            a[i - 1] = Integer.parseInt(args[i]);
        }
        //(a.length > 0 && args.length > i >= 0) -> (a[i] = args[i + 1])
        int start = searchFirst(x, a);
        //start - наименьший индекс, для которого a[start] <= x, или a.length, если таких в массиве нет
        int fin = searchLast(x, a, -1, a.length);
        //fin - наибольший индекс, для которого a[R] >= x, или -1, если таких в массиве нет
        int len = fin - start + 1;
        //len == fin - start + 1
        if (len <= 0){
            //fin < start => элементов, равных x, в массиве нет
            System.out.println(start + " " + 0);
        } else {
            //fin >= start
            System.out.println(start + " " + len);
        }
    }
    //Post: R1 - индекс первого элемента, равного args[0], или место вставки, если такого элемента нет
    //R2 - количество элементов, равных args[0]
}
