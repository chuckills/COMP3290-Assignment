import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NArrd.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NARRD rule
 *
 */
public class NArrd extends STNode
{
    public NArrd(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NARRD);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));

        nextToken = tokenList.pop();

        switch(nextToken.getTokenID())
        {
            case TIDEN:
                getSymbol().setType(nextToken.getLexeme());
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected type identifier."));
        }
        table.addSymbol(getSymbol());
    }
}