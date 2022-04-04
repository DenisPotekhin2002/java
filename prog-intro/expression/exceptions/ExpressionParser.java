package expression.exceptions;

import expression.*;
import expression.parser.*;
import java.util.*;
import java.util.stream.*;

public class ExpressionParser implements Parser {

    public ExpressionParser(){}

    public TripleExpression parse(String str) throws Exception {
        return new ExpressionParser.ExpParser(new StringSource(str)).parse();
    }

    private static class ExpParser extends BaseParser {
        Map<Character, AbstractOperation> opMap1 = Stream
                .of(new Not(), new CheckedAdd(), new CheckedMultiply(), new CheckedDivide())
                .collect(Collectors.toMap(op -> op.operator().charAt(0), op -> op));

        Map<String, AbstractOperation> opMap2 = Stream
                .of(new Abs(), new Sqrt(), new Count(), new Min(), new Max())
                .collect(Collectors.toMap(op -> op.operator(), op -> op));

        boolean isUnary = true;

        int AllOpenBrackets = 0;

        AbstractOperation tempOperation = null;

        boolean isWhitespaceBeforeMinMax = true;

        public ExpParser(final CharSource source) {
            super(source);
            nextChar();
        }

        public TripleExpression parse() throws Exception {
            skipWhitespace();
            String acceptable = "xyzasc()+-*/~&|^0123456789 \n\t\n";
            if (acceptable.indexOf(ch) == -1){
                throw new InvalidStartSymbolException("Invalid start symbol: " + ch);
            }
            final TripleExpression result = parseValue(7);
            skipWhitespace();
            return result;
        }

        private Operation parseOp(double priority, AbstractOperation op, Stack<Operation> stack) throws Exception{
            if (priority <= op.priority() && op.priority() > 0){
                tempOperation = op;
                return null;
            }
            if (op.operator().charAt(0) == 'm'){
                if (ch != ' ' && !isWhitespaceBeforeMinMax) {
                    throw new ExpressionException("before or after Min/Max should be a whitespace");
                } else {
                    isWhitespaceBeforeMinMax = true;
                }
            }
            isUnary = true;
            skipWhitespace();
            Operation temp = null;
            if (!eof()) {
                temp = parseValue(op.priority());
            }
            if (!op.isUnaryOp() && stack.empty()) {
                if (temp == null) {
                    throw new ExpressionException("Bare " + op.operator());
                } else {
                    throw new ExpressionException("No first argument " + op.operator());
                }
            }
            if (temp == null){
                throw new ExpressionException("No last argument " + op.operator());
            }
            if (!op.isUnaryOp()) {
                return op.getOp(stack.pop(), temp);
            } else {
                return op.getOp(null, temp);
            }
        }

