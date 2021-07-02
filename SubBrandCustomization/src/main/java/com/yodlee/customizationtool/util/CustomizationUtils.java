/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 *
 * @author Rajeev Anantharaman Iyer
 */
package com.yodlee.customizationtool.util;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class CustomizationUtils {

    public static List<String> hexColorCode;
    public static List<String> customizeFeaturesATLEnableDisableSSO;
    public static List<String> customizeFeaturesATLEnableDisableSessionManager;
    public static List<String> customizeFeaturesATLEnableDisableShowTimeOutNotification;
    public static List<String> customizeFeaturesFLEnableDisableSplashPage;
    public static List<String> customizeFeaturesFLTNCEnableDisableSplash;
    public static List<String> customizeFeaturesFLTNCEnableDisableVerifyCredentials;
    public static List<String> customizeFeaturesFLTNCVerifyCredentialsType;
    public static List<String> customizeFeaturesFLEnableDisableStepper;
    public static List<String> customizeFeaturesFLSearchBarPositionType;
    public static List<String> customizeFeaturesFLEnableDisablePopularSites;
    public static List<String> customizeFeaturesFLPopularSitesNames;
    public static List<String> customizeFeaturesFLEnableDisableDisplaySiteNameOnLogoButton;
    public static List<String> customizeFeaturesFLEnableDisableFooter;
    public static List<String> customizeFeaturesFLEnableDisableSuccessPageForAddAccount;


    public static void loadCustomizationParams(){
        addHexColorCodeToList();
        addATLEnableSessionManagerToList();
        addATLEnableSSOToList();
        addATLShowTimeOutNotificationToList();
        addFLDisplaySiteNameOnLogoButtonToList();
        addFLFooterToList();
        addFLPopularSitesToList();
        addFLSplashPageToList();
        addFLStepperToList();
        addFLSuccessPageForAddAccountToList();
        addFLTNCSplashToList();
        addFLTNCVerifyCredentialsToList();
        addFLSearchBarPositionTypeToList();
        addFLVerifyCredentialsTypeToList();
        addFLPopularSitesNamesToList();
    }

    public static List<String> addFLSearchBarPositionTypeToList(){
        customizeFeaturesFLSearchBarPositionType = new ArrayList();
        customizeFeaturesFLSearchBarPositionType.add("Bottom");
        customizeFeaturesFLSearchBarPositionType.add("Top");
        return customizeFeaturesFLSearchBarPositionType;
    }

    public static List<String> addFLVerifyCredentialsTypeToList(){
        customizeFeaturesFLTNCVerifyCredentialsType = new ArrayList();
        customizeFeaturesFLTNCVerifyCredentialsType.add("Link");
        customizeFeaturesFLTNCVerifyCredentialsType.add("Inline");
        return customizeFeaturesFLTNCVerifyCredentialsType;
    }

    public static List<String> addFLSuccessPageForAddAccountToList(){
        customizeFeaturesFLEnableDisableSuccessPageForAddAccount = new ArrayList();
        customizeFeaturesFLEnableDisableSuccessPageForAddAccount.add("true");
        customizeFeaturesFLEnableDisableSuccessPageForAddAccount.add("false");
        return customizeFeaturesFLEnableDisableSuccessPageForAddAccount;
    }

    public static List<String> addFLFooterToList(){
        customizeFeaturesFLEnableDisableFooter = new ArrayList();
        customizeFeaturesFLEnableDisableFooter.add("true");
        customizeFeaturesFLEnableDisableFooter.add("false");
        return customizeFeaturesFLEnableDisableFooter;
    }

    public static List<String> addFLDisplaySiteNameOnLogoButtonToList(){
        customizeFeaturesFLEnableDisableDisplaySiteNameOnLogoButton = new ArrayList();
        customizeFeaturesFLEnableDisableDisplaySiteNameOnLogoButton.add("true");
        customizeFeaturesFLEnableDisableDisplaySiteNameOnLogoButton.add("false");
        return customizeFeaturesFLEnableDisableDisplaySiteNameOnLogoButton;
    }

    public static List<String> addFLPopularSitesToList(){
        customizeFeaturesFLEnableDisablePopularSites = new ArrayList();
        customizeFeaturesFLEnableDisablePopularSites.add("true");
        customizeFeaturesFLEnableDisablePopularSites.add("false");
        return customizeFeaturesFLEnableDisablePopularSites;
    }

    public static List<String> addFLStepperToList(){
        customizeFeaturesFLEnableDisableStepper = new ArrayList();
        customizeFeaturesFLEnableDisableStepper.add("true");
        customizeFeaturesFLEnableDisableStepper.add("false");
        return customizeFeaturesFLEnableDisableStepper;
    }

    public static List<String> addFLTNCVerifyCredentialsToList(){
        customizeFeaturesFLTNCEnableDisableVerifyCredentials = new ArrayList();
        customizeFeaturesFLTNCEnableDisableVerifyCredentials.add("true");
  //      customizeFeaturesFLTNCEnableDisableVerifyCredentials.add("true");
        return customizeFeaturesFLTNCEnableDisableVerifyCredentials;
    }

    public static List<String> addFLTNCSplashToList(){
        customizeFeaturesFLTNCEnableDisableSplash = new ArrayList();
        customizeFeaturesFLTNCEnableDisableSplash.add("false");
    //    customizeFeaturesFLTNCEnableDisableSplash.add("false");
        return customizeFeaturesFLTNCEnableDisableSplash;
    }

    public static List<String> addFLSplashPageToList(){
        customizeFeaturesFLEnableDisableSplashPage = new ArrayList();
        customizeFeaturesFLEnableDisableSplashPage.add("true");
    //    customizeFeaturesFLEnableDisableSplashPage.add("true");
        return customizeFeaturesFLEnableDisableSplashPage;
    }

    public static List<String> addATLShowTimeOutNotificationToList(){
        customizeFeaturesATLEnableDisableShowTimeOutNotification = new ArrayList();
        customizeFeaturesATLEnableDisableShowTimeOutNotification.add("true");
        customizeFeaturesATLEnableDisableShowTimeOutNotification.add("false");
        return customizeFeaturesATLEnableDisableShowTimeOutNotification;
    }

    public static List<String> addATLEnableSessionManagerToList(){
        customizeFeaturesATLEnableDisableSessionManager = new ArrayList();
        customizeFeaturesATLEnableDisableSessionManager.add("true");
        customizeFeaturesATLEnableDisableSessionManager.add("false");
        return customizeFeaturesATLEnableDisableSessionManager;
    }

    public static List<String> addATLEnableSSOToList(){
        customizeFeaturesATLEnableDisableSSO = new ArrayList();
        customizeFeaturesATLEnableDisableSSO.add("true");
        customizeFeaturesATLEnableDisableSSO.add("false");
        return customizeFeaturesATLEnableDisableSSO;
    }

    public static String getRandomItemFromList(List<String> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size())).toString();
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    public static String getRGBValueFromHexCode(String hexColorCode){
        String rgb = hex2Rgb(hexColorCode).toString().replace("java.awt.Color[","").
                replace("]",")").replace("r=","rgb(").replace("g="," ").
                replace("b="," ");
        return rgb;
    }

    public static int generateRandomNumber(){
        Random rand = new Random();
        int max =8;
        int min =1;
        // nextInt as provided by Random is exclusive of the top value so you need to add 1

        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static List<String> getMultipleItemsFromListWithOutRepetition(List<String> list, int numberOfPopularSites) {
        Random rand = new Random();
        List<String> givenList = list;
        List<String> finalList = new ArrayList();

        for (int i = 0; i < numberOfPopularSites; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            String randomElement = givenList.get(randomIndex);
            finalList.add(randomElement);
            givenList.remove(randomIndex);
        }
        return finalList;
    }

    public static List<String> addHexColorCodeToList(){
        hexColorCode = new ArrayList();
        hexColorCode.add("#FFFAFA");
        hexColorCode.add("#F8F8FF");
        hexColorCode.add("#F5F5F5");
        hexColorCode.add("#DCDCDC");
        hexColorCode.add("#FFFAF0");
        hexColorCode.add("#FDF5E6");
        hexColorCode.add("#FAF0E6");
        hexColorCode.add("#FAEBD7");
        hexColorCode.add("#FFEFD5");
        hexColorCode.add("#FFEBCD");
        hexColorCode.add("#FFE4C4");
        hexColorCode.add("#FFDAB9");
        hexColorCode.add("#FFDEAD");
        hexColorCode.add("#FFE4B5");
        hexColorCode.add("#FFF8DC");
        hexColorCode.add("#FFFFF0");
        hexColorCode.add("#FFFACD");
        hexColorCode.add("#FFF5EE");
        hexColorCode.add("#F0FFF0");
        hexColorCode.add("#F5FFFA");
        hexColorCode.add("#F0FFFF");
        hexColorCode.add("#F0F8FF");
        hexColorCode.add("#E6E6FA");
        hexColorCode.add("#FFF0F5");
        hexColorCode.add("#FFE4E1");
        hexColorCode.add("#FFFFFF");
        hexColorCode.add("#000000");
        hexColorCode.add("#2F4F4F");
        hexColorCode.add("#696969");
        hexColorCode.add("#708090");
        hexColorCode.add("#778899");
        hexColorCode.add("#BEBEBE");
        hexColorCode.add("#D3D3D3");
        hexColorCode.add("#191970");
        hexColorCode.add("#000080");
        hexColorCode.add("#6495ED");
        hexColorCode.add("#483D8B");
        hexColorCode.add("#6A5ACD");
        hexColorCode.add("#7B68EE");
        hexColorCode.add("#8470FF");
        hexColorCode.add("#0000CD");
        hexColorCode.add("#4169E1");
        hexColorCode.add("#0000FF");
        hexColorCode.add("#1E90FF");
        hexColorCode.add("#00BFFF");
        hexColorCode.add("#87CEEB");
        hexColorCode.add("#87CEFA");
        hexColorCode.add("#4682B4");
        hexColorCode.add("#B0C4DE");
        hexColorCode.add("#ADD8E6");
        hexColorCode.add("#B0E0E6");
        hexColorCode.add("#AFEEEE");
        hexColorCode.add("#00CED1");
        hexColorCode.add("#48D1CC");
        hexColorCode.add("#40E0D0");
        hexColorCode.add("#00FFFF");
        hexColorCode.add("#E0FFFF");
        hexColorCode.add("#5F9EA0");
        hexColorCode.add("#66CDAA");
        hexColorCode.add("#7FFFD4");
        hexColorCode.add("#006400");
        hexColorCode.add("#556B2F");
        hexColorCode.add("#8FBC8F");
        hexColorCode.add("#2E8B57");
        hexColorCode.add("#3CB371");
        hexColorCode.add("#20B2AA");
        hexColorCode.add("#98FB98");
        hexColorCode.add("#00FF7F");
        hexColorCode.add("#7CFC00");
        hexColorCode.add("#00FF00");
        hexColorCode.add("#7FFF00");
        hexColorCode.add("#00FA9A");
        hexColorCode.add("#ADFF2F");
        hexColorCode.add("#32CD32");
        hexColorCode.add("#9ACD32");
        hexColorCode.add("#228B22");
        hexColorCode.add("#6B8E23");
        hexColorCode.add("#BDB76B");
        hexColorCode.add("#F0E68C");
        hexColorCode.add("#EEE8AA");
        hexColorCode.add("#FAFAD2");
        hexColorCode.add("#FFFFE0");
        hexColorCode.add("#FFFF00");
        hexColorCode.add("#FFD700");
        hexColorCode.add("#EEDD82");
        hexColorCode.add("#DAA520");
        hexColorCode.add("#B8860B");
        hexColorCode.add("#BC8F8F");
        hexColorCode.add("#CD5C5C");
        hexColorCode.add("#8B4513");
        hexColorCode.add("#A0522D");
        hexColorCode.add("#CD853F");
        hexColorCode.add("#DEB887");
        hexColorCode.add("#F5F5DC");
        hexColorCode.add("#F5DEB3");
        hexColorCode.add("#F4A460");
        hexColorCode.add("#D2B48C");
        hexColorCode.add("#D2691E");
        hexColorCode.add("#B22222");
        hexColorCode.add("#A52A2A");
        hexColorCode.add("#E9967A");
        hexColorCode.add("#FA8072");
        hexColorCode.add("#FFA07A");
        hexColorCode.add("#FFA500");
        hexColorCode.add("#FF8C00");
        hexColorCode.add("#FF7F50");
        hexColorCode.add("#F08080");
        hexColorCode.add("#FF6347");
        hexColorCode.add("#FF4500");
        hexColorCode.add("#FF0000");
        hexColorCode.add("#FF69B4");
        hexColorCode.add("#FF1493");
        hexColorCode.add("#FFC0CB");
        hexColorCode.add("#FFB6C1");
        hexColorCode.add("#DB7093");
        hexColorCode.add("#B03060");
        hexColorCode.add("#C71585");
        hexColorCode.add("#D02090");
        hexColorCode.add("#FF00FF");
        hexColorCode.add("#EE82EE");
        hexColorCode.add("#DDA0DD");
        hexColorCode.add("#DA70D6");
        hexColorCode.add("#BA55D3");
        hexColorCode.add("#9932CC");
        hexColorCode.add("#9400D3");
        hexColorCode.add("#8A2BE2");
        hexColorCode.add("#A020F0");
        hexColorCode.add("#9370DB");
        hexColorCode.add("#D8BFD8");
        hexColorCode.add("#EEE9E9");
        hexColorCode.add("#CDC9C9");
        hexColorCode.add("#8B8989");
        hexColorCode.add("#EEE5DE");
        hexColorCode.add("#CDC5BF");
        hexColorCode.add("#8B8682");
        hexColorCode.add("#FFEFDB");
        hexColorCode.add("#EEDFCC");
        hexColorCode.add("#CDC0B0");
        hexColorCode.add("#8B8378");
        hexColorCode.add("#EED5B7");
        hexColorCode.add("#CDB79E");
        hexColorCode.add("#8B7D6B");
        hexColorCode.add("#EECBAD");
        hexColorCode.add("#CDAF95");
        hexColorCode.add("#8B7765");
        hexColorCode.add("#EECFA1");
        hexColorCode.add("#CDB38B");
        hexColorCode.add("#8B795E");
        hexColorCode.add("#EEE9BF");
        hexColorCode.add("#CDC9A5");
        hexColorCode.add("#8B8970");
        hexColorCode.add("#EEE8CD");
        hexColorCode.add("#CDC8B1");
        hexColorCode.add("#8B8878");
        hexColorCode.add("#EEEEE0");
        hexColorCode.add("#CDCDC1");
        hexColorCode.add("#8B8B83");
        hexColorCode.add("#E0EEE0");
        hexColorCode.add("#C1CDC1");
        hexColorCode.add("#838B83");
        hexColorCode.add("#EEE0E5");
        hexColorCode.add("#CDC1C5");
        hexColorCode.add("#8B8386");
        hexColorCode.add("#EED5D2");
        hexColorCode.add("#CDB7B5");
        hexColorCode.add("#8B7D7B");
        hexColorCode.add("#E0EEEE");
        hexColorCode.add("#C1CDCD");
        hexColorCode.add("#838B8B");
        hexColorCode.add("#836FFF");
        hexColorCode.add("#7A67EE");
        hexColorCode.add("#6959CD");
        hexColorCode.add("#473C8B");
        hexColorCode.add("#4876FF");
        hexColorCode.add("#436EEE");
        hexColorCode.add("#3A5FCD");
        hexColorCode.add("#27408B");
        hexColorCode.add("#0000EE");
        hexColorCode.add("#00008B");
        hexColorCode.add("#1C86EE");
        hexColorCode.add("#1874CD");
        hexColorCode.add("#104E8B");
        hexColorCode.add("#63B8FF");
        hexColorCode.add("#5CACEE");
        hexColorCode.add("#4F94CD");
        hexColorCode.add("#36648B");
        hexColorCode.add("#00B2EE");
        hexColorCode.add("#009ACD");
        hexColorCode.add("#00688B");
        hexColorCode.add("#87CEFF");
        hexColorCode.add("#7EC0EE");
        hexColorCode.add("#6CA6CD");
        hexColorCode.add("#4A708B");
        hexColorCode.add("#B0E2FF");
        hexColorCode.add("#A4D3EE");
        hexColorCode.add("#8DB6CD");
        hexColorCode.add("#607B8B");
        hexColorCode.add("#C6E2FF");
        hexColorCode.add("#B9D3EE");
        hexColorCode.add("#9FB6CD");
        hexColorCode.add("#6C7B8B");
        hexColorCode.add("#CAE1FF");
        hexColorCode.add("#BCD2EE");
        hexColorCode.add("#A2B5CD");
        hexColorCode.add("#6E7B8B");
        hexColorCode.add("#BFEFFF");
        hexColorCode.add("#B2DFEE");
        hexColorCode.add("#9AC0CD");
        hexColorCode.add("#68838B");
        hexColorCode.add("#D1EEEE");
        hexColorCode.add("#B4CDCD");
        hexColorCode.add("#7A8B8B");
        hexColorCode.add("#BBFFFF");
        hexColorCode.add("#AEEEEE");
        hexColorCode.add("#96CDCD");
        hexColorCode.add("#668B8B");
        hexColorCode.add("#98F5FF");
        hexColorCode.add("#8EE5EE");
        hexColorCode.add("#7AC5CD");
        hexColorCode.add("#53868B");
        hexColorCode.add("#00F5FF");
        hexColorCode.add("#00E5EE");
        hexColorCode.add("#00C5CD");
        hexColorCode.add("#00868B");
        hexColorCode.add("#00EEEE");
        hexColorCode.add("#00CDCD");
        hexColorCode.add("#008B8B");
        hexColorCode.add("#97FFFF");
        hexColorCode.add("#8DEEEE");
        hexColorCode.add("#79CDCD");
        hexColorCode.add("#528B8B");
        hexColorCode.add("#76EEC6");
        hexColorCode.add("#458B74");
        hexColorCode.add("#C1FFC1");
        hexColorCode.add("#B4EEB4");
        hexColorCode.add("#9BCD9B");
        hexColorCode.add("#698B69");
        hexColorCode.add("#54FF9F");
        hexColorCode.add("#4EEE94");
        hexColorCode.add("#43CD80");
        hexColorCode.add("#9AFF9A");
        hexColorCode.add("#90EE90");
        hexColorCode.add("#7CCD7C");
        hexColorCode.add("#548B54");
        hexColorCode.add("#00EE76");
        hexColorCode.add("#00CD66");
        hexColorCode.add("#008B45");
        hexColorCode.add("#00EE00");
        hexColorCode.add("#00CD00");
        hexColorCode.add("#008B00");
        hexColorCode.add("#76EE00");
        hexColorCode.add("#66CD00");
        hexColorCode.add("#458B00");
        hexColorCode.add("#C0FF3E");
        hexColorCode.add("#B3EE3A");
        hexColorCode.add("#698B22");
        hexColorCode.add("#CAFF70");
        hexColorCode.add("#BCEE68");
        hexColorCode.add("#A2CD5A");
        hexColorCode.add("#6E8B3D");
        hexColorCode.add("#FFF68F");
        hexColorCode.add("#EEE685");
        hexColorCode.add("#CDC673");
        hexColorCode.add("#8B864E");
        hexColorCode.add("#FFEC8B");
        hexColorCode.add("#EEDC82");
        hexColorCode.add("#CDBE70");
        hexColorCode.add("#8B814C");
        hexColorCode.add("#EEEED1");
        hexColorCode.add("#CDCDB4");
        hexColorCode.add("#8B8B7A");
        hexColorCode.add("#EEEE00");
        hexColorCode.add("#CDCD00");
        hexColorCode.add("#8B8B00");
        hexColorCode.add("#EEC900");
        hexColorCode.add("#CDAD00");
        hexColorCode.add("#8B7500");
        hexColorCode.add("#FFC125");
        hexColorCode.add("#EEB422");
        hexColorCode.add("#CD9B1D");
        hexColorCode.add("#8B6914");
        hexColorCode.add("#FFB90F");
        hexColorCode.add("#EEAD0E");
        hexColorCode.add("#CD950C");
        hexColorCode.add("#8B6508");
        hexColorCode.add("#FFC1C1");
        hexColorCode.add("#EEB4B4");
        hexColorCode.add("#CD9B9B");
        hexColorCode.add("#8B6969");
        hexColorCode.add("#FF6A6A");
        hexColorCode.add("#EE6363");
        hexColorCode.add("#CD5555");
        hexColorCode.add("#8B3A3A");
        hexColorCode.add("#FF8247");
        hexColorCode.add("#EE7942");
        hexColorCode.add("#CD6839");
        hexColorCode.add("#8B4726");
        hexColorCode.add("#FFD39B");
        hexColorCode.add("#EEC591");
        hexColorCode.add("#CDAA7D");
        hexColorCode.add("#8B7355");
        hexColorCode.add("#FFE7BA");
        hexColorCode.add("#EED8AE");
        hexColorCode.add("#CDBA96");
        hexColorCode.add("#8B7E66");
        hexColorCode.add("#FFA54F");
        hexColorCode.add("#EE9A49");
        hexColorCode.add("#8B5A2B");
        hexColorCode.add("#FF7F24");
        hexColorCode.add("#EE7621");
        hexColorCode.add("#CD661D");
        hexColorCode.add("#FF3030");
        hexColorCode.add("#EE2C2C");
        hexColorCode.add("#CD2626");
        hexColorCode.add("#8B1A1A");
        hexColorCode.add("#FF4040");
        hexColorCode.add("#EE3B3B");
        hexColorCode.add("#CD3333");
        hexColorCode.add("#8B2323");
        hexColorCode.add("#FF8C69");
        hexColorCode.add("#EE8262");
        hexColorCode.add("#CD7054");
        hexColorCode.add("#8B4C39");
        hexColorCode.add("#EE9572");
        hexColorCode.add("#CD8162");
        hexColorCode.add("#8B5742");
        hexColorCode.add("#EE9A00");
        hexColorCode.add("#CD8500");
        hexColorCode.add("#8B5A00");
        hexColorCode.add("#FF7F00");
        hexColorCode.add("#EE7600");
        hexColorCode.add("#CD6600");
        hexColorCode.add("#8B4500");
        hexColorCode.add("#FF7256");
        hexColorCode.add("#EE6A50");
        hexColorCode.add("#CD5B45");
        hexColorCode.add("#8B3E2F");
        hexColorCode.add("#EE5C42");
        hexColorCode.add("#CD4F39");
        hexColorCode.add("#8B3626");
        hexColorCode.add("#EE4000");
        hexColorCode.add("#CD3700");
        hexColorCode.add("#8B2500");
        hexColorCode.add("#EE0000");
        hexColorCode.add("#CD0000");
        hexColorCode.add("#8B0000");
        hexColorCode.add("#EE1289");
        hexColorCode.add("#CD1076");
        hexColorCode.add("#8B0A50");
        hexColorCode.add("#FF6EB4");
        hexColorCode.add("#EE6AA7");
        hexColorCode.add("#CD6090");
        hexColorCode.add("#8B3A62");
        hexColorCode.add("#FFB5C5");
        hexColorCode.add("#EEA9B8");
        hexColorCode.add("#CD919E");
        hexColorCode.add("#8B636C");
        hexColorCode.add("#FFAEB9");
        hexColorCode.add("#EEA2AD");
        hexColorCode.add("#CD8C95");
        hexColorCode.add("#8B5F65");
        hexColorCode.add("#FF82AB");
        hexColorCode.add("#EE799F");
        hexColorCode.add("#CD6889");
        hexColorCode.add("#8B475D");
        hexColorCode.add("#FF34B3");
        hexColorCode.add("#EE30A7");
        hexColorCode.add("#CD2990");
        hexColorCode.add("#8B1C62");
        hexColorCode.add("#FF3E96");
        hexColorCode.add("#EE3A8C");
        hexColorCode.add("#CD3278");
        hexColorCode.add("#8B2252");
        hexColorCode.add("#EE00EE");
        hexColorCode.add("#CD00CD");
        hexColorCode.add("#8B008B");
        hexColorCode.add("#FF83FA");
        hexColorCode.add("#EE7AE9");
        hexColorCode.add("#CD69C9");
        hexColorCode.add("#8B4789");
        hexColorCode.add("#FFBBFF");
        hexColorCode.add("#EEAEEE");
        hexColorCode.add("#CD96CD");
        hexColorCode.add("#8B668B");
        hexColorCode.add("#E066FF");
        hexColorCode.add("#D15FEE");
        hexColorCode.add("#B452CD");
        hexColorCode.add("#7A378B");
        hexColorCode.add("#BF3EFF");
        hexColorCode.add("#B23AEE");
        hexColorCode.add("#9A32CD");
        hexColorCode.add("#68228B");
        hexColorCode.add("#9B30FF");
        hexColorCode.add("#912CEE");
        hexColorCode.add("#7D26CD");
        hexColorCode.add("#551A8B");
        hexColorCode.add("#AB82FF");
        hexColorCode.add("#9F79EE");
        hexColorCode.add("#8968CD");
        hexColorCode.add("#5D478B");
        hexColorCode.add("#FFE1FF");
        hexColorCode.add("#EED2EE");
        hexColorCode.add("#CDB5CD");
        hexColorCode.add("#8B7B8B");
        hexColorCode.add("#030303");
        hexColorCode.add("#050505");
        hexColorCode.add("#080808");
        hexColorCode.add("#0A0A0A");
        hexColorCode.add("#0D0D0D");
        hexColorCode.add("#0F0F0F");
        hexColorCode.add("#121212");
        hexColorCode.add("#141414");
        hexColorCode.add("#171717");
        hexColorCode.add("#1A1A1A");
        hexColorCode.add("#1C1C1C");
        hexColorCode.add("#1F1F1F");
        hexColorCode.add("#212121");
        hexColorCode.add("#242424");
        hexColorCode.add("#262626");
        hexColorCode.add("#292929");
        hexColorCode.add("#2B2B2B");
        hexColorCode.add("#2E2E2E");
        hexColorCode.add("#303030");
        hexColorCode.add("#333333");
        hexColorCode.add("#363636");
        hexColorCode.add("#383838");
        hexColorCode.add("#3B3B3B");
        hexColorCode.add("#3D3D3D");
        hexColorCode.add("#404040");
        hexColorCode.add("#424242");
        hexColorCode.add("#454545");
        hexColorCode.add("#474747");
        hexColorCode.add("#4A4A4A");
        hexColorCode.add("#4D4D4D");
        hexColorCode.add("#4F4F4F");
        hexColorCode.add("#525252");
        hexColorCode.add("#545454");
        hexColorCode.add("#575757");
        hexColorCode.add("#595959");
        hexColorCode.add("#5C5C5C");
        hexColorCode.add("#5E5E5E");
        hexColorCode.add("#616161");
        hexColorCode.add("#636363");
        hexColorCode.add("#666666");
        hexColorCode.add("#6B6B6B");
        hexColorCode.add("#6E6E6E");
        hexColorCode.add("#707070");
        hexColorCode.add("#737373");
        hexColorCode.add("#757575");
        hexColorCode.add("#787878");
        hexColorCode.add("#7A7A7A");
        hexColorCode.add("#7D7D7D");
        hexColorCode.add("#7F7F7F");
        hexColorCode.add("#828282");
        hexColorCode.add("#858585");
        hexColorCode.add("#878787");
        hexColorCode.add("#8A8A8A");
        hexColorCode.add("#8C8C8C");
        hexColorCode.add("#8F8F8F");
        hexColorCode.add("#919191");
        hexColorCode.add("#949494");
        hexColorCode.add("#969696");
        hexColorCode.add("#999999");
        hexColorCode.add("#9C9C9C");
        hexColorCode.add("#9E9E9E");
        hexColorCode.add("#A1A1A1");
        hexColorCode.add("#A3A3A3");
        hexColorCode.add("#A6A6A6");
        hexColorCode.add("#A8A8A8");
        hexColorCode.add("#ABABAB");
        hexColorCode.add("#ADADAD");
        hexColorCode.add("#B0B0B0");
        hexColorCode.add("#B3B3B3");
        hexColorCode.add("#B5B5B5");
        hexColorCode.add("#B8B8B8");
        hexColorCode.add("#BABABA");
        hexColorCode.add("#BDBDBD");
        hexColorCode.add("#BFBFBF");
        hexColorCode.add("#C2C2C2");
        hexColorCode.add("#C4C4C4");
        hexColorCode.add("#C7C7C7");
        hexColorCode.add("#C9C9C9");
        hexColorCode.add("#CCCCCC");
        hexColorCode.add("#CFCFCF");
        hexColorCode.add("#D1D1D1");
        hexColorCode.add("#D4D4D4");
        hexColorCode.add("#D6D6D6");
        hexColorCode.add("#D9D9D9");
        hexColorCode.add("#DBDBDB");
        hexColorCode.add("#DEDEDE");
        hexColorCode.add("#E0E0E0");
        hexColorCode.add("#E3E3E3");
        hexColorCode.add("#E5E5E5");
        hexColorCode.add("#E8E8E8");
        hexColorCode.add("#EBEBEB");
        hexColorCode.add("#EDEDED");
        hexColorCode.add("#F0F0F0");
        hexColorCode.add("#F2F2F2");
        hexColorCode.add("#F7F7F7");
        hexColorCode.add("#FAFAFA");
        hexColorCode.add("#FCFCFC");
        hexColorCode.add("#A9A9A9");

        return hexColorCode;
    }

    public static List<String> addFLPopularSitesNamesToList(){
        customizeFeaturesFLPopularSitesNames = new ArrayList();
        customizeFeaturesFLPopularSitesNames.add("Citibank (online.citibank.com)");
        customizeFeaturesFLPopularSitesNames.add("Bank of America");
        customizeFeaturesFLPopularSitesNames.add("Citibank");
        customizeFeaturesFLPopularSitesNames.add("Bank of America (Stage)");
        customizeFeaturesFLPopularSitesNames.add("Community Bank of Wichita");
        customizeFeaturesFLPopularSitesNames.add("Farmers and Merchants Bank");
        customizeFeaturesFLPopularSitesNames.add("Citizens State Bank & Trust Company");
        customizeFeaturesFLPopularSitesNames.add("Aeromexico");
        customizeFeaturesFLPopularSitesNames.add("Grant County Bank");
        customizeFeaturesFLPopularSitesNames.add("American Bar Association");
        customizeFeaturesFLPopularSitesNames.add("Indianapolis Colts - Credit Cards");
        customizeFeaturesFLPopularSitesNames.add("Philadelphia Eagles - Credit Cards");
        customizeFeaturesFLPopularSitesNames.add("MBNA - CFO Credits");
        customizeFeaturesFLPopularSitesNames.add("Merrill Visa Credit Card");
        customizeFeaturesFLPopularSitesNames.add("AdvisorTestSiteforDummyCard");
        customizeFeaturesFLPopularSitesNames.add("Bank of America (CA) (Stage)");
        customizeFeaturesFLPopularSitesNames.add("DagBank");
        customizeFeaturesFLPopularSitesNames.add("DagMiles");
        customizeFeaturesFLPopularSitesNames.add("DagInsurance");
        customizeFeaturesFLPopularSitesNames.add("DagCreditcard");
        customizeFeaturesFLPopularSitesNames.add("DagLoan");
        customizeFeaturesFLPopularSitesNames.add("DagBills");
        customizeFeaturesFLPopularSitesNames.add("DagInvestments");
        customizeFeaturesFLPopularSitesNames.add("Security State Bank");
        customizeFeaturesFLPopularSitesNames.add("FIA Card Services");
        customizeFeaturesFLPopularSitesNames.add("DagBankMultilevel");
        customizeFeaturesFLPopularSitesNames.add("Test Creditcard Special Case");
        customizeFeaturesFLPopularSitesNames.add("American Trust Bank");
        customizeFeaturesFLPopularSitesNames.add("Union State Bank (KS, MO)");
        customizeFeaturesFLPopularSitesNames.add("Dag Site");
        customizeFeaturesFLPopularSitesNames.add("Dag Site Multilevel");
        customizeFeaturesFLPopularSitesNames.add("DagSIteMFAAndNonMFA");
        customizeFeaturesFLPopularSitesNames.add("BBVA Bancomer");
        customizeFeaturesFLPopularSitesNames.add("Jacksonville Jaguars Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Barnes & Noble Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Major League Baseball Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Boston Red Sox Credit Card");
        customizeFeaturesFLPopularSitesNames.add("NASCAR Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Gateway Credit Card");
        customizeFeaturesFLPopularSitesNames.add("New York Yankees Credit Card");
        customizeFeaturesFLPopularSitesNames.add("National Football League Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Cincinnati Bengals Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Chicago Cubs Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Minnesota Twins (MBNA) Credit Card");
        customizeFeaturesFLPopularSitesNames.add("New England Patriots Credit Card");
        customizeFeaturesFLPopularSitesNames.add("NFL Shield Preferred Visa Card");
        customizeFeaturesFLPopularSitesNames.add("New York Mets Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Cleveland Indians Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Miami Dolphins Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Cleveland Browns Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Atlanta Braves Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Chicago Bears Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Green Bay Packers Credit Card");
        customizeFeaturesFLPopularSitesNames.add("New York Jets Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Buffalo Bills Credit Card");
        customizeFeaturesFLPopularSitesNames.add("New York Giants Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Baltimore Orioles Credit Card");
        customizeFeaturesFLPopularSitesNames.add("San Francisco Giants Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Cincinnati Reds Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Houston Astros Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Dale Earnhardt Jr. Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Chicago White Sox Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Jeff Gordon Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Pittsburgh Steelers Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Detriot Red Wings Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Philadelphia Phillies (MBNA) Credit Card");
        customizeFeaturesFLPopularSitesNames.add("St Louis Cardinals Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Denver Broncos Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Washington Redskins Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Firefighters Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Kansas City Chiefs Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Anaheim Angels Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Dale Earnhardt Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Minnesota Vikings Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Atlanta Falcons Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Carolina Hurricanes Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Milwaukee Brewers Credit Card");
        customizeFeaturesFLPopularSitesNames.add("San Francisco 49ers Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Oakland Raiders Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Los Angeles Dodgers Credit Card");
        customizeFeaturesFLPopularSitesNames.add("San Diego Chargers Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Law Enforcement (MBNA) Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Boston Bruins Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Colorado Rockies Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Florida Marlins (MBNA) Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Carolina Panthers Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Buffalo Sabres Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Dallas Stars Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Arizona Diamondbacks Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Bobby Labonte Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Texas Rangers Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Bill Elliott Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Tampa Bay Buccaneers Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Detroit Lions Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Oakland Athletics (MBNA) Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Kyle Petty Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Arizona Cardinals Credit Card");
        customizeFeaturesFLPopularSitesNames.add("New Jersey Devils Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("NHL Shield Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Pittsburgh Pirates Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Los Angeles Kings Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("New York Rangers Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("New Orleans Saints Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Tennessee Titans Credit Card");
        customizeFeaturesFLPopularSitesNames.add("National Hockey League Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Florida Panthers Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("New York Islanders Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Philadelphia Flyers Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Detroit Tigers Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Michael Waltrip Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Colorado Avalanche Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Tampa Bay Devil Rays Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Jeff Burton Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Dale Jarrett Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Morgan Shepherd Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Tony Stewart Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Kenny Wallace Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Columbus Blue Jackets Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Edmonton Oilers Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Minnesota Wild Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Nashville Predators Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Washington Capitals Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Kansas City Royals Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Seattle Mariners (MBNA) Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Loy Allen Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Seattle Seahawks Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Anaheim Mighty Ducks Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Calgary Flames Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Montreal Canadiens Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Pittsburgh Penguins Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Lyndon Amick Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Johnny Benson Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Derrike Cope Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Ernie Irvan Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Joe Nemechek Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Mike Skinner Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Darrell Waltrip Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("St. Louis Rams Credit Card");
        customizeFeaturesFLPopularSitesNames.add("San Jose Sharks Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("St. Louis Blues Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Rusty Wallace Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Ottawa Senators Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Phoenix Coyotes Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Tampa Bay Lightning Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Toronto Blue Jays Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Toronto Maple Leafs Preferred Credit Card");
        customizeFeaturesFLPopularSitesNames.add("San Diego Padres (MBNA) Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Wally Dallenbach Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Ted Musgrave Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Ward Burton Platinum Credit Card");
        customizeFeaturesFLPopularSitesNames.add("Bank 21");
        customizeFeaturesFLPopularSitesNames.add("Dakota Heritage Bank of North Dakota");
        customizeFeaturesFLPopularSitesNames.add("First State Bank (IA)");
        customizeFeaturesFLPopularSitesNames.add("Freeport State Bank");
        customizeFeaturesFLPopularSitesNames.add("United Minnesota Bank");
        customizeFeaturesFLPopularSitesNames.add("Dag Statement Download");
        customizeFeaturesFLPopularSitesNames.add("Rolfe State Bank");
        customizeFeaturesFLPopularSitesNames.add("Iowa Prairie Bank");
        customizeFeaturesFLPopularSitesNames.add("First Iowa State Bank(Community First Bank)");
        customizeFeaturesFLPopularSitesNames.add("DAGCustomGoogle");
        customizeFeaturesFLPopularSitesNames.add("Santander");
        customizeFeaturesFLPopularSitesNames.add("Banamex Particular");
        customizeFeaturesFLPopularSitesNames.add("DAGCustomGoogleMultilevel");
        customizeFeaturesFLPopularSitesNames.add("American Express Cards");
        customizeFeaturesFLPopularSitesNames.add("BBVA Bancomer (Empresas)");
        customizeFeaturesFLPopularSitesNames.add("Banamex Empresas");
        customizeFeaturesFLPopularSitesNames.add("Banorte");
        customizeFeaturesFLPopularSitesNames.add("HSBC (Empresas)");
        customizeFeaturesFLPopularSitesNames.add("Scotiabank");
        customizeFeaturesFLPopularSitesNames.add("BanRegio");
        customizeFeaturesFLPopularSitesNames.add("Banco Del Bajio Personal Banking");
        customizeFeaturesFLPopularSitesNames.add("Banco Del Bajio Business Banking");
        customizeFeaturesFLPopularSitesNames.add("Banco Del Bajio Government Banking");
        customizeFeaturesFLPopularSitesNames.add("Banorte Business Banking");
        customizeFeaturesFLPopularSitesNames.add("Managed Accounts");
        customizeFeaturesFLPopularSitesNames.add("Banamex Bancanet");
        customizeFeaturesFLPopularSitesNames.add("Banco Azteca");
        customizeFeaturesFLPopularSitesNames.add("First New Mexico Bank");
        customizeFeaturesFLPopularSitesNames.add("Bankaool");
        customizeFeaturesFLPopularSitesNames.add("Banamex Aquí");
        customizeFeaturesFLPopularSitesNames.add("Leighton State Bank");
        customizeFeaturesFLPopularSitesNames.add("Southern Missouri Bank of Marshfield");
        customizeFeaturesFLPopularSitesNames.add("Franklin State Bank");
        customizeFeaturesFLPopularSitesNames.add("SBL");
        customizeFeaturesFLPopularSitesNames.add("Community Bank & Trust - Muscatine");
        customizeFeaturesFLPopularSitesNames.add("Farmers State Bank of Hamel");
        customizeFeaturesFLPopularSitesNames.add("Home Savings Bank Wapakoneta");
        customizeFeaturesFLPopularSitesNames.add("Pine River State Bank");
        customizeFeaturesFLPopularSitesNames.add("Kingsley state bank");
        customizeFeaturesFLPopularSitesNames.add("Prairie Bank of Kansas");
        customizeFeaturesFLPopularSitesNames.add("American Society of Mechanical Engineers");
        customizeFeaturesFLPopularSitesNames.add("The Bennington State Bank");
        customizeFeaturesFLPopularSitesNames.add("1st New Mexico Bank");
        customizeFeaturesFLPopularSitesNames.add("Earlham Savings Bank");
        customizeFeaturesFLPopularSitesNames.add("Bellevue State Bank");
        customizeFeaturesFLPopularSitesNames.add("Security State Bank (Texas)");
        customizeFeaturesFLPopularSitesNames.add("DAG FRBTest");
        customizeFeaturesFLPopularSitesNames.add("First National Bank (Scott City)");
        customizeFeaturesFLPopularSitesNames.add("First National Bank of Manning");
        customizeFeaturesFLPopularSitesNames.add("Bank of Cashton");
        customizeFeaturesFLPopularSitesNames.add("Santander Empresas - Business");
        customizeFeaturesFLPopularSitesNames.add("Invex Tarjetas");
        customizeFeaturesFLPopularSitesNames.add("Randall State Bank");
        customizeFeaturesFLPopularSitesNames.add("Modelo Bank");
        customizeFeaturesFLPopularSitesNames.add("Goose River Bank");
        customizeFeaturesFLPopularSitesNames.add("Farmers State Bank (NE)");
        return customizeFeaturesFLPopularSitesNames;
    }

    public static String getFontAbsolutePath(String fontName){
        String filename = ".\\src\\main\\resources\\fonts\\"+fontName;
        File file = new File(filename);
        String path = file.getAbsolutePath();
        return path;
    }

    public static List<String> getListOfFonts(){

        List<String> results = new ArrayList<String>();

        File[] files = new File(".\\src\\main\\resources\\fonts").listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null.

        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        return  results;
    }

    public static void main(String[] args)
    {
        /*// Get the size n
        int n = 6;
        GenericUtils getColor = new GenericUtils();
        // Get and display the alphanumeric string
        System.out.println(GenericUtils.getAlphaNumericString(n));*/

        /*GenericUtils.addHexColorCodeToList();
        String color = GenericUtils.getRandomItemFromList(GenericUtils.hexColorCode);
        System.out.println(color);*/

    }
}