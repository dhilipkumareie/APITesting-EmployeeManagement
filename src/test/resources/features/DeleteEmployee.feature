@employeeAPI @deleteEmployee
Feature: To delete an employee


  @deleteEmployeeIDs
  Scenario: To delete a employee
      Given user has access to endpoint "/delete/1"
    When user makes a request to delete employee
    Then user should get the response code 200
