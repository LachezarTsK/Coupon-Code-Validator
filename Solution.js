
/**
 * @param {string[]} code
 * @param {string[]} businessLine
 * @param {boolean[]} isActive
 * @return {string[]}
 */
var validateCoupons = function (code, businessLine, isActive) {
    const businessLineToCodes = createBusinessLineToCodes(code, businessLine, isActive);
    return createSortedValidCoupons(businessLineToCodes);
};

/**
 * @param {string[]} code
 * @param {string[]} businessLine
 * @param {boolean[]} isActive
 * @return {Map<string, Array<string>>}
 */
function createBusinessLineToCodes(code, businessLine, isActive) {
    const businessLineToCodes = new Map();
    for (let i = 0; i < code.length; ++i) {
        if (isValidCode(code[i]) && isValidBusinessLine(businessLine[i]) && isActive[i]) {
            if (!businessLineToCodes.has(businessLine[i])) {
                businessLineToCodes.set(businessLine[i], new Array());
            }
            businessLineToCodes.get(businessLine[i]).push(code[i]);
        }
    }
    return businessLineToCodes;
}

/**
 * @param {string} code
 * @return {boolean}
 */
function isValidCode(code) {
    return Util.ALPHANUMERIC_OR_UNDERSCORE.test(code);
}

/**
 * @param {string} businessLine
 * @return {boolean}
 */
function isValidBusinessLine(businessLine) {
    return businessLine === "electronics"
            || businessLine === "grocery"
            || businessLine === "pharmacy"
            || businessLine === "restaurant";
}

/**
 * @param {Map<string, Array<string>>} businessLineToCodes
 * @return {string[]}
 */
function createSortedValidCoupons(businessLineToCodes) {
    const sortedValidCoupons = new Array();
    for (let businessLine of Util.BUSINESS_LINE_ORDERED_BY_SORTING_PRIORIITY) {
        if (!businessLineToCodes.has(businessLine)) {
            continue;
        }
        businessLineToCodes.get(businessLine).sort();
        for (let code of businessLineToCodes.get(businessLine)) {
            sortedValidCoupons.push(code);
        }
    }
    return sortedValidCoupons;
}

class Util {
    static BUSINESS_LINE_ORDERED_BY_SORTING_PRIORIITY = ["electronics", "grocery", "pharmacy", "restaurant"];
    static ALPHANUMERIC_OR_UNDERSCORE = /^[\w]+$/;
}
