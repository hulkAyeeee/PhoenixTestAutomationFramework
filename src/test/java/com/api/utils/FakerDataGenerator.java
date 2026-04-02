package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDataGenerator {
	
	private static Faker faker=new Faker(new Locale("en-IND"));
	private final static String COUNTRY="India";
	private final static Random RANDOM=new Random();
	
	private final static int MST_SERVICE_LOCATION_ID=0;
	private final static int MST_PLATFORM_ID=2;
	private final static int MST_WARRENTY_STATUS_ID=1;
	private final static int MST_OEM_ID=1;
	private final static int PRODUCT_ID=1;
	private final static int MST_MODEL_ID=1;
	private final static int VALID_PROBLEMS_ID[]= {1,2,3,4,5,6,7,8,9,10,11,12,15,16,17,19,20,22,24,26,27,28,29};
	
	
	
	public FakerDataGenerator(){
		
	}
	
	public static CreateJobPayload generateFakeCreateJobData() {
		Customer customer=generateFakeCustomerData();
		CustomerAddress customerAddress= generateFakeCustomerAddressData();
		CustomerProduct customerProduct= generateCustomerProduct();
		List<Problems> problemsList= generateFakeProblemsList();
		
		CreateJobPayload createJobPayload= new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID, MST_WARRENTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemsList);
		return createJobPayload;
	}
	
	public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();

		for (int i = 1; i <= count; i++) {
			Customer customer = generateFakeCustomerData();
			CustomerAddress customerAddress = generateFakeCustomerAddressData();
			CustomerProduct customerProduct = generateCustomerProduct();
			List<Problems> problemsList = generateFakeProblemsList();

			CreateJobPayload createJobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
					MST_WARRENTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemsList);
			
			payloadList.add(createJobPayload);
		}
		return payloadList.iterator();
	}
	

	private static List<Problems> generateFakeProblemsList() {
		
		String fakeRemark=faker.lorem().sentence(5);
		int randomIndex=RANDOM.nextInt(VALID_PROBLEMS_ID.length);
		
		Problems problems= new Problems(VALID_PROBLEMS_ID[randomIndex], fakeRemark);
		
		List<Problems> problemList=new ArrayList<Problems>();
		problemList.add(problems);
		
		return problemList;
	}

	private static CustomerProduct generateCustomerProduct() {
		String dop=DateTimeUtil.getTimeWithDaysAgo(10);
		String imeiSerialNumber=faker.numerify("###############");
		String popUrl=faker.internet().url();
		
		CustomerProduct customerProduct=new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber, popUrl, PRODUCT_ID, MST_MODEL_ID);
		return customerProduct;
	}

	private static CustomerAddress generateFakeCustomerAddressData() {
		String flatNumber=faker.numerify("###");
		String apartmentName=faker.address().streetName();
		String streetName=faker.address().streetName();
		String landmark=faker.address().streetName();
		String area=faker.address().streetName();
		String pinCode=faker.numerify("#####");
		String state=faker.address().state();
		
		CustomerAddress customerAddress=new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area, pinCode, COUNTRY, state);
		return customerAddress;
		
	}

	private static Customer generateFakeCustomerData() {
		String fname=faker.name().firstName();
		String lname=faker.name().lastName();
		String mobileNumer=faker.numerify("839#######");
		String altMobileNumer=faker.numerify("8368#####");
		String customerEmailAddress=faker.internet().emailAddress();
		String altustomerEmailAddress=faker.internet().emailAddress();
		
		
		Customer customer=new Customer(fname, lname, mobileNumer, altMobileNumer, customerEmailAddress, altustomerEmailAddress);
		return customer;
	}

}
