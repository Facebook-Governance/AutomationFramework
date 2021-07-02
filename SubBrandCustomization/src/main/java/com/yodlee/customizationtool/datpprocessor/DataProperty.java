/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.datpprocessor;

public class DataProperty {

    private String loginUsername;
    private String loginPassword;
    private String footerText;
    private String loginPageUserIdFormatDefaultText;
    private String forgotPasswordOnClickMessage;
    private String loginPageUserIdFormatErrorText;
    private String loginPagePasswordErrorText;
    private String loginPageUsernameFieldColorOnError;
    private String loginPagePasswordFieldColorOnError;
    private String loginPageUserIdFormatErrorTextColor;
    private String loginPagePasswordFormatErrorTextColor;

    private String loginPageUserIDBlankErrorText;
    private String loginPagePasswordBlankErrorText;

    private String loginPageUserIdGhostText;
    private String loginPagePasswordGhostText;

    private String subBrandName;
    private String domainNameSubString;
    private String nonExistantSubBrandName;
    private String noResultsFoundText;
    private String customerType;

    private String emptySubBrandName;
    private String emptySubBrandNameErrorText;
    private String subBrandNameLessThan2Characters;
    private String subBrandNameLessThan2CharactersErrorText;
    private String subBrandNameExceed50Characters;
    private String subBrandNameExceed50CharactersErrorText;
    private String subBrandNameNoSpecialCharactersAllowed;
    private String subBrandNameNoSpecialCharactersAllowedErrorText;

    private String emptyDomainNameSubString;
    private String emptyDomainNameSubStringErrorText;
    private String domainNameSubStringLessThan2Characters;
    private String domainNameSubStringLessThan2CharactersErrorText;
    private String domainNameSubStringExceeds50Characters;
    private String domainNameSubStringExceeds50CharactersErrorText;
    private String domainNameSubStringNoSpecialCharactersAllowed;
    private String domainNameSubStringNoSpecialCharactersAllowedErrorText;

    private String emptySourceIpAddress;
    private String invalidSourceIpAddress;
    private String duplicateSourceIPAddress;
    private String sourceIPAddressLimitExceed;

    private String emptySourceIpAddressErrorText;
    private String invalidSourceIpAddressErrorText;
    private String duplicateSourceIPAddressErrorText;
    private String sourceIPAddressLimitExceedGreyedOutText;

    private String noLoginMechanismErrorText;
    private String noProductSelectedErrorText;

    private String emptySAMLCertificate;
    private String emptySAMLCertificateErrorText;
    private String emptyCredentialsLoginId;
    private String emptyCredentialsLoginIdErrorText;
    private String credentialsLoginIdInvalid;
    private String credentialsLoginIdInvalidErrorText;
    private String credentialsLoginIdLessThan6Characters;
    private String credentialsLoginIdLessThan6CharactersErrorText;
    private String credentialsLoginIdExceeds50Characters;
    private String credentialsLoginIdExceeds50CharactersErrorText;
    private String credentialsLoginIdNoSpecialCharactersAllowed;
    private String credentialsLoginIdNoSpecialCharactersAllowedErrorText;

    private String emptySAMLIssuerID;
    private String emptySAMLIssuerIDErrorText;
    private String samlIssuerIdExceeds50Characters;
    private String samlIssuerIdExceeds50CharactersErrorText;

    private String emptySSOPostSource;
    private String emptySSOPostSourceErrorText;
    private String ssoPostSourceExceeds50Characters;
    private String ssoPostSourceExceeds50CharactersErrorText;

    private String emptySSOKeepLiveReferralURL;
    private String emptySSOKeepLiveReferralURLErrorText;
    private String ssoKeepLiveReferralURLExceeds100Characters;
    private String ssoKeepLiveReferralURLExceeds100CharactersErrorText;
    private String ssoKeepLiveReferralURLNotValid;
    private String ssoKeepLiveReferralURLNotValidErrorText;

    private String emptyJWTMaxExpTime;
    private String emptyJWTMaxExpTimeErrorText;
    private String jwtMaxExpTimeMoreThan1800;
    private String jwtMaxExpTimeMoreThan1800ErrorText;
    private String jwtMaxExpTimeAlphaNumericCharacter;
    private String jwtMaxExpTimeAlphaNumericCharacterErrorText;

    private String emptyJWTRSAPublicKey;
    private String emptyJWTRSAPublicKeyErrorText;
    private String invalidJWTRSAPublicKey;
    private String invalidJWTRSAPublicKeyErrorText;
    private String jwtRSAPublicKeyExceeds800Characters;
    private String jwtRSAPublicKeyExceeds800CharactersErrorText;

    private String emptyContactFN;
    private String emptyContactFNErrorText;
    private String contactFNExceeds200Characters;
    private String contactFNExceeds200CharactersErrorText;
    private String contactFNAlphaNumericCharacters;
    private String contactFNAlphaNumericCharactersErrorText;

    private String emptyContactLN;
    private String emptyContactLNErrorText;
    private String contactLNExceeds200Characters;
    private String contactLNExceeds200CharactersErrorText;
    private String contactLNAlphaNumericCharacters;
    private String contactLNAlphaNumericCharactersErrorText;

    private String emptyContactNum;
    private String emptyContactNumErrorText;
    private String contactNumberExceeds10Characters;
    private String contactNumberExceeds10CharactersErrorText;
    private String contactNumberAlphaNumeric;
    private String contactNumberAlphaNumericErrorText;

