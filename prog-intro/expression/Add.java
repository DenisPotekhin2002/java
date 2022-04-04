package expression;

public class Add extends AbstractOperation {

    public Add(Operation first, Operation second){
        super(first, second);
    }

    public Add(){
        super();
    }

    @Override
    public String operator() {
        return "+";
    }

    @Override
    public double eval(double first, double second) {
        return first + second;
    }

    @Override
    public int eval(int first, int second) {
        return first + second;
    }

    @Override
    public double priority(){
        return 2;
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
        return new Add(x, y);
    }
}
