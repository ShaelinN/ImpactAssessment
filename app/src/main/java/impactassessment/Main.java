package impactassessment;

import java.util.Collection;

import impactassessment.numberrangesummarizer.InputValidatingNumberRangeSummarizer;

public class Main {
    public static void main(String[] args) {
        InputValidatingNumberRangeSummarizer ns = new InputValidatingNumberRangeSummarizer();
        String input = "1,1,1,3,6,7,8,12,13,14,15,21,22,23,24,31"; 
        Collection<Integer> col = ns.collect(input);
        String res = ns.summarizeCollection(col);
        System.out.println(res);
    }
}
