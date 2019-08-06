import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NProg.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NPROG rule
 *
 */
public class NProg extends STNode
{
    public NProg(LinkedList<Token> tokenList, SymbolTable globalTable)

    {
        super(NID.NPROG);

        Token nextToken = tokenList.pop();

        // Check for a program identifier
        switch(nextToken.getTokenID())
        {
            case TIDEN:
                setSymbol(new TableEntry(nextToken));

                // Add the identifier to the symbol table
                globalTable.addSymbol(getSymbol());
                break;

            // Program identifier is missing, throw error and continue without one
            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected program identifier."));
                tokenList.push(nextToken);
        }

        // Create global node sub-tree
        setLeft(new NGlob(tokenList, globalTable));

        nextToken = tokenList.pop();
		
		// Check for function definitions and main section
		switch(nextToken.getTokenID())
		{
			// Create functions sub-tree
			case TFUNC:
				nextToken = tokenList.pop();
				switch(nextToken.getTokenID())
				{
					case TIDEN:
						tokenList.push(nextToken);
						setMiddle(processFuncs(tokenList, globalTable));
						nextToken = tokenList.pop();
						break;

					default:
						errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected function identifier."));
						while(nextToken.getTokenID() != Token.TID.TFUNC && nextToken.getTokenID() != Token.TID.TMAIN)
						{
							nextToken = tokenList.pop();
						}
						tokenList.push(nextToken);
				}
				break;

			default:
				if(nextToken.getTokenID() != Token.TID.TMAIN)
				{
					errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected function or main block."));
				}
		}

		switch(nextToken.getTokenID())
		{
			// Create main sub-tree
			case TMAIN:
				nextToken = tokenList.pop();
				switch(nextToken.getTokenID())
				{
					// Variable declarations
					case TIDEN:
						tokenList.push(nextToken);
						setRight(new NMain(tokenList, globalTable));
						break;

					// No variable declarations, throw an error and continue
					case TBEGN:
						errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Must be at least one variable defined for main."));
						tokenList.push(nextToken);
						setRight(new NMain(tokenList, globalTable));
						break;

					// Variables section starts with something other than a variable declaration
					default:
						errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected variable and found invalid symbol."));
						while(nextToken.getTokenID() != Token.TID.TBEGN && nextToken.getTokenID() != Token.TID.TIDEN)
						{
							nextToken = tokenList.pop();
						}
						tokenList.push(nextToken);
						setRight(new NMain(tokenList, globalTable));
						getRight().setNodeID(NID.NUNDEF);
				}

				/*if(!getSymbol().equals(getRight().getSymbol()))
				{
					errorList.push(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Closing program identifier does not match opening identifier."));
				}*/

				break;

			// Missing function block or main block after program identifier or globals, throw error and continue
			default:
				errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected function or main block after globals section."));

				switch(nextToken.getTokenID())
				{
					case TIDEN:
					case TBEGN:
						tokenList.push(nextToken);
						setRight(new NMain(tokenList, globalTable));
						getRight().setNodeID(NID.NUNDEF);
						break;

					default:
				}
		}
		nextToken = tokenList.pop();
    }
}
