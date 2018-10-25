package ca.pringle.library;


import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The FileReader allows files to be read.
 */


public final class FileReader {
    private java.io.BufferedReader br = null;
    private String fileName = null;
    private int lineNumber;


    public FileReader() {
        lineNumber = 0;
    }


    /**
     * returns true if the file can be opened for reading, false otherwise.
     */
    public boolean open(String fileNameParam) {
        lineNumber = 0;

        fileName = fileNameParam;
        try {
            br = new java.io.BufferedReader(new java.io.FileReader(fileNameParam));
        } catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }


    /**
     * close the file.
     */
    public void close()
            throws IOException {
        br.close();
    }


    /**
     * get the next character in the file.
     * closes the file if there are no more characters.
     * If getNextChar is called after EOF is hit, EOF is returned.
     */
    public char getNextChar()
            throws IOException {
        int i;

        try {
            i = br.read();

            if (i == -1) {
                br.close();
                throw new EOFException("End of file.");
            }

            return (char) i;
        } catch (IOException e) {
            br.close();
            throw e;
        }
    }


    /**
     * gets the next line from the file. Stops at '\n2' char or when
     * end of file (EOF is hit. if getNextLine is after EOF is hit, null
     * is returned.
     */
    public String getNextLine()
            throws IOException {
        StringBuffer s = new StringBuffer();
        char c;

        do {
            c = getNextChar();
            s.append(c);
        }
        while (c != '\n');

        lineNumber = lineNumber + 1;
        return s.toString();
    }


    public int getLineNumber() {
        return lineNumber;
    }


    /**
     * returns the name of the file opened. If none opened, returns null.
     */
    public String getFileName() {
        return fileName;
    }


    /**
     * standard toString() method.
     */
    public String toString() {
        StringBuffer s = new StringBuffer();

        s.append("fileName: ");
        s.append(fileName);

        return s.toString();
    }

}    //End of class FileReader
