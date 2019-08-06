import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;

/** CD18Parser.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * Parser launcher class for the CD18 compiler
 *
 */
public class CD18Parser
{
    private LinkedList<Token> tokenList;

    public CD18Parser(LinkedList<Token> tList)
    {
        tokenList = tList;
    }

    public STNode parseInput()
    {
        Token nextToken = tokenList.pop();
        STNode STRoot = null;
        SymbolTable globalTable = new SymbolTable(null);

        switch(nextToken.getTokenID())
        {
            case TCD18:
                STRoot = new NProg(tokenList, globalTable);
                break;

            default:
                STNode.errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Program must start with CD18"));
                STRoot = new NProg(tokenList, globalTable);
                STRoot.setNodeID(STNode.NID.NUNDEF);
        }

        return STRoot;
    }
}
