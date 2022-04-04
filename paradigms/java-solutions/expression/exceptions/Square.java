package expression.exceptions;

import expression.Operation;

import java.math.BigInteger;

public class Square extends AbstractOperation{
    public Square(Operation second){
        super(null, second);
    }

    public Square(){
        super(null, null);
    }

    @Override
    public String operator() {
        return "square";
    }

    @Override
    public double eval(double first, double second) {
        return second * second;
    }

    @Override
    public BigInteger eval(BigInteger first, BigInteger second){
        return second.multiply(second);
    }

    @Override
    public long eval(long first, long second) {
        return second * second;
    }

    @Override
    public short eval(short first, short second) {
        return (short)(second * second);
    }

    @Override
    public int eval(int mode, int first, int second) {
        return second * second;
    }

    @Override
    public int eval(int first, int second) {
        return second * second;
    }

    @Override
    public Operation getOp(Operation x, Operation y) {
        return new Square(y);
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
        if (mode.equals("i") && (int)second.evaluate(mode, x, y, z) == Integer.MIN_VALUE) {
            throw new Exception("Constant overflow 2");
        } else {
            switch (mode) {
                case "i":
                    int sec = (int) second.evaluate(mode, x, y, z);
                    if (Math.abs(sec) > Math.sqrt(Integer.MAX_VALUE)){
                        throw new OverflowException("Constant overflow 2");
                    }
                    return eval(mode, 0, second.evaluate(mode, x, y, z));
                case "u":
                    return eval(mode, 0, second.evaluate(mode, x, y, z));
                case "s":
                    return eval(mode, (short)0, second.evaluate(mode, x, y, z));
                case "l":
                    return eval(mode, (long)0, second.evaluate(mode, x, y, z));
                case "d":
                    return eval(mode, 0.0, second.evaluate(mode, x, y, z));
                case "bi":
                    return eval(mode, BigInteger.valueOf(0), second.evaluate(mode, x, y, z));
            }
            throw new AssertionError("Incompatible type");
        }
    }

    @Override
    public boolean isUnaryOp(){
        return true;
    }
}
