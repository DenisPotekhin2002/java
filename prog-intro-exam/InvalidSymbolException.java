
public class InvalidSymbolException extends ParseException {

    public InvalidSymbolException(String message, int position){
        super(message + " at position " + position);
    }

}
