/*
* Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of Yodlee, Inc.
* Use is subject to license terms.
*
*/
package com.yodlee.insights.yodleeApi.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.yodlee.insights.yodleeApi.utils.ExceptionFactory;

public abstract class StringUtil {
	private static final Logger LOG = LoggerFactory.getLogger(StringUtil.class);
	
	private static Pattern amountPattern = Pattern.compile("\\d+(\\.\\d{1,2})?");
	private static Pattern numberPattern = Pattern.compile("\\d+");

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isLong(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String convertUpperCaseUnderscoredToCamelCase(String s) {
		StringBuilder b = new StringBuilder();
		for (String segment : s.split("_")) {
			if (segment.length() > 0) {
				b.append(Character.toUpperCase(segment.charAt(0)));
			}
			if (segment.length() > 1) {
				b.append(segment.substring(1, segment.length()).toLowerCase());
			}
		}
		return b.toString();
	}

	public static String convertFirstLetterToUpperRestLowerCase(String s) {
		StringBuilder b = new StringBuilder();
		if (!StringUtil.isNullOrEmpty(s)) {
			if (s.length() > 0) {
				b.append(Character.toUpperCase(s.charAt(0)));
			}
			if (s.length() > 1) {
				b.append(s.substring(1, s.length()).toLowerCase());
			}
		}
		return b.toString();
	}

	public static String convertCamelCaseToUpperCaseUnderscored(String s) {
		StringBuilder b = new StringBuilder();
		boolean isPreviousUpper = false;
		for (int i = 0, n = s.length(); i < n; i++) {
			char ch = s.charAt(i);
			if (i > 0 && Character.isUpperCase(ch)) {
				if (!isPreviousUpper || i < n - 1 && !Character.isUpperCase(s.charAt(i + 1))) {
					b.append("_").append(ch);
				}
				else {
					b.append(ch);
				}
			} else {
				b.append(Character.toUpperCase(ch));
			}
			isPreviousUpper = Character.isUpperCase(ch);
		}
		return b.toString();
	}

	public static String[] splitCamelCase(String s) {
		List<String> list = new ArrayList<String>();
		boolean isPreviousUpper = false;
		StringBuilder b = new StringBuilder();
		for (int i = 0, n = s.length(); i < n; i++) {
			char ch = s.charAt(i);
			if (i > 0 && Character.isUpperCase(ch)) {
				if (!isPreviousUpper || i < n - 1 && !Character.isUpperCase(s.charAt(i + 1))) {
					if (b.length() != 0) {
						list.add(b.toString());
					}
					b = new StringBuilder();
				}
			}
			isPreviousUpper = Character.isUpperCase(ch);
			b.append(ch);
		}
		if (b.length() != 0) {
			list.add(b.toString());
		}
		return list.toArray(new String[list.size()]);
	}

	public static <T> String toStringInner(T[] array) {
		String s = Arrays.toString(array);
		return s.substring(1, s.length() - 1);
	}

	public static <T> String toStringInner(List<T> list) {
		String s = Arrays.toString(list.toArray());
		return s.substring(1, s.length() - 1);
	}

	// For legacy
	public static String toStringInner(long[] array) {
		String s = Arrays.toString(array);
		return s.substring(1, s.length() - 1);
	}

	// For legacy
	public static String toStringInner(int[] array) {
		String s = Arrays.toString(array);
		return s.substring(1, s.length() - 1);
	}

	// sjr Remove all spaces, tabs and returns except single spaces between
	// non-delimiters
	public static String removeExtraSpaces(String s, String delimiters) {
		boolean isPreviousDelimiter = false;
		StringBuilder b = new StringBuilder();
		for (int i = 0, n = s.length(); i < n; i++) {
			char c = s.charAt(i);
			if (c == '\t' || c == '\n' || c == '\r') {
				c = ' ';
			}
			if (c == ' ') {
				if (!isPreviousDelimiter) {
					isPreviousDelimiter = true;
					if (i < n - 1 && !isDelimiter(s.charAt(i + 1), delimiters)) {
						b.append(c);
					}
				}
				continue;
			}
			isPreviousDelimiter = isDelimiter(c, delimiters);
			b.append(c);
		}
		return b.toString();
	}

	private static boolean isDelimiter(char c, String delimiters) {
		return delimiters.contains(Character.toString(c));
	}

	public static boolean isEqual(String s1, String s2) {
		return isNullOrEmpty(s1) ? StringUtil.isNullOrEmpty(s2) : s1.equals(s2);
	}

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	public static boolean isEmpty(String s) {
		return s!= null && s.trim().isEmpty();
	}

	public static boolean isValidAmount(String amount) {
		if (isNullOrEmpty(amount)) {
			return false;
		}
		return amountPattern.matcher(amount).matches();
	}

	public static boolean isNumber(String number) {
		if (isNullOrEmpty(number)) {
			return false;
		}
		return numberPattern.matcher(number).matches();
	}

	public static String removeFirstStringSenquence(String origString, String replacedString) {
		if (!isNullOrEmpty(origString) && !isNullOrEmpty(replacedString)) {
			origString = origString.substring(origString.indexOf(replacedString));
			origString = origString.replace(replacedString, "");
		}
		return origString;
	}

	public static String[] splitCSVExt(String s) {
		String regex = "(?<!\\\\)" + Pattern.quote(","); // Pattern to not use "," as a delimiter if it is part of the
															// string
		List<String> split = new ArrayList<String>();
		while (true) {
			String[] subSplit = s.startsWith("\"") ? s.split("([\t,])(?=(?:[^\"]|\"[^\"]*\")*$)", 2)
					: s.split(regex, 2);
			subSplit[0] = subSplit[0].contains("\\,") ? subSplit[0].replace("\\,", ",") // Remove escape char from the
																						// string value
					: subSplit[0];
			split.add(subSplit[0]);
			if (subSplit.length == 1) {
				break;
			}
			s = subSplit[1];
		}
		return split.toArray(new String[split.size()]);
	}

	public static List<Long> getListFromCommaSeparatedValues(String values) {
		if (StringUtil.isNullOrEmpty(values)) {
			return null;
		}
		List<Long> result = new ArrayList<Long>();
		if (!StringUtil.isNullOrEmpty(values)) {
			String[] valueArray = values.split(",");
			for (String value : valueArray) {
				result.add(Long.parseLong(value));
			}
		}
		return result;
	}

	public static String getTerm(String s) {
		return s != null && Pattern.compile(
				"TIME_DUR::P(([0-9]+W)|(([0-9]+Y)?([0-9]+M)?([0-9]+D)?(T[([0-9]+H)?([0-9]+M)?([0-9]+S)?]{1,10})?))")
				.matcher(s).matches() ? s.replaceFirst("TIME_DUR::", "") : null;
	}

	public static int getIndexOfThirdQuote(String s, int iStart) {
		return s.indexOf("'", s.indexOf("'", s.indexOf("'", iStart + 1) + 1) + 1);
	}

	public static int getIndexOfSecondBlank(String s, int iStart) {
		return s.indexOf(" ", s.indexOf(" ", iStart + 1) + 1);
	}

	public static int getIndexOfLastSecondBlank(String s, int iEnd) {
		return s.lastIndexOf(" ", s.lastIndexOf(" ", iEnd - 1) - 1);
	}

	public static boolean contains(String s, String token, String delimiter) {
		return s.equals(token) || s.startsWith(token + delimiter) || s.endsWith(delimiter + token)
				|| s.contains(delimiter + s + delimiter);
	}

	/**
	 * Check if the given array contains the given value (with case-insensitive
	 * comparison).
	 *
	 * @param array The array
	 * @param value The value to search
	 * @return true if the array contains the value
	 */
	public static boolean containsIgnoreCase(String[] array, String value) {
		for (String str : array) {
			if (value == null && str == null) {
				return true;
			}
			if (value != null && value.equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Join an array of strings with the given separator.
	 * <p>
	 * Note: This might be replaced by utility method from commons-lang or guava
	 * someday if one of those libraries is added as dependency.
	 * </p>
	 *
	 * @param array     The array of strings
	 * @param separator The separator
	 * @return the resulting string
	 */
	public static String join(String[] array, String separator) {
		int len = array.length;
		if (len == 0) {
			return "";
		}

		StringBuilder out = new StringBuilder();
		out.append(array[0]);
		for (int i = 1; i < len; i++) {
			out.append(separator).append(array[i]);
		}
		return out.toString();
	}
	
	/**
	 * Method takes a String of Comma-separated numbers, and returns an Array of Long numbers
	 * @param attr
	 * @return
	 */
	public static Long[] splitStringToListOfLong(String attr) {
		Long[] responseList = null;
		if (!StringUtil.isNullOrEmpty(attr)) {
			List<String> attrList = Arrays.asList(attr.split(","));
			List<Long> longList = attrList
							.stream()
							.map(Long::valueOf)
							.collect(Collectors.toList());
			responseList = longList.toArray(new Long[longList.size()]);
		}
		return responseList;
	}
	
	/**
	 * Converts String to Integer. Returns NULL if String is NULL.
	 * @param val
	 * @return
	 */
	public static Integer stringToInteger(String val){
		Integer result = null;
		if(!StringUtil.isNullOrEmpty(val)) {
			try {
				result = Integer.valueOf(val);
			} catch (NumberFormatException e) {
				LOG.error("NumberFormatException for value: {}", val);
				throw e;
			}
		}
		return result;
	}
	
	public static Integer stringToInteger(String val, String errorCode, String errorMessage){
		Integer result = null;
		if(!StringUtil.isNullOrEmpty(val)) {
			try {
				result = Integer.valueOf(val);
			} catch (NumberFormatException e) {
				//ExceptionFactory.createAndThrowException(errorCode, new Object[] {errorMessage});
			}
		}
		return result;
	}
	
	/**
	 * Method takes 2 list and returns duplicates among those 2 lists
	 * @param list
	 * @param array
	 * @return
	 */
	public static <T> List<T> fetchCommonElements(List<T> listA, List<T> listB){
		if(CollectionUtils.isEmpty(listA) || CollectionUtils.isEmpty(listB)) {
			return new ArrayList<>();
		}
		
		Set<T> set = new HashSet<>(listA); 
		return listB
				.stream()
				.filter(set::contains)
				.collect(Collectors.toList());
	}
}
