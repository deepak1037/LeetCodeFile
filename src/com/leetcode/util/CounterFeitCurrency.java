package com.leetcode.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CounterFeitCurrency {

	public static void main(String[] args) {
		String[] serialNumber1 = { "QDB2012R20B", "RED190250E", "RFV201111T", "TYU20121000E", "AAA198710B",
				"AbC200010E" };
		
		String[] serialNumber2 = { "AVG190420T", "RTF20001000Z", "QWER201850G", "AFA199620E", "ERT1947200T",
		"DRV1984500Y","ETB2010400G" };
		
		String[] serialNumber = { "A201550B", "ABB19991000Z", "XYZ200019Z", "ERF200220", "SCD203010T"};
		
		System.out.println(countCounterfeit(Arrays.asList(serialNumber)));

	}

	public static int countCounterfeit(List<String> serialNumber) {
		int sum = 0;
		for (String sNo : serialNumber) {
			sum += getCurrencyValue(sNo);
		}
		return sum;
	}

	private static int getCurrencyValue(String sNo) {

		int billValue = 0;

		if (sNo.length() >= 10 && sNo.length() <= 12) {
			if (firstThreeDistinctUppercase(sNo) && validYear(sNo) && validTermination(sNo)) {
				billValue = getBillValue(sNo);
			}

		}

		return billValue;
	}

	private static int getBillValue(String sNo) {

		int billValue = 0;
		String curValue = sNo.substring(7, sNo.length() - 1);

		try {
			billValue = Integer.parseInt(curValue);
		} catch (NumberFormatException ex) {
			return 0;
		}

		if (!validCurrency(billValue)) {
			return 0;
		}
		return billValue;
	}

	private static boolean validCurrency(int value) {

		switch (value) {
		case 10:
		case 20:
		case 50:
		case 100:
		case 200:
		case 500:
		case 1000:
			return true;
		default:
			return false;
		}

	}

	private static boolean validTermination(String sNo) {
		char end = sNo.charAt(sNo.length() - 1);
		return Character.isUpperCase(end);
	}

	private static boolean validYear(String sNo) {

		String year = sNo.substring(3, 7);
		int value = 0;
		try {
			value = Integer.parseInt(year);
		} catch (NumberFormatException ex) {
			return false;
		}

		if (value < 1900 || value > 2019) {
			return false;
		}
		return true;
	}

	private static boolean firstThreeDistinctUppercase(String sNo) {
		Set<Character> chars = new HashSet<>();
		for (int i = 0; i < 3; i++) {
			char ch = sNo.charAt(i);
			if (Character.isUpperCase(ch) && Character.isLetter(ch)) {
				chars.add(ch);
			}

		}
		return chars.size() == 3;
	}

}
