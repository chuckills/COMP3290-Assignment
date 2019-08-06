import java.util.LinkedList;
/** NVlist.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NVLIST rule
 *
 */
public class NVlist extends STNode
{
    public NVlist(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NVLIST);

        setRight(processVlist(tokenList, table));

    }
}
