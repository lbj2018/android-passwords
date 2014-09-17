package com.derek.dpasswords.model;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	private static int BLOCKS = 128;

	public static byte[] encrypt(final String key, final String origin) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(getRawKey(key.getBytes("UTF8")), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(origin.getBytes());
		return encrypted;
	}

	public static byte[] decrypt(final String key, final byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(getRawKey(key.getBytes("UTF8")), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(BLOCKS, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}
}
