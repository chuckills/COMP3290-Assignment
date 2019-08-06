import java.util.LinkedList;
/** NUndef.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NUNDEF rule
 *
 */
public class NUndef extends STNode
{
    public NUndef(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NUNDEF);

    }

    public NUndef(Token token)
    {
        super(NID.NUNDEF);

        setSymbol(new TableEntry(token));
    }

}
