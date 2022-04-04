package expression.exceptions;

import expression.Operation;

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
    public int evaluate(int x, int y, int z) throws Exception {
        if (second.evaluate(x, y, z) < 0) {
        /*int fir = first.evaluate(x, y, z);
        int sec = second.evaluate(x, y, z);
        if (fir > 0 && sec > 0 && (Integer.MAX_VALUE - fir) < sec){*/
            throw new Exception(" < 0");
        } else {
            return eval(0, second.evaluate(x, y, z));
        }
    }

    @Override
    public boolean isUnaryOp(){
        return true;
    }
}
