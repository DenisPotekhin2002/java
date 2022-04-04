package search;

public class BinarySearch {

    //Pre: (i < j) -> (a[i] >= a[j])
    //Post: R - наименьший индекс, для которого a[R] < x, или a.length, если таких в массиве нет
    //-> -1 < R <= a.length; все элементы массива остаются такими же, какими были (Inv)
    public static int search(int x, int[] a){
        for (int i = 1; i < a.length; i++){
            assert a[i] <= a[i - 1];
        }
        //(i < j) -> (a[i] >= a[j])
        int[] atemp = new int[a.length + 2];
        //(i < j) -> (a[i] >= a[j]); atemp.length == a.length + 2
        atemp[0] = Integer.MAX_VALUE;
        //(i < j) -> (a[i] >= a[j]); atemp[0] == Integer.MAX_VALUE
        atemp[a.length + 1] = Integer.MIN_VALUE;
        //(i < j) -> (a[i] >= a[j]); atemp[0] == Integer.MAX_VALUE; atemp[a.length + 1] == Integer.MIN_VALUE
        System.arraycopy(a, 0, atemp, 1, a.length);
        //(i < j) -> (atemp[i] >= atemp[j]), т.к. atemp[0] >= atemp[k] && atemp[k] >= atem[atemp.length - 1]
        // && для 0 < i < j < atemp.length - 1 верно, т.к. было верно для а
        //atemp[0] >= x
        //atemp[atemp.length - 1] <= x
        if (x == Integer. MAX_VALUE){
            return 0;
        }
        //(i < j) -> (atemp[i] >= atemp[j]); atemp[0] > x; atemp[atemp.length - 1] <= x; l == 0
        int l = 0;
        //(i < j) -> (atemp[i] >= atemp[j]); atemp[0] > x; atemp[atemp.length - 1] <= x; l == 0
        int r = atemp.length - 1;
        //(i < j) -> (atemp[i] >= atemp[j]); atemp[0] > x; atemp[atemp.length - 1] <= x; l == 0; r == atemp.length - 1;
        //Inv: atemp[l] > x; atemp[r] <= x; 0 <= l <= r
        while (r - l > 1){
            //atemp[l] > x; atemp[r] <= x; 0 <= l < r; r - l > 1
            int m = l + (r - l) / 2;
            //atemp[l] > x; atemp[r] <= x; 0 <= l < r; r - l > 1; l <= m <= r
            if (atemp[m] > x){
                l = m;
                //atemp[l] > x; atemp[r] <= x; 0 <= l <= r
            } else {
                r = m;
                //atemp[l] > x; atemp[r] <= x; 0 <= l <= r
            }
            //atemp[l] > x; atemp[r] <= x; 0 <= l <= r
        }
        //Post: atemp[l] > x; atemp[r] <= x; 0 <= l <= r; r - l <= 1 -> r = l + 1
        return r - 1;
    }


    public static void main(String[] args){
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        //System.arraycopy(args, 1, a, 0, args.length - 1);
        for (int i = 1; i < args.length; i++){
            a[i - 1] = Integer.parseInt(args[i]);
        }
        int res1 = search(x, a);
        int res2 = BinarySearchTemp.search(x, a);
        int res3 = BinarySearchRecur.search(x, a, -1, a.length);
        if (res1 == res2 && res2 == res3) {
            System.out.println(res1);
        } else {
            throw new AssertionError("one of results is wrong");
        }
    }
}
