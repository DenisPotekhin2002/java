package expression;

public class Multiply extends AbstractOperation {

    public Multiply(Operation first, Operation second){
        super(first, second);
    }

    public Multiply(){
        super();
    }

    @Override
    public String operator() {
        return "*";
    }

    @Override
    public double eval(double first, double second) {
        return first * second;
    }

    @Override
    public int eval(int first, int second) {
        return first * second;
    }

    @Override
    public double priority(){
        return 1;
    }

    @Override
    public boolean isAssocLeft(){
        return true;
    }

    @Override
    public boolean isAssocRight(){
        return true;
    }

    @Override
    public Operation getOp(Operation x, Operation y){
        return new Multiply(x, y);
    }
}