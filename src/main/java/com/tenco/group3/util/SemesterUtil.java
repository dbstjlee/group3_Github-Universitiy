package com.tenco.group3.util;

import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.BreakApp;
import com.tenco.group3.model.Student;

public class SemesterUtil {

	// 현재 학기는 2024년 1학기 고정 --> 새학기 버튼 누르면 2024년 2학기 됨
	private static int currentYear;
	private static int currentSemester;
	private static int afterYear;
	private static int afterSemester;

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
	
	// 해당 학생의 학기 상승
	public static Student updateStudent(Student student){
		if (student.getSemester() == 2) {
			student.setGrade(student.getGrade() + 1);
			student.setSemester(1);
		} else {
			student.setSemester(2);
		}
		return student;
	}

	public static int getCurrentSemester() {
		return currentSemester;
	}

	public static int getCurrentYear() {
		return currentYear;
	}

	public static int getAfterYear() {
		if (afterYear == 0) {
			if (currentSemester == 2) {
				afterYear = currentYear + 1;
			} else {
				afterYear = currentYear;
			}
		}
		return afterYear;
	}

	public static int getAfterSemester() {
		if (afterSemester == 0) {
			if (currentSemester == 2) {
				afterSemester = currentSemester - 1;
			} else {
				afterSemester = currentSemester + 1;
			}
		}
		return afterSemester;
	}

	public static void setAfterYear(int afterYear) {
		SemesterUtil.afterYear = afterYear;
	}

	public static void setAfterSemester(int afterSemester) {
		SemesterUtil.afterSemester = afterSemester;
	}

	public static void setCurrentYear(int currentYear) {
		SemesterUtil.currentYear = currentYear;
	}

	public static void setCurrentSemester(int currentSemester) {
		SemesterUtil.currentSemester = currentSemester;
	}

}
