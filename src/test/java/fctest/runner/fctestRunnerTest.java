package fctest.runner;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fctest.utility.GenerateBanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:cucumber",
        plugin = {"html:target/cucumber-html-report", "json:target/fctest-json-report.json" },
        glue = "fctest.stepDefinition"
//        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:output/ui_report.html"}
)


public class fctestRunnerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(fctestRunnerTest.class);
    public static String path = null;

    @BeforeClass
    public static void setup() throws MojoExecutionException {

        Path currentRelativePath = Paths.get("");
        path = currentRelativePath.toAbsolutePath().toString();
        LOGGER.info("Current relative path is: " + path);

        GenerateBanner banner = new GenerateBanner();
        banner.execute();

    }

    @AfterClass
    public static void teardown() {
    }

    @AfterClass
    public static void uploadscreenshots() {
        //No Need.
    }

    private static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

}
