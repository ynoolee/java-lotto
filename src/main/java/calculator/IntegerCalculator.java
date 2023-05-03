package calculator;

import calculator.parser.Expression;
import calculator.parser.ExpressionParser;

public class IntegerCalculator {
    private final ExpressionParser parser;
    private final Operations operators;

    public IntegerCalculator(ExpressionParser parser,
                             Operations operations) {
        this.parser = parser;
        this.operators = operations;
    }

    public int calculate(String expressionString) {
        Expression expression = parser.parse(expressionString);

        Integer sum = expression.nextOperand();

        while(expression.hasOperator()) {
            String operator = expression.nextOperator();
            Integer number = expression.nextOperand();
            checkArithmeticOperator(operator);

            sum = operators.operationOf(operator)
                    .operate(sum, number);
        }

        return sum;
    }

    private void checkArithmeticOperator(String operator) {
        if(!operators.hasOperator(operator)) {
            throw new IllegalArgumentException("사칙 연산자가 아닙니다.");
        }
    }
}
