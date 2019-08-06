import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/** SM18CodeGen.java
 *
 * Author: Greg Choice c9311718@uon.edu.au
 *
 * Created: 05/11/2018
 * Updated: 09/11/2018
 *
 * Description:
 * SM18CodeGen class generates a low level language
 * for running on the SM18 stack machine.
 *
 *
 */
public class SM18CodeGen
{
    enum SM
    {
        HALT(0), NO_OP(1), TRAP(2), ZERO(3), FALSE(4), TRUE(5),
        TYPE(7), ITYPE(8), FTYPE(9), ADD(11), SUB(12), MUL(13),
        DIV(14), REM(15), POW(16), CHS(17), ABS(18), GT(21),
        GE(22), LT(23), LE(24), EQ(25), NE(26), AND(31), OR(32),
        XOR(33), NOT(34), BT(35), BF(36), BR(37), L(40), LB(41),
        LH(42), ST(43), STEP(51), ALLOC(52), ARRAY(53), INDEX(54),
        SIZE(55), DUP(56), READF(60), READI(61), VALPR(62),
        STRPR(63), CHRPR(64), NEWLN(65), SPACE(66), RVAL(70),
        RETN(71), JS2(72), LV0(80), LV1(81), LV2(82), LA0(90),
        LA1(91), LA2(92);

        private int opcode;
        SM(int code){ opcode = code; }
        public int op(){ return opcode; }
    }

    private ArrayList<Integer> code;
    private ArrayList<Integer> intConstants;
    private ArrayList<Double> fltConstants;
    private ArrayList<Integer> strConstants;
    private ArrayList<Character> constTypes;

    private int intOffset, fltOffset, strOffset;
    private int BR_0, count;

    /** Constructor
     *
     */
    public SM18CodeGen()
    {
        code = new ArrayList<>();
        intConstants = new ArrayList<>();
        fltConstants = new ArrayList<>();
        strConstants = new ArrayList<>();
        constTypes = new ArrayList<>();
        BR_0 = 0;
        intOffset = fltOffset = strOffset = 0;
        count = 0;
    }

    /** generate()
     *
     * The beginnings of the structure to translate the syntax tree to SM18
     *
     * @param root - STNode, the root node of the syntax tree
     */
    public void generate(STNode root)
    {
        switch(root.getNodeID())
        {
            // Traverses the tree to the point where something is implemented
            case NPROG:
                if(root.getLeft() != null)
                    generate(root.getLeft());
                if(root.getMiddle() != null)
                    generate(root.getMiddle());
                if(root.getRight() != null)
                    generate(root.getRight());
                break;

            case NGLOB:
                if(root.getLeft() != null)
                    generate(root.getLeft());
                if(root.getMiddle() != null)
                    generate(root.getMiddle());
                if(root.getRight() != null)
                    generate(root.getRight());
                break;

            case NMAIN:
                if(root.getLeft() != null)
                    generate(root.getLeft());
                if(root.getRight() != null)
                    generate(root.getRight());
                break;

            // The first thing implemented is memory allocation for Main variables
            case NSDLST:
            case NSDECL:
                code.add(SM.LB.op());
                code.add(sDecl(root));
                code.add(SM.ALLOC.op());
                count = 0;
                break;

            // Traverse NSTATS to an implemented structure
            case NSTATS:
                if(root.getLeft() != null)
                    generate(root.getLeft());
                if(root.getRight() != null)
                    generate(root.getRight());
                break;

            // Only NPRLN implemented
            case NPRLN:
                printLine(root.getLeft());
                break;

            default:
        }
    }

