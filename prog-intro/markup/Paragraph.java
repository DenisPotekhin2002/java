package markup;

import java.util.List;

class Paragraph extends Mark{
    public Paragraph(List<Mark> list) {
        super("", "", "", "", list);
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