    private String emptyContactEmailId;
    private String emptyContactEmailIdErrorText;
    private String invalidContactEmail;
    private String invalidContactEmailErrorText;
    private String contactEmailExceeds50Characters;
    private String contactEmailExceeds50CharactersErrorText;

    private String emptyContactUserId;
    private String emptyContactUserIdErrorText;
    private String contactUserIdLessThan6CharactersErrorText;
    private String contactUserIdLessThan6Characters;
    private String contactUserIdExceeds50Characters;
    private String contactUserIdExceeds50CharactersErrorText;
    private String contactUserIdNoSpecialCharactersAllowed;
    private String contactUserIdNoSpecialCharactersAllowedErrorText;

    private boolean addMultipleContact;
    private boolean addAnotherContact;
    private boolean addMultipleJWT;
    private boolean addAnotherJWT;
    private boolean editSAMLDetails;

    private String dagSiteName;
    private String dagUsername;
    private String dagPassword;

    private String toBeRegisteredUserName;
    private String toBeRegisteredUserPassword;

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getFooterText() {
        return footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    public String getLoginPageUserIdFormatDefaultText() {
        return loginPageUserIdFormatDefaultText;
    }

    public void setLoginPageUserIdFormatDefaultText(String loginPageUserIdFormatDefaultText) {
        this.loginPageUserIdFormatDefaultText = loginPageUserIdFormatDefaultText;
    }

    public String getForgotPasswordOnClickMessage() {
        return forgotPasswordOnClickMessage;
    }

    public void setForgotPasswordOnClickMessage(String forgotPasswordOnClickMessage) {
        this.forgotPasswordOnClickMessage = forgotPasswordOnClickMessage;
    }

    public String getLoginPageUserIdFormatErrorText() {
        return loginPageUserIdFormatErrorText;
    }

    public void setLoginPageUserIdFormatErrorText(String loginPageUserIdFormatErrorText) {
        this.loginPageUserIdFormatErrorText = loginPageUserIdFormatErrorText;
    }

    public String getLoginPagePasswordErrorText() {
        return loginPagePasswordErrorText;
    }

    public void setLoginPagePasswordErrorText(String loginPagePasswordErrorText) {
        this.loginPagePasswordErrorText = loginPagePasswordErrorText;
    }

    public String getLoginPageUsernameFieldColorOnError() {
        return loginPageUsernameFieldColorOnError;
    }

    public void setLoginPageUsernameFieldColorOnError(String loginPageUsernameFieldColorOnError) {
        this.loginPageUsernameFieldColorOnError = loginPageUsernameFieldColorOnError;
    }

    public String getLoginPagePasswordFieldColorOnError() {
        return loginPagePasswordFieldColorOnError;
    }

    public void setLoginPagePasswordFieldColorOnError(String loginPagePasswordFieldColorOnError) {
        this.loginPagePasswordFieldColorOnError = loginPagePasswordFieldColorOnError;
    }

    public String getLoginPageUserIdFormatErrorTextColor() {
        return loginPageUserIdFormatErrorTextColor;
    }

    public void setLoginPageUserIdFormatErrorTextColor(String loginPageUserIdFormatErrorTextColor) {
        this.loginPageUserIdFormatErrorTextColor = loginPageUserIdFormatErrorTextColor;
    }

    public String getLoginPagePasswordFormatErrorTextColor() {
        return loginPagePasswordFormatErrorTextColor;
    }

    public void setLoginPagePasswordFormatErrorTextColor(String loginPagePasswordFormatErrorTextColor) {
        this.loginPagePasswordFormatErrorTextColor = loginPagePasswordFormatErrorTextColor;
    }

    public String getLoginPageUserIDBlankErrorText() {
        return loginPageUserIDBlankErrorText;
    }

    public void setLoginPageUserIDBlankErrorText(String loginPageUserIDBlankErrorText) {
        this.loginPageUserIDBlankErrorText = loginPageUserIDBlankErrorText;
    }

    public String getLoginPagePasswordBlankErrorText() {
        return loginPagePasswordBlankErrorText;
    }

    public void setLoginPagePasswordBlankErrorText(String loginPagePasswordBlankErrorText) {
        this.loginPagePasswordBlankErrorText = loginPagePasswordBlankErrorText;
    }

    public String getLoginPageUserIdGhostText() {
        return loginPageUserIdGhostText;
    }

    public void setLoginPageUserIdGhostText(String loginPageUserIdGhostText) {
        this.loginPageUserIdGhostText = loginPageUserIdGhostText;
    }

    public String getLoginPagePasswordGhostText() {
        return loginPagePasswordGhostText;
    }

    public void setLoginPagePasswordGhostText(String loginPagePasswordGhostText) {
        this.loginPagePasswordGhostText = loginPagePasswordGhostText;
    }

    public String getSubBrandName() {
        return subBrandName;
    }

    public void setSubBrandName(String subBrandName) {
        this.subBrandName = subBrandName;
    }

    public String getDomainNameSubString() {
        return domainNameSubString;
    }

    public void setDomainNameSubString(String domainNameSubString) {
        this.domainNameSubString = domainNameSubString;
    }

    public String getNonExistantSubBrandName() {
        return nonExistantSubBrandName;
    }

    public void setNonExistantSubBrandName(String nonExistantSubBrandName) {
        this.nonExistantSubBrandName = nonExistantSubBrandName;
    }

    public String getNoResultsFoundText() {
        return noResultsFoundText;
    }

    public void setNoResultsFoundText(String noResultsFoundText) {
        this.noResultsFoundText = noResultsFoundText;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getEmptySubBrandName() {
        return emptySubBrandName;
    }

    public void setEmptySubBrandName(String emptySubBrandName) {
        this.emptySubBrandName = emptySubBrandName;
    }

    public String getEmptySubBrandNameErrorText() {
        return emptySubBrandNameErrorText;
    }

    public void setEmptySubBrandNameErrorText(String emptySubBrandNameErrorText) {
        this.emptySubBrandNameErrorText = emptySubBrandNameErrorText;
    }

    public String getSubBrandNameLessThan2Characters() {
        return subBrandNameLessThan2Characters;
    }

    public void setSubBrandNameLessThan2Characters(String subBrandNameLessThan2Characters) {
        this.subBrandNameLessThan2Characters = subBrandNameLessThan2Characters;
    }

    public String getSubBrandNameLessThan2CharactersErrorText() {
        return subBrandNameLessThan2CharactersErrorText;
    }

    public void setSubBrandNameLessThan2CharactersErrorText(String subBrandNameLessThan2CharactersErrorText) {
        this.subBrandNameLessThan2CharactersErrorText = subBrandNameLessThan2CharactersErrorText;
    }

    public String getSubBrandNameExceed50Characters() {
        return subBrandNameExceed50Characters;
    }

    public void setSubBrandNameExceed50Characters(String subBrandNameExceed50Characters) {
        this.subBrandNameExceed50Characters = subBrandNameExceed50Characters;
    }

    public String getSubBrandNameExceed50CharactersErrorText() {
        return subBrandNameExceed50CharactersErrorText;
    }

    public void setSubBrandNameExceed50CharactersErrorText(String subBrandNameExceed50CharactersErrorText) {
        this.subBrandNameExceed50CharactersErrorText = subBrandNameExceed50CharactersErrorText;
    }

    public String getSubBrandNameNoSpecialCharactersAllowed() {
        return subBrandNameNoSpecialCharactersAllowed;
    }

    public void setSubBrandNameNoSpecialCharactersAllowed(String subBrandNameNoSpecialCharactersAllowed) {
        this.subBrandNameNoSpecialCharactersAllowed = subBrandNameNoSpecialCharactersAllowed;
    }

    public String getSubBrandNameNoSpecialCharactersAllowedErrorText() {
        return subBrandNameNoSpecialCharactersAllowedErrorText;
    }

    public void setSubBrandNameNoSpecialCharactersAllowedErrorText(String subBrandNameNoSpecialCharactersAllowedErrorText) {
        this.subBrandNameNoSpecialCharactersAllowedErrorText = subBrandNameNoSpecialCharactersAllowedErrorText;
    }

    public String getEmptyDomainNameSubString() {
        return emptyDomainNameSubString;
    }

    public void setEmptyDomainNameSubString(String emptyDomainNameSubString) {
        this.emptyDomainNameSubString = emptyDomainNameSubString;
    }

    public String getEmptyDomainNameSubStringErrorText() {
        return emptyDomainNameSubStringErrorText;
    }

    public void setEmptyDomainNameSubStringErrorText(String emptyDomainNameSubStringErrorText) {
        this.emptyDomainNameSubStringErrorText = emptyDomainNameSubStringErrorText;
    }

    public String getDomainNameSubStringLessThan2Characters() {
        return domainNameSubStringLessThan2Characters;
    }

    public void setDomainNameSubStringLessThan2Characters(String domainNameSubStringLessThan2Characters) {
        this.domainNameSubStringLessThan2Characters = domainNameSubStringLessThan2Characters;
    }

    public String getDomainNameSubStringLessThan2CharactersErrorText() {
        return domainNameSubStringLessThan2CharactersErrorText;
    }

    public void setDomainNameSubStringLessThan2CharactersErrorText(String domainNameSubStringLessThan2CharactersErrorText) {
        this.domainNameSubStringLessThan2CharactersErrorText = domainNameSubStringLessThan2CharactersErrorText;
    }

    public String getDomainNameSubStringExceeds50Characters() {
        return domainNameSubStringExceeds50Characters;
    }

    public void setDomainNameSubStringExceeds50Characters(String domainNameSubStringExceeds50Characters) {
        this.domainNameSubStringExceeds50Characters = domainNameSubStringExceeds50Characters;
    }

    public String getDomainNameSubStringExceeds50CharactersErrorText() {
        return domainNameSubStringExceeds50CharactersErrorText;
    }

    public void setDomainNameSubStringExceeds50CharactersErrorText(String domainNameSubStringExceeds50CharactersErrorText) {
        this.domainNameSubStringExceeds50CharactersErrorText = domainNameSubStringExceeds50CharactersErrorText;
    }

    public String getDomainNameSubStringNoSpecialCharactersAllowed() {
        return domainNameSubStringNoSpecialCharactersAllowed;
    }

    public void setDomainNameSubStringNoSpecialCharactersAllowed(String domainNameSubStringNoSpecialCharactersAllowed) {
        this.domainNameSubStringNoSpecialCharactersAllowed = domainNameSubStringNoSpecialCharactersAllowed;
    }

    public String getDomainNameSubStringNoSpecialCharactersAllowedErrorText() {
        return domainNameSubStringNoSpecialCharactersAllowedErrorText;
    }

    public void setDomainNameSubStringNoSpecialCharactersAllowedErrorText(String domainNameSubStringNoSpecialCharactersAllowedErrorText) {
        this.domainNameSubStringNoSpecialCharactersAllowedErrorText = domainNameSubStringNoSpecialCharactersAllowedErrorText;
    }

    public String getEmptySourceIpAddress() {
        return emptySourceIpAddress;
    }

    public void setEmptySourceIpAddress(String emptySourceIpAddress) {
        this.emptySourceIpAddress = emptySourceIpAddress;
    }

    public String getInvalidSourceIpAddress() {
        return invalidSourceIpAddress;
    }

    public void setInvalidSourceIpAddress(String invalidSourceIpAddress) {
        this.invalidSourceIpAddress = invalidSourceIpAddress;
    }

    public String getDuplicateSourceIPAddress() {
        return duplicateSourceIPAddress;
    }

    public void setDuplicateSourceIPAddress(String duplicateSourceIPAddress) {
        this.duplicateSourceIPAddress = duplicateSourceIPAddress;
    }

    public String getSourceIPAddressLimitExceed() {
        return sourceIPAddressLimitExceed;
    }

    public void setSourceIPAddressLimitExceed(String sourceIPAddressLimitExceed) {
        this.sourceIPAddressLimitExceed = sourceIPAddressLimitExceed;
    }

    public String getEmptySourceIpAddressErrorText() {
        return emptySourceIpAddressErrorText;
    }

    public void setEmptySourceIpAddressErrorText(String emptySourceIpAddressErrorText) {
        this.emptySourceIpAddressErrorText = emptySourceIpAddressErrorText;
    }

    public String getInvalidSourceIpAddressErrorText() {
        return invalidSourceIpAddressErrorText;
    }

    public void setInvalidSourceIpAddressErrorText(String invalidSourceIpAddressErrorText) {
        this.invalidSourceIpAddressErrorText = invalidSourceIpAddressErrorText;
    }

    public String getDuplicateSourceIPAddressErrorText() {
        return duplicateSourceIPAddressErrorText;
    }

    public void setDuplicateSourceIPAddressErrorText(String duplicateSourceIPAddressErrorText) {
        this.duplicateSourceIPAddressErrorText = duplicateSourceIPAddressErrorText;
    }

    public String getSourceIPAddressLimitExceedGreyedOutText() {
        return sourceIPAddressLimitExceedGreyedOutText;
    }

    public void setSourceIPAddressLimitExceedGreyedOutText(String sourceIPAddressLimitExceedGreyedOutText) {
        this.sourceIPAddressLimitExceedGreyedOutText = sourceIPAddressLimitExceedGreyedOutText;
    }

    public String getNoLoginMechanismErrorText() {
        return noLoginMechanismErrorText;
    }

    public void setNoLoginMechanismErrorText(String noLoginMechanismErrorText) {
        this.noLoginMechanismErrorText = noLoginMechanismErrorText;
    }

    public String getNoProductSelectedErrorText() {
        return noProductSelectedErrorText;
    }

    public void setNoProductSelectedErrorText(String noProductSelectedErrorText) {
        this.noProductSelectedErrorText = noProductSelectedErrorText;
    }

    public String getEmptySAMLCertificate() {
        return emptySAMLCertificate;
    }

    public void setEmptySAMLCertificate(String emptySAMLCertificate) {
        this.emptySAMLCertificate = emptySAMLCertificate;
    }

    public String getEmptySAMLCertificateErrorText() {
        return emptySAMLCertificateErrorText;
    }

    public void setEmptySAMLCertificateErrorText(String emptySAMLCertificateErrorText) {
        this.emptySAMLCertificateErrorText = emptySAMLCertificateErrorText;
    }

    public String getEmptyCredentialsLoginId() {
        return emptyCredentialsLoginId;
    }

    public void setEmptyCredentialsLoginId(String emptyCredentialsLoginId) {
        this.emptyCredentialsLoginId = emptyCredentialsLoginId;
    }

    public String getEmptyCredentialsLoginIdErrorText() {
        return emptyCredentialsLoginIdErrorText;
    }

    public void setEmptyCredentialsLoginIdErrorText(String emptyCredentialsLoginIdErrorText) {
        this.emptyCredentialsLoginIdErrorText = emptyCredentialsLoginIdErrorText;
    }

    public String getCredentialsLoginIdInvalid() {
        return credentialsLoginIdInvalid;
    }

    public void setCredentialsLoginIdInvalid(String credentialsLoginIdInvalid) {
        this.credentialsLoginIdInvalid = credentialsLoginIdInvalid;
    }

    public String getCredentialsLoginIdInvalidErrorText() {
        return credentialsLoginIdInvalidErrorText;
    }

    public void setCredentialsLoginIdInvalidErrorText(String credentialsLoginIdInvalidErrorText) {
        this.credentialsLoginIdInvalidErrorText = credentialsLoginIdInvalidErrorText;
    }

    public String getCredentialsLoginIdLessThan6Characters() {
        return credentialsLoginIdLessThan6Characters;
    }

    public void setCredentialsLoginIdLessThan6Characters(String credentialsLoginIdLessThan6Characters) {
        this.credentialsLoginIdLessThan6Characters = credentialsLoginIdLessThan6Characters;
    }

    public String getCredentialsLoginIdLessThan6CharactersErrorText() {
        return credentialsLoginIdLessThan6CharactersErrorText;
    }

    public void setCredentialsLoginIdLessThan6CharactersErrorText(String credentialsLoginIdLessThan6CharactersErrorText) {
        this.credentialsLoginIdLessThan6CharactersErrorText = credentialsLoginIdLessThan6CharactersErrorText;
    }

    public String getCredentialsLoginIdExceeds50Characters() {
        return credentialsLoginIdExceeds50Characters;
    }

    public void setCredentialsLoginIdExceeds50Characters(String credentialsLoginIdExceeds50Characters) {
        this.credentialsLoginIdExceeds50Characters = credentialsLoginIdExceeds50Characters;
    }

    public String getCredentialsLoginIdExceeds50CharactersErrorText() {
        return credentialsLoginIdExceeds50CharactersErrorText;
    }

    public void setCredentialsLoginIdExceeds50CharactersErrorText(String credentialsLoginIdExceeds50CharactersErrorText) {
        this.credentialsLoginIdExceeds50CharactersErrorText = credentialsLoginIdExceeds50CharactersErrorText;
    }

    public String getCredentialsLoginIdNoSpecialCharactersAllowed() {
        return credentialsLoginIdNoSpecialCharactersAllowed;
    }

    public void setCredentialsLoginIdNoSpecialCharactersAllowed(String credentialsLoginIdNoSpecialCharactersAllowed) {
        this.credentialsLoginIdNoSpecialCharactersAllowed = credentialsLoginIdNoSpecialCharactersAllowed;
    }

    public String getCredentialsLoginIdNoSpecialCharactersAllowedErrorText() {
        return credentialsLoginIdNoSpecialCharactersAllowedErrorText;
    }

    public void setCredentialsLoginIdNoSpecialCharactersAllowedErrorText(String credentialsLoginIdNoSpecialCharactersAllowedErrorText) {
        this.credentialsLoginIdNoSpecialCharactersAllowedErrorText = credentialsLoginIdNoSpecialCharactersAllowedErrorText;
    }

    public String getEmptySAMLIssuerID() {
        return emptySAMLIssuerID;
    }

    public void setEmptySAMLIssuerID(String emptySAMLIssuerID) {
        this.emptySAMLIssuerID = emptySAMLIssuerID;
    }

    public String getEmptySAMLIssuerIDErrorText() {
        return emptySAMLIssuerIDErrorText;
    }

    public void setEmptySAMLIssuerIDErrorText(String emptySAMLIssuerIDErrorText) {
        this.emptySAMLIssuerIDErrorText = emptySAMLIssuerIDErrorText;
    }

    public String getSamlIssuerIdExceeds50Characters() {
        return samlIssuerIdExceeds50Characters;
    }

    public void setSamlIssuerIdExceeds50Characters(String samlIssuerIdExceeds50Characters) {
        this.samlIssuerIdExceeds50Characters = samlIssuerIdExceeds50Characters;
    }

    public String getSamlIssuerIdExceeds50CharactersErrorText() {
        return samlIssuerIdExceeds50CharactersErrorText;
    }

    public void setSamlIssuerIdExceeds50CharactersErrorText(String samlIssuerIdExceeds50CharactersErrorText) {
        this.samlIssuerIdExceeds50CharactersErrorText = samlIssuerIdExceeds50CharactersErrorText;
    }

    public String getEmptySSOPostSource() {
        return emptySSOPostSource;
    }

    public void setEmptySSOPostSource(String emptySSOPostSource) {
        this.emptySSOPostSource = emptySSOPostSource;
    }

    public String getEmptySSOPostSourceErrorText() {
        return emptySSOPostSourceErrorText;
    }

    public void setEmptySSOPostSourceErrorText(String emptySSOPostSourceErrorText) {
        this.emptySSOPostSourceErrorText = emptySSOPostSourceErrorText;
    }

    public String getSsoPostSourceExceeds50Characters() {
        return ssoPostSourceExceeds50Characters;
    }

    public void setSsoPostSourceExceeds50Characters(String ssoPostSourceExceeds50Characters) {
        this.ssoPostSourceExceeds50Characters = ssoPostSourceExceeds50Characters;
    }

    public String getSsoPostSourceExceeds50CharactersErrorText() {
        return ssoPostSourceExceeds50CharactersErrorText;
    }

    public void setSsoPostSourceExceeds50CharactersErrorText(String ssoPostSourceExceeds50CharactersErrorText) {
        this.ssoPostSourceExceeds50CharactersErrorText = ssoPostSourceExceeds50CharactersErrorText;
    }

    public String getEmptySSOKeepLiveReferralURL() {
        return emptySSOKeepLiveReferralURL;
    }

    public void setEmptySSOKeepLiveReferralURL(String emptySSOKeepLiveReferralURL) {
        this.emptySSOKeepLiveReferralURL = emptySSOKeepLiveReferralURL;
    }

    public String getEmptySSOKeepLiveReferralURLErrorText() {
        return emptySSOKeepLiveReferralURLErrorText;
    }

    public void setEmptySSOKeepLiveReferralURLErrorText(String emptySSOKeepLiveReferralURLErrorText) {
        this.emptySSOKeepLiveReferralURLErrorText = emptySSOKeepLiveReferralURLErrorText;
    }

    public String getSsoKeepLiveReferralURLExceeds100Characters() {
        return ssoKeepLiveReferralURLExceeds100Characters;
    }

    public void setSsoKeepLiveReferralURLExceeds100Characters(String ssoKeepLiveReferralURLExceeds100Characters) {
        this.ssoKeepLiveReferralURLExceeds100Characters = ssoKeepLiveReferralURLExceeds100Characters;
    }

    public String getSsoKeepLiveReferralURLExceeds100CharactersErrorText() {
        return ssoKeepLiveReferralURLExceeds100CharactersErrorText;
    }

    public void setSsoKeepLiveReferralURLExceeds100CharactersErrorText(String ssoKeepLiveReferralURLExceeds100CharactersErrorText) {
        this.ssoKeepLiveReferralURLExceeds100CharactersErrorText = ssoKeepLiveReferralURLExceeds100CharactersErrorText;
    }

    public String getSsoKeepLiveReferralURLNotValid() {
        return ssoKeepLiveReferralURLNotValid;
    }

    public void setSsoKeepLiveReferralURLNotValid(String ssoKeepLiveReferralURLNotValid) {
        this.ssoKeepLiveReferralURLNotValid = ssoKeepLiveReferralURLNotValid;
    }

    public String getSsoKeepLiveReferralURLNotValidErrorText() {
        return ssoKeepLiveReferralURLNotValidErrorText;
    }

    public void setSsoKeepLiveReferralURLNotValidErrorText(String ssoKeepLiveReferralURLNotValidErrorText) {
        this.ssoKeepLiveReferralURLNotValidErrorText = ssoKeepLiveReferralURLNotValidErrorText;
    }

    public String getEmptyJWTMaxExpTime() {
        return emptyJWTMaxExpTime;
    }

    public void setEmptyJWTMaxExpTime(String emptyJWTMaxExpTime) {
        this.emptyJWTMaxExpTime = emptyJWTMaxExpTime;
    }

    public String getEmptyJWTMaxExpTimeErrorText() {
        return emptyJWTMaxExpTimeErrorText;
    }

    public void setEmptyJWTMaxExpTimeErrorText(String emptyJWTMaxExpTimeErrorText) {
        this.emptyJWTMaxExpTimeErrorText = emptyJWTMaxExpTimeErrorText;
    }

    public String getJwtMaxExpTimeMoreThan1800() {
        return jwtMaxExpTimeMoreThan1800;
    }

    public void setJwtMaxExpTimeMoreThan1800(String jwtMaxExpTimeMoreThan1800) {
        this.jwtMaxExpTimeMoreThan1800 = jwtMaxExpTimeMoreThan1800;
    }

    public String getJwtMaxExpTimeMoreThan1800ErrorText() {
        return jwtMaxExpTimeMoreThan1800ErrorText;
    }

    public void setJwtMaxExpTimeMoreThan1800ErrorText(String jwtMaxExpTimeMoreThan1800ErrorText) {
        this.jwtMaxExpTimeMoreThan1800ErrorText = jwtMaxExpTimeMoreThan1800ErrorText;
    }

    public String getJwtMaxExpTimeAlphaNumericCharacter() {
        return jwtMaxExpTimeAlphaNumericCharacter;
    }

    public void setJwtMaxExpTimeAlphaNumericCharacter(String jwtMaxExpTimeAlphaNumericCharacter) {
        this.jwtMaxExpTimeAlphaNumericCharacter = jwtMaxExpTimeAlphaNumericCharacter;
    }

    public String getJwtMaxExpTimeAlphaNumericCharacterErrorText() {
        return jwtMaxExpTimeAlphaNumericCharacterErrorText;
    }

    public void setJwtMaxExpTimeAlphaNumericCharacterErrorText(String jwtMaxExpTimeAlphaNumericCharacterErrorText) {
        this.jwtMaxExpTimeAlphaNumericCharacterErrorText = jwtMaxExpTimeAlphaNumericCharacterErrorText;
    }

    public String getEmptyJWTRSAPublicKey() {
        return emptyJWTRSAPublicKey;
    }

    public void setEmptyJWTRSAPublicKey(String emptyJWTRSAPublicKey) {
        this.emptyJWTRSAPublicKey = emptyJWTRSAPublicKey;
    }

    public String getEmptyJWTRSAPublicKeyErrorText() {
        return emptyJWTRSAPublicKeyErrorText;
    }

    public void setEmptyJWTRSAPublicKeyErrorText(String emptyJWTRSAPublicKeyErrorText) {
        this.emptyJWTRSAPublicKeyErrorText = emptyJWTRSAPublicKeyErrorText;
    }

    public String getInvalidJWTRSAPublicKey() {
        return invalidJWTRSAPublicKey;
    }

    public void setInvalidJWTRSAPublicKey(String invalidJWTRSAPublicKey) {
        this.invalidJWTRSAPublicKey = invalidJWTRSAPublicKey;
    }

    public String getInvalidJWTRSAPublicKeyErrorText() {
        return invalidJWTRSAPublicKeyErrorText;
    }

    public void setInvalidJWTRSAPublicKeyErrorText(String invalidJWTRSAPublicKeyErrorText) {
        this.invalidJWTRSAPublicKeyErrorText = invalidJWTRSAPublicKeyErrorText;
    }

    public String getJwtRSAPublicKeyExceeds800Characters() {
        return jwtRSAPublicKeyExceeds800Characters;
    }

    public void setJwtRSAPublicKeyExceeds800Characters(String jwtRSAPublicKeyExceeds800Characters) {
        this.jwtRSAPublicKeyExceeds800Characters = jwtRSAPublicKeyExceeds800Characters;
    }

    public String getJwtRSAPublicKeyExceeds800CharactersErrorText() {
        return jwtRSAPublicKeyExceeds800CharactersErrorText;
    }

    public void setJwtRSAPublicKeyExceeds800CharactersErrorText(String jwtRSAPublicKeyExceeds800CharactersErrorText) {
        this.jwtRSAPublicKeyExceeds800CharactersErrorText = jwtRSAPublicKeyExceeds800CharactersErrorText;
    }

    public String getEmptyContactFN() {
        return emptyContactFN;
    }

    public void setEmptyContactFN(String emptyContactFN) {
        this.emptyContactFN = emptyContactFN;
    }

    public String getEmptyContactFNErrorText() {
        return emptyContactFNErrorText;
    }

    public void setEmptyContactFNErrorText(String emptyContactFNErrorText) {
        this.emptyContactFNErrorText = emptyContactFNErrorText;
    }

    public String getContactFNExceeds200Characters() {
        return contactFNExceeds200Characters;
    }

    public void setContactFNExceeds200Characters(String contactFNExceeds200Characters) {
        this.contactFNExceeds200Characters = contactFNExceeds200Characters;
    }

    public String getContactFNExceeds200CharactersErrorText() {
        return contactFNExceeds200CharactersErrorText;
    }

    public void setContactFNExceeds200CharactersErrorText(String contactFNExceeds200CharactersErrorText) {
        this.contactFNExceeds200CharactersErrorText = contactFNExceeds200CharactersErrorText;
    }

    public String getContactFNAlphaNumericCharacters() {
        return contactFNAlphaNumericCharacters;
    }

    public void setContactFNAlphaNumericCharacters(String contactFNAlphaNumericCharacters) {
        this.contactFNAlphaNumericCharacters = contactFNAlphaNumericCharacters;
    }

    public String getContactFNAlphaNumericCharactersErrorText() {
        return contactFNAlphaNumericCharactersErrorText;
    }

    public void setContactFNAlphaNumericCharactersErrorText(String contactFNAlphaNumericCharactersErrorText) {
        this.contactFNAlphaNumericCharactersErrorText = contactFNAlphaNumericCharactersErrorText;
    }

    public String getEmptyContactLN() {
        return emptyContactLN;
    }

    public void setEmptyContactLN(String emptyContactLN) {
        this.emptyContactLN = emptyContactLN;
    }

    public String getEmptyContactLNErrorText() {
        return emptyContactLNErrorText;
    }

    public void setEmptyContactLNErrorText(String emptyContactLNErrorText) {
        this.emptyContactLNErrorText = emptyContactLNErrorText;
    }

    public String getContactLNExceeds200Characters() {
        return contactLNExceeds200Characters;
    }

    public void setContactLNExceeds200Characters(String contactLNExceeds200Characters) {
        this.contactLNExceeds200Characters = contactLNExceeds200Characters;
    }

    public String getContactLNExceeds200CharactersErrorText() {
        return contactLNExceeds200CharactersErrorText;
    }

    public void setContactLNExceeds200CharactersErrorText(String contactLNExceeds200CharactersErrorText) {
        this.contactLNExceeds200CharactersErrorText = contactLNExceeds200CharactersErrorText;
    }

    public String getContactLNAlphaNumericCharacters() {
        return contactLNAlphaNumericCharacters;
    }

    public void setContactLNAlphaNumericCharacters(String contactLNAlphaNumericCharacters) {
        this.contactLNAlphaNumericCharacters = contactLNAlphaNumericCharacters;
    }

    public String getContactLNAlphaNumericCharactersErrorText() {
        return contactLNAlphaNumericCharactersErrorText;
    }

    public void setContactLNAlphaNumericCharactersErrorText(String contactLNAlphaNumericCharactersErrorText) {
        this.contactLNAlphaNumericCharactersErrorText = contactLNAlphaNumericCharactersErrorText;
    }

    public String getEmptyContactNum() {
        return emptyContactNum;
    }

    public void setEmptyContactNum(String emptyContactNum) {
        this.emptyContactNum = emptyContactNum;
    }

    public String getEmptyContactNumErrorText() {
        return emptyContactNumErrorText;
    }

    public void setEmptyContactNumErrorText(String emptyContactNumErrorText) {
        this.emptyContactNumErrorText = emptyContactNumErrorText;
    }

    public String getContactNumberExceeds10Characters() {
        return contactNumberExceeds10Characters;
    }

    public void setContactNumberExceeds10Characters(String contactNumberExceeds10Characters) {
        this.contactNumberExceeds10Characters = contactNumberExceeds10Characters;
    }

    public String getContactNumberExceeds10CharactersErrorText() {
        return contactNumberExceeds10CharactersErrorText;
    }

    public void setContactNumberExceeds10CharactersErrorText(String contactNumberExceeds10CharactersErrorText) {
        this.contactNumberExceeds10CharactersErrorText = contactNumberExceeds10CharactersErrorText;
    }

    public String getContactNumberAlphaNumeric() {
        return contactNumberAlphaNumeric;
    }

    public void setContactNumberAlphaNumeric(String contactNumberAlphaNumeric) {
        this.contactNumberAlphaNumeric = contactNumberAlphaNumeric;
    }

    public String getContactNumberAlphaNumericErrorText() {
        return contactNumberAlphaNumericErrorText;
    }

    public void setContactNumberAlphaNumericErrorText(String contactNumberAlphaNumericErrorText) {
        this.contactNumberAlphaNumericErrorText = contactNumberAlphaNumericErrorText;
    }

    public String getEmptyContactEmailId() {
        return emptyContactEmailId;
    }

    public void setEmptyContactEmailId(String emptyContactEmailId) {
        this.emptyContactEmailId = emptyContactEmailId;
    }

    public String getEmptyContactEmailIdErrorText() {
        return emptyContactEmailIdErrorText;
    }

    public void setEmptyContactEmailIdErrorText(String emptyContactEmailIdErrorText) {
        this.emptyContactEmailIdErrorText = emptyContactEmailIdErrorText;
    }

    public String getInvalidContactEmail() {
        return invalidContactEmail;
    }

    public void setInvalidContactEmail(String invalidContactEmail) {
        this.invalidContactEmail = invalidContactEmail;
    }

    public String getInvalidContactEmailErrorText() {
        return invalidContactEmailErrorText;
    }

    public void setInvalidContactEmailErrorText(String invalidContactEmailErrorText) {
        this.invalidContactEmailErrorText = invalidContactEmailErrorText;
    }

    public String getContactEmailExceeds50Characters() {
        return contactEmailExceeds50Characters;
    }

    public void setContactEmailExceeds50Characters(String contactEmailExceeds50Characters) {
        this.contactEmailExceeds50Characters = contactEmailExceeds50Characters;
    }

    public String getContactEmailExceeds50CharactersErrorText() {
        return contactEmailExceeds50CharactersErrorText;
    }

    public void setContactEmailExceeds50CharactersErrorText(String contactEmailExceeds50CharactersErrorText) {
        this.contactEmailExceeds50CharactersErrorText = contactEmailExceeds50CharactersErrorText;
    }

    public String getEmptyContactUserId() {
        return emptyContactUserId;
    }

    public void setEmptyContactUserId(String emptyContactUserId) {
        this.emptyContactUserId = emptyContactUserId;
    }

    public String getEmptyContactUserIdErrorText() {
        return emptyContactUserIdErrorText;
    }

    public void setEmptyContactUserIdErrorText(String emptyContactUserIdErrorText) {
        this.emptyContactUserIdErrorText = emptyContactUserIdErrorText;
    }

    public String getContactUserIdLessThan6CharactersErrorText() {
        return contactUserIdLessThan6CharactersErrorText;
    }

    public void setContactUserIdLessThan6CharactersErrorText(String contactUserIdLessThan6CharactersErrorText) {
        this.contactUserIdLessThan6CharactersErrorText = contactUserIdLessThan6CharactersErrorText;
    }

    public String getContactUserIdLessThan6Characters() {
        return contactUserIdLessThan6Characters;
    }

    public void setContactUserIdLessThan6Characters(String contactUserIdLessThan6Characters) {
        this.contactUserIdLessThan6Characters = contactUserIdLessThan6Characters;
    }

    public String getContactUserIdExceeds50Characters() {
        return contactUserIdExceeds50Characters;
    }

    public void setContactUserIdExceeds50Characters(String contactUserIdExceeds50Characters) {
        this.contactUserIdExceeds50Characters = contactUserIdExceeds50Characters;
    }

    public String getContactUserIdExceeds50CharactersErrorText() {
        return contactUserIdExceeds50CharactersErrorText;
    }

    public void setContactUserIdExceeds50CharactersErrorText(String contactUserIdExceeds50CharactersErrorText) {
        this.contactUserIdExceeds50CharactersErrorText = contactUserIdExceeds50CharactersErrorText;
    }

    public String getContactUserIdNoSpecialCharactersAllowed() {
        return contactUserIdNoSpecialCharactersAllowed;
    }

    public void setContactUserIdNoSpecialCharactersAllowed(String contactUserIdNoSpecialCharactersAllowed) {
        this.contactUserIdNoSpecialCharactersAllowed = contactUserIdNoSpecialCharactersAllowed;
    }

    public String getContactUserIdNoSpecialCharactersAllowedErrorText() {
        return contactUserIdNoSpecialCharactersAllowedErrorText;
    }

    public void setContactUserIdNoSpecialCharactersAllowedErrorText(String contactUserIdNoSpecialCharactersAllowedErrorText) {
        this.contactUserIdNoSpecialCharactersAllowedErrorText = contactUserIdNoSpecialCharactersAllowedErrorText;
    }

    public boolean isAddMultipleContact() {
        return addMultipleContact;
    }

    public void setAddMultipleContact(boolean addMultipleContact) {
        this.addMultipleContact = addMultipleContact;
    }

    public boolean isAddAnotherContact() {
        return addAnotherContact;
    }

    public void setAddAnotherContact(boolean addAnotherContact) {
        this.addAnotherContact = addAnotherContact;
    }

    public boolean isAddMultipleJWT() {
        return addMultipleJWT;
    }

    public void setAddMultipleJWT(boolean addMultipleJWT) {
        this.addMultipleJWT = addMultipleJWT;
    }

    public boolean isAddAnotherJWT() {
        return addAnotherJWT;
    }

    public void setAddAnotherJWT(boolean addAnotherJWT) {
        this.addAnotherJWT = addAnotherJWT;
    }

    public boolean isEditSAMLDetails() {
        return editSAMLDetails;
    }

    public void setEditSAMLDetails(boolean editSAMLDetails) {
        this.editSAMLDetails = editSAMLDetails;
    }

    public String getDagSiteName() {
        return dagSiteName;
    }

    public void setDagSiteName(String dagSiteName) {
        this.dagSiteName = dagSiteName;
    }

    public String getDagUsername() {
        return dagUsername;
    }

    public void setDagUsername(String dagUsername) {
        this.dagUsername = dagUsername;
    }

    public String getDagPassword() {
        return dagPassword;
    }

    public void setDagPassword(String dagPassword) {
        this.dagPassword = dagPassword;
    }

    public String getToBeRegisteredUserName() {
        return toBeRegisteredUserName;
    }

    public void setToBeRegisteredUserName(String toBeRegisteredUserName) {
        this.toBeRegisteredUserName = toBeRegisteredUserName;
    }

    public String getToBeRegisteredUserPassword() {
        return toBeRegisteredUserPassword;
    }

    public void setToBeRegisteredUserPassword(String toBeRegisteredUserPassword) {
        this.toBeRegisteredUserPassword = toBeRegisteredUserPassword;
    }
}