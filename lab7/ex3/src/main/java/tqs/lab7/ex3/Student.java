package tqs.lab7.ex3;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student")
@Getter
@Setter 
@NoArgsConstructor
public class Student {


    private int nmec;
    private int age;
    private String name;
    private String email;

    
}
