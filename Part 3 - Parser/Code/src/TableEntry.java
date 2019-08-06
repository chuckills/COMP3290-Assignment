/** TableEntry.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated: 27/09/2018
 *
 * Description:
 * Symbol table record class for the CD18 compiler
 *
 */
public class TableEntry
{
    private String symbolName;
    private String symbolType;
    private Token.TID tokenID;
    private int line;
    private int col;

    public TableEntry(Token token)
    {
        symbolName = token.getLexeme();
        tokenID = token.getTokenID();
        line = token.getLineNum();
        col = token.getColNum();
    }

    public String getName()
    {
        return symbolName;
    }

    public String getType()
    {
        return symbolType;
    }

    public void setName(String name)
    {
        symbolName = name;
    }

    public void setType(String type)
    {
        symbolType = type;
    }

    public int getLine()
    {
        return line;
    }

    public int getCol()
    {
        return col;
    }

    public Token.TID getTokenID()
    {
        return tokenID;
    }

    @Override
    public boolean equals(Object object)
    {
        if(object instanceof TableEntry)
        {
            TableEntry other = (TableEntry)object;
            return symbolName.equals(other.getName());
        }
        return false;
    }

}
