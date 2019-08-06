import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NSimv.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NSIMV rule
 *
 */
public class NSimv extends STNode
{
    public NSimv(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NSIMV);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));

        if(table.hasID(getSymbol().getName()))
        {
            getSymbol().setType(table.getIdEntry(getSymbol().getName()).getType());
        }
        else
            errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Undeclared identifier."));
    }
}
