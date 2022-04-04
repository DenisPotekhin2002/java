package expression.exceptions;

import expression.*;

import java.math.BigInteger;

public class Subtract extends AbstractOperation {

    public Subtract(Operation first, Operation second){
        super(first, second);
    }

    public Subtract(){
        super();
    }

    @Override
    public String operator() {
        return "-";
    }

    @Override
    public double eval(double first, double second) {
        if (super.second instanceof Const && super.second.toString().equals(Double.toString((double)Integer.MAX_VALUE + 1))){
            return first + Integer.MIN_VALUE;
        }
        return first - second;
    }

    @Override
    public int eval(int first, int second) {
        if (super.second instanceof Const && super.second.toString().equals(Double.toString((double)Integer.MAX_VALUE + 1))){
            return first + Integer.MIN_VALUE;
        }
        return first - second;
    }


    @Override
    public BigInteger eval(BigInteger first, BigInteger second){
        return first.subtract(second);
    }

    @Override
    public long eval(long first, long second) {
        return first - second;
    }

    @Override
    public short eval(short first, short second) {
        return (short) (first - second);
    }

    @Override
    public int eval(int mode, int first, int second) {
        return first - second;
    }

    @Override
    public double priority(){
        return 2;
    }

    @Override
    public boolean isAssocLeft(){
        return false;
    }

    @Override
    public boolean isAssocRight(){
        return true;
    }

    @Override
    public Operation getOp(Operation x, Operation y){
        return new Subtract(x, y);
    }
}