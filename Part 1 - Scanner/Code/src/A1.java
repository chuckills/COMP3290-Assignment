import java.io.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

/** A1.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created: 03/08/2018
 * Updated: 21/08/2018
 *
 * Description:
 * A1 class is the launcher for the CD18 compiler.
 *
 * Only lexical analysis is implemented in this version
 *
 */
public class A1
{
    /** run()
     *
     * @param fileName - String, Name of the source file
     */
    private void run(String fileName)
    {
        HashMap<Integer, String> symbolTable = new HashMap<>();
        CD18Scanner myScanner = new CD18Scanner(fileName);
        Token token;

        try
        {
            saveListing(fileName);
            int charCount = 0;

            // Scan the source file and display tokens
            while (!myScanner.eof())
            {
                token = myScanner.getToken();

                if(token.getTokenID() == Token.ID.TIDEN)
                {
                    token.setSymbol(new TableEntry(token.getLexeme()));
                    symbolTable.put(token.getSymbol().getSymbolReference(), token.getSymbol().getName());
                }

                // Keep count of characters on each line
                if(token.getTokenID() != Token.ID.TUNDF)
                {
                    charCount += token.toParser().length();
                }
                else if(charCount > 0)
                {
                    System.out.println();
                    charCount = 0;
                }

                // Display token
                System.out.print(token.toParser());

                // Display error message
                if(token.getTokenID() == Token.ID.TUNDF)
                {
                    System.out.println('\n' + myScanner.getError());
                }

                // New line if required
                if(charCount > 60)
                {
                    System.out.println();
                    charCount = 0;
                }
            }

            // Determine result of analysis
            if(myScanner.hasErrors())
            {
                System.out.println("\n\nError List:");
                System.out.println("===========");
            }
            else
            {
                System.out.println("\n\nLexical anaylsis success.");
            }

            // If source contains errors, display list of errors
            while(myScanner.hasErrors())
            {
                // Retrieve next error
                SimpleEntry error = myScanner.nextError();

                // Cast to correct types
                //token = (Token)error.getKey();
                String msg = (String)error.getValue();

                // Display error message
                System.out.println(msg);
            }
            System.out.println("=================================================================");

            System.out.println("\n\nIdentifier List:");
            System.out.println("===========");
            for(String iden : symbolTable.values())
            {
                System.out.println(iden);
            }

        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void saveListing(String fileName)
    {
        try(BufferedReader in = new BufferedReader(new FileReader(fileName));
            PrintWriter out = new PrintWriter("ProgramListing.txt"))
        {
            int i = 1;
            String line;
            while((line = in.readLine()) != null)
            {
                out.println(i + " " + line);
                i++;
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /** main()
     *
     * @param args - String[], Command line arguments
     */
    public static void main(String[] args)
    {
        A1 myCompiler = new A1();
        myCompiler.run(args[0]);
    }
}
