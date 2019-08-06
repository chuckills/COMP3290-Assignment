import java.util.LinkedList;
/** NPlist.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NPLIST rule
 *
 */
public class NPlist extends STNode
{
    public NPlist(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NPLIST);

        setRight(processPlist(tokenList, table));

    }
}
