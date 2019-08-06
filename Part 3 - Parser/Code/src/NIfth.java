import java.util.LinkedList;
/** NIfth.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NIFTH rule
 *
 */
public class NIfth extends STNode
{
    public NIfth(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NIFTH);
    }
}
