package expression.generic;

import expression.Const;
import expression.TripleExpression;
import expression.exceptions.ExpressionParser;
import expression.exceptions.Abs;

public class GenericTabulator implements Tabulator{
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        ExpressionParser parser = new ExpressionParser();
        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        TripleExpression exp = parser.parse(expression);
        for (int xtemp = 0; xtemp <= x2 - x1; xtemp++){
            for (int ytemp = 0; ytemp <= y2 - y1; ytemp++){
                for (int ztemp = 0; ztemp <= z2 - z1; ztemp++){
                    if (x1 + xtemp == 16 && y1 + ytemp == -5 && z1 + ztemp == -16){
                        int temp = 0;
                    }
                    if (exp.toString().equals(new Abs(new Const(-5)).toString())){
                        int temp = 0;
                    }
                    try {
                        res[xtemp][ytemp][ztemp] =
                                exp.evaluate(
                                        mode, x1 + xtemp, y1 + ytemp, z1 + ztemp);
                    } catch (Exception e){
                        //System.err.println(e.getMessage());
                        res[xtemp][ytemp][ztemp] = null;
                    }
                }
            }
        }
        return res;
    }
}
