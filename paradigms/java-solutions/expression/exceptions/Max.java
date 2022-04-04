package expression.exceptions;

import expression.Operation;

import java.math.BigInteger;

public class Max extends AbstractOperation{
    public Max(Operation first, Operation second){
        super(first, second);
    }

    public Max(){
        super(null, null);
    }

    @Override
    public String operator() {
        return "max";
    }

    @Override
    public double eval(double first, double second) {
        if (second > first){
            return second;
        } else {
            return first;
        }
    }

    @Override
    public int eval(int first, int second) {
        if (second > first){
            return second;
        } else {
            return first;
        }
    }

    @Override
    public BigInteger eval(BigInteger first, BigInteger second){
        if (first.compareTo(second) >= 0){
            return first;
        } else {
            return second;
        }
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
        return new Max(x, y);
    }

    @Override
    public double priority() {
        return 6;
    }

    @Override
    public boolean isAssocLeft() {
        return false;
    }

    @Override
    public boolean isAssocRight() {
        return false;
    }
}
