import java.util.Arrays;

public class CashObl {
    public static int[][] transp(int[][] mat){
        int len1 = mat[0].length;
        int len2 = mat.length;
        int[][] matTran = new int[len1][len2];
        if (len1 * len2 <= 256){
            for (int i = 0; i < len1; i++){
                for (int j = 0; j < len2; j++){
                    matTran[i][j] = mat[j][i];
                }
            }
            return matTran;
        } else {
            if (len2 >= len1){
                int[][] matTemp1 = Arrays.copyOf(mat, len2/2);
                int[][] matTemp2 = Arrays.copyOfRange(mat, len2/2, len2);
                int[][] matTranTemp1 = transp(matTemp1);
                int[][] matTranTemp2 = transp(matTemp2);
                for (int i = 0; i < len1; i++) {
                    System.arraycopy(matTranTemp1[i], 0, matTran[i], 0, len2/2);
                    System.arraycopy(matTranTemp2[i], 0, matTran[i], len2/2, len2 - len2/2);
                }
                return matTran;
            } else {
                int[][] matTemp1 = new int[len2][len1/2];
                for (int i = 0; i < len2; i++) {
                    System.arraycopy(mat[i], 0, matTemp1[i], 0, len1/2);
                }
                int[][] matTemp2 = new int[len2][len1 - len1/2];
                for (int i = 0; i < len2; i++) {
                    System.arraycopy(mat[i], len1/2, matTemp2[i], 0, len1 - len1/2);
                }
                int[][] matTranTemp1 = transp(matTemp1);
                int[][] matTranTemp2 = transp(matTemp2);
                System.arraycopy(matTranTemp1, 0, matTran, 0, len1/2);
                System.arraycopy(matTranTemp2, 0, matTran, len1/2, len1 - len1/2);
                return matTran;
            }
        }
    }

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        int len1 = 10000;
        int len2 = 10000;
        int[][] mat = new int[len1][len2];
        for (int i = 0; i < len1; i++){
            for (int j = 0; j < len2; j++){
                mat[i][j] = (int) (Math.random() * Integer.MAX_VALUE);
            }
        }
        int[][] matTran = transp(mat);
        //System.err.println(0);
        long timeSpent = System.currentTimeMillis() - startTime;
        System.err.println("CacheOblivious: " + timeSpent);
    }
}
