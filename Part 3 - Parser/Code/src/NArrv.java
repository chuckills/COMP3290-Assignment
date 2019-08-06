import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NArrv.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NArrv rule
 *
 */
public class NArrv extends STNode
{
    public NArrv(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NARRV);
        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));

        table.addSymbol(getSymbol());

        nextToken = tokenList.pop();

        switch(nextToken.getTokenID())
        {
            case TLBRK:
                setLeft(processExpression(tokenList, table));
                nextToken = tokenList.pop();

                if(nextToken.getTokenID() == Token.TID.TRBRK)
                {
                    nextToken = tokenList.pop();
                    if(nextToken.getTokenID() == Token.TID.TDOT)
                    {
                        setRight(new NSimv(tokenList, table));
                    }
                    else
                    {
                        errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Error: Missing \".\" in array member call."));
                    }
                }
                else
                {
                    errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Error: Missing \"]\" in array identifier."));
                }
                break;

            default:
        }
    }
}
