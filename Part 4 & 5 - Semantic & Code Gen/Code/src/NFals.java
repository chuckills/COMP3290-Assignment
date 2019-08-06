import java.util.LinkedList;
/** NFals.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NFALS rule
 *
 */
public class NFals extends STNode
{
    public NFals(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NFALS);

    }

    public NFals(Token token, SymbolTable table)
    {
        super(NID.NFALS);
        //setSymbol(new TableEntry(token));
    }
}