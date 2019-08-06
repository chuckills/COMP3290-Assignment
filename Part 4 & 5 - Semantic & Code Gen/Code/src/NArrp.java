import java.util.LinkedList;
/** NArrp.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NARRP rule
 *
 */
public class NArrp extends STNode
{
    public NArrp(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NARRP);

        setLeft(new NArrd(tokenList, table));
    }
}