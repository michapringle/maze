package ca.pringle.library;


/**
 * Debugging
 */


public final class Debugger {
    public static final boolean ON = true;
    public static final boolean OFF = false;
    private static final boolean MASTER_SWITCH = ON;
    public final boolean isOn;


    public Debugger(boolean onOrOff) {
        isOn = onOrOff;
    }


    public void print(char c) {
        if (MASTER_SWITCH == ON)
            if (isOn)
                System.out.print(c);
    }


    public void println(char c) {
        if (MASTER_SWITCH == ON)
            if (isOn)
                System.out.println(c);
    }


    public void print(Object o) {
        if (MASTER_SWITCH == ON)
            if (isOn)
                System.out.print(o);
    }


    public void println(Object o) {
        if (MASTER_SWITCH == ON)
            if (isOn)
                System.out.println(o);
    }


}    //End of class Debugger