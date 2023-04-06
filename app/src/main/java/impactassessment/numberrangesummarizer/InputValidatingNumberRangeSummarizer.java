package impactassessment.numberrangesummarizer;

import java.util.Collection;
import java.util.TreeSet;

import impactassessment.numberrangesummarizer.exception.InvalidCollectionException;
import impactassessment.numberrangesummarizer.exception.InvalidInputStringException;
import impactassessment.numberrangesummarizer.inputvalidation.NRSInputValidator;

/**
 * Decorator for the SimpleNumberRangerSummarizer which adds input validation.
 * If invalid input is detected this class immediately throws an exception.
 * This allows the caller to implement behaviour to counter invalid inputs in any way they see fit.
 * 
 * It is not appropriate for this class to implement behaviour such as retries, default values, etc.
 * This class is meant to be a component of a delegator which has other components to handle such behabiour.
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
            // uses default validation behaviour for strings
            // uses custom validaton behaviour for collections
            @Override
            public boolean validateCollection(Collection<Integer> collection) {
                if (collection.getClass() != TreeSet.class) {
                    String reason = "Input collection is not of the expected class TreeSet";
                    throw new InvalidCollectionException(reason);
                }
                return true;
            }
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

    /**
     * check that the input string matches requirements.
     * Only if it does, then run the algorithm from BasicNumberRangeSummarizer.collect.
     * the checks ensure we will not be thrown exceptions from BasicNumberRangeSummarizer.collect.
     */
    @Override
    public Collection<Integer> collect(String input) {
        try {
            inputValidator.validateString(input);
        } catch (InvalidInputStringException e) {
            throw e;
        }
        return innerNumberRangerSummarizer.collect(input);
    }

    /**
     * check that the collection matches requirements.
     * Only if it does, then run the algorithm from BasicNumberRangeSummarizer.summarizeCollection.
     * the checks will ensure that we are using the TreeSet form as produced by the collect method
     */
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
