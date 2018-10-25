package ca.pringle.library;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;


/**
 * <font face="Courier New">
 * ������������������������������������������������������������<br>
 * <b>Interpreter</b><br><br>
 * An expression interpreter.<br>
 * Evaluates exressions such as y = 10 + 12<br>
 * Must have at least one delimiter character between each symbol in the expression.
 * The delimiter character is used to seperate tokens. ' ' (space character) is a good choice.<br><br>
 * Supported functions:<br>
 * Unary operator: -<br>
 * Note that the unary minus can only be used on numeric values, with no delimiting token between
 * the numeric value and the '-'. '-12' is good, '- 12' is bad. '-identifier' is bad.<br>
 * Binary Operators: +, -, *, /, div, mod<br>
 * Brackets: (, )<br>
 * Identifiers, for example:<br>
 * x = 2<br>
 * y = x + 3<br>
 * Expressions containing their own identifiers, for example:<br>
 * y = 2<br>
 * y = y + 3<br>
 * <p>
 * ____________________________________________________________<br>
 * ������������������������������������������������������������<br>
 * Author(s): Micha Pringle
 * ____________________________________________________________<br>
 * ������������������������������������������������������������<br>
 * Date: November 20, 2000
 * ____________________________________________________________<br>
 * ������������������������������������������������������������<br>
 * Version: <i>$Id: Interpreter.java 1.6 2000/12/08 09:56:22 Micha Exp
 * $</i><br>
 * ____________________________________________________________<br>
 * ������������������������������������������������������������<br>
 * Change Log:<br>
 * $Log: Interpreter.java $
 * Revision 1.6  2000/12/08 09:56:22  Micha
 * Operator prededence is handled differently. The precedence is all handled
 * within getHighestPrecedenceOperator, where it should be. Shrank and
 * simplified code. Should be faster by a (miniscule) constant factor.
 * <p>
 * Revision 1.5  2000/12/07 12:02:21  Micha
 * Big changes! First, bracketing was handled correctly but poorly. brackets
 * are now treated in the same fashion as binary operators, making the code
 * faster eand easier to understand. This also eliminated some methods. I think
 * that the new treatment of brackets may have resulted in an asymptotic
 * improvement for the cases where brackets were used.
 * Second and last change was the addition of functionality to handle
 * expressions that contain the same identifier on the left hand side and right
 * hand side of the same expression. For example, y = y + 3. This did not
 * significantly improve the complexity of the code. In fact, the design became
 * easier to understand due to the way this functionality had to be
 * implemented.
 * The intepreter algorithm may be O(n2), although it might also be O(nlgn).
 * Worst case I'm guessing is somewhere around O(nlgn).
 * <p>
 * Revision 1.1  2000/11/24 12:24:14  Micha
 * Initial revision
 * <br>
 * ____________________________________________________________<br>
 * ������������������������������������������������������������<br>
 * </font>
 */


public class Interpreter {
    public final int decimalPlaces;
    public final char delimiterChar;
    public final char assignmentChar;
    private HashMap symbolTable = new HashMap();


    /**
     * Creates an Interpreter with the following defaults:<br>
     * decimal places = 20<br>
     * delimiting character (to separate tokens) = ' ' (space)<br>
     * assignment character = '='<br>
     */
    public Interpreter() {
        decimalPlaces = 20;
        delimiterChar = ' ';
        assignmentChar = '=';
    }


    /**
     * Creates an Interpreter.<br>
     * decimalPlaces is the number of decimal places.<br>
     * delimiting character is the character used to separate tokens.<br>
     * assignmentChar is the character used to assign the right hand side of the
     * expression the the left hand side of the expression.<br>
     */
    public Interpreter(int decimalPlaces, char delimiterChar, char assignmentChar) {
        this.decimalPlaces = decimalPlaces;
        this.delimiterChar = delimiterChar;
        this.assignmentChar = assignmentChar;
    }

    private static BigDecimal exp(BigDecimal n, BigInteger e) {
        final BigInteger zero = new BigInteger("0");
        final BigInteger one = new BigInteger("1");
        final BigInteger two = new BigInteger("2");
        //base case
        if (e.compareTo(zero) <= 0) return new BigDecimal("1");

        //recursive call
        if (e.mod(two).equals(zero)) return exp(n.multiply(n), e.divide(two));
        else return n.multiply(exp(n, e.subtract(one)));
    }

