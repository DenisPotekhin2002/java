
public class ExpressionException extends ParseException {

    public ExpressionException(String message, int position) {
        super(message + " at position " + position);
    }

}
