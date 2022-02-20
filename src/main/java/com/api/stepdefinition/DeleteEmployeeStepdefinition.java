package com.api.stepdefinition;

import com.api.utils.TestContext;
import io.cucumber.java.en.When;

public class DeleteEmployeeStepdefinition {
	private TestContext context;

	public DeleteEmployeeStepdefinition(TestContext context) {
		this.context = context;
	}

	@When("user makes a request to delete employee")
	public void userMakesARequestToDeleteEmployee() {
		context.response = context.requestSetup().when().delete(context.session.get("endpoint").toString());

	}
}
