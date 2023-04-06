package impactassessment.numberrangesummarizer.inputvalidation;

import java.util.Collection;

import impactassessment.numberrangesummarizer.exception.InvalidCollectionException;
import impactassessment.numberrangesummarizer.exception.InvalidInputStringException;
/**
 * A tool for checking the validity of the string being provided to the NumberRangeSummarizer.
 * A default implementation has been provided, although alteration of the rules is still possible
 * by implementing different behaviour, and supplying that implementation to the FailHardNumberRangeSummarizer
 */
public interface NRSInputValidator {
    default boolean validateString(String input) throws InvalidInputStringException {
        if (!input.matches("^(((-?\\d+)(,-?\\d+)*)?)$")) {     
            String reason = "Input string \'" + input + "\' contains non-numeric characters or is otherwise malformed";
            throw new InvalidInputStringException(reason);
        }
        return true;
    };

    default boolean validateCollection(Collection<Integer> input) throws InvalidCollectionException {
        return true;
    }
}
