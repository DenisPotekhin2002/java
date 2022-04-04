package markup;

import java.util.List;

class Strong extends Mark{
    public Strong(List<Mark> list) {
        super("__", "__", "\\textbf{", "}", list);
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
