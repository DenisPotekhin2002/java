import java.math.BigInteger;

public class Choose {

    private static final BigInteger[] table = new BigInteger[505];

    static BigInteger binomial(final int N, final int K) {
        BigInteger ret = BigInteger.ONE;
        for (int k = 0; k < K; k++) {
            ret = ret.multiply(BigInteger.valueOf(N - k))
                    .divide(BigInteger.valueOf(k + 1));
        }
        return ret;
    }

    private static BigInteger number(int k) {
        BigInteger ans = binomial(k, (k - 189) / 9);
        for (int i = 1; i <= (k - 189) / 9; i++) {
            if (table[k - 9 * i].equals(BigInteger.valueOf(0))) {
                table[k - 9 * i] = number(k - 9 * i);
            }
            ans = ans.subtract(binomial(9 * i, i).multiply(table[k - 9 * i]));
        }
        return ans;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 504; i++) {
            table[i] = BigInteger.valueOf(0);
        }
        System.out.println(number(504));
        System.out.println(binomial(504, 35));
        System.out.println("0." + number(504).multiply(BigInteger.valueOf(10000)).divide(binomial(504, 35)));
    }
}
