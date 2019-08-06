import java.util.LinkedList;
/** NMul.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NMUL rule
 *
 */
public class NMul extends STNode
{
    public NMul(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NMUL);
        setRight(processTerm(tokenList, table));
    }
}
