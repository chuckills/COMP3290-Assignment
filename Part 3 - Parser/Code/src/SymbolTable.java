import java.util.ArrayList;
import java.util.HashSet;

/** SymbolTable.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created:
 * Updated:
 *
 * Description:
 * Symbol table class for the CD18 compiler.
 *
 */
public class SymbolTable
{
    private HashSet<String> identifiers;
    private ArrayList<TableEntry> symbolTable;
    private SymbolTable previous;

    public SymbolTable(SymbolTable prev)
    {
        previous = prev;
        identifiers = new HashSet<>();
        symbolTable = new ArrayList<>();
    }

    public void addSymbol(TableEntry entry)
    {
        if(entry.getTokenID() == Token.TID.TIDEN && !identifiers.contains(entry.getName()))
        {
            identifiers.add(entry.getName());
            symbolTable.add(entry);
        }
        else if(entry.getTokenID() != Token.TID.TIDEN)
        {
            symbolTable.add(entry);
        }
    }

    /*public TableEntry getSymbol(TableEntry entry)
    {
        for(SymbolTable table = this; table != null; table = table.previous)
        {
            TableEntry found = table.symbolTable.get(symbol);

            if(found != null)
            {
                return symbol;
            }
        }
        return null;
    }*/

    public ArrayList<TableEntry> getTable()
    {
        return symbolTable;
    }

    public ArrayList<TableEntry> getPrevTable()
    {
        return previous.getTable();
    }
}
