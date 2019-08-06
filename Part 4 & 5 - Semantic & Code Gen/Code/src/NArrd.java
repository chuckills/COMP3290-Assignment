import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
/** NArrd.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 28/09/2018
 *
 * Description:
 * STNode sub-class for NARRD rule
 *
 */
public class NArrd extends STNode
{
    public NArrd(LinkedList<Token> tokenList, SymbolTable table)
    {
        super(NID.NARRD);

        Token nextToken = tokenList.pop();

        setSymbol(new TableEntry(nextToken));

        nextToken = tokenList.pop();

        switch(nextToken.getTokenID())
        {
            case TIDEN:
                getSymbol().setType(nextToken.getLexeme());
                break;

            default:
                errorList.add(new SimpleEntry<>(nextToken, "Syntax Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Expected type identifier."));
        }
        if(!table.hasID(getSymbol().getName()))
        {
            table.addSymbol(getSymbol());
            Token arrMemberToken;
            TableEntry arrMember;
            for(TableEntry member : table.getStructMembers(table.getArrayType(getSymbol().getType())))
            {
                /*arrMemberToken = new Token(member.getTokenID(), member.getLine(), member.getCol(), getSymbol().getName() + "." + member.getName());
                arrMember = new TableEntry(arrMemberToken);
                arrMember.setType(member.getType());
                arrMember.setValue(member.getValue());
                //arrMember.getToken().setLexeme(arrMember.getName());
                //arrMember.setToken(arrMemberToken);*/
                member.setName(getSymbol().getName() + "." + member.getName().substring(member.getName().indexOf(".")+1));
                table.addSymbol(member);
            }
        }
        else
            errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + getSymbol().getLine() + ", " + getSymbol().getCol() + "): Array identifier already declared."));

        if(!table.hasTypeDeclared(nextToken.getLexeme()))
            errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Undefined array type."));
        else if(table.hasSubType(nextToken.getLexeme()) != 'a')
        {
            errorList.add(new SimpleEntry<>(nextToken, "Semantic Error: (" + nextToken.getLineNum() + ", " + nextToken.getColNum() + "): Type identifier not of array type."));
        }/*
        else
        {
            table.addType(getSymbol(),'a');
        }*/
    }
}