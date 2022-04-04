package expression;

import java.math.BigInteger;

public class Variable extends AbstractExpression {
    private final String x;

    public Variable(String x){
        this.x = x;
    }

    @Override
    public String toString() {
        return x;
    }

    @Override
    public String toMiniString() {
        return x;
    }

    @Override
    public int evaluate(int i) {
        return i;
    }

    @Override
    public int hashCode(){
        return x.hashCode();
    }

    @Override
    public double evaluate(double x) {
        return x;
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

    @Override
    public Object evaluate(String mode, int x, int y, int z) throws Exception {
        int res;
        if (this.x.charAt(0) == 'x'){
            res = x;
        } else if (this.x.charAt(0) == 'y'){
            res = y;
        } else if (this.x.charAt(0) == 'z'){
            res = z;
        } else {
            throw new AssertionError("Wrong name of variable");
        }
        if (mode.equals("i")){
            return res;
        }
        if (mode.equals("d")){
            return (double)res;
        }
        if (mode.equals("bi")){
            return BigInteger.valueOf(res);
        }
        if (mode.equals("u")){
            return res;
        }
        if (mode.equals("l")){
            return (long)res;
        }
        if (mode.equals("s")){
            return (short)res;
        }
        throw new AssertionError("Incompatible type");
    }

    @Override
    public boolean equals(Object e) {
        if (e == null){
            return false;
        }
        if (!(e instanceof Variable)){
            return false;
        } else {
            Variable et = (Variable)e;
            return x.equals(et.x);
        }
    }

}
