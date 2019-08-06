import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NRept.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NREPT rule
 *
 */
public class NRept extends STNode
{
    public NRept(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NREPT);

        Token nextToken = tokenList.pop();

        switch(nextToken.getTokenID())
        {
            case TIDEN:
                STNode assgn;
                STNode assgns;

                tokenList.push(nextToken);
                assgn = processAssgn(tokenList, table);

                nextToken = tokenList.pop();
                switch(nextToken.getTokenID())
                {
                    case TCOMA:
                        assgns = new NAsgns(tokenList, table);
                        assgns.setLeft(assgn);
                        setLeft(assgns);
                        break;

                    case TRPAR:
                    default:
                        tokenList.push(nextToken);
                        setLeft(assgn);
                }
                break;

            case TRPAR:
                tokenList.push(nextToken);
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected identifier."));
        }
    }
}
