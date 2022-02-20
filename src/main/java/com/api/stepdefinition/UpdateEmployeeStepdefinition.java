package com.api.stepdefinition;

import com.api.model.EmployeeDetailsDTO;
import com.api.utils.ExcelUtils;
import com.api.utils.JsonReader;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UpdateEmployeeStepdefinition {
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(UpdateEmployeeStepdefinition.class);

	public UpdateEmployeeStepdefinition(TestContext context) {
		this.context = context;
	}

	@When("user creates a auth token with credential {string} & {string}")
	public void userCreatesAAuthTokenWithCredential(String username, String password) {
		JSONObject credentials = new JSONObject();
		credentials.put("username", username);
		credentials.put("password", password);
		context.response = context.requestSetup().body(credentials.toString())
				.when().post(context.session.get("endpoint").toString());
		String token = context.response.path("token");
		LOG.info("Auth Token: "+token);
		context.session.put("token", "token="+token);	
	}

	@When("user updates the details of a employee")
	public void userUpdatesAEmployee(DataTable dataTable) {
		Map<String,String> employeeData = dataTable.asMaps().get(0);
		JSONObject employeeBody = new JSONObject();
		employeeBody.put("name", employeeData.get("name"));
		employeeBody.put("salary", employeeData.get("salary"));
		employeeBody.put("age", (employeeData.get("age")));

		context.response = context.requestSetup().body(employeeBody.toString())
				.when().put(context.session.get("endpoint").toString());


		EmployeeDetailsDTO employeeDetailsDTO = ResponseHandler.deserializedResponse(context.response, EmployeeDetailsDTO.class);
	}
	
	@When("user updates the booking details using data {string} from Excel")
	public void userUpdatesTheBookingDetailsUsingDataFromExcel(String dataKey) throws Exception {
		Map<String,String> excelDataMap = ExcelUtils.getData(dataKey);
		context.response = context.requestSetup()
				.header("Cookie", context.session.get("token").toString())
				.pathParam("bookingID", context.session.get("bookingID"))
				.body(excelDataMap.get("requestBody"))
				.when().put(context.session.get("endpoint")+"/{bookingID}");
		
		EmployeeDetailsDTO employeeDetailsDTO = ResponseHandler.deserializedResponse(context.response, EmployeeDetailsDTO.class);
		assertNotNull("Booking not created", employeeDetailsDTO);
		context.session.put("excelDataMap", excelDataMap);
	}
	
	@When("user updates the employee details using data {string} from JSON file {string}")
	public void userUpdatesTheEmployeeDetailsUsingDataFromJSONFile(String dataKey, String JSONFile) {
		context.response = context.requestSetup()
				.header("Cookie", context.session.get("token").toString())
				.pathParam("bookingID", context.session.get("bookingID"))
				.body(JsonReader.getRequestBody(JSONFile,dataKey))
				.when().put(context.session.get("endpoint")+"/{bookingID}");

		EmployeeDetailsDTO employeeDetailsDTO = ResponseHandler.deserializedResponse(context.response, EmployeeDetailsDTO.class);
		assertNotNull("Booking not created", employeeDetailsDTO);
	}
	
	@When("user makes a request to update first name {string} & Last name {string}")
	public void userMakesARequestToUpdateFirstNameLastName(String firstName, String lastName) {
		JSONObject body = new JSONObject();
		body.put("firstname", firstName);
		body.put("lastname", lastName);
		
		context.response = context.requestSetup()
				.header("Cookie", context.session.get("token").toString())
				.pathParam("bookingID", context.session.get("bookingID"))
				.body(body.toString())
				.when().patch(context.session.get("endpoint")+"/{bookingID}");

		EmployeeDetailsDTO emplyeeDetailsDTO = ResponseHandler.deserializedResponse(context.response, EmployeeDetailsDTO.class);
		assertEquals("Booking not created", emplyeeDetailsDTO.getAge());
		assertEquals("First Name did not match", firstName, emplyeeDetailsDTO.getName());
		assertEquals("Last Name did not match", lastName, emplyeeDetailsDTO.getSalary());
	}
}
