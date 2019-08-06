import java.util.LinkedList;
/** NPow.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NPOW rule
 *
 */
public class NPow extends STNode
{
    public NPow(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NPOW);
        setRight(processFactor(tokenList, table));
    }
}
