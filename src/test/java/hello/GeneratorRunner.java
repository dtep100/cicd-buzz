package hello;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.PendingStepStrategy;
import org.jbehave.core.io.*;

import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.springframework.context.ApplicationContext;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.*;
import static org.jbehave.core.reporters.Format.CONSOLE;

public class GeneratorRunner extends JUnitStory {

    // Here we specify the configuration, starting from default MostUsefulConfiguration, and changing only what is needed
    @Override
    public Configuration configuration() {

        StoryLoader loader = null;//LoadFromURL();
        try {
            loader = new LoadFromRelativeFile(new URL("file:///Users/dtep100/cicd-buzz/src/test/stories/"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //loader.loadResourceAsText("file:///Users/dtep100/cicd-buzz/src/test/stories/hello/generator_runner.story");

        return new MostUsefulConfiguration()
                // where to find the stories
                .useStoryLoader(loader)
                // CONSOLE and TXT reporting
                .useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats().withFormats(Format.CONSOLE, Format.TXT));

        //        return Arrays.asList("com/ontestautomation/jbehave/demo/test_value.story");
    }

    // Here we specify the steps classes
    @Override
    public InjectableStepsFactory stepsFactory() {
        // varargs, can have more that one steps classes
        return new InstanceStepsFactory(configuration(), new GeneratorSteps());
    }
}


//public class EtsyDotComStories extends JUnitStories {
//
//    PendingStepStrategy pendingStepStrategy = new FailingUponPendingStep();
//    CrossReference crossReference = new CrossReference().withJsonOnly().withPendingStepStrategy(pendingStepStrategy)
//            .withOutputAfterEachStory(true).excludingStoriesWithNoExecutedScenarios(true);
//    ContextView contextView = new LocalFrameContextView().sized(640, 120);
//    SeleniumContext seleniumContext = new SeleniumContext();
//    SeleniumStepMonitor stepMonitor = new SeleniumStepMonitor(contextView, seleniumContext,
//            crossReference.getStepMonitor());
//    Format[] formats = new Format[] { new SeleniumContextOutput(seleniumContext), CONSOLE, WEB_DRIVER_HTML };
//    StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
//            .withCodeLocation(codeLocationFromClass(EtsyDotComStories.class)).withFailureTrace(true)
//            .withFailureTraceCompression(true).withDefaultFormats().withFormats(formats)
//            .withCrossReference(crossReference);
//
//
//
//
//}
