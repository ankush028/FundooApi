package com.bridgelabz.fundoonote.restassuredapitest;

import static org.testng.Assert.assertEquals;
import java.util.List;
import org.testng.annotations.Test;
import com.bridgelabz.fundoonote.model.User;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;



/**
 * @author Ankush Kumar Agrawal
 *@purpose rest Assured Api test for Users
 */
public class UserRestAssuredApiTest {	
		
	private String baseurl ="http://localhost:8080/userapi";
	/**
	 * @purpose searchUser Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void searchUserApiTest() {
			
			RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
			Response response = httpRequest.request(Method.GET,"getUser?id=5de38fcc235442524e0823bd");
			int code = response.getStatusCode();
			System.out.println("Get User Test "+code);		
			assertEquals(200,code);
	}
	/**
	 * @purpose Get All User Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void getAllUserApiTest() {
		RequestSpecification httpRequest = RestAssured.given();
		List<User> response = httpRequest.when().get("http://localhost:8080/userapi/getAllUser")
				.then().extract().body().jsonPath().getList(".",User.class);
			for(User u:response)
				System.out.println(u.toString());
		
	}
	/**
	 * @purpose Loggedin user Sucessful
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void loginApiTest() {
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
		JSONObject logindto = new JSONObject();
		logindto.put("email","akag006@gmail.com");
		logindto.put("password","1234");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(logindto.toJSONString());
		Response response = httpRequest.request(Method.POST,"/login");
		assertEquals(response.getStatusCode(),200);
		
		
	}
	/**
	 * @purpose login user with wrong password
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test 
	public void loginApiTestWithWrongPassword() {
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
		JSONObject logindto = new JSONObject();
		logindto.put("email","akag006@gmail.com");
		logindto.put("password","12234");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(logindto.toJSONString());
		Response response = httpRequest.request(Method.POST,"/login");
		assertEquals(response.getStatusCode(),200);
	}
	/**
	 * @purpose login user with wrong email
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test 
	public void loginApiTestWithWrongEmail() {
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
		JSONObject logindto = new JSONObject();
		logindto.put("email","akag00@gmail.com");
		logindto.put("password","1234");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(logindto.toJSONString());
		Response response = httpRequest.request(Method.POST,"/login");
		assertEquals(response.getStatusCode(),500);
	}
	/**
	 * @purpose forget password
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void forgetPasswordTest() {
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
		Response response = httpRequest.request(Method.POST,"/forgot?email=akag0284%40gmail.com");
		assertEquals(response.getStatusCode(),200);
	}
	/**
	 * @purpose reset password 
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void resetPasswordTest() {
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
		JSONObject resetdto = new JSONObject();
		resetdto.put("password","akag00@gmail.com");
		resetdto.put("confirmPassword","1234");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(resetdto.toJSONString());
		Response response = httpRequest.request(Method.POST,"/reset/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha2FnMDA2QGdtYWlsLmNvbSIsImlhdCI6MTU3NTA5MTExMX0.iqiFM0YMblL9oT80rXa3fbhcQQcYu-UwAHRExkhaKm8");
		assertEquals(response.getStatusCode(),200);
	}
	/**
	 * @purpose getAll User
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void getAllUser() {		
		RestAssured.baseURI ="http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest =RestAssured.given();
		Response response =httpRequest.request(Method.GET,"/Hyderabad");
		int code = response.getStatusCode();
		assertEquals(200,code);
				
	}	
	/**
	 * @purpose Add User Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void addUserApiTest() {
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
		JSONObject regdto = new JSONObject();
		regdto.put("name","RestAssured");
		regdto.put("email","akg0284@gmail.com");
		regdto.put("password","12334");
		regdto.put("confirmPassword","12334");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(regdto.toJSONString());
		Response response = httpRequest.request(Method.POST,"/addUser");
		System.out.println("addUser"+response.toString());
		System.out.println("Code is coming "+response.getStatusCode());
		assertEquals(response.getStatusCode(),200);
	}
	/**
	 * @purpose delete User Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void deleteUserApiTest() {
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
		Response response = httpRequest.request(Method.DELETE,"/delete?id=5de38fcc235442524e0823bd");
		int code = response.getStatusCode();
		assertEquals(code,200);	
		
	}

	
}
