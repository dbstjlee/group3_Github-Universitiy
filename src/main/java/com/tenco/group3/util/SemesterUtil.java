package com.tenco.group3.util;

import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.BreakApp;

public class SemesterUtil {

	// 현재 학기는 2024년 1학기 고정 --> 새학기 버튼 누르면 2024년 2학기 됨
	private static int currentYear;
	private static int currentSemester;
	private static int beforeYear;
	private static int beforeSemester;

	// 2024년 2학기 이전으로 설정된 학기라면 true 반환
	public static boolean isBeforeSemester(int year, int semester) {
		if (year < currentYear) {
			return true;
		} else if (year == currentYear) {
			if (semester < currentSemester) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// BreakAppList를 받아서 휴학기간이 끝난 학생id 리스트 반환
	public static List<Integer> breakDone(List<BreakApp> breakAppList) {
		List<Integer> studentList = new ArrayList<>();
		for (BreakApp breakApp : breakAppList) {
			int year = breakApp.getToYear();
			int semester = breakApp.getToSemester();
			if (isBeforeSemester(year, semester)) {
				studentList.add(breakApp.getStudentId());
			}
		}
		return studentList;
	}

	public static int getCurrentSemester() {
		return currentSemester;
	}

	public static int getCurrentYear() {
		return currentYear;
	}

	public static int getBeforeYear() {
		if (beforeYear == 0) {
			if (currentSemester == 2) {
				beforeYear = currentYear;
			} else {
				beforeYear = currentYear - 1;
			}
		}
		return beforeYear;
	}

	public static int getBeforeSemester() {
		if (beforeSemester == 0) {
			if (currentSemester == 2) {
				beforeSemester = currentSemester - 1;
			} else {
				beforeSemester = currentSemester + 1;
			}
		}
		return beforeSemester;
	}

	public static void setBeforeYear(int beforeYear) {
		SemesterUtil.beforeYear = beforeYear;
	}

	public static void setBeforeSemester(int beforeSemester) {
		SemesterUtil.beforeSemester = beforeSemester;
	}

	public static void setCurrentYear(int currentYear) {
		SemesterUtil.currentYear = currentYear;
	}

	public static void setCurrentSemester(int currentSemester) {
		SemesterUtil.currentSemester = currentSemester;
	}

}
