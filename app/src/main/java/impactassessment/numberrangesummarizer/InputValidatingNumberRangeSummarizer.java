package impactassessment.numberrangesummarizer;

import java.util.Collection;

import impactassessment.numberrangesummarizer.exception.InvalidCollectionException;
import impactassessment.numberrangesummarizer.exception.InvalidInputStringException;
import impactassessment.numberrangesummarizer.inputvalidation.NRSInputValidator;

/**
 * Decorator for the SimpleNumberRangerSummarizer which adds input validation.
 * If invalid input is detected during collect phase, this class immediately throws an exception.
 * This allows the caller to implement behaviour to counter invalid inputs in any way they see fit
 */
public class InputValidatingNumberRangeSummarizer implements NumberRangeSummarizer {
    private final BasicNumberRangerSummarizer innerNumberRangerSummarizer = new BasicNumberRangerSummarizer();
    
    /*
    * By default, construct this summarizer with the default input validation rules,
    * (non-null, non-empty, string with only numeric characters and commas)
    */
    private NRSInputValidator inputValidator;

    /**
     * Use this constructor to use default validator.
     */
    public InputValidatingNumberRangeSummarizer() {
        inputValidator = new NRSInputValidator() {
            // uses default validation behaviour;
        };
    }

    /**
     * Allow caller to supply their own input validator during construction.
     * @param inputValidator an implementation of the NRSInputValidator 
     * with different rules for disqualifying an input than the default.
     */
    public InputValidatingNumberRangeSummarizer(NRSInputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    @Override
    public Collection<Integer> collect(String input) {
        try {
            inputValidator.validateString(input);
        } catch (InvalidInputStringException e) {
            throw e;
        }
        return innerNumberRangerSummarizer.collect(input);
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {
        try {
            inputValidator.validateCollection(input);
        } catch (InvalidCollectionException e) {
            throw e;
        }
        return innerNumberRangerSummarizer.summarizeCollection(input);
    }
}
