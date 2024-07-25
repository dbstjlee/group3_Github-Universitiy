package com.tenco.group3.util;

import java.util.Random;

public class SampleDataGenerate {
	
	public static void main(String[] args) {
		// 1000줄 한번에 안되서 학생 id 바꿔가면서 만들기!
		Random random = new Random();
		int firstStudent = 2023000001;
		int lastStudent = 2023000200;
		String[] grades = {"'A+'","'A0'","'B+'","'B0'","'C+'","'C0'","'D+'","'F'"};
		String str = " INSERT INTO stu_sub_tb (student_id, subject_id, grade, complete_grade) VALUES (";
		StringBuffer sb = new StringBuffer();
		for (int i = firstStudent; i <= lastStudent; i++) {
			for (int j = 0; j < 5; j++) {
				sb.append(str);
				sb.append(String.valueOf(i) + ", ");
				int sub = random.nextInt(80) + 10000;
				sb.append(String.valueOf(sub) + ", ");
				int index = random.nextInt(8);
				sb.append(grades[index] + ", ");
				int cg = random.nextInt(3) + 1;
				sb.append(String.valueOf(cg) + ");\n");
				System.out.println(sb);
			}
		}
	}
}
