public class CacheObl {
    public static int[][] main(int[][] mat){
        long startTime = System.currentTimeMillis();
        int len1 = mat.length;
        int len2 = mat[0].length;
        int[][] matTran = new int[len2][len1];
        int block = 16;
        for (int i = 0; i < len1; i += block){
            for (int j = 0; j < len2; j += block){
                for (int p = i; p < i + block && p < len1; p++){
                    for (int q = j; q < j + block && q < len2; q++){
                        matTran[q][p] = mat[p][q];
                    }
                }
            }
        }
        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("CacheOblivious: " + timeSpent + "ms");
        return matTran;
    }
}
