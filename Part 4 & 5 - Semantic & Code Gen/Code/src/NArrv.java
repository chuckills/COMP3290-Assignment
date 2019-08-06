import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NArrv.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NArrv rule
 *
 */
public class NArrv extends STNode
{
    public NArrv(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NARRV);
        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));

        nextToken = tokenList.pop();

        switch(nextToken.getTokenID())
        {
            case TLBRK:
                setLeft(processExpression(tokenList, table));
                /*if(getLeft().getNodeID() != NID.NFLIT && getLeft().getNodeID() != NID.NILIT)
                    setLeft(foldConstants(getLeft(), table));*/
                nextToken = tokenList.pop();

                if(nextToken.getTokenID() == Token.TID.TRBRK)
                {
                    nextToken = tokenList.pop();
                    if(nextToken.getTokenID() == Token.TID.TDOT)
                    {
                        nextToken = tokenList.pop();

                        // Check the identifier is declared
                        if(table.hasID(getSymbol().getName()))
                        {
                            String structName = table.getGlobalTypeEntry(table.getIdEntry(getSymbol().getName()).getType()).getType();

                            // Check that it is declared as an array type
                            if(table.hasSubType(table.getTypeEntry(table.getIdEntry(getSymbol().getName()).getType()).getName()) == 'a')
                            {
                                // Check that the array member exists in the structure type
                                if(table.hasID(structName + "." + nextToken.getLexeme()))
                                {
                                    nextToken.setLexeme(getSymbol().getName() + "." + nextToken.getLexeme());
                                    tokenList.push(nextToken);
                                    //table.addSymbol(getRight().getSymbol());
                                }
                                else
                                    errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Member does not exist in type structure."));
                            }
                            else
                                errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Type mismatch."));
                        }
                        else
                            errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + getSymbol().getCol() + "): Undeclared array identifier."));

                        setRight(new NSimv(tokenList, table));
                    }
                    else
                    {
                        errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Missing \".\" in array member call."));
                    }
                }
                else
                {
                    errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Missing \"]\" in array identifier."));
                }
                break;

            default:
        }
    }
}
