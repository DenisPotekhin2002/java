
public interface Operation extends Expression, TripleExpression{
    String toString();
    String toMiniString();
    boolean equals(Object e);
    int hashCode();
    double priority();
    String operator();
}
