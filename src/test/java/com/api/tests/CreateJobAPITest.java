package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platfrom;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.SpecUtil;

public class CreateJobAPITest {
	
	
	
	@Test
	public void createJobAPITest() {
		
		Customer customer=new Customer("Nikhil", "Hulke", "8390276733", "", "nik7hulke@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("A-107", "Hil Apartments", "Rohini West", "Near Sachdeva High School", "Delhi", "110085", "India", "Delhi");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10),"12510855381228", "12510855381228", "12510855381228", getTimeWithDaysAgo(10),
										Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems=new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(),"Battery Issue");
		
		
		List<Problems> problemsList= new ArrayList<Problems>();
		problemsList.add(problems);
		
		CreateJobPayload createJobPayload= new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platfrom.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemsList);
		
		
		 
		given()
			.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
				.post("/job/create")
		.then()
				.spec(SpecUtil.responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. "))
				.body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", Matchers.startsWith("JOB_"));

	}

}
