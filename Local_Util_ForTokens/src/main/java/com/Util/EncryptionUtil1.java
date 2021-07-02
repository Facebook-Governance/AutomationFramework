package com.Util;


/**
* @author Manjunath Sampath
*
*/
import java.io.File;
import java.io.FileInputStream;
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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.util.text.AES256TextEncryptor;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;

//import com.yodlee.insights.common.config.PropertiesEnum;

public class EncryptionUtil1 {


    private static final String JCEKS = "JCEKS";

    private static String ALIAS = "master_key";

    private static final byte[] ENC_KEY = {55, 60, 65, 55, 53, 54, 66, 65, 14, -1, 0, 1};
    //private static final byte[] IV = { 88, 15, 19, 125, 112, -96, 93, 102, 102, 93, -96, 112, 125, 19, 15, 88 };
    private static Key privateKey;
    private static PublicKey publicKey;

    private static String RSA_ECB_PKCS5 = "RSA/ECB/PKCS1Padding";

    private static String devProfileName = "dev";
    private boolean isDevProfile=false;


    /**
     * Initiates Encryptor Service
     */
    EncryptionUtil1() {
   String keyStoreFilename = "C:\\Users\\rshrivastav\\opt\\ctier\\security\\yodleeKeystore";
   String keyStorePassword="yodlee123";

        Security.addProvider(new BouncyCastleProvider());
        KeyStore keyStore = null;
        try {
            if (keyStoreFilename == null) {
                if (keyStoreFilename == null || keyStoreFilename.length() == 0) {

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
                }
            }
        } catch (KeyStoreException keyStoreException) {
           // System.out.print("ERROR: ", keyStoreException);
        } catch (Exception exception) {
           // System.out.print("ERROR: ", exception);
        }
    }



    /**
     * Encrypts the value mased on master key
     *
     * @param plainText
     * @return
     */
    public final String encrypt(String plainText) {

        String encryptedString = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS5);

            cipher.init(Cipher.ENCRYPT_MODE,
                    transformKey(publicKey, "RSA", new BouncyCastleProvider()));
            encryptedString = new String(Base64.encodeBase64(cipher.doFinal(plainText.getBytes())));
        } catch (InvalidKeyException invalidKeyException) {
            //System.out.print("ERROR: " + invalidKeyException);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
           // System.out.print("ERROR: " + noSuchAlgorithmException);
        } catch (NoSuchPaddingException noSuchPaddingException) {
            //System.out.print("ERROR: " + noSuchPaddingException);
        } catch (IllegalBlockSizeException illegalBlockSizeException) {
            //System.out.print("ERROR: " + illegalBlockSizeException);
        } catch (BadPaddingException badPaddingException) {
            //System.out.print("ERROR: " + badPaddingException);
        } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            //System.out.print("ERROR: " + invalidAlgorithmParameterException);
        } catch (Exception exception) {
            //System.out.print("ERROR: " + exception);
        }
        return encloseCipherWithENCString(encryptedString);
    }

    public final String encryptHeaderForInternalAuth(final Long memId) {
        return encryptHeaderForInternalAuth(memId, 600000L);
    }

    public final String encryptHeaderForInternalAuth(final Long memId, Long time) {
        if(memId==null) {
            System.out.print("ERROR: Null memId provided");
            return null;
        }
        String encryptedString = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS5);

            cipher.init(Cipher.ENCRYPT_MODE,
                    transformKey(publicKey, "RSA", new BouncyCastleProvider()));

            String plainTextHeader = new StringBuilder().append(memId.toString()).append("#")
                    .append((System.currentTimeMillis() + time)).toString();

            encryptedString = new String(Base64.encodeBase64(cipher.doFinal(plainTextHeader.getBytes())));
        } catch (InvalidKeyException invalidKeyException) {
           // System.out.println("ERROR: {}", invalidKeyException);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            //System.out.print("ERROR: {}", noSuchAlgorithmException);
        } catch (NoSuchPaddingException noSuchPaddingException) {
            //System.out.print("ERROR: {}", noSuchPaddingException);
        } catch (IllegalBlockSizeException illegalBlockSizeException) {
           // System.out.print("ERROR: {}", illegalBlockSizeException);
        } catch (BadPaddingException badPaddingException) {
           // System.out.print("ERROR: {}", badPaddingException);
        } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
           // System.out.print("ERROR: {}", invalidAlgorithmParameterException);
        } catch (Exception exception) {
           // System.out.print("ERROR: {}", exception);
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
     * If Cipher text String is enclosed within-> ENC(cipher_text) or enc(), then this method will Strip off leading and training ENC() or enc()
     * , and return just the cipher_text
     * @param cipherText
     * @return
     */
    private static String extractCipher(String cipherText) {

        if(cipherText.startsWith("ENC(") || cipherText.startsWith("enc(")){
            cipherText=cipherText.substring(4);
            if(cipherText.endsWith(")")){
                cipherText=cipherText.substring(0, cipherText.length()-1);
            }
        }
        return cipherText;
    }

    /**
     * If Cipher text String is enclosed within-> ENC(cipher_text) or enc(), then this method will Strip off leading and training ENC() or enc()
     * , and return just the cipher_text
     * @param cipherText
     * @return
     */
   private static String encloseCipherWithENCString(String cipherText) {
        if(cipherText!=null) {
            cipherText= "ENC(" + cipherText + ")";
        }
        return cipherText;
    }

    /**
     * Util func converts String password into byte[] array and adds a padding of -50 points in each array index
     * @param text
     * @return
     */
    public static byte[] encodePasswordToBytes(String text) {
        byte[] bytes = text.getBytes();
        int idx=0;
        for(byte b1 : bytes) {
            bytes[idx++]=(byte) (b1-50);
        }
        return bytes;
    }

    /**
     * Util func converts byte[] array encoded password into Plaintext String password and removes the padding of -50 points in each array index
     * @return
     */
    public static String decodeBytesToPassword(byte[] bytes) {
        int idx=0;
        for(byte b1 : bytes) {
            bytes[idx]=(byte) (b1+50);
            idx++;
        }
        return new String(bytes);
    }

    public static void main(String[] args) throws Exception {

        EncryptionUtil1 util = new EncryptionUtil1();
        //util.initService();
        String encryptedUserName = null;
        encryptedUserName = util.encryptHeaderForInternalAuth(10000004L);
        //encryptedUserName = util.encrypt("10000004" + System.currentTimeMillis());
        System.out.print("Encrypted UserName yyy: " + encryptedUserName);

    }

    /**
     * Returns the Keystore password from application.properties file. If (Spring profile==dev + Password is Plaintext), then returns plaintext password
     * Else Returns Decrypted Password
     * @return
     */
    private String getKeyStorePassword() {
        //String keyStorePasswordEnc = environment.getProperty(PropertiesEnum.YODLEE_KEYSTORE_PASSWORD.getKey());
        String keyStorePasswordEnc = "sECHuiJ10Uxbd0ZFABO+57S6FawMoDQn/cNhXHZhKqfYsKnZ+tzlO3WGDDRsOKjX";
        String keyStorePassword=extractCipher(keyStorePasswordEnc);
        if(isDevProfile && keyStorePassword.equals(keyStorePasswordEnc)) {
            //logger.debug("INFO: keyStorePassword Decryption Request was made for DEV profile. keyStorePassword:{}", keyStorePassword);
            return keyStorePassword;
        }
        String encryptionKey = decodeBytesToPassword(ENC_KEY);

        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(encryptionKey);
        keyStorePassword = textEncryptor.decrypt(keyStorePassword);
        return keyStorePassword;
    }

}
