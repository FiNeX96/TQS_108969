
package tqs.lab3.ex2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CarController.class)
class RestAssuredTest {

    @MockBean
    private CarManagerService carManagerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("When post a car return a car")
    void whenPostCar_thenReturnCar() throws Exception {
        Car tesla = new Car("Tesla", "Model S");

        when(carManagerService.save(any())).thenReturn(tesla);

        RestAssuredMockMvc.given()
                .mockMvc(mockMvc)
                .contentType("application/json")
                .body("{\"maker\": \"Tesla\", \"model\": \"Model S\"}")
                .when()
                .post("/api/cars")
                .then()
                .statusCode(201)
                .assertThat()
                .body("maker", is(tesla.getMaker()));
    }

    @Test
    @DisplayName("When get all cars return a json array with all cars")
    void whenGetCars_thenReturnJsonArrayWithAllCars() throws Exception {
        Car tesla = new Car("Tesla", "Model S");
        Car audi = new Car("Audi", "A4");
        Car bmw = new Car("BMW", "M3");

        when(carManagerService.getAllCars()).thenReturn(java.util.Arrays.asList(tesla, audi, bmw));

        RestAssuredMockMvc.given()
                .mockMvc(mockMvc)
                .when()
                .get("/api/cars")
                .then()
                .statusCode(200)
                .assertThat()
                .body("$", hasSize(3))
                .body("[0].maker", is(tesla.getMaker()))
                .body("[1].maker", is(audi.getMaker()))
                .body("[2].maker", is(bmw.getMaker()));
    }

    @Test
    @DisplayName("When get car details return a json with car details")
    void whenGetCarDetails_thenReturnJsonWithCarDetails() throws Exception {
        Car tesla = new Car("Tesla", "Model S");

        when(carManagerService.getCarDetails(1L)).thenReturn(java.util.Optional.of(tesla));

        RestAssuredMockMvc.given()
                .mockMvc(mockMvc)
                .when()
                .get("/api/cars/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("maker", is(tesla.getMaker()))
                .body("model", is(tesla.getModel()));
    }

}
