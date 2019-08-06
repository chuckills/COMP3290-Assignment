import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NAtype.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NATYPE rule
 *
 */
public class NAtype extends STNode
{
    public NAtype(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NATYPE);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));

        nextToken = tokenList.pop();

		// Check for left bracket
        switch(nextToken.getTokenID())
        {
            case TLBRK:

                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected \"[\" before array size."));
                tokenList.push(nextToken);
        }
		
        setLeft(processExpression(tokenList, table));
        nextToken = tokenList.pop();

		// Check for right bracket
        switch(nextToken.getTokenID())
        {
            case TRBRK:
                nextToken= tokenList.pop();
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected \"]\" after array size."));
        }
		
		// Check for of statement
        switch(nextToken.getTokenID())
        {
            case TOF:
                nextToken = tokenList.pop();

                switch(nextToken.getTokenID())
                {
                    case TIDEN:
                        getSymbol().setType(nextToken.getLexeme());
                        break;

                    default:
                        errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected array type identifier."));
                        tokenList.push(nextToken);
                }
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected of after array size."));

        }

        table.addSymbol(getSymbol());
    }
}
