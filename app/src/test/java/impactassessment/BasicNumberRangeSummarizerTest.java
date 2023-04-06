package impactassessment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;

import impactassessment.numberrangesummarizer.BasicNumberRangerSummarizer;
import impactassessment.numberrangesummarizer.element.MultiNumberRangeElement;
import impactassessment.numberrangesummarizer.element.NumberRangeSummarizerElement;
import impactassessment.numberrangesummarizer.element.SingleNumberElement;

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
     * -non-numeric characters other than comma (including scientific notation and float notation of integers)
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
            "3.0", 
            "2e3"
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
        Collection<Integer> resultTrivial = summarizer.collect("1,2,3");
        Collection<Integer> resultTrailingComma = summarizer.collect("1,");
        Collection<Integer> resultNegativeIncluded = summarizer.collect("1,-1,2,-5");
        Collection<Integer> resultWithRepetition = summarizer.collect("1,1,1,2,2,3");

        // ensuring treesets ensures order and uniqueness
        assertEquals(TreeSet.class, resultTrivial.getClass());
        assertEquals(TreeSet.class, resultTrailingComma.getClass());
        assertEquals(TreeSet.class, resultNegativeIncluded.getClass());
        assertEquals(TreeSet.class, resultWithRepetition.getClass());
        
        assertEquals(3, resultTrivial.size());
        assertTrue(resultTrivial.contains(1));
        assertTrue(resultTrivial.contains(2));
        assertTrue(resultTrivial.contains(3));

        assertEquals(1, resultTrailingComma.size());
        assertTrue(resultTrailingComma.contains(1));

        assertEquals(4, resultNegativeIncluded.size());
        assertTrue(resultNegativeIncluded.contains(-5));
        assertTrue(resultNegativeIncluded.contains(-1));
        assertTrue(resultNegativeIncluded.contains(1));
        assertTrue(resultNegativeIncluded.contains(2));

        assertEquals(3, resultWithRepetition.size());
        assertTrue(resultTrivial.contains(1));
        assertTrue(resultTrivial.contains(2));
        assertTrue(resultTrivial.contains(3));

    }

    @Test 
    void summarizeCollectionNullInputTest() {
        Collection<Integer> input = null;
        assertThrows(
            NullPointerException.class, () -> { 
            summarizer.summarizeCollection(input);
        });
    }

    @Test
    void summarizeCollectionSuccessfulTest() {
        Collection<Integer> input = new TreeSet<>();
        input.add(4);
        input.add(3);
        input.add(1);
        input.add(2);
        input.add(42);
        input.add(-1);

        String result = summarizer.summarizeCollection(input);
        assertEquals(result, "-1,1-4,42");
    }

    @Test
    void fromIntegerListInputSizeOneTest() {
        List<Integer> consecutive = new ArrayList<>();
        consecutive.add(2);

        NumberRangeSummarizerElement element = summarizer.fromIntegerList(consecutive);
        assertEquals(SingleNumberElement.class, element.getClass());
        assertEquals("2", element.toString());
    }

    @Test
    void fromIntegerListInputSizeManyTest() {
        /* This operation is always provided a sorted list 
        (based on the treeset given to the summarizeCollection method) 
        */
        List<Integer> consecutive = new ArrayList<>();
        consecutive.add(2);
        consecutive.add(3);
        consecutive.add(4);

        NumberRangeSummarizerElement element = summarizer.fromIntegerList(consecutive);
        assertEquals(MultiNumberRangeElement.class, element.getClass());
        assertEquals("2-4", element.toString());
    }

    @Test
    void performGroupingTest() {
        TreeSet<Integer> setIn = new TreeSet<>();
        setIn.add(1);
        setIn.add(3);
        setIn.add(5);
        setIn.add(6);
        setIn.add(7);
        setIn.add(8);
        setIn.add(10);
        setIn.add(11);
        setIn.add(12);
        setIn.add(15);
        setIn.add(16);
        setIn.add(18);
        
        List<NumberRangeSummarizerElement> result = summarizer.performGrouping(setIn);
        assertEquals(6, result.size());
        assertEquals(result.get(0), new SingleNumberElement(1));
        assertEquals(result.get(1), new SingleNumberElement(3));
        assertEquals(result.get(2), new MultiNumberRangeElement(5, 8));
        assertEquals(result.get(3), new MultiNumberRangeElement(10, 12));
        assertEquals(result.get(4), new MultiNumberRangeElement(15, 16));
        assertEquals(result.get(5), new SingleNumberElement(18));
    }
}