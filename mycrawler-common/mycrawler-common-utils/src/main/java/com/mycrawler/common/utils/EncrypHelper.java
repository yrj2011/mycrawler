package com.mycrawler.common.utils;

import java.security.SecureRandom;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;
public class EncrypHelper {
	// KeyGenerator 提供对称密钥生成器的功能，支持各种算法
	private KeyGenerator keygen;
	// SecretKey 负责保存对称密钥
	private SecretKey deskey;
	// Cipher负责完成加密或解密工作
	private Cipher cipher;
	// 该字节数组负责保存加密的结果
	private byte[] cipherByte;

	private static EncrypHelper instance;

	private EncrypHelper() {
	}

	private void init() throws Exception {
		keygen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
		secureRandom.setSeed(SecureRandom.class.getName().getBytes());
		keygen.init(128,secureRandom);
		deskey = keygen.generateKey();
		cipher = Cipher.getInstance("AES");
	}

	public static synchronized EncrypHelper getInstance() throws Exception {
		if (instance == null) {
			instance = new EncrypHelper();
			instance.init();
		}
		return instance;
	}

	public String encryt(String plaintext) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] src = plaintext.getBytes();
		cipherByte = cipher.doFinal(src);
		return new Base64().encodeToString(cipherByte);
	}

	public String decrypt(String encryptedText) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		byte[] buff = new Base64().decode(encryptedText);
		cipherByte = cipher.doFinal(buff);
		return new String(cipherByte);
	}

	public void tryDecryptDBConnPwd(Properties properties) {
		if (properties == null) {
			return;
		}
		final String KEY_PWD = "DB_CONN_PWD";
		if (properties.containsKey(KEY_PWD)) {
			String encryptedPwd = properties.getProperty(KEY_PWD);
			String plainPwd = null;
			try {
				plainPwd = EncrypHelper.getInstance().decrypt(encryptedPwd);
			} catch (Exception e) {
				plainPwd = "<INVALID_ENCRYPTED_DB_PWD>";
			}
			properties.put(KEY_PWD, plainPwd);
		}
	}

	public static void main(String[] args) throws Exception {
		String plaintext = args.length == 0 ? "" : args[0];
		String encryptedText = EncrypHelper.getInstance().encryt(plaintext);
		System.out.println(String.format("����:%s", plaintext));
		System.out.println(String.format("����:%s", encryptedText));
		// System.out.println("����:" + EncrypHelper.getInstance().decrypt(encryptedText));
	}
}
