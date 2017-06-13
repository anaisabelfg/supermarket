# Coding Exercise

Please write a console application that simulates a simple supermarket checkout. It needs to meet the following requirements:

* The application should read a list of items and prices from a JSON file. Please include an example input file.

* From the list of items and prices, the application should calculate the total price, including any discounts.

* Discounts are calculated based on rules defined in the system, and should be applied automatically. The following types of discount should be calculated:

* A fixed percentage off an item (e.g. 50% off)

* Buy 3 items of a type, get the cheapest free (e.g. buy three boxes of cereal, get the cheapest free)

* Buy 2 identical items receive a promotional price(e.g. buy 2 for £2)

* The application should output the total price of the items, a list of the items bought and any special offers applied

* It should be easy to add more special offer rules

* The application should be appropriately tested

* A readme file should be included to explain project usage, and how to add more special offers.

We expect you to take 2 to 4 hours to complete this exercise. Please prioritise producing a running console application over implementing all the requirements.

There is some code provided to you already to start you off - feel free to change/adapt anything you deem necessary.

Please provide your completed code as a zip file to your contact.


## Assumptions ##
- Offers apply just one type of product at the same time.
- One item can be just in one offer.
- The program should choose the best offer when more than one applies.
- A json file with the offers would be a good method to load them.

## How to get setup ##
You would need Java 8 and maven 2 to run this project
 
## How to run it ##
First extract the zip file provided and go the folder, then run:

`mvn verify`

then run:
 
`java -jar target/mendeley-coding-exercise-0.0.1.jar itemList.json offersList.json`


