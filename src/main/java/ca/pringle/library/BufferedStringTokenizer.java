package ca.pringle.library;


import java.util.StringTokenizer;


/**
 * This class is an extension of the StringTokenizer class. The functionality is the same
 * except that the last bufferSize tokens are buffered, so that they can be retrieved
 * immediately if you have to look at a previous token for some reason.
 * In addition, the current token number is also stored, indexed at 0.
 */


public final class BufferedStringTokenizer {

    private final StringTokenizer st;
    private CircularQueue buffer;
    private int currentToken = 0;


    /**
     * this constuctor is identical to the StringTokenizer constructor except for the
     * bufferSize field. The bufferSize field specifies how many tokens should be
     * buffered. Any number entered less than 2 results in a bufferSize of 2. Any number
     * larger than the total number of tokens result in a bufferSize of the total number of
     * tokens.
     */
    public BufferedStringTokenizer(String str, String delim, boolean returnDelims, int bufferSize)
            throws SizeException {
        st = new StringTokenizer(str, delim, returnDelims);

    /**/
        if (bufferSize < 2) bufferSize = 2;
        else if (bufferSize > st.countTokens()) bufferSize = st.countTokens();

        buffer = new CircularQueue(bufferSize);
    }

    /**
     * gets the current token pointed at in StringTokenizer. NextToken will retrieve the
     * currentToken + 1th token
     */
    public int currentToken() {
        return currentToken;
    }

    /**
     * returns the number of elements in the buffer
     */
    public int bufferSize() {
        return buffer.numElements();
    }

    /**
     * returns the token stored in the buffer at index index. Tokens are stored in the buffer
     * with the most recently fetched token stored at index 0 and the least recent token stored
     * at the maximum index.
     */
    public String getToken(int index) {
        if (index >= 0 && index <= buffer.numElements())
            return (String) buffer.get(index);
        return null;
    }

    /**
     * exactly the same as stringTokenizer's has next elements
     */
    public boolean hasMoreTokens() {
        return st.hasMoreTokens();
    }

    /**
     * indentical to the stringTokenizer's nextToken except of course the buffer is updated.
     * Each time a call is made to nextToken(), the nextToken takes position
     * 0 in the buffer and all other tokens are effectively moved down (index+1) a slot in the buffer.
     * Note that this is an O(1) operation.
     */
    public String nextToken() {
        buffer.add(st.nextToken());
        currentToken = currentToken + 1;

        return (String) buffer.get(buffer.numElements() - 1);
    }

    /**
     * indentical to stringTokenize's countTokens function
     */
    public int countTokens() {
        return st.countTokens();
    }


    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("Current token index = " + currentToken);
        s.append(", token's left = " + st.countTokens());
        s.append(", buffer size is " + buffer.toString() + "\n");
        return s.toString();
    }
}
