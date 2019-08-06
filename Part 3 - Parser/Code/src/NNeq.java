import java.util.LinkedList;
/** NNeq.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NNEQ rule
 *
 */
public class NNeq extends STNode
{
    public NNeq(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NNEQ);

        setRight(processExpression(tokenList, table));
    }
}
