package com.tenco.group3.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.StringTokenizer;

public class PasswordUtil {

	private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
	
	// 비밀번호 암호화 메서드
	public static String hashPassword(String password, String salt) {
		try {
			// 1. SHA256 알고리즘 객체 생성
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			// 2. 비밀번호와 salt 합친 문자열에 SHA 256 적용
			md.update((password + salt).getBytes());
			byte[] pwdsalt = md.digest();

			// 3. byte To String (16진수의 문자열로 변경)
			StringBuffer sb = new StringBuffer();
			for (byte b : pwdsalt) {
				sb.append(String.format("%02x", b));
			}
			// 4. 비밀 번호 뒤에 솔트를 붙여서 반환
			sb.append(":" + salt);
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 비밀번호 확인 메서드
	public static boolean checkPassword(String dbPwSalt, String inputPw) {
		StringTokenizer token = new StringTokenizer(dbPwSalt, ":");
		token.nextToken();
		String salt = token.nextToken();
		if(dbPwSalt.equals(hashPassword(inputPw, salt))) {
			return true;
		}
		return false;
	}
	
	// 비밀번호 생성 메서드 (길이 6 ~ 20)
	public static String generatePassword() {
		SecureRandom random = new SecureRandom();
        int length = random.nextInt(15) + 6; // 길이 6~20
        StringBuffer sb = new StringBuffer(length);
        
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHAR_SET.length());
            sb.append(CHAR_SET.charAt(index));
        }
        return sb.toString();
	}
	
	// salt 생성 메서드
	public static String getSalt() {

		// 1. Random, byte 객체 생성
		SecureRandom r = new SecureRandom();
		byte[] salt = new byte[20];

		// 2. 난수 생성
		r.nextBytes(salt);

		// 3. byte To String (16진수의 문자열로 변경)
		StringBuffer sb = new StringBuffer();
		for (byte b : salt) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}
	public static void main(String[] args) {
		String salt = PasswordUtil.getSalt();
		String dbPwSalt = PasswordUtil.hashPassword("123123", salt);
		System.out.println("salt : " + salt);
		System.out.println("dbPwSalt : " + dbPwSalt);
		boolean check = PasswordUtil.checkPassword(dbPwSalt, "123123");
		System.out.println("check : " + check);
	}
}
