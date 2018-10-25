package ca.pringle.library;


public final class Math {
    public static final double E = 2.71828182845904523536028747135266;
    public static final double PI = 3.1415926535897932384626433832795;


    /**
     * simple exponentiation.
     */
    public static double exp(double num, int exponent) {
        if (exponent < 0) {
            return 1 / exp(num, -exponent);
        } else if (exponent == 0) {
            return 1;
        } else if (exponent == 1) {
            return num;
        } else {
            if (ca.pringle.library.Math.even(exponent)) {
                double j = exp(num, exponent / 2);
                return j * j;
            } else {
                double j = exp(num, exponent - 1);
                return num * j;
            }
        }
    }


    /**
     * performs the natural logarithm.
     */
    public static double ln(double num) {
        return java.lang.Math.log(num);
    }


    /**
     * tests a number for eveness.
     */
    public static boolean even(long num) {
        return ((num % 2) == 0);
    }


    /**
     * returns the floor of a number.
     * For any real number x, the floor of x is the unique integer n2
     * such that n2 <= x < n2 + 1. floor(-2/3) = -1. floor(2/3) = 0.
     */
    public static int floor(double num) {
        if (num < 0) {
            return -ceiling(-num);
        } else return (int) num;
    }


    /**
     * returns the ceiling of a number.
     * For any real number x, the ceiling of x is the unique integer n2
     * such that n2 < x <= n2 + 1. ceiling(-2/3) = 0. ceiling(2/3) = 1.
     */
    public static int ceiling(double num) {
        if (num < 0) {
            return -floor(-num);
        } else {
            if (num - (long) num == 0) {
                return (int) num;
            } else return (int) num + 1;
        }
    }


    /**
     * returns the absolute value of a number.
     */
    public static double abs(double num) {
        if (num < 0) return -num;
        return num;
    }


    /**
     * returns the minimum of two numbers.
     */
    public static double min(double a, double b) {
        if (a < b) return a;
        return b;
    }


}