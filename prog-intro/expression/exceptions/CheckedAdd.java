package expression.exceptions;

import expression.*;

public class CheckedAdd extends Add {
    public CheckedAdd(Operation first, Operation second){
        super(first, second);
    }

    public CheckedAdd(){
        super();
    }

    @Override
    public int evaluate(int x, int y, int z) throws Exception {
        //if (eval((double)first.evaluate(x, y, z), (double)second.evaluate(x, y, z)) < Integer.MIN_VALUE) {
        int fir = first.evaluate(x, y, z);
        int sec = second.evaluate(x, y, z);
        if (fir > 0 && sec > 0 && (Integer.MAX_VALUE - fir) < sec){
            throw new OverflowException("Constant overflow 2");
        }
        //if (eval((double)first.evaluate(x, y, z), (double)second.evaluate(x, y, z)) > Integer.MAX_VALUE) {
        if (fir < 0 && sec < 0 && (Integer.MIN_VALUE - fir) > sec){
            throw new OverflowException("Constant overflow 1");
        } else {
            return eval(first.evaluate(x, y, z), second.evaluate(x, y, z));
        }
    }

    @Override
    public Operation getOp(Operation x, Operation y){
        return new CheckedAdd(x, y);
    }
}
