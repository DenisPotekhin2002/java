package expression.exceptions;

public interface CheckedTripleExpression {
    double evaluate(int x, int y, int z);
    int evaluate(int x);
    double evaluate(double x);
    double priority();
    boolean isAssocLeft();
    boolean isAssocRight();
    String toMiniString();
}
