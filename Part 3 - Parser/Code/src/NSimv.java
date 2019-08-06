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

        table.addSymbol(getSymbol());
    }
}
