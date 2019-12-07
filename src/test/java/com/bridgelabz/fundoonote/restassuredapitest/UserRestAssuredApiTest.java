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



public class UserRestAssuredApiTest {	
		
	private String baseurl ="http://localhost:8080/userapi";
	
	@Test
	public void searchUserApiTest() {
			
			RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
			Response response = httpRequest.request(Method.GET,"getUser?id=5de38fcc235442524e0823bd");
			int code = response.getStatusCode();
			System.out.println("Get User Test "+code);		
			assertEquals(200,code);
	}
	@Test
	public void getAllUserApiTest() {
		RequestSpecification httpRequest = RestAssured.given();
		List<User> response = httpRequest.when().get("http://localhost:8080/labelapi/getAllLabel")
				.then().extract().body().jsonPath().getList(".",User.class);
			System.out.println("List is coming");
			for(User u:response)
				System.out.println(u.toString());
		
}
	@Test
	public void getAllUser() {		
		RestAssured.baseURI ="http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest =RestAssured.given();
		Response response =httpRequest.request(Method.GET,"/Hyderabad");
		int code = response.getStatusCode();
		assertEquals(200,code);
				
	}	
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
	@Test
	public void deleteUserApiTest() {
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
		Response response = httpRequest.request(Method.DELETE,"/delete?id=5de38fcc235442524e0823bd");
		int code = response.getStatusCode();
		assertEquals(code,200);	
		
	}

	
}
