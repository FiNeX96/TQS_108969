
# Ex1 

## Review Questions 

a) Identify a couple of examples that use AssertJ expressive methods chaining.

 - givenSetOfEmployees_whenFindAll_thenReturnAllEmployees() in A_EmployeeRepositoryTest :

```java 
 assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
``` 

- whenValidInput_thenCreateEmployee() in D_EmployeeRestControllerIT


```java 

assertThat(found).extracting(Employee::getName).containsOnly("bob");

```

- givenEmployees_whenGetEmployees_thenStatus200 in E_EmployeeRestControllerTemplateIT

```java

    assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");

```

b) Identify an example in which you mock the behavior of the repository (and avoid involving a
database). 

In B_EmployeeService_UnitTest , the repository is mocked to avoid involving a database. 

```java

@ExtendWith(MockitoExtension.class)
class B_EmployeeService_UnitTest {

    // mocking the responses of the repository (i.e., no database will be used)
    // lenient is required because we load more expectations in the setup
    // than those used in some tests. As an alternative, the expectations
    // could move into each test method and be trimmed (no need for lenient then)
    @Mock( lenient = true)
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

}

```

c) What is the difference between standard @Mock and @MockBean?

- @Mock is used in tests to mock the behavior of a class. It can be stubbed to return specific values or throw exceptions when specific methods are called. It is used in unit tests to replace the real objects with mocks.

- @MockBean registers the Mock as a bean in the Spring Application Context, making it replace any existing bean of the same type in the application context. It is used in integration tests to replace the real bean objects with mocks.

d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be
used?

This file is used to configure the application for integration tests. It is used when running integration tests, and it overrides the normal properties defined ( not in this case ) in the application.properties file when running the integration tests.

e) the sample project demonstrates three test strategies to assess an API (C, D and E) developed with
SpringBoot. Which are the main/key differences? 


- In C, we are only loading the Controller Layer and not the full Spring Context. This is done using the @WebMvcTest annotation. This is useful when we want to test the controller layer in isolation. To access the controller we use @MockMvc object to perform the requests and assert the responses.

- In D, there is no real web server running. The web server is being mocked and the requests are being made directly to the controller. This is done using the @SpringBootTest and @AutoConfigureMockMvc annotations. This is useful when we want to test the controller layer in isolation, but we also want to test the full Spring Context.

- in E, the full Spring Context is being loaded and the requests are being made to the real web server. This is done using the @SpringBootTest annotation, starting the server in a random port  ( webEnvironment = WebEnvironment.RANDOM_PORT). We can later access this port using the @LocalServerPort annotation. This is useful when we want to test the full application, including the server and the controller layer.




