
using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

public class Solution
{
    private static readonly string[] BUSINESS_LINE_ORDERED_BY_SORTING_PRIORIITY
            = { "electronics", "grocery", "pharmacy", "restaurant" };
    private static Regex ALPHANUMERIC_OR_UNDERSCORE = new Regex("^[\\w]+$");

    public IList<string> ValidateCoupons(string[] code, string[] businessLine, bool[] isActive)
    {
        Dictionary<string, List<string>> businessLineToCodes = CreateBusinessLineToCodes(code, businessLine, isActive);
        return CreateSortedValidCoupons(businessLineToCodes);
    }

    private Dictionary<string, List<string>> CreateBusinessLineToCodes(string[] code, string[] businessLine, bool[] isActive)
    {
        Dictionary<string, List<string>> businessLineToCodes = [];
        for (int i = 0; i < code.Length; ++i)
        {
            if (IsValidCode(code[i]) && IsValidBusinessLine(businessLine[i]) && isActive[i])
            {
                businessLineToCodes.TryAdd(businessLine[i], new List<string>());
                businessLineToCodes[businessLine[i]].Add(code[i]);
            }
        }
        return businessLineToCodes;
    }

    private bool IsValidCode(string code)
    {
        return ALPHANUMERIC_OR_UNDERSCORE.IsMatch(code);
    }

    private bool IsValidBusinessLine(string businessLine)
    {
        return businessLine.Equals("electronics")
                || businessLine.Equals("grocery")
                || businessLine.Equals("pharmacy")
                || businessLine.Equals("restaurant");
    }

    private IList<string> CreateSortedValidCoupons(Dictionary<string, List<string>> businessLineToCodes)
    {
        IList<string> sortedValidCoupons = new List<string>();
        foreach (string businessLine in BUSINESS_LINE_ORDERED_BY_SORTING_PRIORIITY)
        {
            if (!businessLineToCodes.ContainsKey(businessLine))
            {
                continue;
            }

            businessLineToCodes[businessLine].Sort((x, y) => string.Compare(x, y, StringComparison.Ordinal));
            foreach (string code in businessLineToCodes[businessLine])
            {
                sortedValidCoupons.Add(code);
            }
        }
        return sortedValidCoupons;
    }
}
