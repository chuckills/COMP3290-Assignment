import java.io.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;

/** CD.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created: 03/08/2018
 * Updated: 09/11/2018
 *
 * Description:
 * CD class is the launcher for the CD18 compiler.
 *
 *
 *
 */
public class CD
{
    private CD18Scanner myScanner;
    private LinkedList<Token> tokenList;
    private STNode STRoot;

    /** run()
     *
     * @param filename - String, Name of the source file
     */
    private void run(String filename)
    {
        try(PrintWriter codeOut = new PrintWriter(filename.substring(0,filename.lastIndexOf('.')) + ".mod");
            PrintWriter screenOut = new PrintWriter(System.out))
        {
            // Create scanner and generate initial program listing
            myScanner = new CD18Scanner(filename);

            screenOut.println("\n====> Performing lexical analysis.\n");

			// Lexical analysis of source file
			scanSource();

            // Determine result of lexical analysis
            if(myScanner.hasErrors())
            {
                // Print error list heading
                screenOut.println("\n\nError List:");
                screenOut.println("===========");
                screenOut.println();
            }
            else
            {
                // Move on to parsing and semantic check
                screenOut.println("\nLexical anaylsis success.\n");
                screenOut.println("\n====> Performing syntax and semantic analysis.\n");

                // Create parser
                CD18Parser myParser = new CD18Parser(tokenList);

                STRoot = myParser.parseInput();

                screenOut.println();
				
                if(STRoot != null)
                {
					// Determine result of syntax and semantic analysis
                    if(STRoot.hasErrors())
                    {
                        // Print error list heading
                        screenOut.println("Error List:");
                        screenOut.println("===========");
                        screenOut.println();
                    }
                    else
                    {
                        // Move on to code generation
                        screenOut.println("Syntax and semantic anaylsis success.\n");
                        screenOut.println("\nNo errors found.\n");
                        screenOut.println("\n====> Gererating SM module.");

// ====================================================================================
// TO CHECK MORE SEMANTICS YOU CAN COMMENT OUT THIS SECTION
// ====================================================================================
                        SM18CodeGen codeGen = new SM18CodeGen();

                        // Generate code sections for module
                        codeGen.generate(STRoot);

                        // Backpatch memory addresses
                        codeGen.patchAddress();

                        // Output module file
                        codeGen.modOut(codeOut);

                        // Output code to console
                        screenOut.println("\nSM18 Code:");
                        screenOut.println("==========\n");
                        codeGen.modOut(screenOut);
// ====================================================================================
// ====================================================================================
                    }
                }
            }

			// Gererate program listing
            screenOut.println(generateListing(filename));

            screenOut.println("\nEnd of source reached.");
            screenOut.println("======================================================================");

            screenOut.println("Listing Filename: "+ filename.substring(0,filename.lastIndexOf('.')) + ".lst");
            screenOut.println("Module Filename: "+ filename.substring(0,filename.lastIndexOf('.')) + ".mod");
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /** scanSource()
     *
     * Creates a token stream from the source file
     *
     * @throws IOException, thrown from the CD18Scanner object
     */
    private void scanSource() throws IOException
    {
            Token token;
            int charCount = 0;
            tokenList = new LinkedList<>();

            // Scan the source file and display tokens
            while(!myScanner.eof())
            {
                token = myScanner.getToken();
                tokenList.add(token);
                charCount += token.toParser().length();

                if(charCount > 60)
                {
                    charCount = 0;
                }
            }
    }

    /** generateListing()
     *
     * Creates a program listing with errors interleaved.
     * Displays either scanner or parser errors.     *
     *
     * @param fileName - String, name of the source file
     */
    private StringBuilder generateListing(String fileName)
    {
        StringBuilder errList = new StringBuilder();

        try(BufferedReader in = new BufferedReader(new FileReader(fileName));
            PrintWriter out = new PrintWriter(fileName.substring(0,fileName.lastIndexOf('.')) + ".lst"))
        {
            int i = 1;
            String line;
            SimpleEntry error = null;
            String msg;
            int errorCount = 0;

            // Gets the first error from the scanner
            if(myScanner.hasErrors())
                error = myScanner.nextError();

            // Gets the first error from parsing
            if(STRoot.hasErrors())
                error = STRoot.nextError();

			// Read from the source file
            while((line = in.readLine()) != null)
            {
				// Print the line of source code
                out.println(i + " " + line);

                while(error != null)
                {
					// Print the error under the line of code
                    if(((Token)error.getKey()).getLineNum() == i)
                    {
                        msg = error.getValue() + "\n";
                        out.println("========================================================================");
                        out.print(msg);
                        out.println("========================================================================");
                        errList.append(msg);
                        errorCount++;
                    }
					// Read the next line of code
                    else
                        break;

					// Check for more lexical errors
                    if(myScanner.hasErrors())
                    {
                        error = myScanner.nextError();
                        if(((Token)error.getKey()).getLineNum() > i)
                            break;
                    }
					// Check for more syntax/semantic errors
                    else if(STRoot.hasErrors())
                    {
                        error = STRoot.nextError();
                        if(((Token)error.getKey()).getLineNum() > i)
                            break;
                    }
					// Read the next line of code
                    else
                        error = null;
                }
                i++;
            }

			// Print any remaining lexical errors
            out.println("========================================================================");
            while(myScanner.hasErrors())
            {
                error = myScanner.nextError();
                msg = error.getValue() + "\n";
                out.print(msg);
                errList.append(msg);
            }
			
			// Print any remaining parser errors
            while(STRoot.hasErrors())
            {
                error = STRoot.nextError();
                msg = error.getValue() + "\n";
                out.print(msg);
                errList.append(msg);
            }

            // Show an error count
            if(errorCount > 0)
                if(errorCount == 1)
                    errList.append("\nFound 1 error.");
                else
                    errList.append(String.format("\nFound %1s errors", errorCount));

            out.println("END OF LISTING");
            out.println("========================================================================");
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        return errList;
    }

    /** main()
     *
     * @param args - String[], Command line arguments
     */
    public static void main(String[] args)
    {
        CD myCompiler = new CD();
        myCompiler.run(args[0]);
    }
}
