package expression.generic;

import expression.exceptions.ExpressionParser;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws Exception {
        String mode = args[0].substring(1);
        int x1 = -2;
        int x2 = 2;
        int y1 = -2;
        int y2 = 2;
        int z1 = -2;
        int z2 = 2;
        String expression = args[1];
        ExpressionParser parser = new ExpressionParser();
        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int xtemp = 0; xtemp <= x2 - x1; xtemp++){
            for (int ytemp = 0; ytemp <= y2 - y1; ytemp++){
                for (int ztemp = 0; ztemp <= z2 - z1; ztemp++){
                    try {
                            res[xtemp][ytemp][ztemp] =
                                    parser.parse(expression).evaluate(mode,
                                            x1 + xtemp, y1 + ytemp, z1 + ztemp);
                    } catch (Exception e){
                        res[xtemp][ytemp][ztemp] = null;
                    }
                    System.out.print(res[xtemp][ytemp][ztemp] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
