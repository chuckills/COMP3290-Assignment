// COMP3290 CD18 Compiler
//
// Output Controller class -	Contains descriptor for the listing (PrintWriter) file
//				  and a reference to the error buffer (StringBuffer).
//				Produces the program listing (incl errors) line by line and
//				  a complete error report to the error buffer.
//				Expects the listing file to be an open text stream and
//				  the error buffer to have been initialised empty.
//
//				Method setError(String) serves as a central mechanism for reporting
//				  errors and can be used by future phases to report parsing errors
//				  and semantic errors.
//
//    	Rules of Use:	The text for this class has been extracted from a working CD13 scanner.
//			Code released via Blackboard may not be passed on to anyone outside this
//			  semester's COMP3290 class.
//			You may not complain or expect any consideration if the code does not work
//			  the way you expect it to.
//			It is supplied as an assistance and may be used in your project if you wish.
//
//	Based on Code by MRH (2013) - Updated by DB for CD18	
//

import java.io.*;

public class OutputController {

	private int line 			= 0;		// current line number of source file -> listing
	private int charPos			= 0;		// character position within listing line
	private int errorCount 		= 0;		// count of errors for entire compiler
	private PrintWriter listing = null;		// descriptor of the listing file
	private StringBuffer err 	= null;		// reference to error StringBuffer
	private String currLine 	= null;		// current listing line being built
	private String errLine 		= null;		// errors associated with current listing line

	public OutputController(PrintWriter l, StringBuffer e) {
		listing = l;						// Copy the file/buffer references to local attributes
		err = e;
		err.setText("");
		currLine = "  1: ";					// Initialise buffer for first line of output
		errLine = "";
		line = 1;
		charPos = 0;
		errorCount = 0;
	}

	public int getErrorCount() { return errorCount; }

	public void printImmediateError(String msg) {	// Used for immediate output of lexical error found at eol.
		listing.println(msg);				// Prevents such an error from being reported as occurring
		err.append(msg+"\n");				//   at position 0 of the following line.
		errorCount++;
	}

	public void setError(String msg) {		// save up an error to be output at eol
		if (!errLine.equals("")) errLine += "\n";	// terminate line for previous error message
		errLine += msg;
		errorCount++;
	}

	public void printChar(char ch) {		// stores next char - Prints a listing line if a newline char

		if (ch == '\n') {					// at newline - produce the next line of the listing
			listing.println(currLine);
			// Trace output if reqd - System.out.println(currLine);
			if (! errLine.equals("")) {		// if there are errors then report them as well
				listing.println(errLine);
				err.append(currLine+"\n");	// put source line that contains the errors into text area
				err.append( errLine+"\n");	// put error messages for this line into text area
				// Trace output if reqd - System.out.println(errline);
				errLine = "";				// reset error message string
			}
			line++;
			if (line < 10) currLine = "  "+line+": ";	// Initialise buffer to new line number
			else if (line < 100) currLine = " "+line+": ";
			else currLine = line+": ";
			charPos = 0;					// Reset column position within line
		} else {							// otherwise simply put the character into the output buffer
			currLine += ""+ch;
			charPos++;
		} 
	}

}
