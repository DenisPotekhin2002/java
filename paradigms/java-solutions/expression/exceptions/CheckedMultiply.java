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
    public Object evaluate(String mode, int x, int y, int z) throws Exception {
        if (mode.equals("i")) {
                if (first == null || second == null) {
                    return null;
                }
                int firtemp = (int) first.evaluate(mode, x, y, z);
                int sectemp = (int) second.evaluate(mode, x, y, z);
                if (firtemp == 0 || sectemp == 0) {
                    return 0;
                }
                int fir = (int) first.evaluate(mode, x, y, z);
                if (fir < 0 && fir > Integer.MIN_VALUE) {
                    fir *= -1;
                } else {
                    if (fir == Integer.MIN_VALUE) {
                        if (sectemp > 1) {
                            throw new OverflowException("Constant overflow 1");
                            //return null;
                        }
                        if (sectemp < 0) {
                            throw new OverflowException("Constant overflow 2");
                            //return null;
                        }
                        return eval(mode, first.evaluate(mode, x, y, z),
                                second.evaluate(mode, x, y, z));
                    }
                }
                int sec = (int) second.evaluate(mode, x, y, z);
                if (sec < 0 && sec > Integer.MIN_VALUE) {
                    sec *= -1;
                } else {
                    if (sec == Integer.MIN_VALUE) {
                        if (firtemp > 1) {
                            throw new OverflowException("Constant overflow 1");
                            //return null;
                        }
                        if (firtemp < 0) {
                            throw new OverflowException("Constant overflow 2");
                            //return null;
                        }
                        return eval(mode, first.evaluate(mode, x, y, z),
                                second.evaluate(mode, x, y, z));
                    }
                }
                if ((Integer.MAX_VALUE / fir) < sec && (sectemp / sec) * (firtemp / fir) > 0) {
                    throw new OverflowException("Constant overflow 2");
                    //return null;
                }
                if ((Integer.MIN_VALUE / fir) > -sec && (sectemp / sec) * (firtemp / fir) < 0) {
                    throw new OverflowException("Constant overflow 1");
                    //return null;
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
    public Operation getOp(Operation x, Operation y){
        return new CheckedMultiply(x, y);
    }
}
