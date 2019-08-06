import java.util.LinkedList;
/** NSdlst.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NSDLST rule
 *
 */
public class NSdlst extends STNode
{
    public NSdlst(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NSDLST);

        setRight(processSdlst(tokenList, table));

    }
}
