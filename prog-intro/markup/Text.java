package markup;

//import java.util.List;

class Text extends Mark{
    private final StringBuilder str;

    public Text(String string){
        super("","","","", null);
        str = new StringBuilder(string);
    }

    @Override
    public StringBuilder toMarkdown(StringBuilder str){
        return this.str;
    }

    @Override
    public StringBuilder toTex(StringBuilder str) {
            return this.str;
    }

}
