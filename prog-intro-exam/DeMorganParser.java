
import java.util.*;

public class DeMorganParser{

    public DeMorganParser(){}

    public static TripleExpression parse(String str) throws Exception{
        try {
            return new DeMorganParser.ExpParser(new StringSource(str)).parse();
        } catch (Exception e){
            System.err.println(str);
            throw e;
        }
    }

    private static class ExpParser extends BaseParser {

        int allOpenBrackets = 0;

        int allNegations = 0;

        int position = 0;

        AbstractOperation tempOperation = null;

        // :NOTE: - Не соблюдение правил именования
        public ExpParser(final CharSource source) {
            super(source);
            nextChar();
        }

        public TripleExpression parse() throws Exception {
            skipWhitespace();
            final TripleExpression result = parseValue(7, false);
            skipWhitespace();
            return result;
        }

        private Operation parseOp(double priority, AbstractOperation op, Stack<Operation> stack, boolean negative) throws Exception{
            if (priority <= op.priority() && op.priority() > 0){
                tempOperation = op;
                return null;
            }
            skipWhitespace();
            Operation temp = null;
            if (!eof()) {
                temp = parseValue(op.priority(), negative);
            }
            if (!op.isUnaryOp() && stack.empty()) {
                if (temp == null) {
                    throw new ExpressionException("Bare " + op.operator(), position);
                } else {
                    throw new ExpressionException("No first argument " + op.operator(), position);
                }
            }
            if (temp == null){
                throw new ExpressionException("No last argument " + op.operator(), position);
            }
            if (!op.isUnaryOp()) {
                return op.getOp(stack.pop(), temp);
            } else {
                return op.getOp(null, temp);
            }
        }

        private Operation parseValue(double priority, boolean negative) throws Exception {
            int openBrackets = 0;
            int negations;
            if (!negative){
                negations = 0;
            } else {
                negations = 1;
                allNegations++;
            }
            // :NOTE: - Использование java.util.Stack
            Stack<Operation> stack = new Stack<>();
            do {
                skipWhitespace();
                if (tempOperation != null){
                    AbstractOperation tempOp = tempOperation;
                    tempOperation = null;
                    Operation temp = parseOp(priority, tempOp, stack, negative);
                    if (temp == null){
                        return stack.pop();
                    }
                    stack.push(temp);
                } else {
                    if (test('(')) {
                        position++;
                        openBrackets++;
                        // :NOTE: * Явный подсчёт скобок
                        allOpenBrackets++;
                        stack.push(parseValue(7, false));
                    } else if (test('~')) {
                        position++;
                        stack.push(parseValue(0, true));
                    } else if (test('&')) {
                        // :NOTE: * Дублирование кода
                        position++;
                        Operation temp;
                        if (allNegations % 2 == 0) {
                            temp = parseOp(priority, new BitAnd(), stack, negative);
                        } else {
                            temp = parseOp(priority, new BitOr(), stack, negative);
                        }
                        if (temp == null){
                            if (stack.empty()){
                                throw new ExpressionException("No middle argument", position);
                            }
                            return stack.pop();
                        }
                        stack.push(temp);
                    } else if (test('|')) {
                        position++;
                        Operation temp;
                        if (allNegations % 2 == 0) {
                            temp = parseOp(priority, new BitOr(), stack, negative);
                        } else {
                            temp = parseOp(priority, new BitAnd(), stack, negative);
                        }
                        if (temp == null){
                            if (stack.empty()){
                                throw new ExpressionException("No middle argument", position);
                            }
                            return stack.pop();
                        }
                        stack.push(temp);
                    } else if (ch == ')') {
                        if (openBrackets > 0) {
                            if (negations > 0) {
                                negations--;
                                allNegations--;
                            }
                            openBrackets--;
                            allOpenBrackets--;
                            nextChar();
                            position++;
                        } else {
                            if (allOpenBrackets == 0) {
                                throw new ParenthesisException("No opening parenthesis", position);
                            }
                            if (stack.empty()) {
                                throw new ParenthesisException("Empty brackets", position);
                            }
                            return stack.pop();
                        }
                    } else if (!eof()) {
                        StringBuilder str = new StringBuilder();
                        String acceptable = "()&| \r\t\n";
                        while (acceptable.indexOf(ch) == -1 & !eof()) {
                            if (ch == '~'){
                                throw new ExpressionException("Invalid use of unary ~", position + 1);
                            }
                            str.append(ch);
                            nextChar();
                            position++;
                        }
                        if (str.toString().equals("1")){
                            // :NOTE: - Создание дублей констант
                            if (allNegations % 2 == 0) {
                                stack.push(new Const(1));
                            } else {
                                stack.push(new Const(0));
                            }
                        } else if (str.toString().equals("0")){
                            if (allNegations % 2 == 0) {
                                stack.push(new Const(0));
                            } else {
                                stack.push(new Const(1));
                            }
                        } else if (str.length() == 1 && str.charAt(0) - 'a' >= 0 && str.charAt(0) - 'a' < 26) {
                            // :NOTE: - Создание дублей переменных
                            if (allNegations % 2 == 0) {
                                stack.push(new Variable(str.toString()));
                            } else {
                                stack.push(new Not(new Variable(str.toString())));
                            }
                        } else {
                            throw new InvalidSymbolException("Invalid Symbol", position);
                        }
                        if (negations > 0){
                            negations--;
                            allNegations--;
                        }
                    }
                }
                skipWhitespace();
            } while (!eof() || tempOperation != null);
            if (openBrackets > 0){
                throw new ParenthesisException("No closing parenthesis", position);
            }
            if (!stack.empty()) {
                return stack.pop();
            } else {
                throw new ExpressionException("No last argument", position);
            }
        }

        private void skipWhitespace() {
            while (test(' ') || test('\r') || test('\n') || test('\t')) {
                position++;
            }
        }
    }
}

