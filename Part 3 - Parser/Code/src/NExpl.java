import java.util.LinkedList;
/** NExpl.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NEXPL rule
 *
 */
public class NExpl extends STNode
{
    public NExpl(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NEXPL);
        setRight(processElist(tokenList, table));
    }
}
