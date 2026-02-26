API Endpoints and Output

1.Initial Parsing from JSON file to Database: 

Endpoint:  /api/recipes/upload 									
Initial Parsing had been done with the help of Spring Batch. 
Starts the execution with the help of JobOperator class while hit the api endpoint via postman the JobExecution it returns the output as either COMPLETED or FAILED.

2. POST /recipes
	Endpoint: /api/recipes

3. GET /recipes/top

Endpoint: /api/recipes/top?limit=2

This endpoint is helps for GET the top rating recipe details with limitation.
The Rating is sorted in descending order.
