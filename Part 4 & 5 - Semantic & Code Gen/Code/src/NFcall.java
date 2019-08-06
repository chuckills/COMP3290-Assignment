import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NFcall.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NFCALL rule
 *
 */
public class NFcall extends STNode
{
    public NFcall(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NFCALL);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));

        table.addSymbol(getSymbol());

        nextToken = tokenList.pop();

        int numArguments;
        int numParameters = Integer.parseInt(table.getGlobalIdEntry(getSymbol().getName()).getValue());

        switch(nextToken.getTokenID())
        {
            case TLPAR:
                nextToken = tokenList.pop();
                switch(nextToken.getTokenID())
                {
                    case TRPAR:
                        numArguments = 0;
                        break;

                    default:
                        tokenList.push(nextToken);
                        setLeft(processElist(tokenList, table));
                        numArguments = getNumArguments(getLeft(), 0);
                }
                if(numArguments != numParameters)
                    errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Number of arguments does not match number of parameters."));
                else
                {
                    SymbolTable callTable = SymbolTable.getScopeTable(getSymbol().getName());
                    STNode param = getLeft();
                    for(int i = 0; i < numParameters; i++)
                    {
                        if(param.getNodeID() == NID.NEXPL)
                        {
                            if(!callTable.matchParam(i, param.getLeft().getSymbol().getType()))
                            {
                                errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Call argument does not match parameter type."));
                            }
                            param = param.getRight();
                        }
                        else
                        {
                            if(!callTable.matchParam(i, param.getSymbol().getType()))
                            {
                                errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Call argument does not match parameter type."));
                            }
                        }

                    }
                }
                break;

            default:
        }

        getSymbol().setType(table.getGlobalIdEntry(getSymbol().getName()).getType());
    }
}
