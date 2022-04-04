package expression.exceptions;

import expression.Operation;

import java.math.BigInteger;

public class Abs extends AbstractOperation{
    public Abs(Operation second){
        super(null, second);
    }

    public Abs(){
        super(null, null);
    }

    @Override
    public String operator() {
        return "abs";
    }

    @Override
    public double eval(double first, double second) {
        if (second < 0){
            return -second;
        } else {
            return second;
        }
    }

    @Override
    public BigInteger eval(BigInteger first, BigInteger second){
        return second.abs();
    }

    @Override
    public long eval(long first, long second) {
        if (second >= 0) {
            return second;
        } else {
            return -second;
        }
    }

    @Override
    public short eval(short first, short second) {
        if (second >= 0) {
            return second;
        } else {
            return (short)-second;
        }
    }

    @Override
    public int eval(int mode, int first, int second) {
        if (second >= 0) {
            return second;
        } else {
            return -second;
        }
    }

    @Override
    public int eval(int first, int second) {
        if (second < 0){
            return -second;
        } else {
            return second;
        }
    }

    @Override
    public Operation getOp(Operation x, Operation y) {
        return new Abs(y);
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
                case "u":
                    return eval(mode, 0, second.evaluate(mode, x, y, z));
                case "l":
                    return eval(mode, (long)0, second.evaluate(mode, x, y, z));
                case "s":
                    return eval(mode, (short)0, second.evaluate(mode, x, y, z));
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
