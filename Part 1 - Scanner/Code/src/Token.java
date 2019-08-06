/** Token.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * ==========================================================================================
 * Based on supplied sample Token class for COMP3290 by MRH (2013) and Updated by DB for CD18
 * ==========================================================================================
 *
 * Created: 03/08/2018
 * Updated: 21/08/2018
 *
 * Description:
 * Token class stores information that describes a token for a CD18 lexer/scanner
 *
 */
public class Token
{
    /** ID
     *
     * Contains all token IDs for the CD18 programming language lexer/scanner
     */
    enum ID
    {
        // Token value for end of file
        TEOF,

        // The 30 keywords
        TCD18, TCONS, TTYPS, TIS, TARRS, TMAIN, TBEGN, TEND, TARAY, TOF, TFUNC,	TVOID,
        TCNST, TINTG, TREAL, TBOOL, TFOR, TREPT, TUNTL, TIFTH, TELSE, TINPT, TPRIN, TPRLN,
        TRETN, TNOT, TAND, TOR, TXOR, TTRUE, TFALS,

        // the operators and delimiters
        TCOMA, TLBRK, TRBRK, TLPAR, TRPAR, TEQUL, TPLUS, TMINS, TSTAR, TDIVD, TPERC,
        TCART, TLESS, TGRTR, TCOLN, TLEQL, TGEQL, TNEQL, TEQEQ, TPLEQ, TMNEQ, TSTEQ, TDVEQ,
        TPCEQ, TSEMI, TDOT,

        // the tokens which need tuple values
        TIDEN, TILIT, TFLIT, TSTRG, TUNDF
    }

    private ID tokenID;
    private int lineNum;
    private int colNum;
    private String lexeme;
    private TableEntry symbol;

    /** Constructor
     *
     * @param tid - ID, selected from enum ID
     * @param line - int, line number of the token
     * @param col - int, column number of the token
     * @param lex - String, lexeme associated with the token
     */
    public Token(ID tid, int line, int col, String lex)
    {
        // Identifier lexeme could be a reserved keyword
        if (tid == ID.TIDEN)
        {
            ID tokenVal = checkKeywords(lex);

            // Replace identifier token with keyword token
            if (tokenVal != ID.TIDEN)
            {
                tid = tokenVal;
                lex = null;
            }
        }
        tokenID = tid;
        lineNum = line;
        colNum = col;
        lexeme = lex;
        symbol = null;
    }

    /** checkKeywords()
     *
     * Takes a lexeme recognised as an Identifier
     * Returns the correct keyword Token or TIDEN if not matched
     *
     * @param lex - String, lexeme of the identifier token
     * @return - ID, returns the correct token based on the lexeme
     */
    private ID checkKeywords(String lex)
    {
        switch(lex.toLowerCase())
        {
            case "cd18"         : return ID.TCD18;
            case "constants"    : return ID.TCONS;
            case "types"        : return ID.TTYPS;
            case "is"           : return ID.TIS;
            case "arrays"       : return ID.TARRS;
            case "main"         : return ID.TMAIN;
            case "begin"        : return ID.TBEGN;
            case "end"          : return ID.TEND;
            case "array"        : return ID.TARAY;
            case "of"           : return ID.TOF;
            case "func"         : return ID.TFUNC;
            case "void"         : return ID.TVOID;
            case "const"        : return ID.TCNST;
            case "integer"      : return ID.TINTG;
            case "real"         : return ID.TREAL;
            case "boolean"      : return ID.TBOOL;
            case "for"          : return ID.TFOR;
            case "repeat"       : return ID.TREPT;
            case "until"        : return ID.TUNTL;
            case "if"           : return ID.TIFTH;
            case "else"         : return ID.TELSE;
            case "input"        : return ID.TINPT;
            case "print"        : return ID.TPRIN;
            case "printline"    : return ID.TPRLN;
            case "return"       : return ID.TRETN;
            case "and"          : return ID.TAND;
            case "or"           : return ID.TOR;
            case "xor"          : return ID.TXOR;
            case "not"          : return ID.TNOT;
            case "true"         : return ID.TTRUE;
            case "false"        : return ID.TFALS;
            default             : return ID.TIDEN;
        }
    }

    /** toString()
     *
     * Returns a string formatted for a listing of errors
     *
     * @return - String, the string to display in the error list
     */
    @Override
    public String toString()
    {
        String lex = lexeme;

        // Pad the lexeme to multiples of six
        if(lex != null)
        {
            if(lex.length() % 6 == 0)
                lex += ' ';

            StringBuilder sb = new StringBuilder(lex);
            while(sb.length() % 6 != 0)
            {
                sb.append(' ');
            }

            lex = sb.toString();
        }
        else
            lex = "";

        // Return token string without tokenID for error list
        return String.format("(%1$d, %2$d) %3$s", lineNum, colNum, lex);
    }

    /** toParser()
     *
     * Returns a string formatted to display in the token listing
     *
     * @return - String, the string to display in the token list
     */
    public String toParser()
    {
        String lex = lexeme;

        // Pad the lexeme to multiples of six
        if(lex != null)
        {
            if(lex.length() % 6 == 0)
                lex += ' ';

            StringBuilder sb = new StringBuilder(lex);
            while(sb.length() % 6 != 0)
            {
                sb.append(' ');
            }

            lex = sb.toString();
        }
        else
            lex = "";

        // Return tokenID and lexeme in required format
        return String.format("%1$-6s%2$s", tokenID, lex);
    }

    /** getTokenID()
     *
     * Gets the token ID
     *
     * @return - ID, the token ID of the token
     */
    public ID getTokenID()
    {
        return tokenID;
    }

    /** getColNum()
     *
     * Gets the column number of the current token
     *
     * @return - int, the column number of the token
     */
    public int getColNum()
    {
        return colNum;
    }

    /** getLineNum()
     *
     * Gets the line number of the current token
     *
     * @return - int, the line number of the token
     */
    public int getLineNum()
    {
        return lineNum;
    }

    /** getLexeme()
     *
     * Gets the lexeme associated with the token
     *
     * @return - String, the lexeme associated with the token
     */
    public String getLexeme()
    {
        return lexeme;
    }

    public void setSymbol(TableEntry sym)
    {
        symbol = sym;
    }

    public TableEntry getSymbol()
    {
        return symbol;
    }
}
