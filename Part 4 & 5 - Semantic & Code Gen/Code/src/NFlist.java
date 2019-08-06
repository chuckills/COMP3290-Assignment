import java.util.LinkedList;
/** NFlist.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NFLIST rule
 *
 */
public class NFlist extends STNode
{
    public NFlist(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NFLIST);

        setRight(processFlist(tokenList, table));

    }
}
