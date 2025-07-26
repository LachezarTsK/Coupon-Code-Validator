
function validateCoupons(code: string[], businessLine: string[], isActive: boolean[]): string[] {
    const businessLineToCodes = createBusinessLineToCodes(code, businessLine, isActive);
    return createSortedValidCoupons(businessLineToCodes);
};

function createBusinessLineToCodes(code: string[], businessLine: string[], isActive: boolean[]): Map<string, Array<string>> {
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

function isValidCode(code: string): boolean {
    return Util.ALPHANUMERIC_OR_UNDERSCORE.test(code);
}

function isValidBusinessLine(businessLine: string): boolean {
    return businessLine === "electronics"
        || businessLine === "grocery"
        || businessLine === "pharmacy"
        || businessLine === "restaurant";
}

function createSortedValidCoupons(businessLineToCodes: Map<string, Array<string>>): string[] {
    const sortedValidCoupons: string[] = new Array();
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
