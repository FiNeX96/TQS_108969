Feature: Basic Arithmetic

  Background: A Calculator
    Given a calculator I just turned on

  Scenario: Addition
    When I add 4 and 5
    Then the result is 9

  Scenario: Substraction
    When I substract 7 to 2 
    Then the result is 5

  Scenario Outline: Several additions
    When I add <a> and <b>
    Then the result is <c>

  Examples: Single digits
    | a | b | c  |
    | 1 | 2 | 3  |
    | 3 | 7 | 10 |

  @skill_issue
  Scenario: Multiplication
    When I multiply 3 and 4
    Then the result is 12

  @BÁÁAAAAAKEND
  Scenario: Division
    When I divide 10 by 2
    Then the result is 5

  @HUH
  Scenario: Division by zero
    When I divide 10 by 0
    Then the result is Infinity