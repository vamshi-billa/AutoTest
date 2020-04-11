Feature: Share Market
  I want to verify stock prices in Money COntrol and Google

  Scenario: Verify stock price in MC and GOogle
    When I get price values for top 5 most active stocks from Money Control
    And I get price values for those companies from Google
    Then print the values in "F:/ECLIPSE WORKSPACE/AutoProject/src/main/resources/ShareMarketDetails.xlsx" in "ShareMarket1" sheet along with difference in values
