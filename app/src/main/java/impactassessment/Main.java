package impactassessment;

import java.util.Collection;
import java.util.Scanner;

import impactassessment.numberrangesummarizer.InputValidatingNumberRangeSummarizer;
/**
 * Note, this class simply demonstrates the inner functioning of the highest level class in the 
 * numberrangesummarizer package (InputValidatingNumberRangeSummarizer).
 * 
 * In a 'real' use case, the wider application would need to implement its own checks to ensure 
 * exceptions thrown by this class are handled in a way that suites the use case more appropriately.
 */
public class Main {
    public static void main(String[] args) {
        InputValidatingNumberRangeSummarizer ns = new InputValidatingNumberRangeSummarizer();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide a comma separated list of integers (positive or negative, no spaces):");

        String input = scanner.nextLine();
        Collection<Integer> col = ns.collect(input);
        String res = ns.summarizeCollection(col);
        System.out.println(res);
        scanner.close();
    }
}
