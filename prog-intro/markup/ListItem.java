package markup;

import java.util.List;

public class ListItem extends Mark{
    public ListItem(List<Mark> list) {
        super("", "", "\\item ", "", list);
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
