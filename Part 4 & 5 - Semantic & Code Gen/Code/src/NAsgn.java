import java.util.LinkedList;
/** NAsgn.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NASGN rule
 *
 */
public class NAsgn extends STNode
{
    public NAsgn(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NASGN);

        setRight(processBool(tokenList, table));
    }
}