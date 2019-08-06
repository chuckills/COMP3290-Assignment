import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NFor.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NFOR rule
 *
 */
public class NFor extends STNode
{
    public NFor(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NFOR);

        Token nextToken = tokenList.pop();

		// Check for left parentheses
        switch(nextToken.getTokenID())
        {
            case TLPAR:
                nextToken = tokenList.pop();
				
				// Check for assignment list
                switch(nextToken.getTokenID())
                {
                    case TIDEN:
                        STNode assgn;
                        STNode assgns;

                        tokenList.push(nextToken);
                        assgn = processAssgn(tokenList, table);

                        /*if(!assgn.getRight().getSymbol().getType().equals("integer"))
                        {
                            errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Illegal character after assignment list"));
                        }*/

                        nextToken = tokenList.pop();

						// Check for more assignments
                        switch(nextToken.getTokenID())
                        {
                            case TCOMA:
                                assgns = new NAsgns(tokenList, table);
                                assgns.setLeft(assgn);
                                setLeft(assgns);
                                break;

                            case TSEMI:
                                tokenList.push(nextToken);
                                setLeft(assgn);
                                break;

                            default:
                                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Illegal character after assignment list"));

                        }
                        break;

                    case TSEMI:
                        tokenList.push(nextToken);
                        break;

                    default:
                        errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected assignment list or \";\""));
                }
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected \"(\" after for statement"));

        }

        nextToken = tokenList.pop();

		// Check for semi colon before expression
        switch(nextToken.getTokenID())
        {
            case TSEMI:
                setMiddle(processBool(tokenList, table));
                break;

            default:

        }

        nextToken = tokenList.pop();

		// Check for right parentheses
        switch(nextToken.getTokenID())
        {
            case TRPAR:
                setRight(processStats(tokenList, table));
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected \")\" after expression"));
                int line = nextToken.getLineNum();
                while(nextToken.getLineNum() == line)
                {
                    nextToken = tokenList.pop();
                }
                tokenList.push(nextToken);
                setRight(processStats(tokenList, table));
        }

        nextToken = tokenList.pop();
		
		// Check for end statement
        switch(nextToken.getTokenID())
        {
            case TEND:
                tokenList.push(nextToken);
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): For statement without end"));


        }
    }
}
