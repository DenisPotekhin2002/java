package expression.exceptions;

import expression.Operation;

import java.math.BigInteger;

public class Mod extends AbstractOperation{
    public Mod(Operation first, Operation second){
        super(first, second);
    }

    public Mod(){
        super(null, null);
    }

    @Override
    public String operator() {
        return "mod";
    }

    @Override
    public double eval(double first, double second) {
        return first % second;
    }

    @Override
    public BigInteger eval(BigInteger first, BigInteger second){
        return first.mod(second);
    }

    @Override
    public long eval(long first, long second) {
        return first % second;
    }

    @Override
    public short eval(short first, short second) {
        return (short)(first % second);
    }

    @Override
    public int eval(int mode, int first, int second) {
        return first % second;
    }

    @Override
    public int eval(int first, int second) {
        return first % second;
    }

    @Override
    public Operation getOp(Operation x, Operation y) {
        return new Mod(x, y);
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
        return eval(mode, first.evaluate(mode, x, y, z),
                    second.evaluate(mode, x, y, z));
    }

    @Override
    public boolean isUnaryOp(){
        return false;
    }
}
