package activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {
    final static String BaseURI = "https://petstore.swagger.io/v2/user";	
    @Test(priority=1)	
    public void addNewUserFromFile() throws IOException {
        FileInputStream inputJSON = new FileInputStream("src/test/resources/userinfo.json");
        
        // Read JSON file as String
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (inputJSON.read(buffer) != -1) {
            sb.append(new String(buffer));
            buffer = new byte[10];
        }
        inputJSON.close();
        String reqBody = sb.toString();
	
        Response response = 	
            given().contentType(ContentType.JSON)	
            .body(reqBody)	
            .when().post(BaseURI); // Send POST request
        
        // Assertion    	
        response.then().body("code", equalTo(200));	
        response.then().body("message", equalTo("9968"));
    	
    }
    
    
    @Test(priority=2)	
    public void getUserInfo() {
	
        // Import JSON file to write to	
        File outputJSON = new File("src/test/resources/userGETResponse.json");
	
        Response response = 	
            given().contentType(ContentType.JSON) 	
            .pathParam("username", "Rak")	
            .when().get(BaseURI + "/{username}"); 
        // Get response body	
        String resBody = response.getBody().asPrettyString();	
        try {
	
            // Create JSON file	
            outputJSON.createNewFile();	
            // Write response body to external file	
            FileWriter writer = new FileWriter(outputJSON.getPath());	
            writer.write(resBody);	
            writer.close();
	
        } catch (IOException excp) {	
            excp.printStackTrace();
	
        }       
        // Assertion
	
        response.then().body("id", equalTo(9968));	
        response.then().body("username", equalTo("Rak"));	
        response.then().body("firstName", equalTo("Rak"));	
        response.then().body("lastName", equalTo("Case1"));	
        response.then().body("email", equalTo("rakincase@mail.com"));	
        response.then().body("password", equalTo("password123"));	
        response.then().body("phone", equalTo("9812763450"));
    }
    
    @Test(priority=3)
	
    public void deleteUser() throws IOException {
	
        Response response = 	
            given().contentType(ContentType.JSON)	
            .pathParam("username", "Rak")	
            .when().delete(BaseURI + "/{username}");
        // Assertion	
        response.then().body("code", equalTo(200));	
        response.then().body("message", equalTo("Rak"));
	
    }
}
