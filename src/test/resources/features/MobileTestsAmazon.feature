Feature: Amazon Search and Add to Cart

  @MobileTest @SKIP
  Scenario Outline: 18881_Searching a product
    Given open the Amazon app on "<DeviceDetails>"
    And click on skip sign in
    And search for product
    And add the item to amazoncart
    Then verify item is added to cart or not

    Examples: 
      | DeviceDetails      |
      | Google Pixel 3_9.0 |
