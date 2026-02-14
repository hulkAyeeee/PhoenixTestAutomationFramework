package com.dataproviders;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import com.api.utils.CSVReaderUtil;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {

	@DataProvider(name="LoginAPIDataProvider", parallel = true )
	public static Iterator<UserBean> loginAPIDataProvider() {
		
		Iterator <UserBean> userBean=CSVReaderUtil.loadCSV("testData/LoginCreds.csv");
		return userBean;
	}

	//Data provider needs to return something which means we can not use void as the return-type. It returns data in 3 formats
	// [] --> Single Dimensional array
	// [] [] --> Two Dimensional array
	// Iterator<>
	
}
