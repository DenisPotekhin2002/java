package markup;

import java.util.List;

public class OrderedList extends Mark {
    public OrderedList(List<Mark> list) {
        super("", "", "\\begin{enumerate}", "\\end{enumerate}", list);
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