package expression.exceptions;

import expression.*;

public class Subtract extends AbstractOperation {

    public Subtract(Operation first, Operation second){
        super(first, second);
    }

    public Subtract(){
        super();
    }

    @Override
    public String operator() {
        return "-";
    }

    @Override
    public double eval(double first, double second) {
        if (super.second instanceof Const && super.second.toString().equals(Double.toString((double)Integer.MAX_VALUE + 1))){
            return first + Integer.MIN_VALUE;
        }
        return first - second;
    }

    @Override
    public int eval(int first, int second) {
        if (super.second instanceof Const && super.second.toString().equals(Double.toString((double)Integer.MAX_VALUE + 1))){
            return first + Integer.MIN_VALUE;
        }
        return first - second;
    }

    @Override
    public double priority(){
        return 2;
    }

    @Override
    public boolean isAssocLeft(){
        return false;
    }

    @Override
    public boolean isAssocRight(){
        return true;
    }

    @Override
    public Operation getOp(Operation x, Operation y){
        return new Subtract(x, y);
    }
}