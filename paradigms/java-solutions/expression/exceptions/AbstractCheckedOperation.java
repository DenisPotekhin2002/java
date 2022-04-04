package expression.exceptions;

import expression.*;

public abstract class AbstractCheckedOperation extends AbstractOperation {
    protected Operation first;
    protected Operation second;

    public AbstractCheckedOperation(Operation first, Operation second){
        this.first = first;
        this.second = second;
    }

    public AbstractCheckedOperation(Operation second){
        this.first = null;
        this.second = second;
    }

    public AbstractCheckedOperation(){
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

        return String.format("%s %s %s", firstMini(), operator(), secondMini());
    }

    protected String firstMini(){
        if (first.priority() > priority()){
            return String.format("(%s)", first.toMiniString());
        }
        return first.toMiniString();
    }

    protected String secondMini(){
        if (second.priority() < priority() ||
                (second.priority() == priority() && isAssocLeft() && second.isAssocRight()) ||
                second.priority() == 0){
            return second.toMiniString();
        }
        return String.format("(%s)", second.toMiniString());
    }

    @Override
    public int evaluate(int i){
        return eval(first.evaluate(i), second.evaluate(i));
    }

    @Override
    public boolean equals(Object e){
        if (e == null){
            return false;
        }
        if (!(e instanceof AbstractCheckedOperation)){
            return false;
        } else {
            AbstractCheckedOperation et = (AbstractCheckedOperation)e;
            return (first.equals(et.first) && second.equals(et.second) && operator().equals(et.operator()));
        }
    }

    @Override
    public int hashCode(){
        return first.hashCode() * operator().charAt(0) * 13 + 7 * second.hashCode();
    }

    public abstract AbstractCheckedOperation getOp(AbstractCheckedOperation x, AbstractCheckedOperation y);
}

