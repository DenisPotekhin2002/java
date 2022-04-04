
public class Variable extends AbstractExpression {
    private final String x;

    public Variable(String x){
        this.x = x;
    }

    @Override
    public String toString() {
        return x;
    }

    @Override
    public String toMiniString() {
        return x;
    }

    @Override
    public int evaluate(int i) {
        return i;
    }

    @Override
    public int hashCode(){
        return x.hashCode();
    }

    @Override
    public double priority(){
        return 0;
    }

    @Override
    public String operator() {
        return null;
    }

    @Override
    public int evaluate(int x, int y, int z) {

        if (this.x.charAt(0) == 'x'){
            return x;
        } else if (this.x.charAt(0) == 'y'){
            return y;
        } else if (this.x.charAt(0) == 'z'){
            return z;
        } else {
            throw new AssertionError("Wrong name of variable");
        }
    }

    @Override
    public boolean equals(Object e) {
        if (e == null){
            return false;
        }
        if (!(e instanceof Variable)){
            return false;
        } else {
            Variable et = (Variable)e;
            return x.equals(et.x);
        }
    }

}
