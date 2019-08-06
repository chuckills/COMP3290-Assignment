import java.util.LinkedList;
/** NInput.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NINPUT rule
 *
 */
public class NInput extends STNode
{
    public NInput(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NINPUT);

        setLeft(processVlist(tokenList, table));
    }
}
