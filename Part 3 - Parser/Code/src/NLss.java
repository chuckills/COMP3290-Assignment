import java.util.LinkedList;
/** NLss.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NLSS rule
 *
 */
public class NLss extends STNode
{
    public NLss(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NLSS);

        setRight(processExpression(tokenList, table));
    }
}
