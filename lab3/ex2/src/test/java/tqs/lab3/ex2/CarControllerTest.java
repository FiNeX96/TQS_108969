package tqs.lab3.ex2;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(CarController.class)
class CarControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CarManagerService carManagerService;

	@Test
	void whenGetCars_thenReturnJsonArrayWithAllCars() throws Exception {

		Car tesla = new Car("Tesla", "Model S");
		Car audi = new Car("Audi", "A4");
		Car bmw = new Car("BMW", "M3");

		List<Car> allCars = Arrays.asList(tesla, audi, bmw);

		when(carManagerService.getAllCars()).thenReturn(allCars);

		mvc.perform(get("/api/cars"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].maker", is(tesla.getMaker())))
		// .andDo(print())
		;

	}

	@Test
	void whenPostCar_thenReturnCar() throws Exception {

		Car tesla = new Car("Tesla", "Model S");

		when(carManagerService.save(Mockito.any())).thenReturn(tesla);
		mvc.perform(post("/api/cars")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"maker\": \"Tesla\", \"model\": \"Model S\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.maker", is(tesla.getMaker())))
				.andExpect(jsonPath("$.model", is(tesla.getModel())));
	}

	@Test
	void whenGetCarDetails_thenReturnCar() throws Exception {

		Car tesla = new Car("Tesla", "Model S");
		when(carManagerService.getCarDetails(anyLong())).thenReturn(java.util.Optional.of(tesla));
		mvc.perform(get("/api/cars/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.maker", is(tesla.getMaker())))
				.andExpect(jsonPath("$.model", is(tesla.getModel())));
	}

}
