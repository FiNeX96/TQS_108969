package tqs.lab7.ex3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@SpringBootTest
class TCTest {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer<>()
      .withUsername("finex")
      .withPassword("tqs123")
      .withDatabaseName("tqslab7ex3");


    @Autowired
    private StudentRepository studentRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    void contextLoads() {

        Student s = new Student();
        s.setAge(20);
        s.setEmail("mohamedi@gmail.com");
        s.setName("Mohamed");
        s.setNmec(1234);

        studentRepository.save(s);

        //assertThat(studentRepository.findById(1234).get()).isEqualTo(s);
    }

    @Test 
    @DisplayName("Test get student with id 1234")
    void testGetStudent(){

        Student result = studentRepository.findById(1234).get();

        assertThat(result.getAge()).isEqualTo(20);
        assertThat(result.getName()).isEqualTo("Mohamed");
    }



    

    
}
