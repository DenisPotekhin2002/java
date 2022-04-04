package expression.exceptions;

import expression.*;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(Operation first, Operation second){
        super(first, second);
    }

    public CheckedMultiply(){
        super(null, null);
    }

    @Override
    public int evaluate(int x, int y, int z) throws Exception {
        int firtemp = first.evaluate(x, y, z);
        int sectemp = second.evaluate(x, y, z);
        if (firtemp == 0 || sectemp == 0){
            return 0;
        }
        int fir = first.evaluate(x, y, z);
        if (fir < 0 && fir > Integer.MIN_VALUE){
            fir *= -1;
        } else {
            if (fir == Integer.MIN_VALUE) {
                if (sectemp > 1){
                    throw new OverflowException("Constant overflow 1");
                }
                if (sectemp < 0){
                    throw new OverflowException("Constant overflow 2");
                }
                return eval(first.evaluate(x, y, z), second.evaluate(x, y, z));
            }
        }
        int sec = second.evaluate(x, y, z);
        if (sec < 0 && sec > Integer.MIN_VALUE){
            sec *= -1;
        } else {
            if (sec == Integer.MIN_VALUE) {
                if (firtemp > 1){
                    throw new OverflowException("Constant overflow 1");
                }
                if (firtemp < 0){
                    throw new OverflowException("Constant overflow 2");
                }
                return eval(first.evaluate(x, y, z), second.evaluate(x, y, z));
            }
        }
        if ((Integer.MAX_VALUE / fir) < sec && (sectemp / sec) * (firtemp / fir) > 0){
            throw new OverflowException("Constant overflow 2");
        }
        if ((Integer.MIN_VALUE / fir) > -sec && (sectemp / sec) * (firtemp / fir) < 0){
            throw new OverflowException("Constant overflow 1");
        } else {
            return eval(first.evaluate(x, y, z), second.evaluate(x, y, z));
        }
    }

    @Override
    public Operation getOp(Operation x, Operation y){
        return new CheckedMultiply(x, y);
    }
}
