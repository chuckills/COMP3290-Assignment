import java.util.LinkedList;
/** NDveq.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NDVEQ rule
 *
 */
public class NDveq extends STNode
{
    public NDveq(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NDVEQ);

        setRight(processBool(tokenList, table));
    }
}