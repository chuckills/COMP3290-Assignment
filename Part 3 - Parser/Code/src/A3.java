import java.io.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;

/** A3.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created: 03/08/2018
 * Updated: 28/09/2018
 *
 * Description:
 * A3 class is the launcher for the CD18 compiler.
 *
 *
 *
 */
public class A3
{
    private CD18Scanner myScanner;
    private CD18Parser myParser;
    private LinkedList<Token> tokenList;
    private STNode STRoot;

    /** run()
     *
     * @param filename - String, Name of the source file
     */
    private void run(String filename)
    {
        try(PrintWriter fileOut = new PrintWriter("treeout.dat");
            PrintWriter screenOut = new PrintWriter(System.out))
        {
            // Create scanner and generate initial program listing
            myScanner = new CD18Scanner(filename);

            screenOut.println("\n====> Performing lexical analysis.\n");

			// Generate scanner output file
			scannerOutput(filename);

            // Determine result of lexical analysis
            if(myScanner.hasErrors())
            {
                screenOut.println("\n\nError List:");
                screenOut.println("===========");
                screenOut.println();
            }
            else
            {
                screenOut.println("\n====> Lexical anaylsis success.\n");
                screenOut.println("\n====> Performing syntactic analysis.\n");

                // Create parser
                myParser = new CD18Parser(tokenList);

                STRoot = myParser.parseInput();

                screenOut.println();
				
                if(STRoot != null)
                {
					// Display syntax tree
                    STNode.displayTree(screenOut, STRoot);
                    screenOut.println("\n");

					// Determine result of syntactic analysis
                    if(STRoot.hasErrors())
                    {
                        screenOut.println("Error List:");
                        screenOut.println("===========");
                        screenOut.println();
                    }
                    else
                    {
                        screenOut.println("====> Syntactic anaylsis success.");
                    }
                }

            }

			// Gererate program listing
            screenOut.println(generateListing(filename));

            screenOut.println("End of source reached.");
            screenOut.println("======================================================================");
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void scannerOutput(String fileName) throws IOException
    {
        try(PrintWriter out = new PrintWriter(fileName.substring(0,fileName.lastIndexOf('.')) + "_ScannerOut.txt"))
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

                out.print(token.toParser());

                if(charCount > 60)
                {
                    out.println();
                    charCount = 0;
                }
            }
        }
    }

    /** generateListing()
     *
     * Creates a program listing with errors interleaved
     *
     * @param fileName
     */
    private StringBuilder generateListing(String fileName)
    {
        StringBuilder errList = new StringBuilder();

        try(BufferedReader in = new BufferedReader(new FileReader(fileName));
            PrintWriter out = new PrintWriter(fileName.substring(0,fileName.lastIndexOf('.')) + "_ProgramListing.txt"))
        {
            int i = 1;
            String line;
            SimpleEntry error = null;
            String msg;
			
            if(myScanner.hasErrors())
                error = myScanner.nextError();

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
                    }
					// Read the next line of code
                    else
                        break;

					// Check for more lexical errors
                    if(myScanner.hasErrors())
                    {
                        error = myScanner.nextError();
                        if(((Token)error.getKey()).getLineNum() != i)
                            i++;
                    }
					// Check for more syntax errors
                    else if(STRoot.hasErrors())
                    {
                        error = STRoot.nextError();
                        if(((Token)error.getKey()).getLineNum() != i)
                            i++;
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
			
			// Print any remaining syntax errors
            while(STRoot.hasErrors())
            {
                error = STRoot.nextError();
                msg = error.getValue() + "\n";
                out.print(msg);
                errList.append(msg);
            }
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
        A3 myCompiler = new A3();
        myCompiler.run(args[0]);
    }
}
