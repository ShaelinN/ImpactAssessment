package impactassessment.numberrangesummarizer.element;
/**
 * Represents the concept of an element 
 * in the comma-separated string this program needs to return.
 * These elements can either take the form of a single number,
 * or a range of numbers written as "{start}-{end}"
 */
public interface NumberRangeSummarizerElement {
    @Override
    String toString();
}