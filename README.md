# backend-test

To run the tests, you should run the entire package `com.wifiesta.restaurant` located under `src/test/java` as a JUnit Test.

In order to start the App, you should run the main class: `com.wifiesta.restaurant.App` with Java 8.

Once started, you could access all the services via `http://localhost:8080/wifiesta/*`

Some examples:

```bash
curl -X GET 'http://localhost:8080/wifiesta/menus/1' 
curl -X GET 'http://localhost:8080/wifiesta/menus/grouped?by=ranking'
curl -X GET 'http://localhost:8080/wifiesta/menus?validDayOfWeek=SUNDAY'
```

