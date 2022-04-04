package expression.exceptions;

import expression.Operation;

import java.math.BigInteger;

public class Sqrt extends AbstractOperation{
    public Sqrt(Operation second){
        super(null, second);
    }

    public Sqrt(){
        super(null, null);
    }

    @Override
    public String operator() {
        return "sqrt";
    }

    @Override
    public double eval(double first, double second) {
        return Math.sqrt(second);
    }

    @Override
    public int eval(int first, int second) {
        return (int)Math.sqrt(second);
    }


    @Override
    public BigInteger eval(BigInteger first, BigInteger second){
        return first.and(second);
    }

    @Override
    public long eval(long first, long second) {
        return 0;
    }

    @Override
    public short eval(short first, short second) {
        return 0;
    }

    @Override
    public int eval(int mode, int first, int second) {
        return 0;
    }

    @Override
    public Operation getOp(Operation x, Operation y) {
        return new Sqrt(y);
    }

    @Override
    public double priority() {
        return 0;
    }

    @Override
    public boolean isAssocLeft() {
        return false;
    }

    @Override
    public boolean isAssocRight() {
        return false;
    }

    @Override
    public Object evaluate(String mode, int x, int y, int z) throws Exception {
        if (((mode.equals("i") && (int)second.evaluate(mode, x, y, z) < 0)
                || (mode.equals("d") && (double)second.evaluate(mode, x, y, z) < 0)
                || (mode.equals("bi") &&
                ((BigInteger)second.evaluate(mode, x, y, z)).compareTo(BigInteger.valueOf(0)) < 0))) {
            throw new Exception(" < 0");
        } else {
            return eval(mode, 0, second.evaluate(mode, x, y, z));
        }
    }

    @Override
    public boolean isUnaryOp(){
        return true;
    }
}
