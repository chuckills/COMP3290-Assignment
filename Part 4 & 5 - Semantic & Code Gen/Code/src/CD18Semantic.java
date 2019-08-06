import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;

public class CD18Semantic
{
    private String error;
    private LinkedList<SimpleEntry<Token, String>> errorList;

    public CD18Semantic()
    {

    }

    public void analyse(STNode root)
    {
        /*
        • <id> names (arrays and variables) must be declared before they are used;
        • array size – must be known at compile time;
        • strong typing exists for real variables, real arrays, boolean expressions, and arithmetic operations (such as numeric ^ INTEGER);
        • valid assignment operations;
        • actual parameters in a procedure or function call must match the type of their respective formal parameter in the procedure definition;
        • the number of actual parameters in a procedure call must be equal to the number of formal parameters in the procedure definition;
        • a function must have at least one return statement.

    Additionally:
        • <id> names must be unique at their particular block level (scoping)

         */
    }
}
