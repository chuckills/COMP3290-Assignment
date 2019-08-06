import java.util.LinkedList;
/** NMod.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NMOD rule
 *
 */
public class NMod extends STNode
{
    public NMod(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NMOD);
        setRight(processTerm(tokenList, table));
    }
}
