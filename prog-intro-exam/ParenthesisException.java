
public class ParenthesisException extends ParseException{

    public ParenthesisException(String message, int position){
        super(message + " at position " + position);
    }

}
