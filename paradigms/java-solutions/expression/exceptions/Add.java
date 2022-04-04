package expression.exceptions;

import expression.*;

import java.math.BigInteger;

public class Add extends AbstractOperation {

    public Add(Operation first, Operation second){
        super(first, second);
    }

    public Add(){
        super();
    }

    @Override
    public String operator() {
        return "+";
    }

    @Override
    public double eval(double first, double second) {
        return first + second;
    }

    @Override
    public int eval(int first, int second) {
        return first + second;
    }

    @Override
    public BigInteger eval(BigInteger first, BigInteger second){
        return first.add(second);
    }

    @Override
    public long eval(long first, long second) {
        return first + second;
    }

    @Override
    public short eval(short first, short second) {
        return (short) (first + second);
    }

    @Override
    public int eval(int mode, int first, int second) {
        return first + second;
    }

    @Override
    public double priority(){
        return 2;
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
    public Operation getOp(Operation x, Operation y){
        return new Add(x, y);
    }
}
