Feature: API Test

  @api
  Scenario Outline: Get the url
    When I Get the "<name>" url
    Then I am able to Get the return code of 200
    And I am able to Get the "<body>"
    Examples:
      | name     | body                              |
      | root     | hello world                       |
      | health   | healthy                           |
      | metadata | lastcommitsha;version;description |
