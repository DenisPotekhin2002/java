package markup;

import java.util.List;

class Mark {
    protected String mark1;
    protected String mark2;
    protected String tex1;
    protected String tex2;
    protected List<Mark> list;

    protected Mark(final String mark1, final String mark2, final String tex1, final String tex2, final List<Mark> list){
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.tex1 = tex1;
        this.tex2 = tex2;
        this.list = list;
    }

    StringBuilder toMarkdown(StringBuilder str){
        str.append(mark1);
        for (Mark mark : list){
            str.append(mark.toMarkdown(new StringBuilder()));
        }
        str.append(mark2);
        return str;
    }
    StringBuilder toTex(StringBuilder str){
        str.append(tex1);
        for (Mark mark : list){
            str.append(mark.toTex(new StringBuilder()));
        }
        str.append(tex2);
        return str;
    }
}
