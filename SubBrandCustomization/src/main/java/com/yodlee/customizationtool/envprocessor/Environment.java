/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.envprocessor;

public class Environment {

    private String baseUrl;
    private String listSubbrandUrl;
    private String oltpDevUrl;
    private String oltpDevUsername;
    private String oltpDevPassword;
    private String yccDBDevUrl;
    private String yccDBDevUsername;
    private String yccDBDevPassword;
    private String authDBDevUrl;
    private String authDBDevUsername;
    private String authDBDevPassword;
    private String configDBDevUrl;
    private String configDBDevUsername;
    private String configDBDevPassword;
    private String oltpDBProdUrl;
    private String oltpDBProdUsername;
    private String oltpDBProdPassword;
    private String yccDBProdUrl;
    private String yccDBProdUsername;
    private String yccDBProdPassword;
    private String authDBProdUrl;
    private String authDBProdUsername;
    private String authDBProdPassword;
    private String configDBProdUrl;
    private String configDBProdUsername;
    private String configDBProdPassword;
    private String sdpServerName;
    private String sdpServerPort;
    private String sdpServiceName;
    private String sdpDBUsername;
    private String sdpDBPassword;
    private String samlIssuer;
    private String samlSubjectNameUserId;
    private String samlNodeURL;
    private String samlFinAppID;
    private String samlExtraParams;
    private String samlRedirectRequest;
    private String samlAttributes;
    private String samlVersion;
    private String samlAssertionEncryption;
    private String samlMultipleKeys;
    private String samlAttributeEncryption;
    private String samlAttributeEncryptionMechanism;
    private String samlAttributeEncoding;
    private String samlSignResponse;
    private String samlSignAssertion;
    private String samlSigningAliasKey;
    private String samlSSOAttributeKey;
    private String samlLinkIntegrityToken;
    private String isPrivateNonSSO;
    private String isPublicNonSSO;
    private String restURLEncoding;
    private String restVersion;
    private String restBaseURIPrivate;
    private String restBaseURIPublic;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getListSubbrandUrl() {
        return listSubbrandUrl;
    }

    public void setListSubbrandUrl(String listSubbrandUrl) {
        this.listSubbrandUrl = listSubbrandUrl;
    }

    public String getOltpDevUrl() {
        return oltpDevUrl;
    }

    public void setOltpDevUrl(String oltpDevUrl) {
        this.oltpDevUrl = oltpDevUrl;
    }

    public String getOltpDevUsername() {
        return oltpDevUsername;
    }

    public void setOltpDevUsername(String oltpDevUsername) {
        this.oltpDevUsername = oltpDevUsername;
    }

    public String getOltpDevPassword() {
        return oltpDevPassword;
    }

    public void setOltpDevPassword(String oltpDevPassword) {
        this.oltpDevPassword = oltpDevPassword;
    }

    public String getYccDBDevUrl() {
        return yccDBDevUrl;
    }

    public void setYccDBDevUrl(String yccDBDevUrl) {
        this.yccDBDevUrl = yccDBDevUrl;
    }

    public String getYccDBDevUsername() {
        return yccDBDevUsername;
    }

    public void setYccDBDevUsername(String yccDBDevUsername) {
        this.yccDBDevUsername = yccDBDevUsername;
    }

    public String getYccDBDevPassword() {
        return yccDBDevPassword;
    }

    public void setYccDBDevPassword(String yccDBDevPassword) {
        this.yccDBDevPassword = yccDBDevPassword;
    }

    public String getAuthDBDevUrl() {
        return authDBDevUrl;
    }

    public void setAuthDBDevUrl(String authDBDevUrl) {
        this.authDBDevUrl = authDBDevUrl;
    }

    public String getAuthDBDevUsername() {
        return authDBDevUsername;
    }

    public void setAuthDBDevUsername(String authDBDevUsername) {
        this.authDBDevUsername = authDBDevUsername;
    }

    public String getAuthDBDevPassword() {
        return authDBDevPassword;
    }

    public void setAuthDBDevPassword(String authDBDevPassword) {
        this.authDBDevPassword = authDBDevPassword;
    }

    public String getConfigDBDevUrl() {
        return configDBDevUrl;
    }

    public void setConfigDBDevUrl(String configDBDevUrl) {
        this.configDBDevUrl = configDBDevUrl;
    }

    public String getConfigDBDevUsername() {
        return configDBDevUsername;
    }

    public void setConfigDBDevUsername(String configDBDevUsername) {
        this.configDBDevUsername = configDBDevUsername;
    }

