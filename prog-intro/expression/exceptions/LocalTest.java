package expression.exceptions;

import expression.*;

public class LocalTest {
    public static void main(String[] args) throws Exception {
        ExpressionParser parser = new ExpressionParser();
        //TripleExpression temp = parser.parse("y max -394911160 / abs (abs -730020417 max x) - (sqrt 1234530661 / y * - 698257775 min - 677988308)");
        //TripleExpression temp = parser.parse("6 - 10 min-3");
        TripleExpression temp = parser.parse("(((z) / (z)) max (z)) max ((z) + (-1537591270))");
        System.err.println(temp.toString());
        System.err.println(temp.evaluate(1510716961, 852358369, -1538579178));
    }
}
