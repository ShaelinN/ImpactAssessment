package impactassessment.numberrangesummarizer.inputvalidation;

import java.util.Collection;

import javax.annotation.Nullable;

import impactassessment.numberrangesummarizer.exception.InvalidCollectionException;
import impactassessment.numberrangesummarizer.exception.InvalidInputStringException;
/**
 * A tool for checking the validity of the string being provided to the NumberRangeSummarizer.
 * A default implementation has been provided, although alteration of the rules is still possible
 * by implementing different behaviour, and supplying that implementation to the FailHardNumberRangeSummarizer
 */
public interface NRSInputValidator {
    default boolean validateString(@Nullable String input) throws InvalidInputStringException {
        String reason = null;

        if (input == null) {
            reason = "Input string is null";
        } else if (!input.matches("^(((-?\\d+)(,-?\\d+)*)?)$")) {     
            reason = "Input string \'" + input + "\' contains non-numeric characters or is otherwise malformed";
        }

        if (reason == null) {
            return true;
        } else {
            throw new InvalidInputStringException(reason);
        }
    };

    default boolean validateCollection(@Nullable Collection<Integer> input) throws InvalidCollectionException {
        String reason = null;

        if (input == null) {
            reason = "Input collection is null";
        }

        if (reason == null) {
            return true;
        } else {
            throw new InvalidInputStringException(reason);
        }
    };
}
