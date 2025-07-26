
import kotlin.text.Regex

class Solution {

    private companion object {
        val BUSINESS_LINE_ORDERED_BY_SORTING_PRIORIITY = arrayOf("electronics", "grocery", "pharmacy", "restaurant")
        val ALPHANUMERIC_OR_UNDERSCORE = Regex("^[\\w]+$")
    }

    fun validateCoupons(code: Array<String>, businessLine: Array<String>, isActive: BooleanArray): List<String> {
        val businessLineToCodes: MutableMap<String, MutableList<String>> =
            createBusinessLineToCodes(code, businessLine, isActive)
        return createSortedValidCoupons(businessLineToCodes)
    }

    private fun createBusinessLineToCodes(code: Array<String>, businessLine: Array<String>, isActive: BooleanArray)
            : MutableMap<String, MutableList<String>> {
        val businessLineToCodes = mutableMapOf<String, MutableList<String>>()
        for (i in code.indices) {
            if (isValidCode(code[i]) && isValidBusinessLine(businessLine[i]) && isActive[i]) {
                businessLineToCodes.putIfAbsent(businessLine[i], mutableListOf())
                businessLineToCodes[businessLine[i]]?.add(code[i])
            }
        }
        return businessLineToCodes
    }

    private fun isValidCode(code: String): Boolean {
        return ALPHANUMERIC_OR_UNDERSCORE.matches(code)
    }

    private fun isValidBusinessLine(businessLine: String): Boolean {
        return businessLine == "electronics"
                || businessLine == "grocery"
                || businessLine == "pharmacy"
                || businessLine == "restaurant"
    }

    private fun createSortedValidCoupons(businessLineToCodes: MutableMap<String, MutableList<String>>): MutableList<String> {
        val sortedValidCoupons = mutableListOf<String>()

        for (businessLine in BUSINESS_LINE_ORDERED_BY_SORTING_PRIORIITY) {
            if (!businessLineToCodes.containsKey(businessLine)) {
                continue
            }
            businessLineToCodes[businessLine]!!.sort()
            for (code in businessLineToCodes[businessLine]!!) {
                sortedValidCoupons.add(code)
            }
        }
        return sortedValidCoupons
    }
}