    /**
     * Add an expression to be interpreted.<br>
     * Example:<br>
     * add( "y = 5");<br>
     * add( "x = 25 * y * y - 12");<br>
     */
    public void add(String expressionToInterpret)
            throws ExpressionException {
        Tokenizer tokenList = new Tokenizer(expressionToInterpret, delimiterChar);
        TokenTyper tokenTyper = new TokenTyper();
        TypedToken typedToken = null;
        ExpressionChecker eChecker = new ExpressionChecker();
        ArrayList typedExpression = new ArrayList();
        String identifier = null;
        String token = null;
        Stack completeExpression = null;
        int count = 1;


        Iterator i = tokenList.iterator();
        while (i.hasNext()) {
            token = (String) i.next();
            try {
                typedToken = tokenTyper.type(token);
            } catch (NumberFormatException e) {
                throw new ExpressionException("Interpreter.add( String) received invalid number " + token + " at position " + count);
            }
            eChecker.isValid(typedToken);
            if (count == 1) identifier = typedToken.string;
            if (count > 2) typedExpression.add(typedToken);
            count = count + 1;
        }
        eChecker.isComplete();
        completeExpression = (Stack) symbolTable.get(identifier);
        if (completeExpression == null) {
            completeExpression = new Stack();
            completeExpression.push(typedExpression);
            symbolTable.put(identifier, completeExpression);
        } else
            completeExpression.push(typedExpression);
    }

    /**
     * Compute the value for an identifier.<br>
     * Example:<br>
     * getValue("x");<br>
     * returns 613, assuming the add example is used.<br>
     */
    public BigDecimal getValue(String identifier)
            throws ExpressionException {
        Stack expression = (Stack) symbolTable.get(identifier);

        if (expression == null)
            throw new ExpressionException("Interpreter.getValue( identifier) received identifier " + identifier + " with no corresponding value.");

        ArrayList identifierExpression = (ArrayList) expression.pop();
        BigDecimal numi = parse(identifierExpression);
        ArrayList result = new ArrayList();

        //replace an expression in the symbol table with it's actual value so it does not have to be recomputed.
        result.add(new TypedToken(numi.toString(), TypedToken.NUMERIC));
        expression.push(result);

        return numi;
    }

    private BigDecimal parse(ArrayList tokenizedExpression)
            throws ExpressionException {
        BigDecimal numi = null;
        BigDecimal numj = null;
        BigDecimal numr = null;
        ArrayList aList = tokenizedExpression;


        //base case
        if (aList.size() == 1) {
            TypedToken token = (TypedToken) aList.get(0);
            //reduce the number of expressions
            if (token.type == TypedToken.IDENTIFIER) return getValue(token.string);
                //actually return a numeric value
            else if (token.type == TypedToken.NUMERIC) return token.number;
        }

        //recursive step
        int index = findHighestPrecedentOperator(aList);
        TypedToken token = (TypedToken) aList.get(index);
//    System.out.println( "Here is the subexpression " + trace(aList));
//    System.out.println( "Here is the token " + token.string);

        if (token.type == TypedToken.OPENBRACE) {
            ArrayList expression = new ArrayList();

            expression.addAll(subset(aList, 0, index));
            BigDecimal numeric = parse(subset(aList, index + 1, aList.size()));
            expression.add(new TypedToken(numeric.toString(), TypedToken.NUMERIC));
            return parse(expression);
        } else if (token.type == TypedToken.CLOSBRACE) {
            ArrayList expression = new ArrayList();

            BigDecimal numeric = parse(subset(aList, 0, index));
            expression.add(new TypedToken(numeric.toString(), TypedToken.NUMERIC));
            expression.addAll(subset(aList, index + 1, aList.size()));
            return parse(expression);
        } else if (token.type == TypedToken.PLUS) {
            numi = parse(subset(aList, 0, index));
            numj = parse(subset(aList, index + 1, aList.size()));
            return numi.add(numj);
        } else if (token.type == TypedToken.MINUS) {
            numi = parse(subset(aList, 0, index));
            numj = parse(subset(aList, index + 1, aList.size()));
            return numi.subtract(numj);
        } else if (token.type == TypedToken.MULTIPLY) {
            numi = parse(subset(aList, 0, index));
            numj = parse(subset(aList, index + 1, aList.size()));
            return numi.multiply(numj);
        } else if (token.type == TypedToken.DIVIDE) {
            numi = parse(subset(aList, 0, index));
            numj = parse(subset(aList, index + 1, aList.size()));
            return numi.divide(numj, decimalPlaces, BigDecimal.ROUND_HALF_EVEN);
        } else if (token.type == TypedToken.DIV) {
            numi = parse(subset(aList, 0, index));
            numj = parse(subset(aList, index + 1, aList.size()));
            numr = numi.divide(numj, BigDecimal.ROUND_FLOOR);
            return numr.setScale(0, BigDecimal.ROUND_FLOOR);
        } else if (token.type == TypedToken.MOD) {
            numi = parse(subset(aList, 0, index));
            numj = parse(subset(aList, index + 1, aList.size()));
            numr = numi.subtract(numj.multiply(numi.divide(numj, BigDecimal.ROUND_FLOOR)));
            return numr;
        } else
        // if( token.string.equalsIgnoreCase("^"))
        {
            numi = parse(subset(aList, 0, index));
            numj = parse(subset(aList, index + 1, aList.size()));
            numr = exp(numi, new BigInteger(numj.toString()));
            return numr;
        }
    }

