package liveProject;

import static org.hamcrest.CoreMatchers.*;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestAssuredProject {
    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    String sshKey;
    int sshKeyId;

    @BeforeClass
    public void setUp() {
        reqSpec=new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .setBaseUri("https://api.github.com")
                .build();

        sshKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDSgOIhb5MPkmb2rAYBogrREGS3gWhXWlnbX1DNxWHWHm5Fd1gzFKO+cRUVA0MyY3MD16CbdIAZWEN2W8+O7C1iZoYNub50XkllUP1vuTinjdJoA1DTl/Ii7BJ2aqTb6/voeRELuZwsftQlrGvtyOY209ImUVpyV+iiPo44p4qN9+51JS9zZ9B8zs04KzS6nWbj7SHKIJgvjavvFrDT4XOG3Y7xvNaWIV7hU4phEHDYteyiSGla2RnORoeS985H7VWE2iHS43QLEKDzEAYYs5umhIFLF+VsS+XCdUYgPpnrIsr0l6d8hPWBQoYAcIBSwaNFdlcTKsx7m3m14sXzpURJ";
    }



    @Test(priority=1)
    public void postKey() {
        // Create JSON request
        String reqBody = "{\"title\": \"RestAssuredAPIKey\",  \"key\": \""+sshKey+"\" }";

        Response response=given().spec(reqSpec).body(reqBody).when().post("/user/keys");
        String resBody= response.getBody().asPrettyString();
        System.out.println(resBody);
        System.out.println(response.getStatusCode());
        sshKeyId=response.then().extract().path("id");
        System.out.println(sshKeyId);

        Assert.assertEquals(response.getStatusCode(), 201, "Correct status code is not returned");

    }


    @Test(priority=2)
    public void getKey() {
        Response response =
                given().spec(reqSpec).when()
                        .get("/user/keys");
        System.out.println(response.asPrettyString());


        Assert.assertEquals(response.getStatusCode(), 200, "Correct status code is not returned");

    }

    @Test(priority=3)
    public void deleteKey() {
        Response response =
                given().spec(reqSpec)// Set headers
                        .when()
                        .delete("/user/keys/"+sshKeyId); // Send DELETE request

        System.out.println(response.asPrettyString());


        Assert.assertEquals(response.getStatusCode(), 204, "Correct status code is not returned");


    }
}