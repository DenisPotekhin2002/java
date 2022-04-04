package expression.exceptions;

import expression.*;

public class Count extends AbstractOperation  {
    public Count(Operation second){
        super(second);
    }

    public Count(){
        super();
    }

    @Override
    public String operator() {
        return "count ";
    }

    @Override
    public double eval(double first, double second) {
        throw new AssertionError("Unsupported operation");
    }

    @Override
    public int eval(int first, int second) {
        String temp = Integer.toBinaryString(second);
        int ans = 0;
        for (int j = 0; j < temp.length(); j++){
            if (temp.charAt(j) == '1'){
                ans++;
            }
        }
        return ans;
    }

    @Override
    public int evaluate(int x, int y, int z) throws Exception {
        return eval(0, second.evaluate(x, y, z));
    }

    @Override
    public double priority(){
        return 0;
    }

    @Override
    public boolean isAssocLeft(){
        return false;
    }

    @Override
    public boolean isAssocRight(){
        return false;
    }

    @Override
    public Operation getOp(Operation x, Operation y){
        return new Count(y);
    }

    @Override
    public boolean isUnaryOp(){
        return true;
    }
}
