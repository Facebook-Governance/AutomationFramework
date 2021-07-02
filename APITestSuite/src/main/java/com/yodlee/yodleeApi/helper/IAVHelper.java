/*******************************************************************************
 *
 * * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.yodleeApi.helper;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.database.DbQueries;



public class IAVHelper {
	public static Map <Long,List<String>> iavDataSetDetails=new HashMap<Long,List<String>>();

	public static void getIAVSiteDetails() {
		if (Configuration.getInstance().isDbReadable()) {
			iavDataSetDetails = getIAVSiteDatasetDetails();
			System.out.println("site count is " + iavDataSetDetails.size());
		}
	}
	

	public boolean checkIncludeDataSet(String providerId, String includeDataSet)
	{
		
        getIAVSiteDetails();
        String[] dataSets = includeDataSet.split(",");
        System.out.println("Map is :"+iavDataSetDetails);
        boolean flag=true;
        List<String> details=new ArrayList<>();
        
        System.out.println("Length of dataset :"+dataSets.length);
        if(dataSets.length>0 && dataSets[0]!="")
        {
        	for(int count=0; count<dataSets.length; count++)
            {
            	
            	if(iavDataSetDetails.containsKey(Long.parseLong(providerId)))
            	{
            		
            		details=iavDataSetDetails.get(Long.parseLong(providerId));
            		
            		if(!details.contains(dataSets[count].split("\\.")[1]))
            		{
            			
            			flag=false;
            			break;
            		}
            		
                }
            	else
            	{
            	
            		flag=false;
            		break;
            	}
    	
            }
        }
		return flag;

	}
	
	private static Map <Long,List<String>>  getIAVSiteDatasetDetails()
	{

		DBHelper dbHelper=new DBHelper();
		Connection conn=dbHelper.getDBConnection();
		ResultSet rs =null;
		long sumInfoParamKeyId = 0;
		long siteId = 0;
		String query = DbQueries.siteSupportsIAVDataset;
		System.out.println("Query::" + query);
		try {
			rs = dbHelper.getResultSet(conn,query);
			
			while (rs != null && rs.next()) {
				sumInfoParamKeyId = rs.getLong("SUM_INFO_PARAM_KEY_ID");
				String dataSet = "";
				if (sumInfoParamKeyId == 33)
					dataSet = "BANK_TRANSFER_CODE";
				else if (sumInfoParamKeyId == 44)
					dataSet = "HOLDER_NAME";
				else if (sumInfoParamKeyId == 45)
					dataSet = "FULL_ACCT_NUMBER";
				siteId = rs.getLong("SITE_ID");
				List<String> dataSetDetails;

				if (iavDataSetDetails.containsKey(siteId)) {
					dataSetDetails = iavDataSetDetails.get(siteId);

				} else {
					dataSetDetails = new ArrayList<String>();

				}
				dataSetDetails.add(dataSet);
				iavDataSetDetails.put(siteId, dataSetDetails);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, null, rs);
		}
		return iavDataSetDetails;

	}
}
