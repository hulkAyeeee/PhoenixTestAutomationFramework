package com.api.utils;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.*;

import static org.hamcrest.Matchers.*;

import com.api.pojo.UserCredentials;
import com.api.roles.Role;

import static com.api.roles.Role.*;

public class AuthTokenProvider {
	
	private AuthTokenProvider() {
		
	}
	
	public static String getToken(Role role) {		
		
		UserCredentials userCreds = null;
		
		if(role == FD) {
			userCreds=new UserCredentials("iamfd", "password");
		}
		
		else if(role == SUP) {
			userCreds=new UserCredentials("iamsup", "password");
		}
		
		else if(role == ENG) {
			userCreds=new UserCredentials("iameng", "password");
		}
		
		else if(role == QC) {
			userCreds=new UserCredentials("iamqc", "password");
		}
		
		
		String token=given()
						.baseUri(ConfigManager.getProperty("BASE_URI"))
						.contentType(JSON)
						.body(userCreds)
					 .when()
					 	.post("login")
					 .then()
					 	.log().ifValidationFails()
					 	.statusCode(200)
					 	.body("message", equalTo("Success"))
					 	.extract()
					 	.body()
					 	.jsonPath()
					 	.getString("data.token");
		return token;
	}
	
}
