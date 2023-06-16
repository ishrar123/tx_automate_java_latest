@Web
Feature: login into the application

  @ACS_01
  Scenario Outline:: Test to check if user is able to add template.
    Given Read the testdata "<TestData>" from excel file
    Given user navigates to the application
    When user clicks on the sign up
    When user enters the username and password
    When user clicks on the signin button for login
    And hover on the admin name
    And click on the "Templates" option
    And enter the template name
    When click on the "Add template" button
    Then user is able to see the error message
    Examples:
      | TestData  |
      | Testdata1 |


  @ACS_02

  Scenario Outline:: Test to check if user is able to view the edit template screen for existing template.
    Given Read the testdata "<TestData>" from excel file
    Given user navigates to the application
    When user clicks on the sign up
    When user enters the username and password
    When user clicks on the signin button for login
    And hover on the admin name
    And click on the "Templates" option
    And Search the template name
    When click on the search button
    And hover on the manage button
    And Selects the "Edit template" option
    Then user should be navigate to Edit template screen

    Examples:
      | TestData  |
      | Testdata1 |


  @ACS_03
  Scenario Outline:: Test to check if user is able to make the changes in edit template screen.
    Given Read the testdata "<TestData>" from excel file
    Given user navigates to the application
    When user clicks on the sign up
    When user enters the username and password
    When user clicks on the signin button for login
    And hover on the admin name
    And click on the "Templates" option
    And Search the template name
    When click on the search button
    And hover on the manage button
    And Selects the "Edit template" option
    Then user should be navigate to Edit template screen
    Then fill details in edit template form
    And Select activity card options radio button
    And unselect checkboxes if checked in Activity card options
    Then click on update button
    Examples:
      | TestData  |
      | Testdata1 |

  @ACS_05
  Scenario Outline:: Test to check if user is able to make the changes in edit template screen.
    Given Read the testdata "<TestData>" from excel file
    Given user navigates to the application
    When user clicks on the sign up
    When user enters the username and password
    When user clicks on the signin button for login
    And hover on the admin name
    And click on the "Projects" option
    And Search the Project name "Tx-Testing"
    When click on the search button
    And click on next button
	  And hover on the manage button
    And Selects the "View details" option
    Then user should be navigate to Edit project page
    And select project template setting
    And select template name "General_workout"
    Then click on update project button
    Examples:
      | TestData  |
      | Testdata1 |

  @ACS_06
  Scenario Outline:: Test to check if user is able to make the changes in edit template screen.
    Given Read the testdata "<TestData>" from excel file
    Given user navigates to the application
    When user clicks on the sign up
    When user enters the username and password
    When user clicks on the signin button for login
    And hover on the admin name
    And click on the "Projects" option
    And Search the Project name "Tx-Testing2"
    When click on the search button
    And click on next button
    And hover on the manage button
    And Selects the "View details" option
    Then user should be navigate to Edit project page
    And select project template setting
    And select template name "General_workout"
    And select the project doing with others checkbox
    Then click on update project button
    Examples:
      | TestData  |
      | Testdata1 |






    Examples:
      | TestData  |
      | Testdata1 |
   
      



      
      
      
      
    

