@employeeAPI @viewEmployee
Feature: To view the employee details

  @viewAllEmployees
  Scenario: To view all the Employees
    Given user has access to endpoint "/employees"
    When user makes a request to view employee IDs
    Then user should get the response code 200
    And user should see all the employees

  @viewEmployeeDetails
  Scenario: To view employee details
    Given user has access to endpoint "/employee/1"
    When user makes a request to view employee IDs
    And user makes a request to view details of a employee ID
    Then user should get the response code 200
    And user validates the response with JSON schema "employeeDetailsSchema.json"

