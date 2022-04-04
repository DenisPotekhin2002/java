public class CacheTest {
    public static void main(String[] args){
        for (int y = 0; y < 100; y++) {
            int len1 = 10000;
            int len2 = 10000;
            System.out.println("random matrix " + len1 + " X " + len2);
            int[][] mat = new int[len1][len2];
            for (int i = 0; i < len1; i++) {
                for (int j = 0; j < len2; j++) {
                    mat[i][j] = (int) (Math.random() * Integer.MAX_VALUE);
                }
            }
            int[][] mat1 = Standard.main(mat);
            int[][] mat2 = CacheObl.main(mat);
            int checker = 0;
            if (mat1.length != mat2.length || mat1[0].length != mat2[0].length){
                System.out.println("different");
            }
            for (int i = 0; i < mat1.length; i++) {
                for (int j = 0; j < mat1[0].length; j++) {
                    if (mat1[i][j] != mat2[i][j]) {
                        checker = 1;
                    }
                }
            }
            if (checker == 0) {
                System.out.println("equals");
            } else {
                System.out.println("different");
            }
            System.out.println();
        }
    }
}
