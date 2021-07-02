/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.Util.Core.Utilities;

import com.yodlee.sdp.common.crypto.Encryptor;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenericUtils {

    public static String decryptCobrandCredentials(String encryptedCobrandCredential){
        Encryptor encryptor = new Encryptor(".\\src\\main\\resources\\keystore\\yodleeKeystore","master_key","yodlee123");
        String decryptedCobrandCredential = encryptor.decrypt(encryptedCobrandCredential);
        return decryptedCobrandCredential;
    }

    // function to generate a random string of length n
    public static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public static String getTimStamp(){
		/*
		 * Date date= new Date();
		 * 
		 * long time = date.getTime();
		 */
        return new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());

		/* return String.valueOf(time); */
    }

    public static void main(String[] args)
    {
        // Get the size n
        int n = 6;

        // Get and display the alphanumeric string
        System.out.println(GenericUtils.getAlphaNumericString(n));
    }

    public static String getSAMLCertificateAbsolutePath(String certificateName){
        String filename = ".\\src\\main\\resources\\samlcert\\"+certificateName+".cer";
        File file = new File(filename);
        String path = file.getAbsolutePath();
        return path;
    }
}
