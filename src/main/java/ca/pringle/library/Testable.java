package ca.pringle.library;

import java.util.HashMap;


public interface Testable {
    public static final String TEST_FAILED = "f";
    public static final String TEST_SUCCEEDED = "s";


    public HashMap test() throws Exception;
}