@employeeAPI @createEmployee
Feature: To create a new employee

  @createEmployeeDataTable
  Scenario Outline: To create new employee using cucumber Data Table
    Given user has access to endpoint "/create"
    When user creates a employee
      | name   | salary   | age   |
      | <name> | <salary> | <age> |
    Then user should get the response code 200
    And user validates the response with JSON schema "createEmployeeSchema.json"

    Examples:
      | name         | salary | age   |
      | Viruksha     | 12345  | 2     |


  @createEmployeeFromExcel
  Scenario Outline: To create new employee using Excel data
    Given user has access to endpoint "/create"
    When user creates a employee using data "<dataKey>" from Excel
    Then user should get the response code 200
    And user validates the response with JSON schema "createEmployeeSchema.json"

    Examples: 
      | dataKey         |
      | createEmployee1 |


  @createEmployeeFromJSON
  Scenario Outline: To create new employee using JSON data
    Given user has access to endpoint "/create"
    When user creates a employee using data "<dataKey>" from JSON file "<JSONFile>"
    Then user should get the response code 200
    And user validates the response with JSON schema "createEmployeeSchema.json"

    Examples: 
      | dataKey         | JSONFile          |
      | createEmployee1 | employeeBody.json |

