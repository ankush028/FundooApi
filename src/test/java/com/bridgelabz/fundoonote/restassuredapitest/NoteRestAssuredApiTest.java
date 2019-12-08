package com.bridgelabz.fundoonote.restassuredapitest;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;

/**
 * @purpose Rest Assured Test for Note
 * @author Ankush Agrawal
 *
 */
public class NoteRestAssuredApiTest {
	
	private String baseurl="http://localhost:8080/noteapi";
	/**
	 * @purpose Add Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void addNoteApiTest() {
		RequestSpecification httprequest = RestAssured.given().baseUri(baseurl);
		JSONObject notedto = new JSONObject();
		notedto.put("title","Note Assured");
		notedto.put("description","describnekslsnd");
		httprequest.header("Content-Type","application/json");
		httprequest.body(notedto.toJSONString());	
		Response response =httprequest.request(Method.POST,"/addNote?token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha2FnMDA2QGdtYWlsLmNvbSIsImlhdCI6MTU3NTA5MTExMX0.iqiFM0YMblL9oT80rXa3fbhcQQcYu-UwAHRExkhaKm8");
		System.out.println(response.getStatusCode());
		assertEquals(response.getStatusCode(),200);	
	}
	
	/**
	 * @purpose delete note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void deleteNoteApiTest() {
		RequestSpecification httprequest = RestAssured.given().baseUri(baseurl);
		Response response = httprequest.request(Method.DELETE,"/delete/?token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha2FnMDA2QGdtYWlsLmNvbSIsImlhdCI6MTU3NTcxNzg0Nn0.yXLgFAXRsP6rcU2Qk-bZzhGxBGgi0k1WQbyROpq8CdU&id=5deb8c99f77ef33fc26cbf4c");
		assertEquals(response.getStatusCode(),200);
	}
	
	/**
	 * @purpose pin/unpin  Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void pinApiTest() {
		RequestSpecification httprequest = RestAssured.given().baseUri(baseurl);
		Response response = httprequest.request(Method.POST,"/pin/?id=5deb8c36f77ef33fc26cbf4a&token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha2FnMDA2QGdtYWlsLmNvbSIsImlhdCI6MTU3NTcxNzg0Nn0.yXLgFAXRsP6rcU2Qk-bZzhGxBGgi0k1WQbyROpq8CdU");
		assertEquals(response.getStatusCode(),200);
	}
	/**
	 * @purpose trash/untrash Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void trashApiTest() {
		RequestSpecification httprequest = RestAssured.given().baseUri(baseurl);
		Response response = httprequest.request(Method.POST,"/trash/?id=5deb8c36f77ef33fc26cbf4a&token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha2FnMDA2QGdtYWlsLmNvbSIsImlhdCI6MTU3NTcxNzg0Nn0.yXLgFAXRsP6rcU2Qk-bZzhGxBGgi0k1WQbyROpq8CdU");
		assertEquals(response.getStatusCode(),200);
	}
	/**
	 * @purpose archive/unarchive Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void archiveApiTest() {
		RequestSpecification httprequest = RestAssured.given().baseUri(baseurl);
		Response response = httprequest.request(Method.POST,"/archieve/?id=5deb8c36f77ef33fc26cbf4a&token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha2FnMDA2QGdtYWlsLmNvbSIsImlhdCI6MTU3NTcxNzg0Nn0.yXLgFAXRsP6rcU2Qk-bZzhGxBGgi0k1WQbyROpq8CdU");
		assertEquals(response.getStatusCode(),200);
	}
	/**
	 * @purpose Update Note Api Test
	 * @status 200 Test Passed Otherwise Fail
	 */
	@Test
	public void updateNoteApiTest() {
		RequestSpecification httprequest = RestAssured.given().baseUri(baseurl);
		JSONObject notedto = new JSONObject();
		notedto.put("title","updation");
		notedto.put("description","nottedescupdate");
		httprequest.header("Content-Type","application/json");
		httprequest.body(notedto.toJSONString());
		Response response = httprequest.request(Method.PUT,"/updateNote/?id=5deb8c36f77ef33fc26cbf4a&token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha2FnMDA2QGdtYWlsLmNvbSIsImlhdCI6MTU3NTcxNzg0Nn0.yXLgFAXRsP6rcU2Qk-bZzhGxBGgi0k1WQbyROpq8CdU");
		assertEquals(response.getStatusCode(),200);
	}
	
}
