package impactassessment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import impactassessment.numberrangesummarizer.exception.InvalidInputStringException;
import impactassessment.numberrangesummarizer.inputvalidation.NRSInputValidator;

/**
 * Assumption:
 * 1. custom implementations are tested where they are defined
 * (e.g. InputValidatingNumberRangeSummarizer tests it's own validateCollection behaviour)
 * 
 * 2. Nulls should not be caught at this level, as they indicate a systemic problem,
 * which should be handled at a higher level. This class is designed to break down if it encounters a null
 * 
* 3. An integer consists only of the following symbols:
 *      '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
 *      i.e. floats that are equivalent to ints (like 3.0) are invalid, 
 *      as are scientific notation ints like '2e3'/ '2E3' / '2x10^3'
 * 4. integers provided are in base ten
 * 
 * 3. spaces in the input string are not allowed
 */
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
            NullPointerException.class, () -> { 
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
