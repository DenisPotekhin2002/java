package expression.parser;

import expression.TripleExpression;

public class LocalMain {
    public static void main(String[] args) throws Exception {
        ExpressionParser parser = new ExpressionParser();
        TripleExpression temp = parser.parse("count -5");
        System.err.println(temp.toString());
        System.err.println(temp.evaluate("i", 0, 0, 0));
    }
}
