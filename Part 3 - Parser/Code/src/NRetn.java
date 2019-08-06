import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NRetn.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NRETN rule
 *
 */
public class NRetn extends STNode
{
    public NRetn(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NRETN);

        Token nextToken = tokenList.pop();

        switch(nextToken.getTokenID())
        {
            case TIDEN:
            case TILIT:
            case TFLIT:
            case TTRUE:
            case TFALS:
            case TLPAR:
                tokenList.push(nextToken);
                setLeft(processExpression(tokenList, table));
                nextToken = tokenList.pop();
                break;
        }

        switch(nextToken.getTokenID())
        {
            case TSEMI:
                tokenList.push(nextToken);
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Missing \";\" after statement."));
        }
    }
}
