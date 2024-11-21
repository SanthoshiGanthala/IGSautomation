

Feature: Test Google search functionality
  I want to use this template for my feature file

  @tag1
  Scenario: Google search
    Given I launch the application
    When I search
    Then Search result displayed
    
  
  @tag1
  Scenario Outline: Google Search with multiple keywords
  	Given I launch the application
  	When I search "<keyword>"
  	Then Search result displayed
  Examples:
  |keyword|
  |Testing|
  |Automation|
  

