package expression;

public class Not extends AbstractOperation  {
    public Not(Operation second){
        super(second);
    }

    public Not(){
        super();
    }

    @Override
    public String operator() {
        return "~";
    }

    @Override
    public double eval(double first, double second) {
        throw new AssertionError("Unsupported operation");
    }

    @Override
    public int eval(int first, int second) {
        return ~second;
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
        return new Not(y);
    }
}
