import java.util.LinkedList;
/** NIlist.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NILIST rule
 *
 */
public class NIlist extends STNode
{
    public NIlist(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NILIST);

        setRight(processIlist(tokenList, table));

    }
}
