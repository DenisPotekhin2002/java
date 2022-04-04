package markup;

import java.util.List;

public class UnorderedList extends Mark {
    public UnorderedList(List<Mark> list) {
        super("", "", "\\begin{itemize}", "\\end{itemize}", list);
    }

    @Override
    public StringBuilder toTex(StringBuilder str) {
        return super.toTex(str);
    }

    @Override
    public StringBuilder toMarkdown(StringBuilder str) {

        throw new AssertionError("UnsupportedOperationException");
    }

}