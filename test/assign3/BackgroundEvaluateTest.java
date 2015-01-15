package assign3;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

public class BackgroundEvaluateTest extends TestCase {

    private BackgroundEvaluate evaluator;

    @Override
    protected void setUp() {

        evaluator = new BackgroundEvaluate();
    }

    public void testCanary() {
        assertTrue(true);
    }

    private String[] creditApproved(String ssn) {
        String[] result = new String[2];
        result[0] = "Approved";
        result[1] = "";

        return result;
    }

    private String[] creditRejected(String ssn) {
        String[] result = new String[2];
        result[0] = "Rejected";
        result[1] = "Reason code [3521], credit score is low. ";

        return result;
    }

    private String[] crimeApproved(String ssn) {
        String[] result = new String[2];
        result[0] = "Approved";
        result[1] = "";

        return result;
    }

    private String[] crimeRejected(String ssn) {
        String[] result = new String[2];
        result[0] = "Rejected";
        result[1] = "Reason code [1000], bad crime records. ";

        return result;
    }

    @SuppressWarnings({"unchecked", "varargs"})
    public void testEvaluateCreditApproved() {

        Map<String, String> result = evaluator.evaluate("987654321", "Carey", "Scoab", this::creditApproved);

        Map<String, String> expectedresult = createMap("987654321", "Carey", "Scoab", "Approved");

        assertEquals(expectedresult, result);
    }

    @SuppressWarnings({"unchecked", "varargs"})
    public void testEvaluateCreditRejected() {

        Map<String, String> result = evaluator.evaluate("987654321", "Carey", "Scoab", this::creditRejected);

        Map<String, String> expectedresult = createMap("987654321", "Carey", "Scoab", "Rejected");
        expectedresult.put("Reason", "[Reason code [3521], credit score is low. ]");

        assertEquals(expectedresult, result);
    }

    @SuppressWarnings({"unchecked", "varargs"})
    public void testEvaluateCreditAndCrimeBothApproved() {

        Map<String, String> result = evaluator.evaluate("987654321", "Carey", "Scoab", this::creditApproved, this::crimeApproved);

        Map<String, String> expectedresult = createMap("987654321", "Carey", "Scoab", "Approved");

        assertEquals(expectedresult, result);
    }

    @SuppressWarnings({"unchecked", "varargs"})
    public void testEvaluateCreditRejectedAndCrimeApproved() {

        Map<String, String> result = evaluator.evaluate("987654321", "Carey", "Scoab", this::creditRejected, this::crimeApproved);

        Map<String, String> expectedresult = createMap("987654321", "Carey", "Scoab", "Rejected");
        expectedresult.put("Reason", "[Reason code [3521], credit score is low. ]");

        assertEquals(expectedresult, result);
    }

    @SuppressWarnings({"unchecked", "varargs"})
    public void testEvaluateCreditApprovedAndCrimeRejected() {

        Map<String, String> result = evaluator.evaluate("987654321", "Carey", "Scoab", this::creditApproved, this::crimeRejected);

        Map<String, String> expectedresult = createMap("987654321", "Carey", "Scoab", "Rejected");
        expectedresult.put("Reason", "[Reason code [1000], bad crime records. ]");

        assertEquals(expectedresult, result);
    }

    @SuppressWarnings({"unchecked", "varargs"})
    public void testBothEvaluateCreditdAndCrimeRejected() {

        Map<String, String> result = evaluator.evaluate("987654321",
                "Carey", "Scoab", this::creditRejected, this::crimeRejected);

        Map<String, String> expectedresult = createMap("987654321", "Carey", "Scoab", "Rejected");
        String[] reason = new String[2];
        reason[0] = "[Reason code [3521], credit score is low. ,";
        reason[1] = " Reason code [1000], bad crime records. ]";

        expectedresult.put("Reason", reason[0] + reason[1]);

        assertEquals(expectedresult, result);

    }

    @SuppressWarnings({"unchecked", "varargs"})
    public void testNoCriteriaSelected() {

        Map<String, String> result = evaluator.evaluate("987654321",
                "Carey", "Scoab");

        Map<String, String> expectedresult = createMap("987654321", "Carey", "Scoab", "");
        expectedresult.put("Result", "Please select criterias for evaluation.");

        assertEquals(expectedresult, result);
    }

    private Map<String, String> createMap(String SSN, String fname, String lname, String approvestatus) {
        Map<String, String> expectedresult = new HashMap<>();
        expectedresult.put("SSN", SSN);
        expectedresult.put("LN", fname);
        expectedresult.put("FN", lname);
        expectedresult.put("Result", approvestatus);
        return expectedresult;
    }

}