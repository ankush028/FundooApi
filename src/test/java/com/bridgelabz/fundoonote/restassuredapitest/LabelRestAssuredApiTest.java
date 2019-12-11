package com.bridgelabz.fundoonote.restassuredapitest;

import static org.testng.Assert.assertEquals;
import java.util.List;
import org.testng.annotations.Test;
import com.bridgelabz.fundoonote.label.model.Label;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;

/**
 * @purpose write Api Tests Using RestAssured
 * @author Ankush kumar Agrawal
 *
 */
public class LabelRestAssuredApiTest {
	private String baseurl="http://localhost:8080/labelapi";

	
	 
	/**
	 * @purpose Add Label Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void addLabelApiTest() {
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
		JSONObject lbldto = new JSONObject();
		lbldto.put("labeltitle","Rest asdas");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(lbldto.toJSONString());
		Response response = httpRequest.request(Method.POST,"/addlabel?token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha2FnMDA2QGdtYWlsLmNvbSIsImlhdCI6MTU3NTA5MTExMX0.iqiFM0YMblL9oT80rXa3fbhcQQcYu-UwAHRExkhaKm8");
		assertEquals(response.getStatusCode(),200);
	}
	/**
	 * @purpose Add Label Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void updateLabelApiTest() {
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
		JSONObject lbldto = new JSONObject();
		lbldto.put("labeltitle","Rest asdas");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(lbldto.toJSONString());
		Response response = httpRequest.request(Method.PUT,"/update?id=5def8488866b320428d9b6f7&token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha2FnMDA2QGdtYWlsLmNvbSIsImlhdCI6MTU3NTA5MTExMX0.iqiFM0YMblL9oT80rXa3fbhcQQcYu-UwAHRExkhaKm8");
		assertEquals(response.getStatusCode(),200);
	}
	/**
	 * @purpose Add Label Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void deleteLabelApiTest() {
		RequestSpecification httpRequest = RestAssured.given().baseUri(baseurl);
		Response response = httpRequest.request(Method.DELETE,"/delete?id=5def8488866b320428d9b6f7&token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha2FnMDA2QGdtYWlsLmNvbSIsImlhdCI6MTU3NTA5MTExMX0.iqiFM0YMblL9oT80rXa3fbhcQQcYu-UwAHRExkhaKm8");
		assertEquals(response.getStatusCode(),200);
	}

	/**
	 * @purpose Add Label Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	//@Test
	public void getAllDataApiTest() {
		RequestSpecification httpRequest = RestAssured.given();
		List<Label> response = httpRequest.when().get("http://localhost:8080/labelapi/getAllLabel")
				.then().extract().body().jsonPath().getList(".",Label.class);
			System.out.println("List is coming");
			for(Label u:response)
				System.out.println(u.toString());
			System.out.println(response.get(0).toString());
}

}
