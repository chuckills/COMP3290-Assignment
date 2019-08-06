import java.util.LinkedList;
/** NMneq.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NMNEQ rule
 *
 */
public class NMneq extends STNode
{
    public NMneq(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NMNEQ);

        setRight(processBool(tokenList, table));
    }
}
