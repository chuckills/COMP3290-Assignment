import java.util.LinkedList;
/** NSteq.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NSTEQ rule
 *
 */
public class NSteq extends STNode
{
    public NSteq(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NSTEQ);

        setRight(processBool(tokenList, table));
    }
}
