
# General
- Project has 5 parts corresponding to package: controllers, models, repository (database simulation), services, util
- There are unit tests for controller, service, repository, and property testing for Percentile calculator

# Run all tests
`sbt test`
# Start server at localhost:9000
#### `sbt run`

#### When server is ready, we can use some HTTP clients (Postman, Paw,...) for testing.
#### Following is some testing scripts can be run in system terminal:
- insert :
`curl --location --request POST 'localhost:9000/add' \
--header 'Content-Type: application/json' \
--data-raw '{"poolId": 2, "poolValues" : [5, 3, 1 ,6]}'`
- query :
`curl --location --request POST 'localhost:9000/query' \
--header 'Content-Type: application/json' \
--data-raw '{"poolId": 2, "percentile": 60}'`