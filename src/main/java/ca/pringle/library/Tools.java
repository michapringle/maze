package ca.pringle.library;


/**
 * Toolkit of string wrapper methods.
 */


public final class Tools {

    public static final int max(int a, int b) {
        if (a > b) return a;
        return b;
    }


    /**
     * if the given object is null, returns the string "null",
     * otherwise returns o.toString().
     */
    public static final String nullString(Object o) {
        if (o == null) return new String("null");
        return o.toString();
    }

    /**
     * returns true if c in ['0','9'] inclusive, false otherwise.
     */
    public static final boolean isDigit(char c) {
        if (c >= '0' && c <= '9') return true;
        return false;
    }


    /**
     * returns the integer equivalent of the given character. i.e. '9' returns 9.
     * Invalid character returns -1.
     */
    public static final int toDigit(char c) {
        switch (c) {
            case '0':
                return (int) 0;
            case '1':
                return (int) 1;
            case '2':
                return (int) 2;
            case '3':
                return (int) 3;
            case '4':
                return (int) 4;
            case '5':
                return (int) 5;
            case '6':
                return (int) 6;
            case '7':
                return (int) 7;
            case '8':
                return (int) 8;
            case '9':
                return (int) 9;
            default:
                return (int) (-1);
        }
    }


    /**
     * returns true if c in ['a','z'] or ['A','Z']inclusive, false otherwise.
     */
    public static final boolean isLetter(char c) {
        if (c >= 'a' && c <= 'z') return true;
        else if (c >= 'A' && c <= 'Z') return true;
        return false;
    }


    /**
     * returns true if c matches a character in the given list, false otherwise.
     */
    public static final boolean isMatch(char c, char[] cArray) {
        if (cArray == null) return false;
        for (int i = 0; i < cArray.length; i++)
            if (c == cArray[i]) return true;

        return false;
    }


    /**
     * creates a new String of length lengthParam all of char charParam.
     */
    public static final String pad(int lengthParam, char charParam) {
        char[] padded = new char[lengthParam];
        for (int i = 0; i < lengthParam; i++) padded[i] = charParam;

        return new String(padded);
    }


    /**
     * inserts a character into the given string at the given index. If string is null
     * or not long enough, string is created or extended. Index starts at 1.
     */
    public static final String addChar(String string, int index, char insert) {
        StringBuffer s = new StringBuffer();

        if (string == null) {
            s.append(pad(index, ' '));
        } else if (index >= string.length()) {
            s.append(string);
            s.append(pad(index - string.length(), ' '));
        } else {
            s.append(string);
        }
        s.setCharAt(index - 1, insert);
        return s.toString();
    }


}    //End of class Tools