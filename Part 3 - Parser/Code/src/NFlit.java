import java.util.LinkedList;
/** NFlit.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NFLIT rule
 *
 */
public class NFlit extends STNode
{
    public NFlit(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NFLIT);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));
        getSymbol().setType("real");

        table.addSymbol(getSymbol());
    }
}
