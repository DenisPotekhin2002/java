package expression;

public interface TripleExpression extends ToMiniString {
    Object evaluate(String s, int x, int y, int z) throws Exception;
}
