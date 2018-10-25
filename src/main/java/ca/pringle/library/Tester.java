package ca.pringle.library;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public final class Tester {
    private static HashMap testables = new HashMap();


    public final static void register(String className, Testable classInstance) {
        testables.put(className, classInstance);
    }


    public final static void runTests() {
        Set keys = testables.keySet();
        Iterator i = keys.iterator();
        Testable testClass = null;
        String className = null;
        String classInstance = null;


        while (i.hasNext()) {
            className = (String) i.next();
            testClass = (Testable) testables.get(className);


            System.out.println("");
            System.out.println("Testing class " + className);

            try {
                HashMap results = testClass.test();

                System.out.println("Test results for class " + className);
                displayResults(results);
            } catch (Exception e) {
                System.out.println("Good work! You found an uncaught exception in your code with a diligent test.");
                e.printStackTrace();
            }
        }
    }


    private final static void displayResults(HashMap results) {
        int failed = 0;
        int succeeded = 0;
        int total = 0;
        Set keys = results.keySet();
        Iterator i = keys.iterator();
        String testName = null;
        String testResult = null;


        while (i.hasNext()) {
            total = total + 1;
            testName = (String) i.next();
            testResult = (String) results.get(testName);

            if (testResult.equals(Testable.TEST_FAILED)) {
                System.out.println("Test number " + total + ", '" + testName + "' failed.");
                failed = failed + 1;
            } else
                succeeded = succeeded + 1;
        }

        double successRate = (double) (100 * succeeded) / (double) total;
        double failureRate = (double) (100 * failed) / (double) total;

        System.out.println("Test successes: " + successRate + "%, " + succeeded + " of " + total);

        if (successRate != 100)
            System.out.println("Test failures: " + failureRate + "%, " + failed + " of " + total);
    }

}