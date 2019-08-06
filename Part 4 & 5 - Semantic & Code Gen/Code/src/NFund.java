import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NFund.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NFUND rule
 *
 */
public class NFund extends STNode
{
    public NFund(LinkedList<Token> tokenList, SymbolTable globalTable)
    {
        super(NID.NFUND);

        SymbolTable scopeTable = new SymbolTable(globalTable);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));

        nextToken = tokenList.pop();

		// Check for left parentheses
        switch(nextToken.getTokenID())
        {
            case TLPAR:
                nextToken = tokenList.pop();
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected \"(\" after identifier."));

        }

        // Check the type of parameter
        switch(nextToken.getTokenID())
        {
            case TCNST:
            case TIDEN:
                tokenList.push(nextToken);
                setLeft(processPlist(tokenList, scopeTable));
                getSymbol().setValue("" + getNumParameters(getLeft(), 0));
                break;

            case TRPAR:
                getSymbol().setValue("0");
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected const, primitive or array type identifier."));
                tokenList.push(nextToken);
                while(nextToken.getTokenID() != Token.TID.TCNST && nextToken.getTokenID() != Token.TID.TRPAR && nextToken.getTokenID() != Token.TID.TCOMA)
                {
                    nextToken = tokenList.pop();
                }
                tokenList.push(nextToken);
                setLeft(processPlist(tokenList, scopeTable));
        }

        nextToken = tokenList.pop();

		// Check for colon before type
        switch(nextToken.getTokenID())
        {
            case TCOLN:
                nextToken = tokenList.pop();
				
				// Check the function type
                switch(nextToken.getTokenID())
                {
                    case TVOID:
                        getSymbol().setType("void");
                        break;

                    case TINTG:
                        getSymbol().setType("integer");
                        break;

                    case TREAL:
                        getSymbol().setType("real");
                        break;

                    case TBOOL:
                        getSymbol().setType("boolean");
                        break;

                    default:
                }
                break;

            default:
        }

        nextToken = tokenList.pop();

		// Check for declaration list or begin statement
        switch(nextToken.getTokenID())
        {
            case TIDEN:
                tokenList.push(nextToken);
                setMiddle(processDlist(tokenList, scopeTable));
                nextToken = tokenList.pop();
				
				// Check for begin statement
                switch(nextToken.getTokenID())
                {
                    case TBEGN:
                        setRight(processStats(tokenList, scopeTable));
                        break;

                    default:
                        errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected function begin statement."));
                        tokenList.push(nextToken);
                }
                break;

            case TBEGN:
                setRight(processStats(tokenList, scopeTable));
                break;

            case TRPAR:
                break;

            default:
                while(nextToken.getTokenID() != Token.TID.TBEGN && nextToken.getTokenID() != Token.TID.TSEMI)
                {
                    nextToken = tokenList.pop();
                }
                switch(nextToken.getTokenID())
                {
                    case TBEGN:
                        errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected identifier in declaration list."));
                        break;

                    case TSEMI:
                        errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected function begin statement."));
                        break;

                    default:
                }
                setMiddle(new NUndef(tokenList, scopeTable));
                setRight(processStats(tokenList, scopeTable));
        }
        nextToken = tokenList.pop();
		
		// Check for end statement
        switch(nextToken.getTokenID())
        {
            case TEND:
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Missing end statement in previous function."));
                tokenList.push(nextToken);
        }

        globalTable.addSymbol(getSymbol());
        SymbolTable.addScopeTable(getSymbol().getName(), scopeTable);
    }

    private int getNumParameters(STNode pList, int count)
    {
        count++;
        if(pList.getRight() != null)
        {
            return getNumParameters(pList.getRight(), count);
        }
        else
            return count;
    }
}
