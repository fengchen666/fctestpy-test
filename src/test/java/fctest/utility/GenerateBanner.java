package fctest.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.apache.maven.plugin.MojoExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fctest.runner.fctestRunnerTest;

public class GenerateBanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateBanner.class);

    public void execute() throws MojoExecutionException {

        String filelocation = fctestRunnerTest.path + "/src/test/resources/files/banner.txt";

        File bannerFile = new File (filelocation);

        String bannerText;
        try {
            bannerText = new Scanner(bannerFile).useDelimiter("\\A").next();
            LOGGER.info(System.getProperty("line.separator") + bannerText);
        } catch (FileNotFoundException e) {
            LOGGER.info("No banner file found at " + bannerFile + ". banner-maven-plugin cannot display banner.");
        }
    }
}
