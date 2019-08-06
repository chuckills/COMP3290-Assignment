import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NCall.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NCALL rule
 *
 */
public class NCall extends STNode
{
    public NCall(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NCALL);

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
        nextToken = tokenList.pop();
        tokenList.push(nextToken);
        if(nextToken.getTokenID() != Token.TID.TSEMI)
        {
            errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Missing \";\" after function call."));
        }

    }
}
