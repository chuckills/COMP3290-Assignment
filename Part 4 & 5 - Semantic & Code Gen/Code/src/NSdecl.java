import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NSdecl.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NSDECL rule
 *
 */
public class NSdecl extends STNode
{
    public NSdecl(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NSDECL);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));

        nextToken = tokenList.pop();

        switch(nextToken.getTokenID())
        {
            case TINTG:
                getSymbol().setType("integer");
                getSymbol().setValue("0");
                break;

            case TREAL:
                getSymbol().setType("real");
                getSymbol().setValue("0.0");
                break;

            case TBOOL:
                getSymbol().setType("boolean");
                getSymbol().setValue("false");
                break;

            default:
                System.out.println("Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected type identifier.");
        }
        if(!table.hasIdInScope(getSymbol().getName()))
            table.addSymbol(getSymbol());
        else
            errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Identifier already declared in scope."));
    }
}