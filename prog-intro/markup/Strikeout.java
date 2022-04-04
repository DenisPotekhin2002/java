package markup;

import java.util.List;

class Strikeout extends Mark{
    public Strikeout(List<Mark> list) {
        super("~", "~", "\\textst{", "}", list);
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
