package calculator;

import java.math.BigInteger;

class ExpressionElement {
    Operator operator;
    BigInteger operand;

    public ExpressionElement(BigInteger operand) {
        this.operand = operand;
    }

    public ExpressionElement(Operator operator) {
        this.operator = operator;
    }

    public boolean isOperand() {
        return operand != null;
    }

    public boolean isOperator() {
        return operator != null;
    }
}