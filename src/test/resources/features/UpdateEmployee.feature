@employeeAPI @updateEmployee
Feature: To update an employee

  @updateEmployeeDataTable
  Scenario Outline: To update a employee using cucumber Data Table
    Given user has access to endpoint "/update/1"
    And user updates the details of a employee
      | name   | salary   | age   |
      | <name> | <salary> | <age> |
    Then user should get the response code 200
    And user validates the response with JSON schema "employeeDetailSchema.json"

    Examples:
      | name         | salary | age   |
      | Viruksha     | 234    | 21    |

