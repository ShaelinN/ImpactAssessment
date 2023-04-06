package impactassessment;

import java.util.Collection;
import java.util.Scanner;

import impactassessment.numberrangesummarizer.InputValidatingNumberRangeSummarizer;
import impactassessment.numberrangesummarizer.exception.InvalidCollectionException;
import impactassessment.numberrangesummarizer.exception.InvalidInputStringException;
/**
 * Note, this is a demo class to show the inner functioning of the highest level class in the 
 * numberrangesummarizer package (InputValidatingNumberRangeSummarizer).
 * 
 * The error handling implemented here is definitely not complete. 
 * For example, there is no opportunity for nulls to be introduced here,
 * and thus no catch statement for NullPointerException has been written.
 * 
 * In a 'real' use case, the wider application would need to implement its own systems to ensure 
 * exceptions thrown in this class are handled in a way that suites the use case more appropriately.
 * 
 * The opportunity also exists to extend the validation checks to be more stringent if necessary (the
 * way it is implemented here allows negative numbers for example, which may be undesirable 
 * depending on the use case)
 */
public class Main {
    public static void main(String[] args) {
        InputValidatingNumberRangeSummarizer ns = new InputValidatingNumberRangeSummarizer();
        int retries = 3;
        String result = "";
        boolean successful = false;

        try (Scanner scanner = new Scanner(System.in)) {
            while (retries > 0) {
                System.out.println("Provide a comma separated list of integers (positive or negative, no spaces):");

                String input = scanner.nextLine();
                try {
                    Collection<Integer> col = ns.collect(input);
                    result = ns.summarizeCollection(col);
                    successful = true;
                    break;
                } catch (InvalidInputStringException | InvalidCollectionException e) {
                    e.printStackTrace();
                    retries--;
                    System.out.println(String.format("There was an error processing your input. %d retries remaining", retries));
                }
            }

            if (successful) {
                System.out.println("result: " + result);
            } else {
                System.out.println("Retries exhausted. Closing application");
            }
    
        }
    }
}
