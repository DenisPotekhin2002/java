// :NOTE: ## Неверно производится разбор выражения, например для ~~(a & b)|~c
public class Main {

    public static void main(String[] args) throws Exception {
        StringBuilder str = new StringBuilder();
        for (String arg: args){
            str.append(arg);
        }
//        str.append("~~(a & b)|~c");
        DeMorganParser parser = new DeMorganParser();
        TripleExpression ans = parser.parse(str.toString());
        System.out.println(ans.toMiniString());
    }
}
