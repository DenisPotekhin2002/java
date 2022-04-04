package expression;

import java.math.BigInteger;

public class BitXor extends AbstractOperation  {
    public BitXor(Operation first, Operation second){
        super(first, second);
    }

    public BitXor(){
        super();
    }

    @Override
    public String operator() {
        return "^";
    }

    @Override
    public double eval(double first, double second) {
        throw new AssertionError("Unsupported operation");
    }

    @Override
    public int eval(int first, int second) {
        return first ^ second;
    }

    @Override
    public BigInteger eval(BigInteger first, BigInteger second){
        return first.xor(second);
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
    public double priority(){
        return 4;
    }

    @Override
    public boolean isAssocLeft(){
        return false;
    }

    @Override
    public boolean isAssocRight(){
        return false;
    }

    @Override
    public Operation getOp(Operation x, Operation y){
        return new BitXor(x, y);
    }
}
