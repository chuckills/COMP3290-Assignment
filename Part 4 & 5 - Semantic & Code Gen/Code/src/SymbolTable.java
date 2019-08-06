import java.util.ArrayList;
import java.util.HashMap;

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
    private static HashMap<String, SymbolTable> scopeTables = new HashMap<>();

    private HashMap<String, String> identifiers;
    private HashMap<String, Character> types;
    private ArrayList<TableEntry> symbolTable;
    private ArrayList<String> paramTypes;
    private SymbolTable previous;

    /** Constructor
     *
     * @param prev
     */
    public SymbolTable(SymbolTable prev)
    {
        previous = prev;
        identifiers = new HashMap<>();
        types = new HashMap<>();
        symbolTable = new ArrayList<>();
        paramTypes = new ArrayList<>();
        if(prev == null)
        {
            types.put("integer", 'p');
            types.put("real", 'p');
            types.put("boolean", 'p');
        }
    }

    public void addParamType(String pType)
    {
        paramTypes.add(pType);
    }

    public boolean matchParam(int pNumber, String pType)
    {
        return paramTypes.get(pNumber).equals(pType);
    }

    public void updateValue(String symbol, String value)
    {
        identifiers.put(symbol, value);
    }

    public static void addScopeTable(String scopeName, SymbolTable table)
    {
        scopeTables.put(scopeName, table);
    }

    public static SymbolTable getScopeTable(String scopeName)
    {
        return scopeTables.get(scopeName);
    }

    /** addSymbol()
     *
     * @param entry
     */
    public void addSymbol(TableEntry entry)
    {
        if(entry.getTokenID() == Token.TID.TIDEN)
        {
            identifiers.put(entry.getName(), entry.getValue());
        }

        symbolTable.add(entry);
    }

    /**
     *
     * @param id
     */
    public void addID(String id)
    {
        identifiers.put(id, null);
    }

    /** checkIdInScope()
     *
     * @param symbol
     * @return
     */
    public boolean hasIdInScope(String symbol)
    {
        return identifiers.containsKey(symbol);
    }

    /** checkSymbol()
     *
     * @param symbol
     * @return
     */
    public boolean hasID(String symbol)
    {
        boolean found = false;
        for(SymbolTable table = this; table != null; table = table.previous)
        {
            if(table.identifiers.containsKey(symbol))
            {
                found = true;
                break;
            }
        }

        return found;
    }

    /** addType()
     *
     * @param entry
     */
    public void addType(TableEntry entry, char subType)
    {
        if(entry.getTokenID() == Token.TID.TIDEN)
        {
            types.put(entry.getName(), subType);
        }

        symbolTable.add(entry);
    }

    /**
     *
     * @param arrayName
     * @return
     */
    public String getArrayType(String arrayName)
    {
        return getGlobalTypeEntry(arrayName).getType();
    }

    /**
     *
     * @param structName
     * @return
     */
    public ArrayList<TableEntry> getStructMembers(String structName)
    {
        ArrayList<TableEntry> members = new ArrayList<>();

        SymbolTable table = this;

        while(table.previous != null)
        {
            table = table.getPrevious();
        }

        for(String entry : table.identifiers.keySet())
        {
            /*if(entry.startsWith(structName+"."))
                members.add(entry.substring(entry.indexOf(".")+1));*/

            if(entry.startsWith(structName+"."))
            {

                TableEntry te = new TableEntry(getGlobalIdEntry(entry));
                //te.setName(entry.substring(entry.indexOf(".")+1));
                members.add(te);
            }
        }
        return members;
    }

    /** checkGlobalID()
     *
     * @param symbol
     * @return
     */
    public boolean hasGlobalID(String symbol)
    {
        boolean found = false;

        SymbolTable table = this;
        while(table.previous != null)
        {
            table = table.previous;
        }
        if(table.identifiers.containsKey(symbol))
        {
            found = true;
        }

        return found;
    }

    /** checkGlobalType()
     *
     * @param symbol
     * @return
     */
    public boolean hasGlobalType(String symbol)
    {
        boolean found = false;

        SymbolTable table = this;
        while(table.previous != null)
        {
            table = table.previous;
        }
        if(table.types.containsKey(symbol))
        {
            found = true;
        }

        return found;
    }

    /** getIdEntry()
     *
     * @param symbolName
     * @return
     */
    public TableEntry getIdEntry(String symbolName)
    {
        TableEntry symbol = null;
        for(SymbolTable table = this; table != null; table = table.previous)
        {
            if(table.identifiers.containsKey(symbolName))
            {
                for(TableEntry entry : table.getTable())
                {
                    if(entry.getName().equals(symbolName))
                    {
                        symbol = entry;
                        break;
                    }
                }
                break;
            }
        }

        return symbol;
    }

    /** getTypeEntry()
     *
     * @param symbolName
     * @return
     */
    public TableEntry getTypeEntry(String symbolName)
    {
        TableEntry symbol = null;
        for(SymbolTable table = this; table != null; table = table.previous)
        {
            if(table.types.containsKey(symbolName))
            {
                for(TableEntry entry : table.getTable())
                {
                    if(entry.getName().equals(symbolName))
                    {
                        symbol = entry;
                        break;
                    }
                }
                break;
            }
        }

        return symbol;
    }

    /** getGlobalTypeEntry()
     *
     * @param symbolName
     * @return
     */
    public TableEntry getGlobalTypeEntry(String symbolName)
    {
        TableEntry symbol = null;
        SymbolTable table = this;

        while(table.previous != null)
        {
            table = table.getPrevious();
        }

        if(table.types.containsKey(symbolName))
        {
            for(TableEntry entry : table.symbolTable)
            {
                if(entry.getName().equals(symbolName))
                {
                    symbol = entry;
                    break;
                }
            }
        }

        return symbol;
    }

    /** getGlobalIdEntry()
     *
     * @param symbolName
     * @return
     */
    public TableEntry getGlobalIdEntry(String symbolName)
    {
        TableEntry symbol = null;
        SymbolTable table = this;

        while(table.previous != null)
        {
            table = table.previous;
        }

        if(table.identifiers.containsKey(symbolName))
        {
            for(TableEntry entry : table.getTable())
            {
                if(entry.getName().equals(symbolName))
                {
                    symbol = entry;
                    break;
                }
            }
        }

        return symbol;
    }

    /** checkTypeScope()
     *
     * @param symbol
     * @return
     */
    public boolean hasTypeInScope(String symbol)
    {
        return types.containsKey(symbol);
    }

    /** checkTypeDeclared()
     *
     * @param symbol
     * @return
     */
    public boolean hasTypeDeclared(String symbol)
    {
        boolean found = false;
        for(SymbolTable table = this; table != null; table = table.previous)
        {
            if(table.types.containsKey(symbol))
            {
                found = true;
                break;
            }
        }

        return found;
    }

    /** checkSubType()
     *
     * @param symbol
     * @return
     */
    public char hasSubType(String symbol)
    {
        char type = '\0';
        for(SymbolTable table = this; table != null; table = table.previous)
        {
            if(table.types.containsKey(symbol))
            {
                type = table.types.get(symbol);
                break;
            }
        }

        return type;
    }

    /** getTable()
     *
     * @return
     */
    public ArrayList<TableEntry> getTable()
    {
        return symbolTable;
    }

    /** getPrevious()
     *
     * @return
     */
    public SymbolTable getPrevious()
    {
        return previous;
    }
}
