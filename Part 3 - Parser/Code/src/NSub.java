import java.util.LinkedList;
/** NSub.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NSUB rule
 *
 */
public class NSub extends STNode
{
    public NSub(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NSUB);

        setRight(processExpression(tokenList, table));
    }
}
