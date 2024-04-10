
package tqs.lab3.ex4;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Order;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class CarControllerTestIT {


    @Autowired
    private MockMvc mockMvc;

    @Container

    public static PostgreSQLContainer container = new PostgreSQLContainer<>("postgres:14")
            .withUsername("finex")
            .withPassword("tqs123")
            .withDatabaseName("tqslab7ex3");

    @DynamicPropertySource

    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    // create, get all , get all with no results, get by id

    @Test
    @Order(1)
    public void testCreateCar() {
        Car c = new Car("Tesla", "Model S");
        RestAssuredMockMvc.given().contentType("application/json").mockMvc(mockMvc)
                .body(c)
                .when().post("/api/cars")
                .then().statusCode(201);
    }

    @Test
    @Order(2)
    public void testGetAllCars() {
        RestAssuredMockMvc.given().contentType("application/json").mockMvc(mockMvc)
                .when().get("/api/cars")
                .then().statusCode(200)
                .body("[0].maker", is("Toyota"));

    }

    @Test
    @Order(3)
    void testGetCarByID(){

        RestAssuredMockMvc.given().contentType("application/json").mockMvc(mockMvc)
                .when().get("/api/cars/4")
                .then().statusCode(200)
                .body("maker", is("Renault"));
    }

}
