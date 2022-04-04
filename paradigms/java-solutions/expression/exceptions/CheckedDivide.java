package expression.exceptions;

import expression.*;

import java.math.BigInteger;

public class CheckedDivide extends Divide {
    public CheckedDivide(Operation first, Operation second){
        super(first, second);
    }

    public CheckedDivide(){
        super(null, null);
    }

    @Override
    public Object evaluate(String mode, int x, int y, int z) throws Exception {
        if (mode.equals("u")){
            int temp = 0;
        }
        if (mode.equals("i")) {
            int firtemp = (int) first.evaluate(mode, x, y, z);
            int sectemp = (int) second.evaluate(mode, x, y, z);
            if (sectemp == 0) {
                throw new DBZException("Division by zero");
                //return null;
            }
            if (firtemp == 0) {
                return 0;
            }
            if (firtemp == Integer.MIN_VALUE && sectemp == -1) {
                throw new OverflowException("Constant overflow 2");
                //return null;
            }
            return eval(mode, first.evaluate(mode, x, y, z),
                    second.evaluate(mode, x, y, z));
        } else if (mode.equals("d")){
            return eval(mode, first.evaluate(mode, x, y, z),
                    second.evaluate(mode, x, y, z));
        } else if (mode.equals("bi")){
            BigInteger sectemp = (BigInteger) second.evaluate(mode, x, y, z);
            if (sectemp.compareTo(BigInteger.valueOf(0)) == 0) {
                throw new DBZException("Division by zero");
                //return null;
            }
            return eval(mode, first.evaluate(mode, x, y, z),
                    second.evaluate(mode, x, y, z));
        } else {
            if (mode.equals("u")) {
                int firtemp = (int) first.evaluate(mode, x, y, z);
                int sectemp = (int) second.evaluate(mode, x, y, z);
                if (sectemp == 0) {
                    throw new DBZException("Division by zero");
                    //return null;
                }
            } else if (mode.equals("l")){
                long firtemp = (long) first.evaluate(mode, x, y, z);
                long sectemp = (long) second.evaluate(mode, x, y, z);
                if (sectemp == 0) {
                    throw new DBZException("Division by zero");
                    //return null;
                }
            } else if (mode.equals("s")){
                short firtemp = (short) first.evaluate(mode, x, y, z);
                short sectemp = (short) second.evaluate(mode, x, y, z);
                if (sectemp == 0) {
                    throw new DBZException("Division by zero");
                    //return null;
                }
            }
            return eval(mode, first.evaluate(mode, x, y, z),
                    second.evaluate(mode, x, y, z));
        }
    }

    @Override
    public Operation getOp(Operation x, Operation y){
        return new CheckedDivide(x, y);
    }
}
