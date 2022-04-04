
public class BitAnd extends AbstractOperation  {
    public BitAnd(Operation first, Operation second){
        super(first, second);
    }

    public BitAnd(){
        super();
    }

    @Override
    public String operator() {
        return "&";
    }

    @Override
    public double eval(double first, double second) {
        throw new AssertionError("Unsupported operation");
    }

    @Override
    public int eval(int first, int second) {
        return first & second;
    }

    @Override
    public double priority(){
        return 3;
    }

    @Override
    public Operation getOp(Operation x, Operation y){
        return new BitAnd(x, y);
    }
}
