package com.api.tests;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.roles.Role;
import com.api.utils.SpecUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CreateJobAPITest {
	
	
	
	@Test
	public void createJobAPITest() {
		
		Customer customer=new Customer("Nikhil", "Hulke", "8390276733", "", "nik7hulke@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("A-107", "Hil Apartments", "Rohini West", "Near Sachdeva High School", "Delhi", "110085", "India", "Delhi");
		CustomerProduct customerProduct = new CustomerProduct("2025-04-06T18:30:00.000Z","12310855371228", "12310855371228", "12310855371228", "2025-04-06T18:30:00.000Z", 1, 1);
		Problems problems=new Problems(1,"Battery Issue");
		List<Problems> problemsList= new ArrayList<Problems>();
		problemsList.add(problems);
		CreateJobPayload createJobPayload= new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsList);
		
		
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