    private int findHighestPrecedentOperator(ArrayList tokenizedExpression) {
        TypedToken typedToken = null;
        int index = 0;
        int precedence = 0;
        int maxPrecedence = 0;

        for (int i = 0; i < tokenizedExpression.size(); i++) {
            typedToken = (TypedToken) tokenizedExpression.get(i);
            if (typedToken.type == TypedToken.OPENBRACE) return i;
            else if (typedToken.type == TypedToken.CLOSBRACE) return i;

            if (typedToken.type == TypedToken.EXPONENT) precedence = 2;
            else if (typedToken.type == TypedToken.DIV) precedence = 3;
            else if (typedToken.type == TypedToken.MOD) precedence = 3;
            else if (typedToken.type == TypedToken.DIVIDE) precedence = 3;
            else if (typedToken.type == TypedToken.MULTIPLY) precedence = 3;
            else if (typedToken.type == TypedToken.PLUS) precedence = 4;
            else if (typedToken.type == TypedToken.MINUS) precedence = 4;
            else precedence = 0;

            if (precedence >= maxPrecedence) {
                maxPrecedence = precedence;
                index = i;
            }
        }
        return index;
    }


    /**
     * for debugging purposes, but not really necessary. method below is best.
     */
    private String trace(ArrayList a) {
        String s = new String("[");
        for (int i = 0; i < a.size(); i++)
            s = s + " " + a.get(i);
        return s + "]\n";
    }


    /**
     * For debugging purposes. Give the identifier (the leftmost identifier in the expression)
     * and get the expression associated with it in the symbol table.
     */
    private String getSymbolTable(String identifier) {
        String s = new String("");
        Stack expStack = (Stack) symbolTable.get(identifier);
        s = s + identifier + " = " + expStack.toString() + "\n";
        return s;
    }


    private ArrayList subset(ArrayList inList, int front, int end) {
        ArrayList outList = new ArrayList(end - front + 1);

        for (int i = front; i < end; i++)
            outList.add(inList.get(i));

        return outList;
    }

    //--------------------------------------------------------------------------------------------------
    private class ExpressionChecker {
        private int state = 0;
        private int braceCount = 0;
        private int tokenCount = 0;
        private HashMap tempTable = new HashMap();
        private TypedToken tempToken = null;


        public void isValid(TypedToken token)
                throws ExpressionException {
            tokenCount = tokenCount + 1;
            switch (state) {
                case 0:
                    if (token.type != TypedToken.IDENTIFIER)
                        throw new ExpressionException("ExpressionChecker.isValid(TypedToken) expected identifier " + token.string + " to have an associated value at position " + tokenCount);
                    else
                        state = 1;
                    break;
                case 1:
                    if (token.string.charAt(0) != assignmentChar)
                        throw new ExpressionException("ExpressionChecker.isValid(TypedToken) expected " + assignmentChar + " but found " + token.string + "at position " + tokenCount);
                    else state = 2;
                    break;
                case 2:
                    if (token.type == TypedToken.IDENTIFIER) state = 3;
                    else if (token.type == TypedToken.NUMERIC) state = 3;
                    else if (token.type == TypedToken.OPENBRACE) braceCount = braceCount + 1;
                    else
                        throw new ExpressionException("ExpressionChecker.isValid(TypedToken) received unexpected token " + token.string + " at position " + tokenCount);
                    break;
                case 3:
                    if (token.type == TypedToken.MINUS || token.type == TypedToken.PLUS ||
                            token.type == TypedToken.MULTIPLY || token.type == TypedToken.DIVIDE ||
                            token.type == TypedToken.MOD || token.type == TypedToken.DIV ||
                            token.type == TypedToken.EXPONENT) state = 2;
                    else if (token.type == TypedToken.CLOSBRACE) {
                        braceCount = braceCount - 1;
                        if (braceCount < 0)
                            throw new ExpressionException("ExpressionChecker.isValid( TypedToken) received unexpected ) at position " + tokenCount);
                    } else
                        throw new ExpressionException("ExpressionChecker.isValid(TypedToken) received unexpected token " + token.string + " at position " + tokenCount);
                    break;
            }
        }


        public void isComplete()
                throws ExpressionException {
            if (state != 3 || braceCount != 0)
                throw new ExpressionException("ExpressionChecker.isValid(TypedToken) unexpected end of expression.");
        }
    }

