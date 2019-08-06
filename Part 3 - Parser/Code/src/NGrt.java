import java.util.LinkedList;
/** NGrt.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NGRT rule
 *
 */
public class NGrt extends STNode
{
    public NGrt(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NGRT);

        setRight(processExpression(tokenList, table));
    }
}
