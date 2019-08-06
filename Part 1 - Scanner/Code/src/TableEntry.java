public class TableEntry
{
    private static int symbolCount = 0;
    //private static int typeCount = 0;
    private int symbolReference;
    private String symbolName;


    public TableEntry(String name)
    {
        symbolReference = ++symbolCount;
        symbolName = name;
    }

    /*public TableEntry(String name, boolean type)
    {
        symbolReference = ++typeCount;
        symbolName = name;
    }*/

    public String getName()
    {
        return symbolName;
    }

    public int getSymbolReference()
    {
        return symbolReference;
    }
}
