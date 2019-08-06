import java.util.LinkedList;
/** NTypel.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NTYPEL rule
 *
 */
public class NTypel extends STNode
{
    public NTypel(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NTYPEL);

        setRight(processTypes(tokenList, table));


    }
}