    public String getConfigDBDevPassword() {
        return configDBDevPassword;
    }

    public void setConfigDBDevPassword(String configDBDevPassword) {
        this.configDBDevPassword = configDBDevPassword;
    }

    public String getOltpDBProdUrl() {
        return oltpDBProdUrl;
    }

    public void setOltpDBProdUrl(String oltpDBProdUrl) {
        this.oltpDBProdUrl = oltpDBProdUrl;
    }

    public String getOltpDBProdUsername() {
        return oltpDBProdUsername;
    }

    public void setOltpDBProdUsername(String oltpDBProdUsername) {
        this.oltpDBProdUsername = oltpDBProdUsername;
    }

    public String getOltpDBProdPassword() {
        return oltpDBProdPassword;
    }

    public void setOltpDBProdPassword(String oltpDBProdPassword) {
        this.oltpDBProdPassword = oltpDBProdPassword;
    }

    public String getYccDBProdUrl() {
        return yccDBProdUrl;
    }

    public void setYccDBProdUrl(String yccDBProdUrl) {
        this.yccDBProdUrl = yccDBProdUrl;
    }

    public String getYccDBProdUsername() {
        return yccDBProdUsername;
    }

    public void setYccDBProdUsername(String yccDBProdUsername) {
        this.yccDBProdUsername = yccDBProdUsername;
    }

    public String getYccDBProdPassword() {
        return yccDBProdPassword;
    }

    public void setYccDBProdPassword(String yccDBProdPassword) {
        this.yccDBProdPassword = yccDBProdPassword;
    }

    public String getAuthDBProdUrl() {
        return authDBProdUrl;
    }

    public void setAuthDBProdUrl(String authDBProdUrl) {
        this.authDBProdUrl = authDBProdUrl;
    }

    public String getAuthDBProdUsername() {
        return authDBProdUsername;
    }

    public void setAuthDBProdUsername(String authDBProdUsername) {
        this.authDBProdUsername = authDBProdUsername;
    }

    public String getAuthDBProdPassword() {
        return authDBProdPassword;
    }

    public void setAuthDBProdPassword(String authDBProdPassword) {
        this.authDBProdPassword = authDBProdPassword;
    }

    public String getConfigDBProdUrl() {
        return configDBProdUrl;
    }

    public void setConfigDBProdUrl(String configDBProdUrl) {
        this.configDBProdUrl = configDBProdUrl;
    }

    public String getConfigDBProdUsername() {
        return configDBProdUsername;
    }

    public void setConfigDBProdUsername(String configDBProdUsername) {
        this.configDBProdUsername = configDBProdUsername;
    }

    public String getConfigDBProdPassword() {
        return configDBProdPassword;
    }

    public void setConfigDBProdPassword(String configDBProdPassword) {
        this.configDBProdPassword = configDBProdPassword;
    }

    public String getSdpServerName() {
        return sdpServerName;
    }

    public void setSdpServerName(String sdpServerName) {
        this.sdpServerName = sdpServerName;
    }

    public String getSdpServerPort() {
        return sdpServerPort;
    }

    public void setSdpServerPort(String sdpServerPort) {
        this.sdpServerPort = sdpServerPort;
    }

    public String getSdpServiceName() {
        return sdpServiceName;
    }

    public void setSdpServiceName(String sdpServiceName) {
        this.sdpServiceName = sdpServiceName;
    }

    public String getSdpDBUsername() {
        return sdpDBUsername;
    }

    public void setSdpDBUsername(String sdpDBUsername) {
        this.sdpDBUsername = sdpDBUsername;
    }

    public String getSdpDBPassword() {
        return sdpDBPassword;
    }

    public void setSdpDBPassword(String sdpDBPassword) {
        this.sdpDBPassword = sdpDBPassword;
    }

    public String getSamlIssuer() {
        return samlIssuer;
    }

    public void setSamlIssuer(String samlIssuer) {
        this.samlIssuer = samlIssuer;
    }

    public String getSamlSubjectNameUserId() {
        return samlSubjectNameUserId;
    }

    public void setSamlSubjectNameUserId(String samlSubjectNameUserId) {
        this.samlSubjectNameUserId = samlSubjectNameUserId;
    }

    public String getSamlNodeURL() {
        return samlNodeURL;
    }

    public void setSamlNodeURL(String samlNodeURL) {
        this.samlNodeURL = samlNodeURL;
    }

    public String getSamlFinAppID() {
        return samlFinAppID;
    }

