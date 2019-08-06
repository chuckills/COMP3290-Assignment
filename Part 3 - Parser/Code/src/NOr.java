import java.util.LinkedList;
/** NOr.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NOR rule
 *
 */
public class NOr extends STNode
{
    public NOr(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NOR);
        setRight(processBool(tokenList, table));
    }
}