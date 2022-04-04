package expression;

public interface Operation extends Expression, DoubleExpression, TripleExpression{
    String toString();
    String toMiniString();
    boolean equals(Object e);
    int hashCode();
    double priority();
    boolean isAssocLeft();
    boolean isAssocRight();
}
