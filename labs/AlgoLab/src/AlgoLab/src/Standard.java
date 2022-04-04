public class Standard {
    public static int[][] main(int[][] mat){
        long startTime = System.currentTimeMillis();
        int len1 = mat.length;
        int len2 = mat[0].length;
        int[][] matTran = new int[len2][len1];
        for (int i = 0; i < len2; i++){
            for (int j = 0; j < len1; j++){
                matTran[i][j] = mat[j][i];
            }
        }
        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("Standard algorithm: " + timeSpent + "ms");
        return matTran;
    }
}
