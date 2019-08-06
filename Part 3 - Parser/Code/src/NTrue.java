import java.util.LinkedList;
/** NTrue.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NTRUE rule
 *
 */
public class NTrue extends STNode
{
    public NTrue(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NTRUE);
    }
}
