package impactassessment.numberrangesummarizer.element;

/**
 * Implementation of the NumberRangeSummarizerElement concept,
 * which applies to cases where there is more than one consecutive number
 * e.g. the "5,6,7" / "5-7" in the input "1,2,5,6,7,9"
 * 
 * For Java 14+, it would simpler to use a record instead of a class here.
 */
public class MultiNumberRangeElement implements NumberRangeSummarizerElement {
    int start;
    int end;

    public MultiNumberRangeElement(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        String result = new StringBuilder()
                            .append(Integer.toString(start))
                            .append("-")
                            .append(Integer.toString(end))
                            .toString();        
        return result;
    }
}