    //--------------------------------------------------------------------------------------------------
    private class TokenTyper {
        public TypedToken type(String untypedToken)
                throws NumberFormatException {
            TypedToken typedToken = new TypedToken(untypedToken, TypedToken.UNKNOWN);

            typedToken = toOperator(untypedToken);
            if (typedToken.type != TypedToken.UNKNOWN) return typedToken;

            typedToken = toNumeric(untypedToken);
            if (typedToken.type != TypedToken.UNKNOWN) return typedToken;

            typedToken = new TypedToken(untypedToken, TypedToken.IDENTIFIER);
            return typedToken;
        }


        private TypedToken toOperator(String s) {
            TypedToken typedToken = new TypedToken(null, TypedToken.UNKNOWN);

            if (s.length() > 3) return typedToken;

            if (s.equalsIgnoreCase("("))
                typedToken = new TypedToken(s, TypedToken.OPENBRACE);
            if (s.equalsIgnoreCase(")"))
                typedToken = new TypedToken(s, TypedToken.CLOSBRACE);
            if (s.equalsIgnoreCase("^"))
                typedToken = new TypedToken(s, TypedToken.EXPONENT);
            else if (s.equalsIgnoreCase("div"))
                typedToken = new TypedToken(s, TypedToken.DIV);
            else if (s.equalsIgnoreCase("mod"))
                typedToken = new TypedToken(s, TypedToken.MOD);
            else if (s.equalsIgnoreCase("/"))
                typedToken = new TypedToken(s, TypedToken.DIVIDE);
            else if (s.equalsIgnoreCase("*"))
                typedToken = new TypedToken(s, TypedToken.MULTIPLY);
            else if (s.equalsIgnoreCase("-"))
                typedToken = new TypedToken(s, TypedToken.MINUS);
            else if (s.equalsIgnoreCase("+"))
                typedToken = new TypedToken(s, TypedToken.PLUS);

            return typedToken;
        }


        private TypedToken toNumeric(String s)
                throws NumberFormatException {
            boolean decimal = false;

            for (int i = 0; i < s.length(); i++) {
                if ((s.charAt(0) == '-' && s.length() > 1) ||
                        (s.charAt(i) == '.' || isNumeric(s.charAt(i))))
                    return new TypedToken(s, TypedToken.NUMERIC);
            }
            return new TypedToken(null, TypedToken.UNKNOWN);
        }


        private boolean isNumeric(char c) {
            if ((c >= '0') && (c <= '9')) return true;
            return false;
        }
    }

    //--------------------------------------------------------------------------------------------------
    private final class TypedToken {
        public final static int UNKNOWN = 0;
        public final static int NUMERIC = 1;
        public final static int IDENTIFIER = 2;
        public final static int OPENBRACE = 10;
        public final static int CLOSBRACE = 11;
        public final static int PLUS = 12;
        public final static int MINUS = 13;
        public final static int MULTIPLY = 14;
        public final static int DIVIDE = 15;
        public final static int MOD = 16;
        public final static int DIV = 17;
        public final static int EXPONENT = 18;

        public final String string;
        public final BigDecimal number;
        public final int type;


        public TypedToken(TypedToken typedToken)
                throws NumberFormatException {
            this(typedToken.string, typedToken.type);
        }


        public TypedToken(String tokenString, int tokenType)
                throws NumberFormatException {
            string = tokenString;
            type = tokenType;
            if (type == NUMERIC) number = new BigDecimal(tokenString);
            else number = null;
        }


        public String toString() {
            return " " + string + " ";
        }
    }

    //--------------------------------------------------------------------------------------------------
    private final class Tokenizer implements Iterator {
        public final char delimiter;
        private int index;
        private String string = null;


        public Tokenizer(String stringToTokenize) {
            string = new String(stringToTokenize);
            delimiter = ' ';
        }


        public Tokenizer(String stringToTokenize, char delimiter) {
            string = new String(stringToTokenize);
            this.delimiter = delimiter;
        }


        public Iterator iterator() {
            index = 0;
            Iterator i = this;
            return i;
        }


        public boolean hasNext() {
            if (index < string.length() - 1) return true;
            return false;
        }


        public Object next() {
            char currentChar, nextChar;
            String s = new String();
            int i;

            for (i = index; i < string.length() - 1; i++) {
                currentChar = string.charAt(i);
                nextChar = string.charAt(i + 1);

                if (currentChar == delimiter && nextChar != delimiter)
                    index = i + 1;
                else if (currentChar != delimiter && nextChar == delimiter) {
                    s = string.substring(index, i + 1);
                    index = i + 1;
                    return s;
                }
            }
            s = string.substring(index, i + 1);
            index = i + 1;
            return s;
        }


        public void remove()
                throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

}
//end of class Interpreter

