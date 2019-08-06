import java.util.LinkedList;
/** NFuncs.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NFUNCS rule
 *
 */
public class NFuncs extends STNode
{
    public NFuncs(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NFUNCS);

        setRight(processFuncs(tokenList, table));

    }
}
