import java.util.LinkedList;
/** NIlit.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NILIT rule
 *
 */
public class NIlit extends STNode
{
    public NIlit(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NILIT);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));
        getSymbol().setType("integer");

        table.addSymbol(getSymbol());
    }
}
