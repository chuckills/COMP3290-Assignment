import java.util.LinkedList;
/** NSimp.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NSIMP rule
 *
 */
public class NSimp extends STNode
{
    public NSimp(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NSIMP);
		
        setLeft(new NSdecl(tokenList, table));
    }
}