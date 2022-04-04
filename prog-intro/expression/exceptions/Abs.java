package expression.exceptions;

import expression.Operation;

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
    public int evaluate(int x, int y, int z) throws Exception {
        if (second.evaluate(x, y, z) == Integer.MIN_VALUE) {
        /*int fir = first.evaluate(x, y, z);
        int sec = second.evaluate(x, y, z);
        if (fir > 0 && sec > 0 && (Integer.MAX_VALUE - fir) < sec){*/
            throw new Exception("Constant overflow 2");
        } else {
            return eval(0, second.evaluate(x, y, z));
        }
    }

    @Override
    public boolean isUnaryOp(){
        return true;
    }
}
