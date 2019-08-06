import java.util.LinkedList;
/** NStats.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NSTATS rule
 *
 */
public class NStats extends STNode
{
    public NStats(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NSTATS);

        setRight(processStats(tokenList, table));

    }
}
