package com.api.stepdefinition;

import com.api.model.EmployeeDTO;
import com.api.utils.ExcelUtils;
import com.api.utils.JsonReader;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateEmployeeStepdefinition {
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(CreateEmployeeStepdefinition.class);

	public CreateEmployeeStepdefinition(TestContext context) {
		this.context = context;
	}

	@When("user creates a employee")
	public void userCreatesAEmployee(DataTable dataTable) {
		Map<String,String> employeeData = dataTable.asMaps().get(0);
		JSONObject employeeBody = new JSONObject();
		employeeBody.put("name", employeeData.get("name"));
		employeeBody.put("salary", employeeData.get("salary"));
		employeeBody.put("age", employeeData.get("age"));

		context.response = context.requestSetup().body(employeeBody.toString())
				.when().post(context.session.get("endpoint").toString());

	}

	private void validateEmployeeData(JSONObject employeeData, EmployeeDTO employeeDTO) {
		LOG.info(employeeData);


	}

	@When("user creates a employee using data {string} from Excel")
	public void userCreatesAEmployeeUsingDataFromExcel(String dataKey) throws Exception {
		Map<String,String> excelDataMap = ExcelUtils.getData(dataKey);
		context.response = context.requestSetup().body(excelDataMap.get("requestBody"))
				.when().post(context.session.get("endpoint").toString());

	}

	@Then("user validates the response with JSON schema from Excel")
	public void userValidatesTheResponseWithJSONSchemaFromExcel() {
		context.response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(((Map<String,String>) context.session.get("excelDataMap")).get("responseSchema")));
		LOG.info("Successfully Validated schema from Excel");
	}

	@When("user creates a employee using data {string} from JSON file {string}")
	public void userCreatesAEmployeeUsingDataFromJSONFile(String dataKey, String JSONFile) {
		context.response = context.requestSetup().body(JsonReader.getRequestBody(JSONFile,dataKey))
				.when().post(context.session.get("endpoint").toString());

		EmployeeDTO employeeDTO = ResponseHandler.deserializedResponse(context.response, EmployeeDTO.class);

	}
}
