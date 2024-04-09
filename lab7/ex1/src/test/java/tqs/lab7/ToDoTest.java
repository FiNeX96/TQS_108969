package tqs.lab7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


class ToDoTest {

    @Test
    @DisplayName("Test if the API is up")
    void testGetAllToDos() {
        when().get("https://jsonplaceholder.typicode.com/todos").then().statusCode(200);
    }

    @Test
    @DisplayName("Test when querying for TODO #4 the API returns a object with title et porro tempora")
    void testGetToDo4() {
        when().get("https://jsonplaceholder.typicode.com/todos/4").then().body("title", equalTo("et porro tempora"));
    }

    @Test
    @DisplayName("Test that the list of all todos includes entries with id 198 and 199")
    void testGetToDos198and199() {
        when().get("https://jsonplaceholder.typicode.com/todos").then().body("id", hasItems(198, 199));
    }

    @Test
    @DisplayName("When querying all todos, the results take less than 2 seconds")
    void testGetAllToDosTime() {
        when().get("https://jsonplaceholder.typicode.com/todos").then().time(lessThan(2000L));
    }



    
}
