package cn.edu.neu.random;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class KeyGenerator {
	public static String genrate() {
		try {
			return encoderPwdByMd5(encoderPwdByMd5(new Date().toString()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "error key";
	}

	private static String encoderPwdByMd5(String seedStr)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		return getHexValueString(md5.digest(seedStr.getBytes("utf-8")));
	}

	private static String getHexValueString(byte[] md5Bytes) {
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static void main(String args[]) {
		System.err.println(new Date().toString());
		System.err.println(KeyGenerator.genrate());
	}
}
