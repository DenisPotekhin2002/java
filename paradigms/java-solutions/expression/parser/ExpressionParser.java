package expression.parser;

import expression.*;
import java.util.*;
import java.util.stream.*;

public class ExpressionParser{

    public ExpressionParser(){}

    public static TripleExpression parse(String str) {
        return new ExpressionParser.ExpParser(new StringSource(str)).parse();
    }

    private static class ExpParser extends BaseParser {
        Map<Character, AbstractOperation> opMap = Stream
                .of(new Add(), new Multiply(), new Divide(),
                        new BitAnd(), new BitOr(), new BitXor())
                .collect(Collectors.toMap(op -> op.operator().charAt(0), op -> op));

        boolean isUnary = true;

        public ExpParser(final CharSource source) {
            super(source);
            nextChar();
        }

        public TripleExpression parse() {
            skipWhitespace();
            final TripleExpression result = parseValue(7);
            skipWhitespace();
            return result;
        }

        private Operation parseValue(double priority) {
            int openBrackets = 0;
            Stack<Operation> stack = new Stack<>();
            do {
                skipWhitespace();
                AbstractOperation op = opMap.get(ch);
                if (op != null){
                    //expect(op.operator().substring(1));
                    if (priority <= op.priority()){
                        return stack.pop();
                    }
                    isUnary = true;
                    nextChar();
                    stack.push(op.getOp(stack.pop(), parseValue(op.priority())));
                }
                else {
                    if (test('(')) {
                        isUnary = true;
                        openBrackets += 1;
                        stack.push(parseValue(7));
                    } else if (ch == '-') {
                        if (!isUnary) {
                            isUnary = true;
                            if (priority <= 2) {
                                return stack.pop();
                            }
                            nextChar();
                            stack.push(new Subtract(stack.pop(), parseValue(2)));
                        } else {
                            nextChar();
                            stack.push(new Subtract(new Const(0), parseValue(0)));
                        }
                    } else if (ch == ')') {
                        if (openBrackets > 0) {
                            openBrackets--;
                            nextChar();
                        } else {
                            return stack.pop();
                        }
                    } else if (test('~')){
                        stack.push(new Not(parseValue(0)));
                    } else if (ch - '0' < 10 && ch - '0' >= 0) {
                        stack.push(new Const(parseNumber()));
                    } else if (!eof()) {
                        StringBuilder str = new StringBuilder();
                        String acceptable = "()+-*/~&|^0123456789 \r\t\n";
                        while (acceptable.indexOf(ch) == -1 & !eof()) {
                            str.append(ch);
                            nextChar();
                        }
                        if (str.toString().equals("count")) {
                            stack.push(new Count(parseValue(0)));
                        } else {
                            stack.push(new Variable(str.toString()));
                        }
                    }
                }
                isUnary = false;
                skipWhitespace();
            } while (!eof());
            return stack.pop();
        }

        private double parseNumber() {
            final StringBuilder sb = new StringBuilder();
            copyInteger(sb);

            if (test('.')) {
                sb.append('.');
                copyDigits(sb);
            }

            if (test('e') || test('E')) {
                sb.append('e');
                if (test('+')) {
                    // Do nothing
                } else if (test('-')) {
                    sb.append('-');
                }
                copyDigits(sb);
            }

            try {
                return Double.parseDouble(sb.toString());
            } catch (NumberFormatException e) {
                throw error("Invalid number " + sb);
            }
        }

        private void copyDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(ch);
                nextChar();
            }
        }

        private void copyInteger(final StringBuilder sb) {
            if (test('-')) {
                sb.append('-');
            }
            if (test('0')) {
                sb.append('0');
            } else if (between('1', '9')) {
                copyDigits(sb);
            } else {
                throw error("Invalid number");
            }
        }

        private void skipWhitespace() {
            while (test(' ') || test('\r') || test('\n') || test('\t')) {
                //skip
            }
        }
    }
}

