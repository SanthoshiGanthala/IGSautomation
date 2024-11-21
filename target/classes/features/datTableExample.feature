
@tag
Feature: Title of your feature
  I want to use this template for my feature file

  @tag1
  Scenario: Example for data table1
    Given I have to test Data table
    When I am passing table data
    |First Name| Rakesh|
    |Last Name | Madadi|
    |Email		 | abc@gamil.com |
    Then I can access all data values
    
   @tag1
  Scenario: Example for data table2
    Given I have to test Data table
    When I am passing table data values
    |First Name| Last Name| e-Mail 				|
    |Rakesh		 | Madadi		| abc@gamil.com |
    |Ramesh		 | Ram			| efg@gamil.com |
    Then I can access all data values
   
