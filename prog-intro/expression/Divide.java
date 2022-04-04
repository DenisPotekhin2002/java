package expression;

public class Divide extends AbstractOperation {

    public Divide(Operation first, Operation second){
        super(first, second);
    }

    public Divide(){
        super();
    }

    @Override
    public String operator() {
        return "/";
    }

    @Override
    public double eval(double first, double second) {
        return first / second;
    }

    @Override
    public int eval(int first, int second) {
        return first / second;
    }

    @Override
    public double priority(){
        return 1;
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
        return new Divide(x, y);
    }
}
