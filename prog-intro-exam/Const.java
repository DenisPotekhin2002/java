
public class Const extends AbstractExpression {
    private final double k;
    private final int ki;

    public Const(double k){
        this.k = k;
        this.ki = (int) k;
    }

    @Override
    public String toString() {
        if (k == ki) {
            return Integer.toString(ki);
        } else {
            return Double.toString(k);
        }
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public int evaluate(int i) {
        return ki;
    }

    @Override
    public int hashCode(){
        return ki;
    }

    @Override
    public boolean equals(Object e) {
        if (e == null){
            return false;
        }
        if (!(e instanceof Const)){
            return false;
        } else {
            Const et = (Const)e;
            return k == et.k;
        }
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
        return ki;
    }
}
