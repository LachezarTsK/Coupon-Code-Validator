
// outcomment for regex solution
// #include <regex>
#include <vector>
#include <ranges>
#include <string_view>
#include <unordered_map>
using namespace std;

class Solution {

    inline static const vector<string> BUSINESS_LINE_ORDERED_BY_SORTING_PRIORIITY =
    { "electronics", "grocery", "pharmacy", "restaurant" };

    // outcomment for regex solution
    // inline static const regex ALPHANUMERIC_OR_UNDERSCORE = regex("^[\\w]+$");

public:
    vector<string> validateCoupons(vector<string>& code, vector<string>& businessLine, vector<bool>& isActive) const {
        unordered_map<string, vector<string>> businessLineToCodes = createBusinessLineToCodes(code, businessLine, isActive);
        return createSortedValidCoupons(businessLineToCodes);
    }

private:
    unordered_map<string, vector<string>> createBusinessLineToCodes(const vector<string>& code, const vector<string>& businessLine, const vector<bool>& isActive) const {
        unordered_map<string, vector<string>> businessLineToCodes;
        for (int i = 0; i < code.size(); ++i) {
            if (isValidCode(code[i]) && isValidBusinessLine(businessLine[i]) && isActive[i]) {
                businessLineToCodes[businessLine[i]].push_back(code[i]);
            }
        }
        return businessLineToCodes;
    }

    bool isValidCode(string_view code) const {
        for (const auto& current : code) {
            if (!isalnum(current) && current != '_') {
                return false;
            }
        }
        return !code.empty();
    }

    /*
    Alternatively, a solution with regex.
    The code runs much faster when 'isValidCode' does not apply regex.
    To run the solution with regex, outcomment this method
    and the lines indicated to be outcommented for regex.

    bool isValidCode(const string& code) const {
        return regex_match(code, ALPHANUMERIC_OR_UNDERSCORE);
    }
    */

    bool isValidBusinessLine(string_view businessLine) const {
        return businessLine == "electronics"
                || businessLine == "grocery"
                || businessLine == "pharmacy"
                || businessLine == "restaurant";
    }

    vector<string> createSortedValidCoupons(unordered_map<string, vector<string>>& businessLineToCodes) const {
        vector<string> sortedValidCoupons;
        for (const auto& businessLine : BUSINESS_LINE_ORDERED_BY_SORTING_PRIORIITY) {
            if (!businessLineToCodes.contains(businessLine)) {
                continue;
            }
            ranges::sort(businessLineToCodes[businessLine]);
            for (const auto& code : businessLineToCodes[businessLine]) {
                sortedValidCoupons.push_back(code);
            }
        }
        return sortedValidCoupons;
    }
};
