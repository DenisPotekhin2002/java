
public abstract class AbstractOperation extends AbstractExpression {
    protected Operation first;
    protected Operation second;

    public AbstractOperation(Operation first, Operation second){
        this.first = first;
        this.second = second;
    }

    public AbstractOperation(Operation second){
        this.first = null;
        this.second = second;
    }

    public AbstractOperation(){
        this.first = null;
        this.second = null;
    }

    public abstract String operator();

    public abstract double eval(double first, double second);

    public abstract int eval(int first, int second);

    @Override
    public String toString() {
        if (first != null) {
            return String.format("(%s %s %s)", first.toString(), operator(), second.toString());
        } else {
            return String.format("(%s %s)", operator(), second.toString());
        }
    }

    @Override
    public String toMiniString(){

        return String.format("%s%s%s", firstMini(), operator(), secondMini());
    }

    protected String firstMini(){
        if (first == null){
            return "";
        }
        if (first.priority() > priority()){
            return String.format("(%s)", first.toMiniString());
        }
        return first.toMiniString();
    }

    protected String secondMini(){
        if (second.priority() < priority() ||
                second.priority() == 0 ||
                (second.operator().equals(operator()))){
            return second.toMiniString();
        }
        return String.format("(%s)", second.toMiniString());
    }

    @Override
    public int evaluate(int i){
        return eval(first.evaluate(i), second.evaluate(i));
    }

    @Override
    public int evaluate(int x, int y, int z) throws Exception {
        if (first != null) {
            return eval(first.evaluate(x, y, z), second.evaluate(x, y, z));
        } else {
            return eval(0, second.evaluate(x, y, z));
        }
    }

    @Override
    public boolean equals(Object e){
        if (e == null){
            return false;
        }
        if (!(e instanceof AbstractOperation)){
            return false;
        } else {
            AbstractOperation et = (AbstractOperation)e;
            return (first.equals(et.first) && second.equals(et.second) && operator().equals(et.operator()));
        }
    }

    @Override
    public int hashCode(){
        return first.hashCode() * operator().charAt(0) * 13 + 7 * second.hashCode();
    }

    public abstract Operation getOp(Operation x, Operation y);

    public boolean isUnaryOp(){
        return false;
    }
}
