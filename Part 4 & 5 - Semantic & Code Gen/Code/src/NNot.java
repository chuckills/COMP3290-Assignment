import java.util.LinkedList;
/** NNot.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NNOT rule
 *
 */
public class NNot extends STNode
{
    public NNot(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NNOT);
    }
}
