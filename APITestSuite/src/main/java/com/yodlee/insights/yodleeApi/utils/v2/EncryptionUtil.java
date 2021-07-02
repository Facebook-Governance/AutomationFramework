/*
* Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of Yodlee, Inc.
* Use is subject to license terms.
*
*/
package com.yodlee.insights.yodleeApi.utils.v2;

/**
 * @author Manjunath Sampath
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.X509Certificate;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.util.text.AES256TextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.yodlee.insights.yodleeApi.utils.PropertiesEnum;
import com.yodlee.insights.yodleeApi.utils.ApplicationConstants;

@Component
public class EncryptionUtil {

	private static Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);

	private static final String JCEKS = "JCEKS";

	private static final Long MAX_TOKEN_EXPIRY_TIME = 600000L;

	private static String ALIAS = "master_key";

	private static final byte[] ENC_KEY = { 55, 60, 65, 55, 53, 54, 66, 65, 14, -1, 0, 1 };
	// private static final byte[] IV = { 88, 15, 19, 125, 112, -96, 93, 102, 102,
	// 93, -96, 112, 125, 19, 15, 88 };
	private static Key privateKey;
	private static PublicKey publicKey;
	private boolean isDevProfile = false;
	private  final String DEFAULT_ENCODING = "UTF-8";
	private  final String HMAC_SHA512 = "HmacSHA512";
	@Autowired
	private Environment environment;

	/**
	 * Initiates Encryptor Service
	 */
	@PostConstruct
	public final void initService() {
		String keyStoreFilename = "C:\\opt\\ctier\\security\\yodleeKeystore";
		String keyStorePassword="yodlee123";

		//setIsDevProfile();
		//String keyStoreFilename = environment.getProperty(PropertiesEnum.YODLEE_KEYSTORE_FILE_NAME.getKey());
		//String keyStorePassword = getKeyStorePassword();

		Security.addProvider(new BouncyCastleProvider());
		KeyStore keyStore = null;
		try {
			if (keyStoreFilename == null) {
				if (keyStoreFilename == null || keyStoreFilename.length() == 0) {
					logger.error(
							"ERROR: Please provide key store location full path in properties file! Example /opt/ctier/yodleeKeystore");
					System.exit(1);
				}
			}
			if (keyStoreFilename != null && keyStoreFilename.length() > 0) {
				File file = new File(keyStoreFilename);
				keyStore = KeyStore.getInstance(JCEKS);
				if (file.exists()) {
					FileInputStream input = new FileInputStream(file);
					if (keyStore != null && input != null && keyStorePassword != null) {
						keyStore.load(input, keyStorePassword.toCharArray());
						input.close();
						privateKey = keyStore.getKey(ALIAS, keyStorePassword.toCharArray());

						X509Certificate cert = null;
						cert = (X509Certificate) keyStore.getCertificate(ALIAS);
						publicKey = cert.getPublicKey();
					}

				} else {
					logger.error(" No Such file exists: {}", keyStoreFilename);
				}
			}
		} catch (KeyStoreException keyStoreException) {
			logger.error("ERROR: ", keyStoreException);
		} catch (Exception exception) {
			logger.error("ERROR: ", exception);
		}
	}

	/**
	 * Decrypts the value based on Master key
	 * 
	 * @param cypher
	 * @return
	 */
	public final String decrypt(String cypherInput) {
		String cypher = extractCipher(cypherInput);
		logger.info("Resultant cipher: {}", cypher + " :");
		logger.info("value of isDevProfile is {}", isDevProfile);
		if (isDevProfile && cypher.equals(cypherInput)) {
			logger.info("INFO: Decryption Request was made for DEV profile. cypherInput:{}", cypherInput);
			logger.error("INFO: Decryption Request was made for DEV profile. cypherInput:{}", cypherInput);
			return cypherInput;
		}

		String decryptedString = null;
		try {

			logger.info("Before Cipher.getInstance");
			Cipher cipher = Cipher.getInstance(ApplicationConstants.RSA_ECB_PKCS5);
			cipher.init(Cipher.DECRYPT_MODE,
					transformKey(privateKey, CryptoConstants.RSA_CIPHER_TRANSFORMATION, new BouncyCastleProvider()));
			logger.info("After Cipher  init");
			decryptedString = new String(cipher.doFinal(Base64.decodeBase64(cypher.getBytes())));

		} catch (InvalidKeyException invalidKeyException) {
			logger.error("ERROR: ", invalidKeyException);
		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			logger.error("ERROR: ", noSuchAlgorithmException);
		} catch (NoSuchPaddingException noSuchPaddingException) {
			logger.error("ERROR: ", noSuchPaddingException);
		} catch (IllegalBlockSizeException illegalBlockSizeException) {
			logger.error("ERROR: ", illegalBlockSizeException);
		} catch (BadPaddingException badPaddingException) {
			logger.error("ERROR: ", badPaddingException);
		} catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
			logger.error("ERROR: ", invalidAlgorithmParameterException);
		} catch (Exception excaption) {
			logger.error("ERROR: ", excaption);
		}
		return decryptedString;
	}

	/**
	 * Encrypts the value mased on master key
	 * 
	 * @param plainText
	 * @return
	 */
	public final String encrypt(String plainText) {
		if (StringUtil.isNullOrEmpty(plainText)) {
			return null;
		}
		String encryptedString = null;
		try {
			Cipher cipher = Cipher.getInstance(ApplicationConstants.RSA_ECB_PKCS5);

			cipher.init(Cipher.ENCRYPT_MODE,
					transformKey(publicKey, CryptoConstants.RSA_CIPHER_TRANSFORMATION, new BouncyCastleProvider()));
			encryptedString = new String(Base64.encodeBase64(cipher.doFinal(plainText.getBytes())));
		} catch (InvalidKeyException invalidKeyException) {
			logger.error("ERROR: " + invalidKeyException);
		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			logger.error("ERROR: " + noSuchAlgorithmException);
		} catch (NoSuchPaddingException noSuchPaddingException) {
			logger.error("ERROR: " + noSuchPaddingException);
		} catch (IllegalBlockSizeException illegalBlockSizeException) {
			logger.error("ERROR: " + illegalBlockSizeException);
		} catch (BadPaddingException badPaddingException) {
			logger.error("ERROR: " + badPaddingException);
		} catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
			logger.error("ERROR: " + invalidAlgorithmParameterException);
		} catch (Exception exception) {
			logger.error("ERROR: " + exception);
		}
		return encloseCipherWithENCString(encryptedString);
	}

	public final String encryptHeaderForInternalAuth(final Long id) {
		return encryptHeaderForInternalAuth(id, 600000L);
	}

	public final String encryptHeaderForInternalAuth(final Long id, Long time) {
		if (id == null) {
			logger.error("ERROR: Null id provided");
			return null;
		}
		String encryptedString = null;
		try {
			Cipher cipher = Cipher.getInstance(ApplicationConstants.RSA_ECB_PKCS5);

			cipher.init(Cipher.ENCRYPT_MODE,
					transformKey(publicKey, CryptoConstants.RSA_CIPHER_TRANSFORMATION, new BouncyCastleProvider()));

			String plainTextHeader = new StringBuilder().append(id.toString()).append("#")
					.append((System.currentTimeMillis() + time)).toString();

			encryptedString = new String(Base64.encodeBase64(cipher.doFinal(plainTextHeader.getBytes())));
		} catch (InvalidKeyException invalidKeyException) {
			logger.error("ERROR: {}", invalidKeyException);
		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			logger.error("ERROR: {}", noSuchAlgorithmException);
		} catch (NoSuchPaddingException noSuchPaddingException) {
			logger.error("ERROR: {}", noSuchPaddingException);
		} catch (IllegalBlockSizeException illegalBlockSizeException) {
			logger.error("ERROR: {}", illegalBlockSizeException);
		} catch (BadPaddingException badPaddingException) {
			logger.error("ERROR: {}", badPaddingException);
		} catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
			logger.error("ERROR: {}", invalidAlgorithmParameterException);
		} catch (Exception exception) {
			logger.error("ERROR: {}", exception);
		}
		return encryptedString;
	}

	/**
	 * Transforms the key
	 * 
	 * @param key
	 * @param algorithm
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	private Key transformKey(Key key, String algorithm, Provider provider) throws Exception {
		Key ret = null;
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm, provider);
		ret = keyFactory.translateKey(key);
		return ret;
	}

	/**
	 * If Cipher text String is enclosed within-> ENC(cipher_text) or enc(), then
	 * this method will Strip off leading and training ENC() or enc() , and return
	 * just the cipher_text
	 * 
	 * @param cipherText
	 * @return
	 */
	private static String extractCipher(String cipherText) {
		if (StringUtil.isNullOrEmpty(cipherText)) {
			throw new IllegalArgumentException("Null or Empty cipherText is invalid");
		}
		if (cipherText.startsWith("ENC(") || cipherText.startsWith("enc(")) {
			cipherText = cipherText.substring(4);
			if (cipherText.endsWith(")")) {
				cipherText = cipherText.substring(0, cipherText.length() - 1);
			}
		}
		return cipherText;
	}

	/**
	 * If Cipher text String is enclosed within-> ENC(cipher_text) or enc(), then
	 * this method will Strip off leading and training ENC() or enc() , and return
	 * just the cipher_text
	 * 
	 * @param cipherText
	 * @return
	 */
	private static String encloseCipherWithENCString(String cipherText) {
		if (cipherText != null) {
			cipherText = "ENC(" + cipherText + ")";
		}
		return cipherText;
	}

	/**
	 * Util func converts String password into byte[] array and adds a padding of
	 * -50 points in each array index
	 * 
	 * @param text
	 * @return
	 */
	public static byte[] encodePasswordToBytes(String text) {
		byte[] bytes = text.getBytes();
		int idx = 0;
		for (byte b1 : bytes) {
			bytes[idx++] = (byte) (b1 - 50);
		}
		return bytes;
	}

	/**
	 * Util func converts byte[] array encoded password into Plaintext String
	 * password and removes the padding of -50 points in each array index
	 * 
	 * @param text
	 * @return
	 */
	public static String decodeBytesToPassword(byte[] bytes) {
		int idx = 0;
		for (byte b1 : bytes) {
			bytes[idx] = (byte) (b1 + 50);
			idx++;
		}
		return new String(bytes);
	}

	/*public String getEncryptedValue(String memIdCobrandId) {
		EncryptionUtil util = new EncryptionUtil();
		util.initService();
		String encryptedUserName = null;
		String encryptedPassword = null;
		encryptedUserName = util.encrypt(memIdCobrandId);
		logger.error("Encrypted UserName: " + encryptedUserName);
		return encryptedUserName;
	}*/
	
	public String getEncryptedValue(String memIdCobrandId) {
		String securityKey = "yodlee123";
		String messageToEncrypt = memIdCobrandId;
		String generatedToken = getMessageDigest(messageToEncrypt, securityKey);
		logger.error("generatedToken: " + generatedToken);
		return generatedToken;
	}
	
	public String getMessageDigest(String message, String key) {
		String rslt = null;
		try {
			Mac mac = getMac(key);
			byte[] encrData = mac.doFinal(message.getBytes(DEFAULT_ENCODING));
			rslt = String.valueOf(Hex.encodeHex(encrData));
		} catch (Exception e) {
			logger.error("Exception while calculating digest", e);
		}
		return rslt;
	}
	private  Mac getMac(String key) throws CloneNotSupportedException, UnsupportedEncodingException, InvalidKeyException{
		Mac mac = null ;
		SecretKeySpec keySpec = new SecretKeySpec( key.getBytes(DEFAULT_ENCODING), HMAC_SHA512);
		mac = org.apache.commons.codec.digest.HmacUtils.getHmacSha512(key.getBytes());
		mac.init(keySpec);
		return mac ;
	}
	public static void main(String[] args) throws Exception {

		EncryptionUtil util = new EncryptionUtil();
		util.initService();
		String encryptedUserName = null;
		String encryptedPassword = null;
		encryptedUserName = util.encrypt("10153400,10000004");
		encryptedPassword = util.encrypt("pal_pt34m");
		logger.error("Encrypted UserName: " + encryptedUserName);
		logger.error("Encrypted Password: " + encryptedPassword);

		String decryptedUserName = null;
		String decryptedPassword = null;
		decryptedUserName = util.decrypt(
				"G8I/egvrmEbDtMD/EjgvDEFJJaKwTMzjyMUHigPVzjlaLEEdTNFGoOMO1odaqXrjx5QmTauZ+hBs2AkEUpA6iL8FoiJqOJyBTrQp2K2HRkSA471cbjeBeEusXHcdt7TjQ8H1ZBM5lZ7ebrhOVeK2cIr/z+Hk5pJhUaxofDVJumU=");
		decryptedPassword = util.decrypt(
				"Q+BBeOoQ7tdXirrxT8TZihQAxZtvTaiYBz6xhMYQaxl2IFJ7oeN9v3QVx46qhAxKy/o29kszsF6+I7npcbjAWZN1g0BXdaxJrarphMMNY0oZjwvvDxOFU02drAKZUZOhgPQR9qBUHViAiDo+lVF9f86mRsUWVND66HXeH6FYmiI=");
		logger.error("Decrypted UserName: " + decryptedUserName);
		logger.error("Decrypted Password: " + decryptedPassword);

		AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
		textEncryptor.setPassword("insights@123");
		logger.error(textEncryptor.encrypt("yodlee123"));
		logger.error(textEncryptor.decrypt("sECHuiJ10Uxbd0ZFABO+57S6FawMoDQn/cNhXHZhKqfYsKnZ+tzlO3WGDDRsOKjX"));
	}

	/**
	 * Returns the Keystore password from application.properties file. If (Spring
	 * profile==dev + Password is Plaintext), then returns plaintext password Else
	 * Returns Decrypted Password
	 * 
	 * @return
	 */
	private String getKeyStorePassword() {
		String keyStorePasswordEnc = environment.getProperty(PropertiesEnum.YODLEE_KEYSTORE_PASSWORD.getKey());
		String keyStorePassword = extractCipher(keyStorePasswordEnc);
		if (isDevProfile && keyStorePassword.equals(keyStorePasswordEnc)) {
			logger.debug("INFO: keyStorePassword Decryption Request was made for DEV profile. keyStorePassword:{}",
					keyStorePassword);
			return keyStorePassword;
		}
		String encryptionKey = decodeBytesToPassword(ENC_KEY);

		AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
		textEncryptor.setPassword(encryptionKey);
		keyStorePassword = textEncryptor.decrypt(keyStorePassword);
		return keyStorePassword;
	}

	/**
	 * Method returns if SpringBoot runtime environment was started with following
	 * JVM argument-> -Dspring.profiles.active=dev
	 * 
	 * @return
	 */
	private void setIsDevProfile() {
		String[] profileNames = environment.getActiveProfiles();
		for (String profileName : profileNames) {
			if (ApplicationConstants.DEV_PROFILE_NAME.equalsIgnoreCase(profileName)) {
				isDevProfile = true;
				break;
			}
		}
	}

	
}