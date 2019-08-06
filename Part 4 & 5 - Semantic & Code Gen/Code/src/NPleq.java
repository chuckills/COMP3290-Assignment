import java.util.LinkedList;
/** NPleq.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NPLEQ rule
 *
 */
public class NPleq extends STNode
{
    public NPleq(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NPLEQ);

        setRight(processBool(tokenList, table));
    }
}