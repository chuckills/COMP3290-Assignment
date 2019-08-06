import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NMain.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NMAIN rule
 *
 */
public class NMain extends STNode
{
    public NMain(LinkedList<Token> tokenList, SymbolTable globalTable)
    {
        super(NID.NMAIN);
        Token nextToken = tokenList.pop();

        SymbolTable scopeTable = new SymbolTable(globalTable);

		// Check for identifier
        switch(nextToken.getTokenID())
        {
            case TIDEN:
                tokenList.push(nextToken);
                setLeft(processSdlst(tokenList, scopeTable));
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected main block identifier list."));
                tokenList.push(nextToken);
        }

        nextToken = tokenList.pop();

		// Check for begin statement
        switch(nextToken.getTokenID())
        {
            case TBEGN:
                setRight(processStats(tokenList, scopeTable));
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected main block begin statement."));
        }

        nextToken = tokenList.pop();

		// Check for end statement
        switch(nextToken.getTokenID())
        {
            case TEND:
                SymbolTable.addScopeTable("main", scopeTable);
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected main block end statement."));
        }

        nextToken = tokenList.pop();

		// Check for CD18 tag
        switch(nextToken.getTokenID())
        {
            case TCD18:
                nextToken = tokenList.pop();
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Closing CD18 tag missing."));
        }
		
		// Check for final program identifier
        switch(nextToken.getTokenID())
        {
            case TIDEN:
                setSymbol(new TableEntry(nextToken));
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Closing program identifier missing."));
                tokenList.push(nextToken);
        }
    }
}
