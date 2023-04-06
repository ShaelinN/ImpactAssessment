package impactassessment.numberrangesummarizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import impactassessment.numberrangesummarizer.element.MultiNumberRangeElement;
import impactassessment.numberrangesummarizer.element.NumberRangeSummarizerElement;
import impactassessment.numberrangesummarizer.element.SingleNumberElement;

public class BasicNumberRangerSummarizer implements NumberRangeSummarizer {
    /**
     * Split the input string based on commas
     * place elements in a TreeSet. 
     * so that the elements of the collection are both ordered and unique.
     * @param input a string that contains comma seperated integers.
     * @return a TreeSet Collection of the integers in the input
     */
    @Override
    public Collection<Integer> collect(String input) {
        Collection<Integer> result = new TreeSet<>();

        String[] split = input.split(",");
        for (String string : split) {
            int element = Integer.parseInt(string);
            result.add(element);
        }

        return result;
    }

    /**
     * Perform the grouping operation for ranges of consecutive numbers.
     * Convert the result to a String separated by commas.
     * @param input a Collection of Integers values.
     * @return String summary of the original elements in the correct format.
     */
    @Override
    public String summarizeCollection(Collection<Integer> input) {
        List<NumberRangeSummarizerElement> grouped = performGrouping(input);
        String result = grouped.stream()
                        .map(NumberRangeSummarizerElement::toString)
                        .collect(Collectors.joining(","));
        return result;
    }


    /**
     * Convert input to a List to simplify iteration.
     * Wrap the integers in the type NumberRangeSummarizerElement
     * which can represent either a single number or a range.
     * @param input Collection of Integers (ordered and unique)
     * @return List of NumberRangeSummarizerElements.
     */
    List<NumberRangeSummarizerElement> performGrouping(Collection<Integer> input) {
        List<Integer> asList = new ArrayList<>(input);

        List<NumberRangeSummarizerElement> result = new ArrayList<>();

        Integer previous = asList.get(0);
        List<Integer> consecutiveRange = new ArrayList<>();
        consecutiveRange.add(previous);

        /* 
        iterate starting from the second element, 
        since the first element has been loaded into the consecutive range list 
        and the 'previous' variable already 
        */ 
        for (int i = 1; i < asList.size(); i++) {
            int current = asList.get(i);

            if (current - previous != 1) { // non consecutive integers found
                NumberRangeSummarizerElement element = fromIntegerList(consecutiveRange);
                result.add(element);
                consecutiveRange = new ArrayList<>();
            }

            consecutiveRange.add(current);
            previous = current;
        }

        /*
        the last element in the input will be added to consecutiveList 
        but then this state of consecutiveList will not be checked and converted in the if block
        and thus will not be added to result.
        do that separately here:
        */ 
        NumberRangeSummarizerElement element = fromIntegerList(consecutiveRange);
        result.add(element);


        return result;
    }

    /**
     * Choose the correct type of NumberRangeSummarizerElement to use
     * based on how many consecutive numbers there were.
     * Use the type MultiNumberRangeElement for 2 or more consecutive numbers.
     * Use the type SingleNumberElement for 1 number.
     * @param consecutiveRange a list of consecutive numbers with at least 1 element.
     * @return a NumberRangeSummarizerElement which can be either a single number or a range.
     */
    NumberRangeSummarizerElement fromIntegerList(List<Integer> consecutiveRange) {
        NumberRangeSummarizerElement element;
                if (consecutiveRange.size() == 1) {
                    int value = consecutiveRange.get(0);
                    element = new SingleNumberElement(value);
                } else {
                    int start = consecutiveRange.get(0);
                    int end = consecutiveRange.get(consecutiveRange.size() - 1);
                    element = new MultiNumberRangeElement(start, end);
                }
                return element;
    }


}