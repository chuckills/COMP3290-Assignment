import java.util.LinkedList;
/** NStrg.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NSTRG rule
 *
 */
public class NStrg extends STNode
{
    public NStrg(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NSTRG);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));
        getSymbol().setType("string");

        table.addSymbol(getSymbol());
    }
}
