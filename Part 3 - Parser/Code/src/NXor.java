import java.util.LinkedList;
/** NXor.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NXOR rule
 *
 */
public class NXor extends STNode
{
    public NXor(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NXOR);
        setRight(processBool(tokenList, table));
    }
}
