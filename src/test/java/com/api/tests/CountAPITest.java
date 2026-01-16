package com.api.tests;

import static com.api.roles.Role.FD;
import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CountAPITest {

	@Test
	public void verifyCountAPIResponse() {
		
		given()
			.baseUri(getProperty("BASE_URI"))
			.and()
			.header("Authorization", getToken(FD))
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("/dashboard/count")
		.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(1000L))
			.body("message", equalTo("Success"))
			.body("data",notNullValue())
			.body("data.size()", equalTo(3))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", everyItem(not(blankOrNullString())))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"))
			.body("data.key", Matchers.containsInAnyOrder("pending_for_delivery", "pending_fst_assignment", "created_today"));
	}
	
	@Test
	public void countAPITest_MissingAuthToken() {
		
		given()
			.baseUri(getProperty("BASE_URI"))
			.and()
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("/dashboard/count")
		.then()
			.log().all()
			.statusCode(401);
	}
	
}
