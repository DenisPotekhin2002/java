public class Mat {

    private static int[][] phi(int[][] mat){
        int[][] ans = new int[2][2];
        ans[0][0] = (2 * mat[0][0] + mat[0][1] + mat[1][0]) / 4;
        ans[0][1] = (2 * mat[0][1] + mat[0][0] + mat[1][1]) / 2;
        ans[1][0] = (2 * mat[1][0] + mat[0][0] + mat[1][1]) / 2;
        ans[1][1] = (2 * mat[1][1] + mat[1][0] + mat[0][1]) / 4;
        return ans;
    }

    public static void main(String[] args) {
        int[][] res = new int[2][2];
        res[0][0] = 1;
        res[0][1] = 2;
        res[1][0] = 0;
        res[1][1] = 1
        ;
        for (int i = 1; i <= 2021; i++){
            res = phi(res);
        }
        System.out.println(res[0][0] + " " + res[0][1]);
        System.out.println(res[1][0] + " " + res[1][1]);
    }
}
