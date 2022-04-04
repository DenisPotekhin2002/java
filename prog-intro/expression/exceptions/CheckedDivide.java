package expression.exceptions;

import expression.*;

public class CheckedDivide extends Divide {
    public CheckedDivide(Operation first, Operation second){
        super(first, second);
    }

    public CheckedDivide(){
        super(null, null);
    }

    @Override
    public int evaluate(int x, int y, int z) throws Exception {
        int firtemp = first.evaluate(x, y, z);
        int sectemp = second.evaluate(x, y, z);
        if (sectemp == 0){
            throw new DBZException("Division by zero");
        }
        if (firtemp == 0){
            return 0;
        }
        if (firtemp == Integer.MIN_VALUE && sectemp == -1){
            throw new OverflowException("Constant overflow 2");
        }
        return eval(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    @Override
    public Operation getOp(Operation x, Operation y){
        return new CheckedDivide(x, y);
    }
}
