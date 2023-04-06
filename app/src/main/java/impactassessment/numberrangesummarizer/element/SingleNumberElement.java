package impactassessment.numberrangesummarizer.element;

/**
 * Implementation of the NumberRangeSummarizerElement concept,
 * which applies to cases where there is one consecutive number only
 * e.g. the "5" in the input "1,2,5,8,9"
 * 
 * For Java 14+, it would simpler to use a record instead of a class here.
 */
public class SingleNumberElement implements NumberRangeSummarizerElement {
    int value;

    public SingleNumberElement(int value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != SingleNumberElement.class) {
            return false;
        } else {
            return this.value == ((SingleNumberElement)o).value;
        }
    }
}
