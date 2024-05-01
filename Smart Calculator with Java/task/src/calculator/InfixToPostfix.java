package calculator;

import java.math.BigInteger;
import java.util.Map;
import java.util.Stack;

class InfixToPostfix {

    // Function to convert Infix expression to postfix
    public static Stack<ExpressionElement> convertToPostfix(String expression, Map<String, ExpressionElement> variables) {
        Stack<ExpressionElement> postfix = new Stack<>(); // Initialize postfix as empty string.
        Stack<String> operatorStack = new Stack<>();

        for (String s : expression.split("\\s+")) {
            // If character is operator, pop two elements from stack, perform operation and push the result back.
            if (StringUtils.isOperator(s)) {
                while (!operatorStack.isEmpty() &&
                        !operatorStack.peek().equals("(") &&
                        StringUtils.hasHigherPrecedence(operatorStack.peek(), s))
                {
                    ExpressionElement element = new ExpressionElement(Operator.operator(operatorStack.peek()));
                    postfix.push(element);
                    operatorStack.pop();
                }
                operatorStack.push(s);
            } else if (StringUtils.isNumber(s)) {
                postfix.push(new ExpressionElement(new BigInteger(s)));
            } else if ("(".equals(s)) {
                operatorStack.push(s);
            } else if (")".equals(s)) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    ExpressionElement element = new ExpressionElement(Operator.operator(operatorStack.pop()));
                    postfix.push(element);
                }
                operatorStack.pop();
            } else if (variables.containsKey(s)) {
                postfix.push(variables.get(s));
            } else {
                throw new IllegalArgumentException("Unknown variable");
            }
        }

        while (!operatorStack.isEmpty()) {
            ExpressionElement element = new ExpressionElement(Operator.operator(operatorStack.pop()));
            postfix.push(element);
        }
        return postfix;
    }
}
