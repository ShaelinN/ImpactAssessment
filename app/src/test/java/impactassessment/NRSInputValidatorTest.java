package impactassessment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import impactassessment.numberrangesummarizer.exception.InvalidInputStringException;
import impactassessment.numberrangesummarizer.inputvalidation.NRSInputValidator;

public class NRSInputValidatorTest {
    NRSInputValidator defaultImplementation;

    @BeforeEach
    void setup() {
        defaultImplementation = new NRSInputValidator() {
            //default behaviour
        };
    }

    @Test
    void validateStringSuccessfulTest() {
        assertTrue(defaultImplementation.validateString("1"));
        assertTrue(defaultImplementation.validateString("1,2"));
        assertTrue(defaultImplementation.validateString("10,2"));
        assertTrue(defaultImplementation.validateString("1,-2"));
        assertTrue(defaultImplementation.validateString("-10,2"));
        assertTrue(defaultImplementation.validateString(""));
        
    }

    @Test
    void validateStringNullInputTest() {
        assertThrows(
            InvalidInputStringException.class, () -> { 
                defaultImplementation.validateString(null);
            }
        );
    }

    @Test
    void validateStringMalformedTest() {
        String[] inputs = new String[] {
            "1-3,4,5", 
            "a", 
            "1,2,3,a", 
            "1a,2,4",
            ",1",
            "1,",
            "1,,2",
        };
        
        for (int i = 0; i < inputs.length; i++) {
            String input = inputs[i];

            assertThrows(
                InvalidInputStringException.class, () -> { 
                    defaultImplementation.validateString(input);
                }
            );
        }

    }    
}
