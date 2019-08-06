import java.util.LinkedList;
/** NAlist.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NALIST rule
 *
 */
public class NAlist extends STNode
{
    public NAlist(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NALIST);

        setRight(processAlist(tokenList, table));
    }
}