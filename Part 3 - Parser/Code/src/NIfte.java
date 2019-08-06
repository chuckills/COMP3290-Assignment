import java.util.LinkedList;
/** NIfte.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NIFTE rule
 *
 */
public class NIfte extends STNode
{
    public NIfte(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NIFTE);

        setRight(processStats(tokenList, table));
    }
}
