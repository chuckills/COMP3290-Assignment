import java.util.LinkedList;
/** NPrln.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NPRLN rule
 *
 */
public class NPrln extends STNode
{
    public NPrln(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NPRLN);

        setLeft(processPrlst(tokenList, table));
    }
}
