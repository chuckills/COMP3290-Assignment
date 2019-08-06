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

        if(getLeft().getNodeID() != NID.NILIT)
        {
            if(getLeft().getNodeID() == NID.NFCALL)
            {
                errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + getLeft().getSymbol().getLine() + ", " + getLeft().getSymbol().getCol() + "): Array size must be numeric literal or constant expression."));
            }/*
            else if(getLeft().getNodeID() == NID.NSIMV)
            {
                if(!table.hasID(getLeft().getSymbol().getName()))
                {
                    errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + getLeft().getSymbol().getLine() + ", " + getLeft().getSymbol().getCol() + "): Identifier not a declared constant."));
                }
                else
                    setLeft(propagateConstants(getLeft(), table));
            }*/
            else if(getLeft().getNodeID() == NID.NUNDEF)
            {
                errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + getSymbol().getLine() + ", " + getSymbol().getCol() + "): Array size undefined."));

            }
            else
            {
                setLeft(foldConstants(getLeft(), table));
                if(getLeft().getNodeID() != NID.NILIT)
                {
                    setLeft(new NUndef(getSymbol().getToken()));
                    errorList.add(new SimpleEntry<>(getLeft().getSymbol().getToken(), "Semantic Error: (" + getLeft().getSymbol().getLine() + ", " + getLeft().getSymbol().getCol() + "): Array size must be integer or integer expression."));
                }
            }
        }

        if(getLeft().getSymbol().getName().equals("0"))
        {
            errorList.add(new SimpleEntry<>(getLeft().getSymbol().getToken(), "Semantic Error: (" + getLeft().getSymbol().getLine() + ", " + getLeft().getSymbol().getCol() + "): Array size must be greater than zero."));
        }

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
                        if(table.hasTypeDeclared(nextToken.getLexeme()))
                            if(table.hasSubType(nextToken.getLexeme()) == 'c')
                            {
                                getSymbol().setType(nextToken.getLexeme());

                                for(TableEntry member : table.getStructMembers(getSymbol().getType()))
                                {
                                    table.addSymbol(member);
                                }
                            }
                            else
                                errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Type must be complex structure type."));
                        else
                            errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Undeclared type identifier."));
                        break;

                    default:
                        errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected array type identifier."));
                        tokenList.push(nextToken);
                }
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected of after array size."));

        }

        table.addType(getSymbol(), 'a');
    }
}
