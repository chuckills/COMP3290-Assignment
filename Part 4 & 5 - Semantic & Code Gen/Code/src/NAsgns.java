import java.util.LinkedList;
/** NAsgns.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated:
 *
 * Description:
 * STNode sub-class for NASGNS rule
 *
 */
public class NAsgns extends STNode
{
    public NAsgns(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NASGNS);

        STNode assgn;

        assgn = processAssgn(tokenList, table);

        Token nextToken = tokenList.pop();

		// Check for more assignments
        switch(nextToken.getTokenID())
        {
            case TCOMA:
                nextToken = tokenList.pop();
                if(nextToken.getTokenID() != Token.TID.TEND)
                {
                    tokenList.push(nextToken);
                    setLeft(assgn);
                    setRight(new NAsgns(tokenList, table));
                }
                break;

            case TSEMI:
                setRight(assgn);
                break;

            case TRPAR:
                setRight(assgn);
                break;

            default:

        }
        tokenList.push(nextToken);
    }
}
