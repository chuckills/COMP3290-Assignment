import java.util.LinkedList;
/** NPrlst.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NPRLST rule
 *
 */
public class NPrlst extends STNode
{
    public NPrlst(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NPRLST);

        setRight(processPrlst(tokenList, table));
    }
}
