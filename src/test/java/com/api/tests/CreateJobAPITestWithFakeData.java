package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.DateTimeUtil;
import com.api.utils.FakerDataGenerator;
import com.github.javafaker.Faker;

public class CreateJobAPITestWithFakeData{
	
	private CreateJobPayload createJobPayload;

	@BeforeMethod (description ="Creating createJob api request payload")
	public void setup() {
		createJobPayload=FakerDataGenerator.generateFakeCreateJobData();
	}
	
	@Test (description ="Verify if the create job api is able to create Inwarranty job", groups= {"api","regression","smoke"})
	public void createJobAPITest() {

		given()
			.spec(requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
				.post("/job/create")
		.then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. "))
				.body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", Matchers.startsWith("JOB_"));
	}

}
