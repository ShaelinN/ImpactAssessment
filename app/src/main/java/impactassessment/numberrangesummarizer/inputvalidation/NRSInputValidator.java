package impactassessment.numberrangesummarizer.inputvalidation;

import javax.annotation.Nullable;

import impactassessment.numberrangesummarizer.exception.InvalidInputException;
/**
 * A tool for checking the validity of the string being provided to the NumberRangeSummarizer.
 * A default implementation has been provided, although alteration of the rules is still possible
 * by implementing different behaviour, and supplying that implementation to the FailHardNumberRangeSummarizer
 */
public interface NRSInputValidator {
    default boolean validate(@Nullable String input) throws InvalidInputException {
        String reason = null;

        if (input == null) {
            reason = "Input string is null";
        } else if (input.equals("")) {
            reason = "Input string is empty";
        } else if (!input.matches("^[0-9](,[0-9]+)*")) {
            reason = "Input string non-numeric characters or is otherwise malformed";
        }

        if (reason == null) {
            return true;
        } else {
            throw new InvalidInputException(reason);
        }
    };
}
