import java.io.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;

/** CD18Scanner.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created: 03/08/2018
 * Updated: 21/08/2018
 *
 * Description:
 * CD18Scanner class is a lexical analyser for the CD18 language
 *
 */
public class CD18Scanner
{
    private int lineNum;
    private int colNum;
    private BufferedReader srcFile;
    private boolean eof;
    private String error;
    private LinkedList<SimpleEntry<Token, String>> errorList;

    /** Constructor
     *
     * @param fileName - String, The name of the source file
     */
    public CD18Scanner(String fileName)
    {
        lineNum = 1;
        colNum = 1;
        eof = false;
        errorList = new LinkedList<>();
        try
        {
            srcFile = new BufferedReader(new FileReader(fileName));
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /** hasErrors()
     *
     * Returns true if the source has lexical errors
     *
     * @return - boolean, true if source contains lexical errors, false if no errors
     */
    public boolean hasErrors()
    {
        return !errorList.isEmpty();
    }

    /** nextError()
     *
     * Returns the next error in the list of lexical errors
     *
     * @return - AbstractMap.SimpleEntry<Token, String>, a Token-String pair describing the error
     */
    public SimpleEntry nextError()
    {
        return errorList.pop();
    }

    /** getError()
     *
     * Returns the error message of the current token being processed
     *
     * @return - String, an error message describing the error that occured
     */
    public String getError()
    {
        return error;
    }

    /** eof()
     *
     * Checks for end of source file
     *
     * @return - boolean, returns true if end of file reached, false otherwise
     */
    public boolean eof()
    {
        return eof;
    }

    /** getToken()
     *
     * Checks the next character of the source file and returns a Token based on the input
     *
     * @return - Token, returns a token with required parameters based on a prescribed character sequence
     * @throws IOException - BufferedReader can throw an IOException
     */
    public Token getToken() throws IOException
    {
        StringBuilder lexeme = new StringBuilder();

        int startCol;

        int nextChar;

        srcFile.mark(2);

        nextChar = srcFile.read();

        while (nextChar != -1)
        {
            // First character scanned is a letter and initially marked as an identifier
            if(Character.isLetter(nextChar))
            {
                startCol = colNum;

                while(Character.isLetterOrDigit(nextChar))
                {
                    lexeme.append((char)nextChar);
                    srcFile.mark(1);
                    nextChar = srcFile.read();
                    colNum++;
                }
                srcFile.reset();

                // Identifier is valid
                return new Token(Token.TID.TIDEN, lineNum, startCol, lexeme.toString());
            }
            // First character scanned is a number
            else if(Character.isDigit(nextChar))
            {
                //srcFile.mark(1);
                startCol = colNum;

                while(Character.isDigit(nextChar))
                {
                    lexeme.append((char)nextChar);
                    srcFile.mark(1);
                    nextChar = srcFile.read();
                    colNum++;
                }
                srcFile.reset();

                srcFile.mark(2);

                // Decide if integer or float
                if(nextChar == '.')
                {
                    srcFile.skip(1);
                    nextChar = srcFile.read();

                    if(Character.isDigit(nextChar))
                    {
                        srcFile.mark(1);
                        lexeme.append('.');
                        //lexeme.append((char)nextChar);

                        while(Character.isDigit(nextChar))
                        {
                            lexeme.append((char)nextChar);
                            srcFile.mark(1);
                            nextChar = srcFile.read();
                            colNum++;
                        }
                        srcFile.reset();

                        // Number is a float
                        return new Token(Token.TID.TFLIT, lineNum, startCol, lexeme.toString());
                    }
                }
                srcFile.reset();

                //colNum++;

                // Number is integer
                return new Token(Token.TID.TILIT, lineNum, startCol, lexeme.toString());
            }
            // Start of string literal
            else if(nextChar == '\"')
            {
                startCol = colNum++;

                srcFile.mark(1);
                nextChar = srcFile.read();

                while((nextChar) != '\"')
                {
                    colNum++;
                    // String literal missing closing quotes
                    if(nextChar == '\r' ||nextChar == '\n' || nextChar == -1)
                    {
                        colNum = startCol--;
                        Token undefined = new Token(Token.TID.TUNDF, lineNum, startCol, '\"' + lexeme.toString());
                        error = "Lexical Error (" + lineNum + ", " + colNum + "): unclosed string literal : \"" + lexeme.toString();
                        errorList.add(new SimpleEntry<>(undefined, error));
                        return undefined;
                    }
                    lexeme.append((char)nextChar);
                    nextChar = srcFile.read();
                }
                // String literal is complete
                return new Token(Token.TID.TSTRG, lineNum, startCol, lexeme.toString());
            }
            // All other characters
            else
            {
                switch(nextChar)
                {
                    case '\n':
                        lineNum++;
                        colNum = 1;
                        break;

                    case '/':
                        startCol = colNum++;

                        srcFile.mark(3);
                        nextChar = srcFile.read();
                        switch(nextChar)
                        {
                            // Token is '/='
                            case '=':
                                srcFile.mark(1);
                                return new Token(Token.TID.TDVEQ, lineNum, startCol, null);

                            // Decide if a comment follows
                            case '-':
                                nextChar = srcFile.read();

                                if(nextChar == '-')
                                {
                                    srcFile.mark(2);

                                    // Ignore until the end of line or end of file
                                    while((nextChar = srcFile.read()) != '\r' && nextChar != '\n' && nextChar != -1)
                                    {
                                        colNum++;
                                    }
                                    lineNum++;
                                }
                                // Token is '/'
                                else
                                {
                                    colNum++;
                                    srcFile.reset();
                                    return new Token(Token.TID.TDIVD, lineNum, startCol, null);
                                }
                                break;

                            // Token is '/'
                            default:
                                srcFile.reset();
                                return new Token(Token.TID.TDIVD, lineNum, startCol, null);
                        }
                        colNum++;
                        break;

                    case '+':
                        startCol = colNum++;
                        srcFile.mark(1);

                        // Token is '+='
                        if(srcFile.read() == '=')
                        {
                            colNum++;
                            return new Token(Token.TID.TPLEQ, lineNum, startCol, null);
                        }
                        // Token is '+'
                        else
                        {
                            srcFile.reset();
                            return new Token(Token.TID.TPLUS, lineNum, startCol, null);
                        }

                    case '-':
                        startCol = colNum++;
                        srcFile.mark(1);

                        // Token is '-='
                        if(srcFile.read() == '=')
                        {
                            colNum++;
                            return new Token(Token.TID.TMNEQ, lineNum, startCol, null);
                        }
                        // Token is '-'
                        else
                        {
                            srcFile.reset();
                            return new Token(Token.TID.TMINS, lineNum, startCol, null);
                        }

                    case '*':
                        startCol = colNum++;
                        srcFile.mark(1);

                        // Token is '*='
                        if(srcFile.read() == '=')
                        {
                            colNum++;
                            return new Token(Token.TID.TSTEQ, lineNum, startCol, null);
                        }
                        // Token is '*'
                        else
                        {
                            srcFile.reset();
                            return new Token(Token.TID.TSTAR, lineNum, startCol, null);
                        }

                    case '<':
                        startCol = colNum++;
                        srcFile.mark(1);

                        // Token is '<='
                        if(srcFile.read() == '=')
                        {
                            colNum++;
                            return new Token(Token.TID.TLEQL, lineNum, startCol, null);
                        }
                        // Token is '<'
                        else
                        {
                            srcFile.reset();
                            return new Token(Token.TID.TLESS, lineNum, startCol, null);
                        }

                    case '>':
                        startCol = colNum++;
                        srcFile.mark(1);

                        // Token is '>='
                        if(srcFile.read() == '=')
                        {
                            colNum++;
                            return new Token(Token.TID.TGEQL, lineNum, startCol, null);
                        }
                        // Token is '>'
                        else
                        {
                            srcFile.reset();
                            return new Token(Token.TID.TGRTR, lineNum, startCol, null);
                        }

                    case '=':
                        startCol = colNum++;
                        srcFile.mark(1);

                        // Token is '=='
                        if(srcFile.read() == '=')
                        {
                            colNum++;
                            return new Token(Token.TID.TEQEQ, lineNum, startCol, null);
                        }
                        // Token is '='
                        else
                        {
                            srcFile.reset();
                            return new Token(Token.TID.TEQUL, lineNum, startCol, null);
                        }

                    // Token is one of the following '% ^ . , [ ] ( ) : ;'
                    case '%':
                        return new Token(Token.TID.TPERC, lineNum, colNum++, null);

                    case '^':
                        return new Token(Token.TID.TCART, lineNum, colNum++, null);

                    case '.':
                        return new Token(Token.TID.TDOT, lineNum, colNum++, null);

                    case ',':
                        return new Token(Token.TID.TCOMA, lineNum, colNum++, null);

                    case '[':
                        return new Token(Token.TID.TLBRK, lineNum, colNum++, null);

                    case ']':
                        return new Token(Token.TID.TRBRK, lineNum, colNum++, null);

                    case '(':
                        return new Token(Token.TID.TLPAR, lineNum, colNum++, null);

                    case ')':
                        return new Token(Token.TID.TRPAR, lineNum, colNum++, null);

                    case ':':
                        return new Token(Token.TID.TCOLN, lineNum, colNum++, null);

                    case ';':
                        return new Token(Token.TID.TSEMI, lineNum, colNum++, null);

                    case '!':
                        startCol = colNum;

                        // Token is '!='
                        if(srcFile.read() == '=')
                        {
                            return new Token(Token.TID.TNEQL, lineNum, startCol, null);
                        }
                        srcFile.reset();

                    // All other characters
                    default:
                        if(!Character.isWhitespace(nextChar))
                        {
                            startCol = colNum;
                            String validChar = "[]()=+-*/%^;:,.<>\"";

                            while(!validChar.contains(Character.toString((char)nextChar)) && !Character.isLetterOrDigit(nextChar) && !Character.isWhitespace(nextChar))
                            {
                                switch(nextChar)
                                {
                                    case '!':
                                        if(srcFile.read() != '=')
                                        {
                                            srcFile.reset();

                                            lexeme.append((char)srcFile.read());

                                            srcFile.mark(1);
                                            nextChar = srcFile.read();
                                            colNum++;
                                        }
                                        else
                                        {
                                            srcFile.reset();
                                            Token undefined = new Token(Token.TID.TUNDF, lineNum, startCol, lexeme.toString());
                                            error = "Lexical Error (" + lineNum + ", " + startCol + "): invalid character sequence : " + lexeme.toString();
                                            errorList.add(new SimpleEntry<>(undefined, error));
                                            return undefined;
                                        }

                                    default:
                                        if(nextChar == '!')
                                            break;
                                        if(!validChar.contains(Character.toString((char)nextChar)) && !Character.isLetterOrDigit(nextChar) && !Character.isWhitespace(nextChar))
                                        {
                                            lexeme.append((char)nextChar);
                                            srcFile.mark(2);
                                            nextChar = srcFile.read();
                                        }
                                }
                                colNum++;
                            }
                            srcFile.reset();
                            Token undefined = new Token(Token.TID.TUNDF, lineNum, startCol, lexeme.toString());
                            error = "Lexical Error (" + lineNum + ", " + startCol + "): invalid character sequence : " + lexeme.toString();
                            errorList.add(new SimpleEntry<>(undefined, error));
                            return undefined;
                        }
                        if(nextChar == '\t')
                            colNum += 4;
                        if(Character.isSpaceChar(nextChar))
                        {
                            colNum++;
                        }
                }
                srcFile.mark(2);
                nextChar = srcFile.read();
            }
        }
        // End of source file has been reached
        eof = true;
        return new Token(Token.TID.TEOF, lineNum, colNum, null);
    }
}
