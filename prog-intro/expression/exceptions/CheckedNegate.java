package expression.exceptions;

import expression.*;

public class CheckedNegate extends Subtract {
    public CheckedNegate(Operation second){
        super(new Const(0), second);
    }

    @Override
    public int evaluate(int x, int y, int z) throws Exception {
        int sec = second.evaluate(x, y, z);
        if (sec == Integer.MIN_VALUE){
            throw new OverflowException("Constant overflow 2");
        } else {
            return eval(first.evaluate(x, y, z), second.evaluate(x, y, z));
        }
    }

    @Override
    public boolean isUnaryOp(){
        return true;
    }

    @Override
    public Operation getOp(Operation x, Operation y) {
        return new CheckedNegate(y);
    }
}
