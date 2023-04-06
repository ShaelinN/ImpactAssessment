package impactassessment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import impactassessment.numberrangesummarizer.exception.InvalidInputException;
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
    void validateSuccessfulTest() {
        assertTrue(defaultImplementation.validate("1"));
        assertTrue(defaultImplementation.validate("1,2"));
    }

    @Test
    void validateNullInputTest() {
        assertThrows(
            InvalidInputException.class, () -> { 
                defaultImplementation.validate(null);
            }
        );
    }

    @Test
    void validateEmptyInputTest() {
        assertThrows(
            InvalidInputException.class, () -> { 
                defaultImplementation.validate("");
            }
        );
    }

    @Test
    void validateMalformedTest() {
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
                InvalidInputException.class, () -> { 
                    defaultImplementation.validate(input);
                }
            );
        }

    }
}
