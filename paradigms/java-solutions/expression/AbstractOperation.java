package expression;

import java.math.BigInteger;

public abstract class AbstractOperation implements Operation {
    protected Operation first;
    protected Operation second;

    public AbstractOperation(Operation first, Operation second){
        this.first = first;
        this.second = second;
    }

    public AbstractOperation(Operation second){
        this.first = null;
        this.second = second;
    }

    public AbstractOperation(){
        this.first = null;
        this.second = null;
    }

    public abstract String operator();


    public abstract double eval(double first, double second);

    public abstract int eval(int first, int second);

    public abstract BigInteger eval(BigInteger first, BigInteger second);

    public abstract long eval(long first, long second);

    public abstract short eval(short first, short second);

    public abstract int eval(int mode, int first, int second);

    public Object eval(String mode, Object first, Object second){
        if (mode.equals("d")){
            return eval((double)first, (double)second);
        }
        if (mode.equals("bi")){
            return eval((BigInteger)first, (BigInteger)second);
        }
        if (mode.equals("i")){
            return eval((int)first, (int)second);
        }
        if (mode.equals("s")){
            return eval((short)first, (short)second);
        }
        if (mode.equals("u")){
            return eval(1, (int)first, (int)second);
        }
        if (mode.equals("l")){
            return eval((long)first, (long)second);
        }
        throw new AssertionError("Unsupported operation");
    }

    @Override
    public String toString() {
        if (first != null) {
            return String.format("(%s %s %s)", first.toString(), operator(), second.toString());
        } else {
            return String.format("(%s %s)", operator(), second.toString());
        }
    }

    @Override
    public String toMiniString(){

        return String.format("%s %s %s", firstMini(), operator(), secondMini());
    }

    protected String firstMini(){
        if (first.priority() > priority()){
            return String.format("(%s)", first.toMiniString());
        }
        return first.toMiniString();
    }

    protected String secondMini(){
        if (second.priority() < priority() ||
                (second.priority() == priority() && isAssocLeft() && second.isAssocRight()) ||
                second.priority() == 0){
            return second.toMiniString();
        }
        return String.format("(%s)", second.toMiniString());
    }

    @Override
    public int evaluate(int i) {
        return eval(first.evaluate(i), second.evaluate(i));
    }

    @Override
    public double evaluate(double i) {
        return eval(first.evaluate(i), second.evaluate(i));
    }

    @Override
    public Object evaluate(String mode, int x, int y, int z) throws Exception {
        if (first != null) {
            return eval(mode, first.evaluate(mode, x, y, z),
                    second.evaluate(mode, x, y, z));
        } else {
            return eval(mode, 0, second.evaluate(mode, x, y, z));
        }
    }

    @Override
    public boolean equals(Object e){
        if (e == null){
            return false;
        }
        if (!(e instanceof AbstractOperation)){
            return false;
        } else {
            AbstractOperation et = (AbstractOperation)e;
            return (first.equals(et.first) && second.equals(et.second) && operator().equals(et.operator()));
        }
    }

    @Override
    public int hashCode(){
        return first.hashCode() * operator().charAt(0) * 13 + 7 * second.hashCode();
    }

    public abstract Operation getOp(Operation x, Operation y);

    public boolean isUnaryOp(){
        return false;
    }
}
