package expression.exceptions;

import expression.*;

public class CheckedNegate extends Subtract {
    public CheckedNegate(Operation second){
        super(new Const(0), second);
    }

    @Override
    public Object evaluate(String mode, int x, int y, int z) throws Exception {
        if (mode.equals("i")) {
            int sec = (int)second.evaluate(mode, x, y, z);
            if (sec == Integer.MIN_VALUE) {
                throw new OverflowException("Constant overflow 2");
            } else {
                return eval(mode, first.evaluate(mode, x, y, z),
                        second.evaluate(mode, x, y, z));
            }
        } else {
            return eval(mode, first.evaluate(mode, x, y, z),
                    second.evaluate(mode, x, y, z));
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
