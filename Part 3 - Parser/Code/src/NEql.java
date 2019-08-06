import java.util.LinkedList;
/** NEql.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NEQL rule
 *
 */
public class NEql extends STNode
{
    public NEql(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NEQL);

        setRight(processExpression(tokenList, table));
    }
}
