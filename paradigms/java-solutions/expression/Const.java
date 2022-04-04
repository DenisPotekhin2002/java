package expression;

import java.math.BigInteger;

public class Const extends AbstractExpression {
    private final double k;
    private final int ki;

    public Const(double k){
        this.k = k;
        this.ki = (int) k;
    }

    @Override
    public String toString() {
        if (k == ki) {
            return Integer.toString(ki);
        } else {
            return Double.toString(k);
        }
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public int evaluate(int i) {
        return ki;
    }

    @Override
    public int hashCode(){
        return ki;
    }

    @Override
    public boolean equals(Object e) {
        if (e == null){
            return false;
        }
        if (!(e instanceof Const)){
            return false;
        } else {
            Const et = (Const)e;
            return k == et.k;
        }
    }

    @Override
    public double evaluate(double x) {
        return k;
    }

    @Override
    public Object evaluate(String mode, int x, int y, int z) throws Exception {
        if (mode.equals("i") || mode.equals("u")) {
            return ki;
        }
        if (mode.equals("d")){
            return k;
        }
        if (mode.equals("bi")){
            return new BigInteger(Integer.toString(ki));
        }
        if (mode.equals("s")){
            return (short)k;
        }
        if (mode.equals("l")){
            return (long)k;
        }
        throw new AssertionError("Incompatible types");
    }

    @Override
    public double priority(){
        return 0;
    }

    @Override
    public boolean isAssocLeft(){
        return true;
    }

    @Override
    public boolean isAssocRight(){
        return true;
    }

}
