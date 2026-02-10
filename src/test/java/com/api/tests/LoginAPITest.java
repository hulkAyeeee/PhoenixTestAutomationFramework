package com.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import static com.api.utils.SpecUtil.*;

public class LoginAPITest {
	
	private UserCredentials userCreds;
	
	@BeforeMethod(description="Create the Payload for the Login API")
	public void setup() {
		userCreds=new UserCredentials("iamfd", "password");
	}
	
	@Test(description="Verifying if login api is working for user iamfd", groups= {"api", "regression","smoke"})
	public void loginAPITest() throws IOException {
		
		
		given()
			.spec(requestSpec(userCreds))
		.when()
			.post("login")
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));
				
		
	}

}
