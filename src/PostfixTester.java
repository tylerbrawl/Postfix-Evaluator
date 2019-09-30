import java.util.EmptyStackException;
import java.util.Scanner;

public class PostfixTester    
{
    /**
     * Reads and evaluates multiple postfix expressions.
     */
    public static void main(String[] args)
    {
        String expression, again;
        double result;
    
        Scanner in = new Scanner(System.in);

        System.out.println("Enhanced Postfix Evaluator - Tyler Nichols");
        System.out.println();
      
        do
        {  
            PostfixEvaluator evaluator = new PostfixEvaluator();
            
			System.out.println("Enter a valid post-fix expression one token " +
							   "at a time with a space between each token (e.g. 5 4 + 3 2 1 - + *).");
			System.out.println("Each character must be an integer or an operator (+, -, *, /, %, ^, ~, " +
                    "!, >, <, =, &, |, or ?).");
            expression = in.nextLine();
            try{
                result = evaluator.evaluate(expression);

                System.out.println();
                System.out.println("That expression equals " + (int) result + ".");
            }
            catch (StackTooBigException e){
                System.out.println();
                System.out.println(e.getMessage());
            }
            catch (EmptyStackException e){
                System.out.println();
                System.out.println("ERROR: There are too many operators and not enough numbers.");
            }
            catch (NumberFormatException e){
                System.out.println();
                System.out.println("ERROR: You may only enter integer values. Numbers and operators must also be " +
                        "separated from each other by a space character.");
            }

            System.out.print("Evaluate another expression [Y/N]? ");
            again = in.nextLine();
            System.out.println();
        }
        while (again.equalsIgnoreCase("y"));
        
        in.close();
  }
}
