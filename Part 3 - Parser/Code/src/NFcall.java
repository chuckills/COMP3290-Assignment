import java.util.LinkedList;
/** NFcall.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NFCALL rule
 *
 */
public class NFcall extends STNode
{
    public NFcall(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NFCALL);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));

        table.addSymbol(getSymbol());

        nextToken = tokenList.pop();

        switch(nextToken.getTokenID())
        {
            case TLPAR:
                nextToken = tokenList.pop();
                switch(nextToken.getTokenID())
                {
                    case TRPAR:
                        break;

                    default:
                        tokenList.push(nextToken);
                        setLeft(processElist(tokenList, table));
                }
                break;

            default:
        }
    }
}