    public void setSamlFinAppID(String samlFinAppID) {
        this.samlFinAppID = samlFinAppID;
    }

    public String getSamlExtraParams() {
        return samlExtraParams;
    }

    public void setSamlExtraParams(String samlExtraParams) {
        this.samlExtraParams = samlExtraParams;
    }

    public String getSamlRedirectRequest() {
        return samlRedirectRequest;
    }

    public void setSamlRedirectRequest(String samlRedirectRequest) {
        this.samlRedirectRequest = samlRedirectRequest;
    }

    public String getSamlAttributes() {
        return samlAttributes;
    }

    public void setSamlAttributes(String samlAttributes) {
        this.samlAttributes = samlAttributes;
    }

    public String getSamlVersion() {
        return samlVersion;
    }

    public void setSamlVersion(String samlVersion) {
        this.samlVersion = samlVersion;
    }

    public String getSamlAssertionEncryption() {
        return samlAssertionEncryption;
    }

    public void setSamlAssertionEncryption(String samlAssertionEncryption) {
        this.samlAssertionEncryption = samlAssertionEncryption;
    }

    public String getSamlMultipleKeys() {
        return samlMultipleKeys;
    }

    public void setSamlMultipleKeys(String samlMultipleKeys) {
        this.samlMultipleKeys = samlMultipleKeys;
    }

    public String getSamlAttributeEncryption() {
        return samlAttributeEncryption;
    }

    public void setSamlAttributeEncryption(String samlAttributeEncryption) {
        this.samlAttributeEncryption = samlAttributeEncryption;
    }

    public String getSamlAttributeEncryptionMechanism() {
        return samlAttributeEncryptionMechanism;
    }

    public void setSamlAttributeEncryptionMechanism(String samlAttributeEncryptionMechanism) {
        this.samlAttributeEncryptionMechanism = samlAttributeEncryptionMechanism;
    }

    public String getSamlAttributeEncoding() {
        return samlAttributeEncoding;
    }

    public void setSamlAttributeEncoding(String samlAttributeEncoding) {
        this.samlAttributeEncoding = samlAttributeEncoding;
    }

    public String getSamlSignResponse() {
        return samlSignResponse;
    }

    public void setSamlSignResponse(String samlSignResponse) {
        this.samlSignResponse = samlSignResponse;
    }

    public String getSamlSignAssertion() {
        return samlSignAssertion;
    }

    public void setSamlSignAssertion(String samlSignAssertion) {
        this.samlSignAssertion = samlSignAssertion;
    }

    public String getSamlSigningAliasKey() {
        return samlSigningAliasKey;
    }

    public void setSamlSigningAliasKey(String samlSigningAliasKey) {
        this.samlSigningAliasKey = samlSigningAliasKey;
    }

    public String getSamlSSOAttributeKey() {
        return samlSSOAttributeKey;
    }

    public void setSamlSSOAttributeKey(String samlSSOAttributeKey) {
        this.samlSSOAttributeKey = samlSSOAttributeKey;
    }

    public String getSamlLinkIntegrityToken() {
        return samlLinkIntegrityToken;
    }

    public void setSamlLinkIntegrityToken(String samlLinkIntegrityToken) {
        this.samlLinkIntegrityToken = samlLinkIntegrityToken;
    }

    public String getIsPrivateNonSSO() {
        return isPrivateNonSSO;
    }

    public void setIsPrivateNonSSO(String isPrivateNonSSO) {
        this.isPrivateNonSSO = isPrivateNonSSO;
    }

    public String getIsPublicNonSSO() {
        return isPublicNonSSO;
    }

    public void setIsPublicNonSSO(String isPublicNonSSO) {
        this.isPublicNonSSO = isPublicNonSSO;
    }

    public String getRestURLEncoding() {
        return restURLEncoding;
    }

    public void setRestURLEncoding(String restURLEncoding) {
        this.restURLEncoding = restURLEncoding;
    }

    public String getRestVersion() {
        return restVersion;
    }

    public void setRestVersion(String restVersion) {
        this.restVersion = restVersion;
    }

    public String getRestBaseURIPrivate() {
        return restBaseURIPrivate;
    }

    public void setRestBaseURIPrivate(String restBaseURIPrivate) {
        this.restBaseURIPrivate = restBaseURIPrivate;
    }

    public String getRestBaseURIPublic() {
        return restBaseURIPublic;
    }

    public void setRestBaseURIPublic(String restBaseURIPublic) {
        this.restBaseURIPublic = restBaseURIPublic;
    }

}
