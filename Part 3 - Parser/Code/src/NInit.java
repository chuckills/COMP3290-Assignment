import java.util.LinkedList;
/** NInit.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NINIT rule
 *
 */
public class NInit extends STNode
{
    public NInit(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NINIT);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));

    }


}
