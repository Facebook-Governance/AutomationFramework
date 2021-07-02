/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.Util.pageprocessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Config {

    private PagesBean pagesBean;

    private Config() {

        processPage();
    }

    public void processPage() {

        String fullPath = "Pages" + File.separator + "Pages.json";

        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        InputStream in = Config.class.getClassLoader().getResourceAsStream(fullPath);

        try {
            setPagesBean(mapper.readValue(in, PagesBean.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class SingleTonHelper {

        private static final Config INSTANCE = new Config();
    }

    public static Config getInstance() {

        return SingleTonHelper.INSTANCE;
    }

    public PagesBean getPagesBean() {

        PagesBean p = new PagesBean();
        p = pagesBean;
        return p;
    }

    private void setPagesBean(PagesBean pagesBean) {
        this.pagesBean = pagesBean;
    }
}

