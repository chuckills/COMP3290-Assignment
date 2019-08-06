import java.util.LinkedList;
/** NGeq.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NGEQ rule
 *
 */
public class NGeq extends STNode
{
    public NGeq(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NGEQ);

        setRight(processExpression(tokenList, table));
    }
}
