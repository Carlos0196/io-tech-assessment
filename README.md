# io-tech-assessment
iO Technical Assessment

## Assumptions
- Names of speakers are unique and for simplicity they will be used as ID.
- Title of TedTalks are unique and for simplicity they will be used as ID.
- A TedTalk cannot have more likes than views.
- If a record in the file is not valid (negative views or likes, or more likes than views) the record is ignored when importing
- I only made the unit test for the classes that had important logic for the calculations.

## How to run and test
The service doesn't have any specific requirement, so it can be run by executing the main method in the AssessmentApplication class

To test, I've provided a postman collection with the available endpoints, the collection is in the "client" folder in the root of the repo. 