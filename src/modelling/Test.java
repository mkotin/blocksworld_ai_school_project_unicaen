package modelling;

import modellingtests.VariableTests;
import modellingtests.BooleanVariableTests;

public class Test {
    public static void main(String[] args) {
        // Runnin tests
        boolean ok = true;
        ok = ok && VariableTests.testGetName();
        ok = ok && VariableTests.testGetDomain();
        ok = ok && VariableTests.testEquals();
        ok = ok && VariableTests.testHashCode();
        ok = ok && BooleanVariableTests.testConstructor();
        ok = ok && BooleanVariableTests.testEquals();
        ok = ok && BooleanVariableTests.testHashCode();
        System.out.println(ok ? "All tests OK" : "At least one test KO");
    }
}
