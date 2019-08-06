import java.util.LinkedList;
/** NPrint.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NPRINT rule
 *
 */
public class NPrint extends STNode
{
    public NPrint(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NPRINT);

        setLeft(processPrlst(tokenList, table));
    }
}
