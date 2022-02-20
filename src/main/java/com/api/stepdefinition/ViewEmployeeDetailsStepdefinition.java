package com.api.stepdefinition;

import com.api.model.EmployeeID;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ViewEmployeeDetailsStepdefinition {
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(ViewEmployeeDetailsStepdefinition.class);

	public ViewEmployeeDetailsStepdefinition(TestContext context) {
		this.context = context;
	}

	@Given("user has access to endpoint {string}")
	public void userHasAccessToEndpoint(String endpoint) {		
		context.session.put("endpoint", endpoint);
	}

	@When("user makes a request to view employee IDs")
	public void userMakesARequestToViewEmployeeIDs() {
		context.response = context.requestSetup().when().get(context.session.get("endpoint").toString());

	}

	@Then("user should get the response code {int}")
	public void userShouldGetTheResponseCode(Integer statusCode) {
		assertEquals(Long.valueOf(statusCode), Long.valueOf(context.response.getStatusCode()));
		System.out.println(context.response.getStatusCode());
	}

	@Then("user should see all the employees")
	public void userShouldSeeAllTheEmployeeIDS() {
		EmployeeID[] employeeIDS = ResponseHandler.deserializedResponse(context.response, EmployeeID[].class);
	}

	@Then("user makes a request to view details of a employee ID")
	public void userMakesARequestToViewDetailsOfEmployeeID() {
		context.response = context.requestSetup().when().get(context.session.get("endpoint").toString());
	}

	@Given("user makes a request to view employee IDs from {string} to {string}")
	public void userMakesARequestToViewEmployeeFromTo(String checkin, String checkout) {
		context.response = context.requestSetup()
				.queryParams("checkin",checkin, "checkout", checkout)
				.when().get(context.session.get("endpoint").toString());	
	}

	@Then("user makes a request to view all the employee IDs of that user name")
	public void userMakesARequestToViewEmployeeIDByUserName() {
		LOG.info("Session firstname: "+context.session.get("firstname"));
		LOG.info("Session lastname: "+context.session.get("lastname"));
		context.response = context.requestSetup()
				.queryParams("firstname", context.session.get("firstname"), "lastname", context.session.get("lastname"))
				.when().get(context.session.get("endpoint").toString());	
		EmployeeID[] bookingIDs = ResponseHandler.deserializedResponse(context.response, EmployeeID[].class);
		assertNotNull("Booking ID not found!!", bookingIDs);
	}

	@Then("user validates the response with JSON schema {string}")
	public void userValidatesResponseWithJSONSchema(String schemaFileName) {
		context.response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/"+schemaFileName));
		LOG.info("Successfully Validated schema from "+schemaFileName);
	}
	
	@When("user makes a request to check the health of booking service")
	public void userMakesARequestToCheckTheHealthOfBookingService() {
		context.response = context.requestSetup().get(context.session.get("endpoint").toString());
	}
}
