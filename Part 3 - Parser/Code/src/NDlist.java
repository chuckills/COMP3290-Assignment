import java.util.LinkedList;
/** NDlist.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NDLIST rule
 *
 */
public class NDlist extends STNode
{
    public NDlist(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NDLIST);

        setRight(processDlist(tokenList, table));

    }
}
