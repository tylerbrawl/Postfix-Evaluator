import java.util.HashMap;
import java.util.Stack;
import java.util.Scanner;

public class PostfixEvaluator {
    private final static char ADD      = '+';
    private final static char SUBTRACT = '-';
    private final static char MULTIPLY = '*';
    private final static char DIVIDE   = '/';
    //D-Tier:
    private final static char MODULUS = '%';
    private final static char POWER = '^';
    //B-Tier:
    private final static char NEGATE = '~';
    private final static char FACTORIAL = '!';
    //A-Tier:
    private final static char GREATER_THAN = '>';
    private final static char LESS_THAN = '<';
    private final static char EQUALS = '=';
    private final static char BITWISE_AND = '&';
    private final static char BITWISE_OR = '|';
    private final static char TERNARY = '?';

    private Stack<Integer> stack;

    public PostfixEvaluator() {

        stack = new Stack<Integer>();
    }

	public int evaluate(String expr) throws StackTooBigException{
        int result = 0;
        String token;
        Scanner parser = new Scanner(expr);

        while (parser.hasNext()) {
            token = parser.next();
            if (isTernaryOperator(token)){
                int op3 = stack.pop();
                int op2 = stack.pop();
                int op1 = stack.pop();
                stack.push(evaluateSingleOperatorTernary(op1, op2, op3));
            }
            else if (isBinaryOperator(token)) {
                int op2 = stack.pop();
                int op1 = stack.pop();
                result = evaluateSingleOperatorBinary(token.charAt(0), op1, op2);
                stack.push(result);
            }
            else if (isRelationalOperator(token)){
                int op2 = stack.pop();
                int op1 = stack.pop();
                stack.push(evaluateSingleOperatorRelational(token.charAt(0), op1, op2));
            }
            else if(isBooleanOperator(token)){
                int op2 = stack.pop();
                int op1 = stack.pop();
                stack.push(evaluateSingleOperatorBoolean(token.charAt(0), op1, op2));
            }
            else if (isUnaryOperator(token)){
                int op = stack.pop();
                stack.push(evaluateSingleOperatorUnary(token.charAt(0), op));
            }
            else
                stack.push(Integer.parseInt(token));
        }

        parser.close();
        if(stack.size() == 1)
            return (stack.pop());
        else
            throw new StackTooBigException();
    }

    private boolean isBinaryOperator(String token) {
        return (token.charAt(0) == ADD ||
                token.charAt(0) == SUBTRACT ||
                token.charAt(0) == MULTIPLY ||
                token.charAt(0) == DIVIDE ||
                token.charAt(0) == MODULUS ||
                token.charAt(0) == POWER);
        // OR return ("+-*/".indexOf(token) >= 0);
    }

    private boolean isUnaryOperator(String token){
        return (token.charAt(0) == NEGATE ||
                token.charAt(0) == FACTORIAL);
    }

    private boolean isRelationalOperator(String token){
        return (token.charAt(0) == GREATER_THAN ||
                token.charAt(0) == LESS_THAN ||
                token.charAt(0) == EQUALS);
    }

    private boolean isBooleanOperator(String token){
        return (token.charAt(0) == BITWISE_AND ||
                token.charAt(0) == BITWISE_OR);
    }

    private boolean isTernaryOperator(String token){
        return token.charAt(0) == TERNARY;
    }

    private int evaluateSingleOperatorBinary(char operation, int op1, int op2) {
        int result = 0;

        switch (operation) {
            case ADD:
                result = op1 + op2;
                break;
            case SUBTRACT:
                result = op1 - op2;
                break;
            case MULTIPLY:
                result = op1 * op2;
                break;
            case DIVIDE:
                result = op1 / op2;
                break;
            case MODULUS:
                result = op1 % op2;
                break;
            case POWER:
                result = (int) Math.pow(op1, op2);
                break;
            default:
                System.out.println("What?");
                break;
        }

        return result;
    }

    private int evaluateSingleOperatorUnary(char operation, int op){
        switch(operation){
            case NEGATE:
                return (op * -1);
            case FACTORIAL:
                int result = op;
                for(int i = (op - 1); i > 1; i--){
                    result *= (i);
                }
                return result;
            default:
                System.out.println("What?");
                return 0;
        }
    }

    private int evaluateSingleOperatorRelational(char operation, int op1, int op2){
        switch (operation){
            case GREATER_THAN:
                if (op1 > op2)
                    return 1;
                else
                    return 0;
            case LESS_THAN:
                if (op1 < op2)
                    return 1;
                else return 0;
            case EQUALS:
                if (op1 == op2)
                    return 1;
                else return 0;
            default:
                System.out.println("What?");
                return 0;
        }
    }

    private int evaluateSingleOperatorBoolean(char operator, int op1, int op2){
        if(operator == BITWISE_AND){
            if(op1 != 0 && op2 != 0)
                return 1;
            else return 0;
        }
        else{ //This must use BITWISE_OR.
            if(op1 != 0 || op2 != 0)
                return 1;
            else return 0;
        }
    }

    private int evaluateSingleOperatorTernary(int op1, int op2, int op3){
        return (op1 != 0 ? op2 : op3);
    }
}
