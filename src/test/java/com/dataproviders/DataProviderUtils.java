package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.FakerDataGenerator;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {

	@DataProvider(name="LoginAPIDataProvider", parallel = true )
	public static Iterator<UserBean> loginAPIDataProvider() {
		return CSVReaderUtil.loadCSV("testData/LoginCreds.csv", UserBean.class);
		
	}

	//Data provider needs to return something which means we can not use void as the return-type. It returns data in 3 formats
	// [] --> Single Dimensional array
	// [] [] --> Two Dimensional array
	// Iterator<>
	
	@DataProvider(name="CreateJobAPIDataProvider", parallel = true )
	public static Iterator<CreateJobPayload> createJobDataProvider() {
		
		Iterator<CreateJobBean> createJobBeanIterator=CSVReaderUtil.loadCSV("testData/CreateJobData.csv", CreateJobBean.class);
		
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while(createJobBeanIterator.hasNext()) {
			tempBean= createJobBeanIterator.next();
			tempPayload=CreateJobBeanMapper.mapper(tempBean);
			payloadList.add(tempPayload);
		}
		
		return payloadList.iterator();
	}
	
	@DataProvider(name="CreateJobAPIFakeDataProvider", parallel = true )
	public static Iterator<CreateJobPayload> createJobFakeDataProvider() {
		 return FakerDataGenerator.generateFakeCreateJobData(10);
	}
	
}
