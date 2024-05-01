package calculator;

import java.math.BigInteger;
import java.util.Stack;

class PostfixEvaluator {

    public static BigInteger evaluatePostfix(Stack<ExpressionElement> postfix) {
        Stack<BigInteger> stack = new Stack<>();

        for (ExpressionElement element : postfix) {
            if (element.isOperand()) {
                stack.push(element.operand);
            } else {
                BigInteger operand2 = stack.pop();
                BigInteger operand1 = stack.pop();
                BigInteger result = element.operator.execute(operand1, operand2);
                stack.push(result);
            }
        }
        return stack.pop();
    }
}

