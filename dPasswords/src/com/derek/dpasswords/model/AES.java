package com.derek.dpasswords.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

public class AES {
	private final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";

	private final int HASH_ITERATIONS = 10000;
	private final int KEY_LENGTH = 256;

	private byte[] salt = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF };

	private PBEKeySpec myKeyspec;
	private final String CIPHERMODEPADDING = "AES/CBC/PKCS7Padding";

	private SecretKeyFactory keyfactory = null;
	private SecretKey sk = null;
	private SecretKeySpec skforAES = null;
	private byte[] iv = { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC, 0xD, 91 };

	private IvParameterSpec IV;

	public AES(String key) {
		myKeyspec = new PBEKeySpec(key.toCharArray(), salt, HASH_ITERATIONS, KEY_LENGTH);
		try {
			keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
			sk = keyfactory.generateSecret(myKeyspec);

		} catch (NoSuchAlgorithmException nsae) {
			Log.e("AESdemo", "no key factory support for PBEWITHSHAANDTWOFISH-CBC");
		} catch (InvalidKeySpecException ikse) {
			Log.e("AESdemo", "invalid key spec for PBEWITHSHAANDTWOFISH-CBC");
		}

		byte[] skAsByteArray = sk.getEncoded();
		skforAES = new SecretKeySpec(skAsByteArray, "AES");
		IV = new IvParameterSpec(iv);
	}

	public String encrypt(String plaintext) {
		byte[] plaintextBytes;
		try {
			plaintextBytes = plaintext.getBytes("UTF8");
			byte[] ciphertext = encrypt(CIPHERMODEPADDING, skforAES, IV, plaintextBytes);
			String base64_ciphertext = Base64Encoder.encode(ciphertext);
			return base64_ciphertext;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decrypt(String ciphertext_base64) {
		byte[] s = Base64Decoder.decodeToBytes(ciphertext_base64);
		String decrypted = new String(decrypt(CIPHERMODEPADDING, skforAES, IV, s));
		return decrypted;
	}

	private byte[] addPadding(byte[] plain) {
		byte plainpad[] = null;
		int shortage = 16 - (plain.length % 16);
		// if already an exact multiple of 16, need to add another block of 16
		// bytes
		if (shortage == 0)
			shortage = 16;

		// reallocate array bigger to be exact multiple, adding shortage bits.
		plainpad = new byte[plain.length + shortage];
		for (int i = 0; i < plain.length; i++) {
			plainpad[i] = plain[i];
		}
		for (int i = plain.length; i < plain.length + shortage; i++) {
			plainpad[i] = (byte) shortage;
		}
		return plainpad;
	}

	private byte[] dropPadding(byte[] plainpad) {
		byte plain[] = null;
		int drop = plainpad[plainpad.length - 1]; // last byte gives number of
													// bytes to drop

		// reallocate array smaller, dropping the pad bytes.
		plain = new byte[plainpad.length - drop];
		for (int i = 0; i < plain.length; i++) {
			plain[i] = plainpad[i];
			plainpad[i] = 0; // don't keep a copy of the decrypt
		}
		return plain;
	}

	private byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] msg) {
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.ENCRYPT_MODE, sk, IV);
			return c.doFinal(msg);
		} catch (NoSuchAlgorithmException nsae) {
			Log.e("AESdemo", "no cipher getinstance support for " + cmp);
		} catch (NoSuchPaddingException nspe) {
			Log.e("AESdemo", "no cipher getinstance support for padding " + cmp);
		} catch (InvalidKeyException e) {
			Log.e("AESdemo", "invalid key exception");
		} catch (InvalidAlgorithmParameterException e) {
			Log.e("AESdemo", "invalid algorithm parameter exception");
		} catch (IllegalBlockSizeException e) {
			Log.e("AESdemo", "illegal block size exception");
		} catch (BadPaddingException e) {
			Log.e("AESdemo", "bad padding exception");
		}
		return null;
	}

	private byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] ciphertext) {
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.DECRYPT_MODE, sk, IV);
			return c.doFinal(ciphertext);
		} catch (NoSuchAlgorithmException nsae) {
			Log.e("AESdemo", "no cipher getinstance support for " + cmp);
		} catch (NoSuchPaddingException nspe) {
			Log.e("AESdemo", "no cipher getinstance support for padding " + cmp);
		} catch (InvalidKeyException e) {
			Log.e("AESdemo", "invalid key exception");
		} catch (InvalidAlgorithmParameterException e) {
			Log.e("AESdemo", "invalid algorithm parameter exception");
		} catch (IllegalBlockSizeException e) {
			Log.e("AESdemo", "illegal block size exception");
		} catch (BadPaddingException e) {
			Log.e("AESdemo", "bad padding exception");
			e.printStackTrace();
		}
		return null;
	}
}
