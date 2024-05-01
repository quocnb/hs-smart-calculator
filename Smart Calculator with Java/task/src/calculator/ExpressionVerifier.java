package calculator;

import java.util.regex.Pattern;

class ExpressionVerifier {
    static void verify(String expression) throws IllegalArgumentException {
        expression = expression.trim();
        for (Operator operator : Operator.values()) {
            if (expression.endsWith(operator.toString())) {
                throw new IllegalArgumentException("Invalid expression");
            }
            switch (operator) {
                case Plus, Minus:
                    continue;
            }
            if (expression.startsWith(operator.toString())) {
                throw new IllegalArgumentException("Invalid expression");
            }
        }
        Pattern pattern = Pattern.compile("\\*{2,} | /{2,}");
        if (pattern.matcher(expression).find()) {
            throw new IllegalArgumentException("Invalid expression");
        }
    }
}
