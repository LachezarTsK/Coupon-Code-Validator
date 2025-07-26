
package main

import (
    // outcomment for regex solution
    // "regexp"
    "slices"
    "unicode"
)

// outcomment for regex solution
// var ALPHANUMERIC_OR_UNDERSCORE = regexp.MustCompile(`^[\w]+$`)

var BUSINESS_LINE_ORDERED_BY_SORTING_PRIORIITY = []string{"electronics", "grocery", "pharmacy", "restaurant"}

func validateCoupons(code []string, businessLine []string, isActive []bool) []string {
    var businessLineToCodes map[string][]string = createBusinessLineToCodes(code, businessLine, isActive)
    return createSortedValidCoupons(businessLineToCodes)
}

func createBusinessLineToCodes(code []string, businessLine []string, isActive []bool) map[string][]string {
    businessLineToCodes := map[string][]string{}
    for i := range code {
        if isValidCode(code[i]) && isValidBusinessLine(businessLine[i]) && isActive[i] {
            if _, has := businessLineToCodes[businessLine[i]]; !has {
                businessLineToCodes[businessLine[i]] = []string{}
            }
            businessLineToCodes[businessLine[i]] = append(businessLineToCodes[businessLine[i]], code[i])
        }
    }
    return businessLineToCodes
}

func isValidCode(code string) bool {
    for _, current := range code {
        if !unicode.IsLetter(current) && !unicode.IsDigit(current) && current != '_' {
            return false
        }
    }
    return len(code) > 0
}

/*
Alternatively, a solution with regex.
The code runs much faster when 'isValidCode' does not apply regex.
To run the solution with regex, outcomment this method
and the lines indicated to be outcommented for regex.

func isValidCode(code string) bool {
    return ALPHANUMERIC_OR_UNDERSCORE.MatchString(code)
}
*/

func isValidBusinessLine(businessLine string) bool {
    return businessLine == "electronics" ||
            businessLine == "grocery" ||
            businessLine == "pharmacy" ||
            businessLine == "restaurant"
}

func createSortedValidCoupons(businessLineToCodes map[string][]string) []string {
    sortedValidCoupons := []string{}

    for _, businessLine := range BUSINESS_LINE_ORDERED_BY_SORTING_PRIORIITY {
        if _, has := businessLineToCodes[businessLine]; !has {
            continue
        }
        slices.Sort(businessLineToCodes[businessLine])
        for _, code := range businessLineToCodes[businessLine] {
            sortedValidCoupons = append(sortedValidCoupons, code)
        }
    }
    return sortedValidCoupons
}
