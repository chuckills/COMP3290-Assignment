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
                break;

            case TREAL:
                getSymbol().setType("real");
                break;

            case TBOOL:
                getSymbol().setType("boolean");
                break;

            default:
                System.out.println("Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected type identifier.");
        }
        table.addSymbol(getSymbol());
    }
}