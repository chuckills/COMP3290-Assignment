import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NRtype.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NRTYPE rule
 *
 */
public class NRtype extends STNode
{
    public NRtype(LinkedList<Token> tokenList, SymbolTable globalTable)
    {
        super(NID.NRTYPE);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));

        SymbolTable scopeTable = new SymbolTable(globalTable);

        nextToken = tokenList.pop();

        switch(nextToken.getTokenID())
        {
            case TIDEN:
                tokenList.push(nextToken);
                setLeft(processFlist(tokenList, scopeTable));
                for(TableEntry entry : scopeTable.getTable())
                {
                    TableEntry globalEntry = new TableEntry(getSymbol().getName(), entry);
                    globalTable.addSymbol(globalEntry);
                }
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected identifier"));
        }

        globalTable.addType(getSymbol(), 'c');
    }
}