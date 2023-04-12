Feature: Organisation SignUp API Test

  @APItests
  Scenario: 18879_Create a new user with valid data
    Given Read "Testcase1" from Testdata file required to create a user
    And I set the content type and body
    When I post the create user data to the api
    Then I verify the status as "200"

  @APItests
  Scenario: 18880_Create a new user with invalid data
    Given Read "Testcase2" from Testdata file required to create a user
    And I set the content type and body
    When I post the create user data to the api
    Then I verify the status as "400"
    