        private Operation parseValue(double priority) throws Exception {
            int openBrackets = 0;
            Stack<Operation> stack = new Stack<>();
            do {
                skipWhitespace();
                if (tempOperation != null){
                    AbstractOperation tempOp = tempOperation;
                    tempOperation = null;
                    Operation temp = parseOp(priority, tempOp, stack);
                    if (temp == null){
                        return stack.pop();
                    }
                    stack.push(temp);
                } else {
                AbstractOperation op = opMap1.get(ch);
                if (op != null){
                    nextChar();
                    Operation temp = parseOp(priority, op, stack);
                    if (temp == null){
                        if (stack.empty()){
                            throw new ExpressionException("No middle argument " + op.operator());
                        }
                        return stack.pop();
                    }
                    stack.push(temp);
                }
                else {
                    if (test('(')) {
                        isUnary = true;
                        openBrackets ++;
                        AllOpenBrackets++;
                        stack.push(parseValue(7));
                    } else if (ch == '-') {
                        if (!isUnary) {
                            isUnary = true;
                            if (priority <= 2) {
                                return stack.pop();
                            }
                            nextChar();
                            Operation temp = null;
                            if (!eof()) {
                                temp = parseValue(2);
                            }
                            if (stack.empty()) {
                                if (temp == null) {
                                    throw new ExpressionException("Bare -");
                                } else {
                                    throw new ExpressionException("No first argument -");
                                }
                            }
                            if (temp == null){
                                throw new ExpressionException("No last argument -");
                            }
                            stack.push(new CheckedSubtract(stack.pop(), temp));
                        } else {
                            nextChar();
                            skipWhitespace();
                            if (ch - '0' < 10 && ch - '0' >= 0) {
                                stack.push(new Const(parseNumber(new StringBuilder("-"))));
                            } else {
                                Operation temp = null;
                                if (!eof()) {
                                    temp = parseValue(0);
                                }
                                if (stack.empty()) {
                                    if (temp == null) {
                                        throw new ExpressionException("Bare -");
                                    }
                                }
                                if (temp == null){
                                    throw new ExpressionException("No last argument -");
                                }
                                stack.push(new CheckedNegate(temp));
                            }
                        }
                    } else if (ch == ')') {
                        if (openBrackets > 0) {
                            openBrackets--;
                            AllOpenBrackets--;
                            nextChar();
                        } else {
                            if (AllOpenBrackets == 0){
                                throw new ParenthesisException("No opening parenthesis");
                            }
                            if (stack.empty()){
                                throw new ParenthesisException("Empty brackets");
                            }
                            return stack.pop();
                        }
                    } else if (ch - '0' < 10 && ch - '0' >= 0) {
                        stack.push(new Const(parseNumber(new StringBuilder())));
                        if (ch == 'm'){
                            isWhitespaceBeforeMinMax = false;
                        }
                        skipWhitespace();
                        if (ch - '0' < 10 && ch - '0' >= 0){
                            throw new InvalidMiddleSymbolException("Whitespace in numbers");
                        }
                    } else if (!eof()) {
                        StringBuilder str = new StringBuilder();
                        String acceptable = "()+-*/~&|^0123456789 \r\t\n";
                        while (acceptable.indexOf(ch) == -1 & !eof()) {
                            str.append(ch);
                            nextChar();
                        }
                        AbstractOperation op2 = opMap2.get(str.toString());
                        if (op2 != null){
                            //expect(op.operator().substring(1));
                            Operation temp = parseOp(priority, op2, stack);
                            if (temp == null){
                                if (stack.empty()){
                                    throw new InvalidMiddleSymbolException("No middle argument " + op2.operator());
                                }
                                return stack.pop();
                            }
                            stack.push(temp);
                        } else if (str.toString().equals("x") || str.toString().equals("y") || str.toString().equals("z")) {
                            stack.push(new Variable(str.toString()));
                        } else {
                            nextChar();
                            if (!eof()) {
                                throw new InvalidMiddleSymbolException("Invalid middle symbol: " + ch);
                            } else {
                                throw new InvalidEndSymbolException("Invalid end symbol: " + ch);
                            }
                        }
                    }
                }
                }
                isUnary = false;
                skipWhitespace();
            } while (!eof() || tempOperation != null);
            if (openBrackets > 0){
                throw new ParenthesisException("No closing parenthesis");
            }
            return stack.pop();
        }

        private int parseNumber(StringBuilder str) throws Exception {
            final StringBuilder sb = new StringBuilder(str);
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
                    skipWhitespace();
                }
                copyDigits(sb);
            }

            try {
                if (sb.charAt(0) == '-'){
                    if (sb.length() > 11){
                        throw new OverflowException("Constant overflow 1 " + sb);
                    }
                    if (sb.length() == 11){
                        String check = Integer.toString(Integer.MIN_VALUE);
                        for (int i = 0; i < sb.length(); i++){
                            if (sb.charAt(i) < check.charAt(i)){
                                break;
                            }
                            if (sb.charAt(i) > check.charAt(i)){
                                throw new OverflowException("Constant overflow 1 " + sb);
                            }
                        }
                    }
                } else {
                    if (sb.length() > 10){
                        throw new OverflowException("Constant overflow 2 " + sb);
                    }
                    if (sb.length() == 10){
                        String check = Integer.toString(Integer.MAX_VALUE);
                        for (int i = 0; i < sb.length(); i++){
                            if (sb.charAt(i) < check.charAt(i)){
                                break;
                            }
                            if (sb.charAt(i) > check.charAt(i)){
                                throw new OverflowException("Constant overflow 2 " + sb);
                            }
                        }
                    }
                }
                return Integer.parseInt(sb.toString());
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

