import java.util.LinkedList;
/** NAdd.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NADD rule
 *
 */
public class NAdd extends STNode
{
    public NAdd(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NADD);

        setRight(processExpression(tokenList, table));
    }
}
