package com.tenco.group3.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class ValidationUtil {

	// 유효한 이름인지 확인
	public static boolean isValidateName(String str) {
		return isNotOnlyWhitespace(str) &&//
				isOnlyKorean(str) &&//
				isLessThanOrEqual(str, 10) &&//
				isNotContainsWhitespace(str);
	}

	// 유효한 아이디인지 확인
	public static boolean isValidateId(String str) {
		return isOnlyNumber(str) &&//
				isNotOnlyWhitespace(str) &&//
				isNotContainsWhitespace(str);
	}

	// 유효한 비밀번호인지 확인
	public static boolean isValidatePwd(String str) {
		return isNotOnlyWhitespace(str) &&//
				isNotContainsWhitespace(str) &&//
				isLessThanOrEqual(str, 20) &&//
				isMoreThanOrEqual(str, 6);
	}

	// 입력된 날짜가 오늘 이전인지 확인
	public static boolean isDateBeforeToday(String dateStr) {
		try {
			LocalDate date = LocalDate.parse(dateStr);
			LocalDate today = LocalDate.now();
			return date.isBefore(today);
		} catch (DateTimeParseException e) {
			// 날짜 형식이 잘못된 경우 false 반환
			return false;
		}
	}
	

	// 주소가 정규표현식을 만족하는지 확인
	public static boolean isValidateAddress(String address) {
		return Pattern.matches(KOREAN_ADDRESS_PATTERN, address);
	}

	// 문자열이 공백으로만 이루어져 있지 않은지 검사
	public static boolean isNotOnlyWhitespace(String str) {
		return str != null && !str.trim().isEmpty();
	}

	// 문자열이 한글로만 이루어져 있는지 검사
	public static boolean isOnlyKorean(String str) {
		return str != null && Pattern.matches("^[가-힣]+$", str);
	}

	// 숫자로만 이루어져 있는지 검사
	public static boolean isOnlyNumber(String str) {
		return str != null && Pattern.matches("^[0-9]+$", str);
	}

	// 문자열이 이메일 형식으로 이루어져 있는지 검사
	public static boolean isEmail(String str) {
		return str != null && Pattern.matches("^[a-zA-Z0-9_+&*-]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,10}$", str);
	}

	// 문자열 길이가 주어진 정수 이하 인지 검사
	public static boolean isLessThanOrEqual(String str, int length) {
		return str != null && str.length() <= length;
	}

	// 문자열 길이가 주어진 정수 이상 인지 검사
	public static boolean isMoreThanOrEqual(String str, int length) {
		return str != null && str.length() >= length;
	}

	// 문자열에 공백이 포함되어 있지 않은지 검사
	public static boolean isNotContainsWhitespace(String str) {
		return str != null && !str.contains(" ");
	}

	// 대한민국 주소 형식 정규 표현식
	private static final String KOREAN_ADDRESS_PATTERN = "^[가-힣\\s\\d\\-]+"// 한글,공백,숫자,하이픈 문자가 1회 이상 반복됨
			+ "(시|도)\\s"// 시,도를 반드시 포함하고 다음은 공백이 있어야함
			+ "([가-힣\\s\\d\\-]+(구|군)\\s)?"//
			+ "[가-힣\\s\\d\\-]+(길|로|번길)\\s"//
			+ "\\d+(-\\d+)?"; // ()? ()안의 내용이 있어도되고 없어도 되고 예시
																														 // : 123 // 456-7

	// 전화번호가 숫자 10~11자 인지 확인
	public static boolean isValidateTel(String tel) {
		// \\D : 숫자가 아닌 문자 -> ""
		// 즉, 숫자만 추출
		String digits = tel.replaceAll("\\D", "");

		return digits.length() == 10 || digits.length() == 11;
	}

	// 전화번호 가공 메서드
	public static String formatTel(String tel) {
		StringBuffer sb = new StringBuffer();
		String digits = tel.replaceAll("\\D", "");
		if (digits.length() == 10) {
			sb.append(digits.substring(0, 3));
			sb.append("-");
			sb.append(digits.substring(3, 6));
			sb.append("-");
			sb.append(digits.substring(6, 10));
		} else if (digits.length() == 11) {
			sb.append(digits.substring(0, 3));
			sb.append("-");
			sb.append(digits.substring(3, 7));
			sb.append("-");
			sb.append(digits.substring(7, 11));
		} else {
			sb.append(false);
		}
		return sb.toString();
	}

}
