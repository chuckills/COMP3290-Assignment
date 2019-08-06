import java.util.LinkedList;
/** NArrc.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NARRC rule
 *
 */
public class NArrc extends STNode
{
    public NArrc(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NARRC);
        
        setLeft(new NArrd(tokenList, table));

    }
}
