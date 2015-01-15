package assign3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BackgroundEvaluate {

    @SuppressWarnings({"unchecked", "varargs"})
    public Map<String, String> evaluate(String SSN, String lastName, String firstName,
            Function<String, String[]>... checkers) {

        Map<String, String> evaluateResult = new HashMap<>();

        evaluateResult.put("SSN", SSN);
        evaluateResult.put("LN", lastName);
        evaluateResult.put("FN", firstName);

        if (checkers.length == 0) {
            evaluateResult.put("Result", "Please select criterias for evaluation.");
            return evaluateResult;
        }

        List<String> rejectedReasons = Stream.of(checkers)
                .map(checker -> {
                    String[] status = checker.apply(SSN);
                    return status; })
                .filter(result -> result[0].equalsIgnoreCase("Rejected") == true)
                .map(result -> {return result[1];})
                .collect(Collectors.toList());
				
        if (rejectedReasons.isEmpty()) {
            evaluateResult.put("Result", "Approved");
        } else {
            evaluateResult.put("Result", "Rejected");
            evaluateResult.put("Reason", rejectedReasons.toString());
        }

        return evaluateResult;
    }
}