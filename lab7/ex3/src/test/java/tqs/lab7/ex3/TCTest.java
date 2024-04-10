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
import org.junit.jupiter.api.Order;


@Testcontainers
@SpringBootTest
class TCTest {

    @Autowired
    private StudentRepository studentRepository;
    

    @Container
    @Order(1)
    public static PostgreSQLContainer container = new PostgreSQLContainer<>("postgres:14")
      .withUsername("finex")
      .withPassword("tqs123")
      .withDatabaseName("tqslab7ex3");

    @DynamicPropertySource
    @Order(2)
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    @DisplayName("Test add Student")
    @Order(3)
    void testadd() {

        Student s = new Student();
        s.setAge(20);
        s.setEmail("mohamedi@gmail.com");
        s.setNome("Mohamed");
        s.setNmec(1234);

        studentRepository.save(s);

        Student result = studentRepository.findById(1234).get();

        assertThat(result.getAge()).isEqualTo(20);
        assertThat(result.getNome()).isEqualTo("Mohamed");

    }

    @Test 
    @DisplayName("Test update student")
    @Order(4)
    void testupdate() {
        Student s = new Student();
        s.setAge(20);
        s.setEmail("manel@ua.pt");
        s.setNome("Manel");
        s.setNmec(12345);

        studentRepository.save(s);

        Student result = studentRepository.findById(12345).get();

        result.setNome("Manelito");
        studentRepository.save(result);

        Student result2 = studentRepository.findById(12345).get();
        
        assertThat(result2.getNome()).isEqualTo("Manelito");
    }

    @Test
    @DisplayName("Test delete student")
    @Order(5)
    void testdelete() {
        Student s = new Student();
        s.setAge(20);
        s.setEmail("MANELITO@TUGA.UA");
        s.setNome("Manelito");
        s.setNmec(123456);

        studentRepository.save(s);

        studentRepository.deleteById(123456);

        assertThat(studentRepository.findById(123456)).isEmpty();

    }

    @Test
    @DisplayName("Test get student inserted in .sql")
    @Order(6)
    void testget() {
        
        Student result = studentRepository.findById(999).get();

        assertThat(result.getNome()).isEqualTo("João");
    }
        
}
