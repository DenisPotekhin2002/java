package search;

public class BinarySearchTemp {

    //Pre: (i < j) -> (a[i] >= a[j])
    public static int search(int x, int[] a){
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
    //Post: R - наименьший индекс, для которого a[R] < x, или a.length, если таких в массиве нет
    //-> -1 < R <= a.length

    public static void main(String[] args){
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        //System.arraycopy(args, 1, a, 0, args.length - 1);
        for (int i = 1; i < args.length; i++){
            a[i - 1] = Integer.parseInt(args[i]);
        }
        System.out.println(search(x, a));
    }
}
