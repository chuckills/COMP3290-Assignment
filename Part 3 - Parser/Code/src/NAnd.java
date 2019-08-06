import java.util.LinkedList;
/** NAnd.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NAND rule
 *
 */
public class NAnd extends STNode
{
    public NAnd(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NAND);

        setRight(processBool(tokenList, table));
    }
}
