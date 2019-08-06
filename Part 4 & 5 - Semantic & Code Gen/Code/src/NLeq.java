import java.util.LinkedList;
/** NLeq.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NLEQ rule
 *
 */
public class NLeq extends STNode
{
    public NLeq(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NLEQ);

        setRight(processExpression(tokenList, table));
    }
}
