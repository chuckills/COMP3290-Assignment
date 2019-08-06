import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NGlob.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NGLOB rule
 *
 */
public class NGlob extends STNode
{
    public NGlob(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NGLOB);

        Token nextToken = tokenList.pop();

        // Check for constants, types, arrays, func or main keyword, if incorrect throw an error and skip to next global definition section
        if(nextToken.getTokenID() != Token.TID.TCONS && nextToken.getTokenID() != Token.TID.TTYPS && nextToken.getTokenID() != Token.TID.TARRS && nextToken.getTokenID() != Token.TID.TFUNC  && nextToken.getTokenID() != Token.TID.TMAIN)
        {
            errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected constants, types, arrays, function or main block."));

            while(nextToken.getTokenID() != Token.TID.TCONS && nextToken.getTokenID() != Token.TID.TTYPS && nextToken.getTokenID() != Token.TID.TARRS && nextToken.getTokenID() != Token.TID.TFUNC  && nextToken.getTokenID() != Token.TID.TMAIN)
            {
                nextToken = tokenList.pop();
            }
            setLeft(new NUndef(tokenList, table));
        }

        // Process constants section if one exists
        switch(nextToken.getTokenID())
        {
            case TCONS:
                nextToken = tokenList.pop();
                switch(nextToken.getTokenID())
                {
                    // Process constants initialiser list
                    case TIDEN:
                        tokenList.push(nextToken);
                        setLeft(processIlist(tokenList, table));
                        nextToken = tokenList.pop();
                        break;

                    // First token not an identifier, find the first identifier or types, arrays, funcs or main and process as normal
                    default:
                        errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected constant identifier."));
                        while(nextToken.getTokenID() != Token.TID.TIDEN && nextToken.getTokenID() != Token.TID.TTYPS && nextToken.getTokenID() != Token.TID.TARRS && nextToken.getTokenID() != Token.TID.TFUNC  && nextToken.getTokenID() != Token.TID.TMAIN)
                        {
                            nextToken = tokenList.pop();
                        }
                        if(nextToken.getTokenID() == Token.TID.TIDEN)
                        {
                            tokenList.push(nextToken);
                            setLeft(processIlist(tokenList, table));
                            nextToken = tokenList.pop();
                        }
                }
                break;

            // No constants section, Check for types, arrays, func or main keyword, if incorrect throw an error and skip to next global definition section
            default:
                if(nextToken.getTokenID() != Token.TID.TTYPS && nextToken.getTokenID() != Token.TID.TARRS && nextToken.getTokenID() != Token.TID.TFUNC  && nextToken.getTokenID() != Token.TID.TMAIN)
                {
                    errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected types, arrays, function or main block."));
                    while(nextToken.getTokenID() != Token.TID.TTYPS && nextToken.getTokenID() != Token.TID.TARRS && nextToken.getTokenID() != Token.TID.TFUNC  && nextToken.getTokenID() != Token.TID.TMAIN)
                    {
                        nextToken = tokenList.pop();
                    }
                    setLeft(new NUndef(tokenList, table));
                }
        }

        // Process types section if one exists
        switch(nextToken.getTokenID())
        {
            case TTYPS:
                nextToken = tokenList.pop();
                switch(nextToken.getTokenID())
                {
                    case TIDEN:
                        tokenList.push(nextToken);
                        setMiddle(processTypes(tokenList, table));
                        nextToken = tokenList.pop();
                        break;

                    // First token not an identifier, find the first identifier or arrays, funcs or main and process as normal
                    default:
                        errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected type identifier."));
                        while(nextToken.getTokenID() != Token.TID.TIDEN && nextToken.getTokenID() != Token.TID.TARRS && nextToken.getTokenID() != Token.TID.TFUNC  && nextToken.getTokenID() != Token.TID.TMAIN)
                        {
                            nextToken = tokenList.pop();
                            if(nextToken.getTokenID() == Token.TID.TIDEN)
                            {
                                Token tempToken = tokenList.pop();
                                if(tempToken.getTokenID() == Token.TID.TIS)
                                {
                                    tokenList.push(tempToken);
                                }
                                else
                                    nextToken = tempToken;
                            }
                        }
                        if(nextToken.getTokenID() == Token.TID.TIDEN)
                        {
                            tokenList.push(nextToken);
                            setMiddle(processTypes(tokenList, table));
                            nextToken = tokenList.pop();
                        }
                }
                break;

            // No types section, Check for arrays, func or main keyword, if incorrect throw an error and skip to next global definition section
            default:
                if(nextToken.getTokenID() != Token.TID.TARRS && nextToken.getTokenID() != Token.TID.TFUNC  && nextToken.getTokenID() != Token.TID.TMAIN)
                {
                    errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected arrays, function or main block."));
                    while(nextToken.getTokenID() != Token.TID.TARRS && nextToken.getTokenID() != Token.TID.TFUNC  && nextToken.getTokenID() != Token.TID.TMAIN)
                    {
                        nextToken = tokenList.pop();
                    }
                    setMiddle(new NUndef(tokenList, table));
                }
        }

        // Process arrays section if one exists
        switch(nextToken.getTokenID())
        {
            case TARRS:
                nextToken = tokenList.pop();

                switch(nextToken.getTokenID())
                {
                    case TIDEN:
                        tokenList.push(nextToken);
                        setRight(processAlist(tokenList, table));
                        break;

                    // First token not an identifier, find the first identifier, func or main and process as normal
                    default:
                        if(nextToken.getTokenID() != Token.TID.TFUNC  && nextToken.getTokenID() != Token.TID.TMAIN)
                        {
                            errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected array identifier."));
                            while(nextToken.getTokenID() != Token.TID.TCOMA && nextToken.getTokenID() != Token.TID.TFUNC  && nextToken.getTokenID() != Token.TID.TMAIN)
                            {
                                nextToken = tokenList.pop();
                            }
                            if(nextToken.getTokenID() == Token.TID.TCOMA)
                            {
                                setRight(processAlist(tokenList, table));
                            }

                        }
                }
                break;

            // No arrays section, Check for func or main keyword, if incorrect throw an error and skip to next global definition section
            default:
                if(nextToken.getTokenID() != Token.TID.TFUNC  && nextToken.getTokenID() != Token.TID.TMAIN)
                {
                    errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected function or main block."));
                    while(nextToken.getTokenID() != Token.TID.TFUNC  && nextToken.getTokenID() != Token.TID.TMAIN)
                    {
                        nextToken = tokenList.pop();
                    }
                    setRight(new NUndef(tokenList, table));
                }
                tokenList.push(nextToken);

        }
    }
}
