import java.util.LinkedList;
/** NDiv.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NDIV rule
 *
 */
public class NDiv extends STNode
{
    public NDiv(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NDIV);
        setRight(processTerm(tokenList, table));
    }
}
