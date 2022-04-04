package markup;

import java.util.List;

class Emphasis extends Mark{

    public Emphasis(List<Mark> list) {
        super("*", "*", "\\emph{", "}", list);
    }

    @Override
    public StringBuilder toMarkdown(StringBuilder str){
        return super.toMarkdown(str);
    }

    @Override
    public StringBuilder toTex(StringBuilder str) {
        return super.toTex(str);
    }
}
