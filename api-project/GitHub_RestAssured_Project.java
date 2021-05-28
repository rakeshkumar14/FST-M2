package activities;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class GitHub_RestAssured_Project {
	RequestSpecification requestSpec;
	String sshKey; int ssh_Key_ID;
	
	@BeforeClass
	public void setup() {
        requestSpec = new RequestSpecBuilder()	
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .setBaseUri("https://api.github.com")
                .build();
        sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCyj89UzTBrA64g+oAMK0bFkDOTPc7qDho8t5u/M298eBQb4oCl3G02M/7wCJOHLuNuwhUW9KCZ1hP57DTI+8WO0r8e7yWuz9N+soOydLGG52uJIN+W8l18EqrCUowkDAhfjojAto9bPG9N6lPDUQyHzjtQqIJZ/Sg+JxQTjzlMuzO8/RPLlNFL5eKgEvviXt6URxf2kVriVx5DuM/L0fV7Fj3+CLFIVN3mLQGBrjvmTZOc/vQSsDpllMbTweaR27L8YyRm+3MFxIskdLxXd/riW8IqN5gW0O3MUvWP0kdwG26MTl/VSX0jEOy4mpaEDZaZcvrXEm7+NeZh54URrxgV azuread\\rakeshkumar@DESKTOP-239J6I1";
	}
	
  @Test
  public void addSShkeytoGit() {
	  String reqBody = "{\"title\": \"TestKey\", \"key\": \"" + sshKey + "\" }";
	  Response response = given().spec(requestSpec) // Use requestSpec
				.body(reqBody) // Send request body
				.when().post("/user/keys"); // Send POST request
	  String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		ssh_Key_ID = response.then().extract().path("id");
		// Assertions
		response.then().statusCode(201);
  }
  
  @Test(priority = 2)
	// Test case using a DataProvider
	public void getKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.when().get("/user/keys"); // Send GET Request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		// Assertions
		response.then().statusCode(200);
	}
	@Test(priority = 3)
	// Test case using a DataProvider
	public void deleteKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.pathParam("keyId", ssh_Key_ID).when().delete("/user/keys/{keyId}"); // Send GET Request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		// Assertions
		response.then().statusCode(204);
	}
}
