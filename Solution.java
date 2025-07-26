
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Solution {

    private static final String[] BUSINESS_LINE_ORDERED_BY_SORTING_PRIORIITY
            = {"electronics", "grocery", "pharmacy", "restaurant"};
    private static final String ALPHANUMERIC_OR_UNDERSCORE = "^[\\w]+$";

    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {
        Map<String, List<String>> businessLineToCodes = createBusinessLineToCodes(code, businessLine, isActive);
        return createSortedValidCoupons(businessLineToCodes);
    }

    private Map<String, List<String>> createBusinessLineToCodes(String[] code, String[] businessLine, boolean[] isActive) {
        Map<String, List<String>> businessLineToCodes = new HashMap<>();
        for (int i = 0; i < code.length; ++i) {
            if (isValidCode(code[i]) && isValidBusinessLine(businessLine[i]) && isActive[i]) {
                businessLineToCodes.putIfAbsent(businessLine[i], new ArrayList<>());
                businessLineToCodes.get(businessLine[i]).add(code[i]);
            }
        }
        return businessLineToCodes;
    }

    private boolean isValidCode(String code) {
        return Pattern.matches(ALPHANUMERIC_OR_UNDERSCORE, code);
    }

    private boolean isValidBusinessLine(String businessLine) {
        return businessLine.equals("electronics")
                || businessLine.equals("grocery")
                || businessLine.equals("pharmacy")
                || businessLine.equals("restaurant");
    }

    private List<String> createSortedValidCoupons(Map<String, List<String>> businessLineToCodes) {
        List<String> sortedValidCoupons = new ArrayList<>();
        for (String businessLine : BUSINESS_LINE_ORDERED_BY_SORTING_PRIORIITY) {
            if (!businessLineToCodes.containsKey(businessLine)) {
                continue;
            }
            Collections.sort(businessLineToCodes.get(businessLine));
            for (String code : businessLineToCodes.get(businessLine)) {
                sortedValidCoupons.add(code);
            }
        }
        return sortedValidCoupons;
    }
}
