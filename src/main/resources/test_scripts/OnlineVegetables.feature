Feature: OnlineVegetables site.
  Testing onlineVegetales website.

  Scenario: Verify iframe page navigation.
    When I click on Online Vegetables text
    Then verify Order button is enabled in the iframe

  Scenario: Verify opening a new window.
    When I click on vegetable link in the Vegetables Bill table where vegetable name is "Tomatoes"
    Then I should find "Click to know the Vegetable name!" text element displayed in a new window

  Scenario: Verify pop up.
    When I click on Click to view the Vegetable name! element
    Then I should verify that "Tomatoes" is displayed in a popup
    When I close the vegetable window

  Scenario: Verify alert box.
    When I click on the "Tomatoes" vegetable image
    Then I should find the alert box with message "You clicked on a image!"
    And I accept alert

  Scenario: Verify previous date input.
    When I find the date input field
    Then I input yesterday's date in deliver on control
