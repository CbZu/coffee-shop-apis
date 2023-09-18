# Services structure
![image](https://github.com/CbZu/coffee-shop-apis/assets/36872903/545f7a6f-3426-4ed3-954b-7619d1f2795b)

# How to run?
1. Go to the root project and run `mvn clean install`
2. Go to the `infrastructure\docker-compose` and run `docker compose up`
3. Open `Coffee Shop Collection.postman_collection` with postman and send request to test.

# How to test and dectected the error?
1. Run the OrderApplicationServiceTest.java file to test a part of application.
2. When business logic is changed we need to update the test case, If the result is not match with expected value => something wrong need be fixed.
