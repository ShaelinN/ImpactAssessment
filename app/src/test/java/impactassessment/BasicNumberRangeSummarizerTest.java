package impactassessment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;

import impactassessment.numberrangesummarizer.BasicNumberRangerSummarizer;

/**
 * The BasicNumberRangerSummarizer is a simple implementation of the interface,
 * which assumes any inputs given to it are valid.
 * 
 * It houses the basic algorithm for converting the integer list into an integer/range list.
 * 
 * It is intended to break down if it receives invalid inputs.
 * 
 * Wrapper classes must exist to provide input validation and handle invalid inputs.
 */
public class BasicNumberRangeSummarizerTest {

    private BasicNumberRangerSummarizer summarizer;

    @BeforeEach
    void setup() {
        summarizer = new BasicNumberRangerSummarizer();
    }

    @Test
    void collectNullInputTest() {
        String input = null;
        /*
        * Throwing it lets us know that there are logical issues in the wider system,
        * and that the issues were propagated all the way down to the BasicNumberRangerSummarizer class.
        */
        assertThrows(NullPointerException.class, 
            () -> {
                summarizer.collect(input);
            }
        );
    }

    /**
     * At this level, a trailing comma is not detected as a bug. However, the following are:
     * -leading comma,
     * -consecutive commas,
     * -non-numeric characters other than comma
     */
    @Test
    void collectEmptyOrMalformedInputTest() {

        String[] inputs = new String[] {
            "",
            "1-3,4,5", 
            "a", 
            "1,2,3,a", 
            "1a,2,4",
            ",1",
            "1,,2",
        };
        for (int i = 0; i < inputs.length; i++) {
            String input = inputs[i];
            assertThrows(
                NumberFormatException.class, () -> { 
                summarizer.collect(input);
            });
        }
    }

    /**
     * as mentioned, trailing commas will not break the BasicNumbeRangeSummarizer's collect() method
     */
    @Test
    void collectSuccessfulTest() {
        Collection<Integer> result = summarizer.collect("1,2,3");
        assertEquals(3, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
        assertTrue(result.contains(3));

        Collection<Integer> resultTrailingComma = summarizer.collect("1,");
        assertEquals(1, resultTrailingComma.size());
        assertTrue(resultTrailingComma.contains(1));
    }



    @Test 
    void summarizeCollectionNullInputTest() {

    }

    @Test
    void summarizeCollectionSuccessfulTest() {

    }

    @Test
    void fromIntegerListInputSizeOneTest() {

    }

    @Test
    void fromIntegerListInputSizeManyTest() {

    }

    @Test
    void fromIntegerListInputEmptyTest() {

    }

    @Test
    void fromIntegeListNullInputTest() {

    }

    @Test
    void performGroupingTest() {

    }
}