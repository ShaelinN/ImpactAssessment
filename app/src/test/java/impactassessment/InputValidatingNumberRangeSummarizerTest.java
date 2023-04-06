package impactassessment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import impactassessment.numberrangesummarizer.InputValidatingNumberRangeSummarizer;
import impactassessment.numberrangesummarizer.exception.InvalidCollectionException;
import impactassessment.numberrangesummarizer.exception.InvalidInputStringException;

/**
 * Assumptions:
 * 1. It is not the place for the summarizer itself to decide the handling of invalid input,
 * it is okay if it detects the problem and passes the information on to the caller.
 * 
 * 2. Nulls should not be caught at this level, as they indicate a systemic problem,
 * which should be handled at a higher level. This class is designed to break down if it encounters a null
 * 3. An integer consists only of the following symbols:
 *      '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
 *      i.e. floats that are equivalent to ints (like 3.0) are invalid, 
 *      as are scientific notation ints like '2e3'/ '2E3' / '2x10^3'
 * 4. integers provided are in base ten
 */
public class InputValidatingNumberRangeSummarizerTest {

    private InputValidatingNumberRangeSummarizer summarizer;

    @BeforeEach
    void setup() {
        summarizer = new InputValidatingNumberRangeSummarizer();
    }

    @Test
    void collectValidTest() {
        Collection<Integer> result = summarizer.collect("");

        assertEquals(TreeSet.class, result.getClass());
        assertTrue(result.isEmpty());

        result = summarizer.collect("-1,-1,2,30,4");
        assertEquals(TreeSet.class, result.getClass());
        assertEquals(4, result.size());
        assertTrue(result.contains(-1));
        assertTrue(result.contains(2));
        assertTrue(result.contains(30));
        assertTrue(result.contains(4));
    }
    
    @Test
    void collectInvalidTest() {
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
                    summarizer.collect(input);
                }
            );
        }
    }

    @Test
    void summarizeCollectionValidTest() {
        Collection<Integer> input = new TreeSet<>();
        String result = summarizer.summarizeCollection(input);
        assertEquals("", result);
    }

    /**
     * This test covers the validateCollection method, since that
     * is the method that causes the exception to be thrown.
     * In this case, the underlying BasicNumberRangeSummarizer.summarizeCollection
     * method will not even be called.
     */
    @Test
    void summarizeCollectionInvalidTest() {
        List<Collection<Integer>> inputs = new ArrayList<>();
        inputs.add(new ArrayList<>());
        inputs.add(new LinkedList<>());
        inputs.add(new ArrayDeque<>());
        inputs.add(new PriorityQueue<>());
        inputs.add(new Vector<>());
        inputs.add(new Stack<>());
        inputs.add(new HashSet<>());
        inputs.add(new LinkedHashSet<>());

        for (Collection<Integer> input : inputs) {
            assertThrows(
                InvalidCollectionException.class, () -> { 
                    summarizer.summarizeCollection(input);
                }
            );
        }
    }
}
