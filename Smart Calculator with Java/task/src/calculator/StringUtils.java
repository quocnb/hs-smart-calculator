package calculator;

public class StringUtils {
    static boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    static boolean isValidVariableName(String input) {
        return input.matches("[a-zA-Z]+");
    }


    // Function to verify whether a character is operator symbol or not.
    static boolean isOperator(String s) {
        return Operator.isOperator(s);
    }

    // Function to verify whether an operator is right associative or not.
    static boolean isRightAssociative(String op) {
        return "^".equals(op);
    }

    // Function to get weight of an operator. An operator with higher weight will have higher precedence.
    static int getOperatorWeight(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^" -> 3;
            default -> -1; // Invalid operator
        };
    }

    // Function to perform an operation and return output.
    static boolean hasHigherPrecedence(String op1, String op2) {
        int op1Weight = getOperatorWeight(op1);
        int op2Weight = getOperatorWeight(op2);

        // If operators have equal precedence, return true if they are left associative.
        // return false, if right associative.
        // if operator is left-associative, left one should be given priority.
        if (op1Weight == op2Weight) {
            return !isRightAssociative(op1);
        }
        return op1Weight > op2Weight;
    }
}
