package tqs.deti.serviceTests;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import tqs.deti.configs.DefaultValues;

@AutoConfigureTestDatabase
public class DefaultValuesTest {

    @Test

    public void testDefaultValues() throws Exception {
        
        /* [ERROR] tqs.deti.serviceTests.DefaultValuesTest.testDefaultValues  Time elapsed: 0.032 s  <<< ERROR!
java.lang.NullPointerException: Cannot invoke "tqs.deti.repositories.BusRepository.save(Object)" because "this.busRepository" is null
        DefaultValues dv = new DefaultValues();

        dv.run(null);
        */


    }
    
}
