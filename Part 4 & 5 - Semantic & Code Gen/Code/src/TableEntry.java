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
    private String symbolValue;
    private Token token;
    private int line;
    private int col;
    private int register;
    private int offset;

    public TableEntry(Token tkn)
    {
        token = tkn;
        symbolName = token.getLexeme();
        line = token.getLineNum();
        col = token.getColNum();
    }

    public TableEntry(String parentName, TableEntry entry)
    {
        symbolName = parentName + "." + entry.symbolName;
        symbolType = entry.symbolType;
        symbolValue = entry.symbolValue;
        token = entry.token;
        line = entry.line;
        col = entry.col;
    }

    public TableEntry(TableEntry entry)
    {
        symbolName = entry.symbolName;
        symbolType = entry.symbolType;
        symbolValue = entry.symbolValue;
        token = entry.token;
        line = entry.line;
        col = entry.col;
    }

    public void setRegister(int reg)
    {
        register = reg;
    }

    public int getRegister()
    {
        return register;
    }

    public void setOffset(int oSet)
    {
        offset = oSet;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setValue(String value)
    {
        symbolValue = value;
    }

    public String getValue()
    {
        return symbolValue;
    }

    public void setToken(Token tkn)
    {
        token = tkn;
    }

    public Token getToken()
    {
        return token;
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
        return token.getTokenID();
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