    /** patchAddress()
     *
     * Inserts memory addresses for constants after code has been generated
     *
     */
    public void patchAddress()
    {
        // Pad last line of code to word boundary
        while(code.size()%8 != 0)
        {
            code.add(0);
        }

        // Pad last string to word boundary
        if(strConstants.get(strConstants.size()-1) == 0)
            strConstants.remove(strConstants.size()-1);
        while(strConstants.size()%8 != 0)
        {
            strConstants.add(0);
        }

        // Calculate offsets of the constant sections
        intOffset = code.size();
        fltOffset = intOffset + intConstants.size();
        strOffset = fltOffset + fltConstants.size();

        // Mark the start of the string constants. If other types were implemented this may need modifying
        if(BR_0 == 0)
            BR_0 = strOffset;

        // Find all 80 and 90 opcodes. (Could also work for 81, 82, 91, 92 but hasn't been explored yet)
        for(int i = 0; i < code.size(); i++)
        {
            if(code.get(i) == 90 || code.get(i) == 80)
            {
                // Find out what typ of constant it is
                switch(constTypes.remove(0))
                {
                    // String
                    case 's':
                        // Calculate the memory address
                        Integer[] address = calcAddress(strOffset);

                        // Replace temporary marker
                        code.set(i+1, address[0]);
                        code.set(i+2, address[1]);
                        code.set(i+3, address[2]);
                        code.set(i+4, address[3]);

                        // Calculate offset of the next string constant
                        for(int j = strOffset-BR_0; j < strConstants.size() && strConstants.get(j) != 0; j++)
                        {
                            strOffset++;
                        }
                        strOffset++;
                        break;

                    // Integer not yet implemented
                    case 'i':
                        break;

                    // Float not yet implemented
                    case 'f':
                        break;

                    default:
                }
            }
        }
    }

    /** calcAddress()
     *
     * @param decimal - int, the decimal value of the memory address
     * @return Integer[], Decimal coded byte address
     */
    public Integer[] calcAddress(int decimal)
    {
        // Calculate a memory address as an array of decimal coded bytes
        int a0 = decimal/(int)Math.pow(2,24);
        int a1 = (decimal - a0*(int)Math.pow(2,24))/(int)Math.pow(2,16);
        int a2 = (decimal - a0*(int)Math.pow(2,24) - a1*(int)Math.pow(2,16))/256;
        int a3 = (decimal - a0*(int)Math.pow(2,24) - a1*(int)Math.pow(2,16) - a2*256)%256;

        return new Integer[]{a0, a1, a2, a3};
    }

    /** modOut()
     *
     * Structure the code and send to the PrintWriter
     *
     * @param out - PrintWriter, output stream for the structured code
     */
    public void modOut(PrintWriter out)
    {

        // Send instruction section to PrintWriter
        out.println(code.size()/8);
        for(int i = 0; i < code.size(); i++)
        {
            out.print(code.get(i) + " ");
            if(i%8 == 7)
            {
                out.println();
            }
        }

        // Send integer constant section to PrintWriter
        out.println(intConstants.size());
        for(int intConst : intConstants)
        {
            out.println(intConst);
        }

        // Send float constant section to PrintWriter
        out.println(fltConstants.size());
        for(double fltConst : fltConstants)
        {
            out.println(fltConst);
        }

        // Send string constant section to PrintWriter
        out.println(strConstants.size()/8);
        for(int i = 0; i < strConstants.size(); i++)
        {
            out.print(strConstants.get(i) + " ");
            if(i%8 == 7)
            {
                out.println();
            }
        }
    }

    /** sDecl()
     *
     * Counts the number of main variables needed to ALLOC
     *
     * @param root - STNode, the root of the NSDECL or NSDLST
     * @return - int, the number of varibles
     */
    public int sDecl(STNode root)
    {
        if(root.getNodeID() == STNode.NID.NSDECL)
        {
            return ++count;
        }
        else
        {
            count++;
            return sDecl(root.getRight());
        }
    }

    /** printLine()
     *
     * Generates code for a printline statement. Only constants are implemented,
     * and only string constants are tested.
     *
     * @param root - STNode, the root node of the print item or NPRLST
     */
    public void printLine(STNode root)
    {
        switch(root.getNodeID())
        {
            // Item is a string constant
            case NSTRG:
                for(char c : root.getSymbol().getName().toCharArray())
                {
                    strConstants.add((int)c);
                }
                strConstants.add(0);
                Integer[] address = new Integer[4];
                code.add(SM.LA0.op());
                code.addAll(Arrays.asList(address));
                code.add(SM.STRPR.op());
                code.add(SM.NEWLN.op());
                constTypes.add('s');
                break;

            // Item is a float constant
            case NFLIT:
                fltConstants.add(Double.parseDouble(root.getSymbol().getName()));
                constTypes.add('f');
                break;

            // Item is an integer constant
            case NILIT:
                intConstants.add(Integer.parseInt(root.getSymbol().getName()));
                constTypes.add('i');
                break;

            // More items to print
            case NPRLST:
                printLine(root.getLeft());
                printLine(root.getRight());
                break;

            default:

        }
    }
}
