# ImpactAssessment
Take-home assessment from Impact.com

notes:
1. BasicNumberRangeSummarizer is the core of the application; it does the heavy lifting of the range-grouping operation, but assumes any input passed to it is perfect and valid
1. The NumberRangeSummarizerElement interface is created to represent what goes between commas in the final string. It has two implementations: SingleNumberElement, and MultiNumberRangeElement.
1. InputValidatingNumberRangeSummarizer decorates the BasicNumberRangeSummarizer with input validation behaviour, which it delegates to an NRSInputValidator.
1. The InvalidCollectionException and InvalidInputStringException created are meant to be thrown by NRSInputValidators when the input would break the BasicNumberRangeSummarizer if it were to reach there.
   1. The demo application in Main.java provides a simple example of how to react to these exceptions

* ./gradlew run to try out the demo application.
* ./gradlew test to confirm the unit tests work.
