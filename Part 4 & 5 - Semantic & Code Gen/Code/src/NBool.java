import java.util.LinkedList;
/** NBool.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NBOOL rule
 *
 */
public class NBool extends STNode
{
    public NBool(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NBOOL);
    }
}
