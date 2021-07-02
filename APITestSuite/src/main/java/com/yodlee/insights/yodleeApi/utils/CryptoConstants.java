/*
* Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of Yodlee, Inc.
* Use is subject to license terms.
*
*/
package com.yodlee.insights.yodleeApi.utils;

	public class CryptoConstants {
		
		private CryptoConstants() {
			
		}

	  /* programatically enforce keystore type rather than changing the default in java.security */
	  public static final String KEYSTORE_TYPE = "JCEKS";

	  /* used when requesting an instance of KeyGenerator */
	  public static final String AES_SECRET_KEY_ALGORITHM = "AES";
	  
	  public static final String RSA_SECRET_KEY_ALGORITHM = "RSA";

	  public static final String DESEDE_SECRET_KEY_ALGORITHM = "DESede";

	  public static final String DES_SECRET_KEY_ALGORITHM = "DES";

	  public static final String RC4_SECRET_KEY_ALGORITHM = "RC4";

	  /* if you change this, make sure to change the default key length below */
	  public static final String DEFAULT_SECRET_KEY_ALGORITHM = AES_SECRET_KEY_ALGORITHM;

	  //APP 1800 - Ingrian Migration 
	  //Increased the default length of AES key from 128 to 256 
	  public static final int AES_SECRET_KEY_SIZE = 256;

	  public static final int DESEDE_SECRET_KEY_SIZE = 168; /* in bits */

	  public static final int DES_SECRET_KEY_SIZE = 56;

	  public static final int RC4_SECRET_KEY_SIZE = 128;

	  public static final int DEFAULT_SECRET_KEY_SIZE = AES_SECRET_KEY_SIZE;

	  /* used when requesting an instance of Cipher */
	  public static final String DESEDE_CIPHER_TRANSFORMATION = "DESede/CBC/PKCS5Padding";

	  public static final String DES_ECB_CIPHER_TRANSFORMATION = "DES/ECB/PKCS5Padding";

	  public static final String OLD_DES_DO_NOT_USE_CIPHER_TRAN = "DES/ECB/NoPadding";

	  public static final String RC4_CIPHER_TRANSFORMATION = "RC4";

	  public static final String RSA_CIPHER_TRANSFORMATION = "RSA";

	  public static final String DESEDE_INGRIAN_CIPHER_TRANS = "IngrianDESede/CBC/NoPadding";

	  public static final String AES_CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";

	  public static final int CVV2_AES_KEY_SIZE = 256;

	  /* encryption handlers */

	  public static final int ENCRYPTION_HANDLER_UNKNOWN = -1;

	  public static final int ENCRYPTION_HANDLER_JCE_SYMMETRIC = 0;

	  public static final int ENCRYPTION_HANDLER_JCE_RSA = 1;

	  public static final int ENCRYPTION_HANDLER_OLD_EEE = 2;

	  public static final int ENCRYPTION_HANDLER_RSA_DESEDE = 3;

	  public static final int ENCRYPTION_HANDLER_JCE_INGRIAN = 4;
	  
	  public static final int ENCRYPTION_HANDLER_JCE_MASTER_INGRIAN = 5;

	  /* JCE providers */
	  public static final String INGRIAN_JCE_PROVIDER = "IngrianProvider";

	  /* to chose a random 8-byte IV for use with 3DES block cipher (CBC mode here)
	   * the author took the list of the published first 10,000 digits of 1/PI
	   * from the following URL: http://www.cecm.sfu.ca/projects/ISC/dataB/isc/C/1pi10000.txt
	   * He then extracted the 93rd line (each line has 100 digits) and performed
	   * a SHA-1 digest of it and then extracted the first eight bytes of the digest.
	   *
	   * The 93rd line was: 4059744939166598963865956671606918046454105225084228874168719169888676727677663550368232176342250255
	   * the SHA-1 of that (Hex-Encoded) was: 580f137d70a05d9a648ff80559aa30d42fbe55c2
	   * We take the first eight bytes of that to get our IV
	   */

	  public static final byte[] DESEDE_CBC_IV = { 88, 15, 19, 125, 112, -96, 93,
	    102 };

	  public static final String DEFAULT_CIPHER_TRANSFORMATION = AES_CIPHER_TRANSFORMATION;

	  public static final byte[] AES_CBC_IV = { 88, 15, 19, 125, 112, -96, 93, 102,
	    102, 93, -96, 112, 125, 19, 15, 88 };
	  
	  public static final byte[] AES_CBC_IV_GATH = { 0x43, 0x0A, (byte)0xAE, (byte)0x99, (byte)0x8A, 0x16, (byte)0xAB, 0x3A, 0x40, (byte)0xE6, 0x52, 0x0B, 0x09, (byte)0x80, 0x15, (byte)0xD6 };
	  
	    


	  /* used when requesting an instance of MessageDigest */
	  public static final String DEFAULT_MESSAGE_DIGEST = "SHA";

	  /* Mac handlers */

	  public static final int MAC_HANDLER_JCE = 0;

	  public static final int MAC_HANDLER_INGRIAN = 1;

	  /* used when requesting an instance of a MAC */
	  public static final String HMAC_SHA = "HmacSHA1";

	  public static final String HMAC_MD5 = "HmacMD5";

	  public static final String HMAC_SHA1_INGRIAN = "IngrianHmacSHA1";

	  public static final String DEFAULT_HMAC = HMAC_SHA;

	  /* used when requesting an instance of a SecureRandom */
	  public static final String DEFAULT_SECURE_RANDOM_SOURCE = "SHA1PRNG";

	  public static final String CURRENT_KEY_VERSION = "2.0";

	}


