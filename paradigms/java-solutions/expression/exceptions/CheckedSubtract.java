package expression.exceptions;

import expression.*;


public class CheckedSubtract extends Subtract {
    public CheckedSubtract(Operation first, Operation second){
        super(first, second);
    }

    @Override
    public Object evaluate(String mode, int x, int y, int z) throws Exception {
        if (mode.equals("i")) {
            int fir = (int) first.evaluate(mode, x, y, z);
            int sec = (int) second.evaluate(mode, x, y, z);
            if (sec == Integer.MIN_VALUE && fir >= 0) {
                throw new OverflowException("Constant overflow 2");
            }
            if (fir > 0 && sec < 0 && (Integer.MAX_VALUE - fir) < -sec) {
                throw new OverflowException("Constant overflow 2");
            }
            if (fir < 0 && sec > 0 && (Integer.MIN_VALUE - fir) > -sec) {
                throw new OverflowException("Constant overflow 1");
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
    public Operation getOp(Operation x, Operation y) {
        return new CheckedSubtract(x, y);
    }
}
