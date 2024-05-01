package calculator;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    static Map<String, ExpressionElement> variables = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String inputText = scanner.nextLine().trim();
            if (inputText.isEmpty()) {
                continue;
            } else if ("/exit".equals(inputText)) {
                System.out.print("Bye!");
                return;
            } else if ("/help".equals(inputText)) {
                System.out.println("The program calculates the sum of numbers");
                continue;
            } else if (inputText.startsWith("/")) {
                System.out.println("Unknown command");
                continue;
            } else if (inputText.matches("[a-zA-Z]+")) {
                try {
                    ExpressionElement element = getVariable(inputText);
                    System.out.println(element.operand);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                continue;
            } else if (inputText.contains("=")) {
                try {
                    extractVariable(inputText.split("="));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                continue;
            }
            try {
                ExpressionVerifier.verify(inputText);
                inputText = simpleExpressions(inputText);
                Stack<ExpressionElement> postfix = InfixToPostfix.convertToPostfix(inputText,variables);
                BigInteger value = PostfixEvaluator.evaluatePostfix(postfix);
                System.out.println(value);
            } catch (Exception e) {
                System.out.println(e.getMessage() == null ? "Invalid expression" : e.getMessage());
            }
        }
    }

    static String simpleExpressions(String inputText) {
        inputText = inputText
                .replaceAll("--", "+")
                .replaceAll("\\++", "+")
                .replaceAll("\\++-", "-");
        for (Operator operator : Operator.values()) {
            inputText = inputText.replaceAll("\\" + operator.toString(), " " + operator.toString() + " ");
        }
        inputText = inputText.replaceAll("\\(", " ( ");
        inputText = inputText.replaceAll("\\)", " ) ");
        return inputText;
    }

    static ExpressionElement getVariable(String variableName) throws Exception {
        if (variables.containsKey(variableName)) {
            return variables.get(variableName);
        } else {
            throw new Exception("Unknown variable");
        }
    }

    static void extractVariable(String[] inputs) throws IllegalArgumentException {
        // Check key is valid or not
        // Key must not exist and key name must be latin letters
        String key = inputs[0].trim();
        if (!StringUtils.isValidVariableName(key)) {
            throw new IllegalArgumentException("Invalid identifier");
        }
        if (inputs.length != 2) {
            throw new IllegalArgumentException("Invalid assignment");
        }
        // Check value is valid or not
        // value can be a number or another defined key
        String value = inputs[1].trim();
        if (StringUtils.isNumber(value)) {
            BigInteger num = new BigInteger(value);
            variables.put(key, new ExpressionElement(num));
        } else if (variables.containsKey(value)) {
            variables.put(key, variables.get(value));
        } else if (value.matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("Unknown variable");
        } else {
            throw new IllegalArgumentException("Invalid assignment");
        }
    }
}
